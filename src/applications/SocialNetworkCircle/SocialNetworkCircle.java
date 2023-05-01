package applications.SocialNetworkCircle;

import APIs.CircularOrbitAPI;
import APIs.Draw;
import applications.SocialNetworkCircle.GUI.SocialNetworkCirclePanel;
import centralObject.CommonObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import otherDirectory.Relation;
import track.CTFactory;
import track.Track;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *  社交好友网络分布 具体应用类，实现所要求的各种功能及可视化
 */
public class SocialNetworkCircle implements Draw {
    private String filename;
    private centralUser centralUser;
    private List<SocialShipLog> socialShipLogList = new ArrayList<>();
    private List<Friends> FriendsList = new ArrayList<>();
    private List<Track> trackList = new ArrayList<>();
    private SocialNetworkCircularOrbit circularOrbit ;
    private List<Relation<centralUser,Friends>> relOfCT = new ArrayList<>();
    private Map<Friends,List<Relation<Friends,Friends>>> relOf2T = new HashMap<>();
    private Map<Track, List<Friends>> physicalObjMap = new HashMap<>();
    private int maxTrackRadius = 0;

    SocialNetworkCirclePanel panel;

    public SocialNetworkCircle(String filename) {
        this.filename = filename;
    }

    private <E extends CommonObject>  int getNameIndex(List<E> objs, String name) {
        for(int i=0;i<objs.size();i++) {
            if(objs.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 通过正则表达式读取文件配置
     */
    public void loadConfig() {
        BufferedReader reader = null;
        centralUserFactory cuf = new centralUserFactory();
        friendsFactory ff = new friendsFactory();
        try {
            reader = new BufferedReader(new FileReader( new File(filename)));
            String line = reader.readLine().trim();
            String[] strs;
            while(line!=null) {
                if(line.length()==0) {
                    line = reader.readLine();
                    continue;
                }
                //创建正则表达式
                String centralUserRegExp = "CentralUser\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>";
                String FriendsRegExp = "Friend\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>";
                String socialTieRegExp = "SocialTie\\s*::=\\s*<([A-Za-z0-9]+),\\s*([A-Za-z0-9]+),\\s*(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?)>";
                // 根据正则表达式创建匹配
                Matcher centralUserMatcher = Pattern.compile(centralUserRegExp).matcher(line);
                Matcher FriendsMatcher = Pattern.compile(FriendsRegExp).matcher(line);
                Matcher socialTieMatcher = Pattern.compile(socialTieRegExp).matcher(line);
                //根据捕获读取配置
                if(centralUserMatcher.find()) {
                    String userName = centralUserMatcher.group(1);
                    int age = Integer.valueOf(centralUserMatcher.group(2));
                    String gender = centralUserMatcher.group(3);
                    centralUser = cuf.createCentralUser_2(userName,gender,age);
                } else if(FriendsMatcher.find()) {
                    String userName = FriendsMatcher.group(1);
                    int age = Integer.valueOf(FriendsMatcher.group(2));
                    String gender = FriendsMatcher.group(3);
                    //检测是否有同名的朋友
                    assert  FriendsList.stream()
                            .filter(x->x.getName().equals(userName))
                            .count()==0 : "存在同名的朋友";
                    FriendsList.add(ff.createFriends_2(userName,gender,age));
                } else if(socialTieMatcher.find()) {
                    assert centralUser!=null:"输入数据顺序错误，中心物体为Null";
                    socialShipLogList.add(new SocialShipLog(socialTieMatcher.group(1),socialTieMatcher.group(2),
                            Double.valueOf(socialTieMatcher.group(3))));
                } else {
                    assert false:"匹配失败";
                }

                line = reader.readLine();
            }
            // 根据社交关系日志添加关系
            for(SocialShipLog socialTie: socialShipLogList) {
                String userNameA = socialTie.getUserNameA();
                String userNameB = socialTie.getUserNameB();
                double socialDegree = Double.valueOf(socialTie.getSocialDegree());
                assert getNameIndex(Arrays.asList(centralUser),userNameA)!=-1
                        || getNameIndex(FriendsList,userNameA)!=-1: "系统中不包含"+userNameA;
                assert getNameIndex(Arrays.asList(centralUser),userNameB)!=-1
                        || getNameIndex(FriendsList,userNameB)!=-1: "系统中不包含"+userNameB;
                if(getNameIndex(Arrays.asList(centralUser),userNameA)!=-1) {
                    Friends Friends = FriendsList.get(getNameIndex(FriendsList,userNameB));
                    relOfCT.add(Relation.create_Relation1(centralUser,Friends,socialDegree));
                    continue;
                }
                if(getNameIndex(Arrays.asList(centralUser),userNameB)!=-1) {
                    continue;
                }

                Friends FriendsA = FriendsList.get(getNameIndex(FriendsList,userNameA));
                Friends FriendsB = FriendsList.get(getNameIndex(FriendsList,userNameB));
                if(!relOf2T.containsKey(FriendsA)) {
                    relOf2T.put(FriendsA,new ArrayList<>());
                }
                relOf2T.get(FriendsA).add(Relation.create_Relation2(FriendsA,FriendsB,socialDegree));
            }

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *   一级好友的映射的生成函数
     * @param sources 一级好友列表
     */
    private void getPhysicalMap1(List<Friends> sources) {
        Queue<Friends> queue = new LinkedBlockingQueue<>();
        Map<Friends,Integer> distantMap = new HashMap<>();
        for(Friends friends:sources) {
            queue.offer(friends);
            distantMap.put(friends, 1);
        }
        while(!queue.isEmpty()) {
            Friends top = queue.poll();
            CTFactory ctf = new CTFactory();
            int Dis=distantMap.get(top);
            List<Friends> neighborList = relOf2T.getOrDefault(top,new ArrayList<>())
                    .stream()
                    .map(Relation::getobj2).collect(Collectors.toList());
            for(Friends friends:neighborList) if(!distantMap.containsKey(friends)) {
                distantMap.put(friends,Dis+1);
                if(Dis==maxTrackRadius) {
                    trackList.add(ctf.create(++maxTrackRadius));
                    physicalObjMap.put(trackList.get(maxTrackRadius-1),new ArrayList<>());
                }
                physicalObjMap.get(trackList.get(Dis)).add(friends);
                queue.offer(friends);
            }
        }
    }

    /**
     *  得到i级好友的顺序关系和存在性
     * @param source 作为源节点的朋友
     * @param flag i级好友和物理顺序的关系映射
     * @param flagIndex 物理顺序下标
     */
    void getFlag(Friends source , Map<Friends,Integer> flag, int flagIndex) {
        Queue<Friends> queue = new LinkedBlockingQueue<>();
        queue.offer(source);
        flag.put(source,flagIndex);
        while(!queue.isEmpty()) {
            Friends top = queue.poll();
            List<Friends> neighborList = relOf2T.getOrDefault(top,new ArrayList<>())
                    .stream()
                    .map(Relation::getobj2).collect(Collectors.toList());
            for(Friends friends:neighborList) {
                if (!flag.containsKey(friends)) {
                    flag.put(friends, flagIndex);
                    queue.offer(friends);
                }
            }
        }
    }

    /**
     *  所有好友结点的物理映射关系的生成函数
     */
    private void getPhysicalMap() {
        Map<Friends,Integer> flag = new HashMap<>();
        int flagIndex = 0;
        CTFactory ctf = new CTFactory();
        for(Relation<centralUser,Friends> st:relOfCT) {
            getFlag(st.getobj2(),flag,flagIndex);
        }
        FriendsList.removeIf(x->!flag.containsKey(x));
        relOf2T.keySet().stream()
                .filter(x->!FriendsList.contains(x))
                .collect(Collectors.toList())
                .forEach(relOf2T::remove);
        trackList.add(ctf.create(1));
        ++maxTrackRadius;
        physicalObjMap.put(trackList.get(0),new ArrayList<>());
        List<Friends> firstTrackFriends = relOfCT.stream().map(x->x.getobj2()).collect(Collectors.toList());
        physicalObjMap.get(trackList.get(0))
                .addAll(firstTrackFriends);
        getPhysicalMap1(firstTrackFriends);

    }

    /**
     * 根据配置文件完成系统的初始化
     */
    public void initCircularOrbit() {
        SNCOrbitBuilder builder = new SNCOrbitBuilder();
        builder.createConcreteCircularOrbit(null);
        getPhysicalMap();
        builder.buildTracks(trackList);
        builder.buildObjects(centralUser,physicalObjMap);
        builder.buildRelation(relOfCT,relOf2T);
        circularOrbit = (SocialNetworkCircularOrbit) builder.getConcreteCircularOrbit();
    }

    public SocialNetworkCircularOrbit getCircularOrbit() {
        return circularOrbit;
    }


    /**
     *   社交关系日志类
     */
    private class SocialShipLog {
        String userNameA,userNameB;
        double socialDegree; //相当于关系的权，社交距离

        public SocialShipLog(String userNameA, String userNameB, double socialDegree) {
            this.userNameA = userNameA;
            this.userNameB = userNameB;
            this.socialDegree = socialDegree;
        }

        public String getUserNameA() {
            return userNameA;
        }

        public String getUserNameB() {
            return userNameB;
        }

        public double getSocialDegree() {
            return socialDegree;
        }
    }
    
    /**
     *  根据名字得到对应的好友
     */
    private Friends getPhysicalObjByname(Predicate<Friends> predicate, ConcreteCircularOrbit circularOrbit) {
        Iterator<Friends> ite = circularOrbit.iterator();
        List<Friends>  physicalObjs = new ArrayList<>();
        while(ite.hasNext()) {
            Friends tmp = ite.next();
            physicalObjs.add(tmp);
        }
        Collections.shuffle(physicalObjs);
        return physicalObjs.stream().filter(predicate)
                .collect(Collectors.toList()).get(0);
    }

    /**
     * 向轨道系统中添加轨道
     */
    public void addTrack(double Radius) {
        CTFactory ctf = new CTFactory();
        circularOrbit.addTrack(ctf.create(Radius));
        reLoadAll();
    }
    /**
     * 在轨道系统中移除指定轨道
     */
    public void removeTrack(double Radius) {
        CTFactory ctf = new CTFactory();
        circularOrbit.removeTrack(ctf.create(Radius));
        reLoadAll();
    }
    /**
     * 在系统的轨道上添加朋友
     */
    public void addPhysicalObject(Friends Friends,double Radius) {
        CTFactory ctf = new CTFactory();
        circularOrbit.addPhysicalObject(Friends, ctf.create(Radius));
        reLoadAll();
    }
    /**
     * 在轨道系统中移除朋友
     */
    public void removePhysicalObject(String rmname) {
        Friends Friends = getPhysicalObjByname(x->x.getName().equals(rmname)
                ,circularOrbit);
        circularOrbit.removePhysicalObject(Friends);
        reLoadAll();
    }

    @Override
    public void initialize() {
        loadConfig();
        initCircularOrbit();
    }

    /**
     *  绘制框架
     * @param frame 输入的要绘制的框架
     */
    @Override
    public void draw(JFrame frame) {
        panel = new SocialNetworkCirclePanel(this);
        frame.getContentPane().add(panel);
        circularOrbit.visualize(panel.getDrawPanel());
        reLoadAll();
    }

    /**
     *  刷新所有组件的信息
     */
    public void reLoadAll() {
        CircularOrbitAPI<centralUser,Friends> apis = new CircularOrbitAPI<>();
        CircularOrbit<centralUser,Friends> nowCircularOrbit = circularOrbit;
        boolean state = apis.checkOrbitAvailable(nowCircularOrbit);
        double entropy = apis.getObjectDistributionEntropy(nowCircularOrbit);
        List<String> trackList = nowCircularOrbit.getTrackRadiusList();
        Iterator<Friends> ite = ((SocialNetworkCircularOrbit) nowCircularOrbit).iterator();
        List<String> physicalObjs = new ArrayList<>();
        List<String> relObjects = new ArrayList<>();
        while(ite.hasNext()) {
            String name = ite.next().getName();
            physicalObjs.add(name);
            relObjects.add(name);
        }
        relObjects.add(circularOrbit.getcentralUser().getName());

        panel.reloadGameInfo(state,entropy,trackList,physicalObjs,relObjects,circularOrbit.getFirstTrackFriends());
        nowCircularOrbit.visualize(panel.getDrawPanel());
    }


    /**
     *  添加关系
     * @param nameU 其中一个好友的名字
     * @param nameV 另一个朋友的名字
     * @param Degree 关系的权值
     */
    public void addRelation(String nameU,String nameV,double Degree) {
        centralUser centralUser;
        Friends FriendsU,FriendsV;
        if(circularOrbit.isCenterUser(nameU)) {
            centralUser = circularOrbit.getcentralUser();
            FriendsU = getPhysicalObjByname(x->x.getName().equals(nameV),circularOrbit);
            circularOrbit.addRelationOfCT(centralUser,FriendsU,Degree);
            reLoadAll();
            return ;
        }

        if(circularOrbit.isCenterUser(nameV)) {
            centralUser = circularOrbit.getcentralUser();
            FriendsU = getPhysicalObjByname(x->x.getName().equals(nameU),circularOrbit);
            circularOrbit.addRelationOfCT(centralUser,FriendsU,Degree);
            reLoadAll();
            return ;
        }
        FriendsU = getPhysicalObjByname(x->x.getName().equals(nameU),circularOrbit);
        FriendsV = getPhysicalObjByname(x->x.getName().equals(nameV),circularOrbit);
//        if(circularOrbit.getTrackForPO(FriendsU).compareTo(circularOrbit.getTrackForPO(FriendsV))>=0){
//            return ;
//        }
        circularOrbit.addRelationOf2T(FriendsU,FriendsV,Degree);
        circularOrbit.addRelationOf2T(FriendsV,FriendsU,Degree);

        reLoadAll();
    }

    /**
     *  删除社交关系
     * @param nameU 其中一个好友的名字
     * @param nameV 另一个朋友的名字
     */
    public void removeRelation(String nameU,String nameV) {
        centralUser centralUser;
        Friends FriendsU,FriendsV;
        if(circularOrbit.isCenterUser(nameU)) {
            centralUser = circularOrbit.getcentralUser();
            FriendsU = getPhysicalObjByname(x->x.getName().equals(nameV),circularOrbit);
            circularOrbit.removeRelationOfCT(centralUser,FriendsU);
            reLoadAll();
            return ;
        }
        if(circularOrbit.isCenterUser(nameV)) {
            centralUser = circularOrbit.getcentralUser();
            FriendsU = getPhysicalObjByname(x->x.getName().equals(nameU),circularOrbit);
            circularOrbit.removeRelationOfCT(centralUser,FriendsU);
            reLoadAll();
            return ;
        }
        FriendsU = getPhysicalObjByname(x->x.getName().equals(nameU),circularOrbit);
        FriendsV = getPhysicalObjByname(x->x.getName().equals(nameV),circularOrbit);
        circularOrbit.removeRelationOf2T(FriendsU,FriendsV);
        circularOrbit.removeRelationOf2T(FriendsV,FriendsU);

        reLoadAll();
    }

    /**
     *  得到两个好友之间的逻辑距离
     * @param nameU 其中一个好友的名字
     * @param nameV 另一个朋友的名字
     * @return 逻辑距离
     */
    public int getLogicalDistance(String nameU,String nameV) {
        Friends fr1 = getPhysicalObjByname(x->x.getName().equals(nameU),circularOrbit);
        Friends fr2 = getPhysicalObjByname(x->x.getName().equals(nameV),circularOrbit);
        return circularOrbit.getLogicalDistance(fr1,fr2);
    }
    public List<String> getSurroudings(String name) {
        try {
            if (circularOrbit.isCenterUser(name))
                return circularOrbit.getFirstTrackFriends();
            Friends fr = getPhysicalObjByname(x -> x.getName().equals(name), circularOrbit);
            return circularOrbit.getSurroundings(fr);
        }catch(Exception e) {
            return new ArrayList<>();
        }
    }
}
