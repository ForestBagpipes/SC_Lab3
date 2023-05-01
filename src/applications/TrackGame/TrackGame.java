package applications.TrackGame;

import APIs.CircularOrbitAPI;
import APIs.Draw;
import applications.TrackGame.Strategies.*;
import applications.TrackGame.GUI.TrackGamePanel;
import applications.TrackGame.Strategies.Strategy;
import centralObject.CentralObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import APIs.Draw;
import track.CTFactory;
import track.ConcreteTrack;
import track.Track;
import track.TrackFactory;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 题目理解：
 * 在TrackGame中忽略绝对位置
 */
public class TrackGame implements Draw {
    public static final double[] TRACKRADIUS = {1,2,3,4,5,6,7,8};

    public List<Strategy> strategies = new ArrayList<>();
    public List<Runner> runnerList = new ArrayList<>();
    public List<Integer> gameTypes = new ArrayList<>();
    private List<TrackCircularOrbit> trackCircularOrbitList = new ArrayList<>();
    public  int numofTracks;
    private String file;

    private TrackGamePanel panel;
    private int nowDisplayIndex = 1;

    public TrackGame(String file) {
        this.file = file;
    }

    /**
     *  读取配置文件得到各运动员、轨道、比赛的信息
     */
    public void loadConfig() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader( new File(file)));
            String line = reader.readLine().trim();
            String[] splitStrs;
            while(line!=null) {
                if(line.length()==0) {
                    line = reader.readLine();
                    continue;
                }
                //各个信息的正则表达式
                String athleteRegExp = "Athlete\\s*::=\\s*<([a-zA-Z]+),(\\d+),([A-Z]{3}),(\\d+),(\\d{1,2}\\.\\d{2})>";
                String gameRegExp = "Game\\s*::=\\s*(100|200|400)";
                String tracksRegExp = "NumOfTracks\\s*::=\\s*([4-9]|10)";
                //生成匹配器
                Matcher athleteMatcher = Pattern.compile(athleteRegExp).matcher(line);
                Matcher gameMatcher = Pattern.compile(gameRegExp).matcher(line);
                Matcher tracksMatcher = Pattern.compile(tracksRegExp).matcher(line);
                if(athleteMatcher.find()) {
                    String obName = athleteMatcher.group(1);
                    int id = Integer.valueOf(athleteMatcher.group(2));
                    String country = athleteMatcher.group(3);
                    int age = Integer.valueOf(athleteMatcher.group(4));
                    double bestScore = Double.valueOf(athleteMatcher.group(5));
                    Runner runner = new Runner(obName,country,id,age,bestScore);
                    runnerList.add(runner);
                } else if(gameMatcher.find()) {
                    splitStrs = gameMatcher.group(1).split("\\|");
                    for(int i=0;i<splitStrs.length;i++) {
                        gameTypes.add(Integer.valueOf(splitStrs[i]));
                    }
                } else if(tracksMatcher.find()) {
                    numofTracks = Integer.valueOf(tracksMatcher.group(1));
                } else {
                    assert false : "匹配失败";
                }

                line = reader.readLine();
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
     * 根据比赛策略对运动员进行分组，分轨道
     * @param Strategies 比赛编排策略
     */
    public void divideIntoGroups(Strategy Strategies) {
        assert runnerList.isEmpty()!=true : "还没有进行配置读入";
        int tmp = 0;
        List<Track> trackList = new ArrayList<>();
        for(int i=0;i<numofTracks;i++) {
            trackList.add(new ConcreteTrack(TRACKRADIUS[i]));
        }
        List<TrackCircularOrbit> trackCircularOrbits = new ArrayList<>();
        if(Strategies.getStrategyName().equals("RelayStrategy")){
            List<Map<Track, List<Runner>>> runnerGroups = Strategies.Assign(trackList, runnerList);
            for (Map<Track, List<Runner>> group : runnerGroups) {
                TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();
                trackBuilder.createConcreteCircularOrbit(null);
                trackBuilder.buildTracks(trackList);
                //运用流操作进行重新分组
                Map<Track, List<Runner>> newGroup = group;
                System.out.println(newGroup);
                trackBuilder.buildObjects(null, newGroup);
                trackCircularOrbits.add((TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit());
            }
        }else {
            List<Map<Track, Runner>> runnerGroups = Strategies.assign(trackList, runnerList);
            for (Map<Track, Runner> group : runnerGroups) {
                TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();
                trackBuilder.createConcreteCircularOrbit(null);
                trackBuilder.buildTracks(trackList);
                //运用流操作进行重新分组
                Map<Track, List<Runner>> newGroup = group.keySet().stream()
                        .collect(Collectors.toMap(x -> x, x ->
                                Arrays.asList(group.get(x))));
                trackBuilder.buildObjects(null, newGroup);
                trackCircularOrbits.add((TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit());
            }
        }
        this.trackCircularOrbitList = trackCircularOrbits;
    }

    public List<Runner> getRunnerList() {
        return runnerList;
    }

    private Runner getPhysicalObjByObName(String obname, ConcreteCircularOrbit circularOrbit) {
        Iterator<Runner> ite = circularOrbit.iterator();
        List<Runner>  physicalObjs = new ArrayList<>();
        while(ite.hasNext()) {
            Runner tmp = ite.next();
            if(tmp.getName().equals(obname)) {
                physicalObjs.add(tmp);
                break;
            }
        }
        Collections.shuffle(physicalObjs);
        return physicalObjs.get(0);
    }
    /**
     * 向轨道系统中添加轨道
     */
    public void addTrack(double addRadius) {
        TrackFactory tf = new CTFactory();
        trackCircularOrbitList.get(nowDisplayIndex-1).addTrack(tf.create(addRadius));
        reLoadAll();
    }
    /**
     * 在轨道系统中移除轨道
     */
    public void removeTrack(double rmRadius) {
        TrackFactory tf = new CTFactory();
        trackCircularOrbitList.get(nowDisplayIndex-1).removeTrack(tf.create(rmRadius));
        reLoadAll();
    }
    /**
     * 向的轨道添加运动员
     * @param runner 要添加的运动员
     * @param tkRadius 要添加的轨道半径
     */
    public void addPhysicalObject(Runner runner,double tkRadius) {
        TrackFactory tf = new CTFactory();
        trackCircularOrbitList.get(nowDisplayIndex - 1).addPhysicalObject(runner, tf.create(tkRadius));
        reLoadAll();
    }
    /**
     * 在轨道系统中移除运动员runner
     */
    public void removePhysicalObject(String rmObName) {
        TrackCircularOrbit circularOrbit = trackCircularOrbitList.get(nowDisplayIndex-1);
        Runner runner = getPhysicalObjByObName(rmObName,circularOrbit);
        circularOrbit.removePhysicalObject(runner);
        reLoadAll();
    }

    public List<TrackCircularOrbit> getTrackCircularOrbitList() {
        return trackCircularOrbitList;
    }

    /**
     * 初始化
     */
    @Override
    public void initialize() {
        strategies.add(new RandomStrategy());
        strategies.add(new SortStrategy());
        strategies.add(new RelayStrategy());
        loadConfig();
    }

    /**
     *  绘制框架
     * @param frame 输入的要绘制的框架
     */
    @Override
    public void draw(JFrame frame) {
        panel = new TrackGamePanel(this);

        panel.initComboBoxStrategyItems(strategies.stream()
                .map(Strategy::getStrategyName).collect(Collectors.toList()));

        frame.getContentPane().add(panel);
    }

    /**
     * 重新加载所有组件，刷新
     */
    public void reLoadAll() {
        CircularOrbitAPI<CentralObject,Runner> apis = new CircularOrbitAPI<>();
        CircularOrbit<CentralObject,Runner> nowCircularOrbit = trackCircularOrbitList.get(nowDisplayIndex-1);
        boolean state = apis.checkOrbitAvailable(nowCircularOrbit);
        double entropy = apis.getObjectDistributionEntropy(nowCircularOrbit);
        List<String> trackList = nowCircularOrbit.getTrackRadiusList();
        Iterator<Runner> ite = ((TrackCircularOrbit) nowCircularOrbit).iterator();
        List<String> physicalObjs = new ArrayList<>();
        while(ite.hasNext()) {
            physicalObjs.add(ite.next().getName());
        }
        List<String> otherGroupObjs = new ArrayList<>();
        for(int i=1;i<=trackCircularOrbitList.size();i++) if(i!=nowDisplayIndex){
            Iterator<Runner> it = trackCircularOrbitList.get(i-1).iterator();
            while(it.hasNext()) {
                Runner r = it.next();
                otherGroupObjs.add(String.format("%d::%s",i,r.getName()));
            }
        }

        panel.reloadGameInfo(state,entropy,trackList,physicalObjs,otherGroupObjs);
        nowCircularOrbit.visualize(panel.getDrawPanel());
    }

    public void visualizeOrbit(Integer index) {
        nowDisplayIndex = index;
        reLoadAll();
    }
    public void selectedGameStrategies(String StrategiesName) {
        for(Strategy Strategies:strategies)
            if(Strategies.getStrategyName().equals(StrategiesName)) {
                divideIntoGroups(Strategies);
            }
        List<String> groups = new ArrayList<>();
        for(int i=1;i<=trackCircularOrbitList.size();i++) {
            groups.add(Integer.toString(i));
        }
        this.panel.initComboBoxGroupSelectItems(groups);
    }
    /**
     * 交换选手1和2的比赛组
     */
    public void exchangeGroup(String r1Name,String r2Name) {
        Runner ra = getPhysicalObjByObName(r1Name,trackCircularOrbitList.get(nowDisplayIndex-1));
        Runner rb = getPhysicalObjByObName(r2Name.split("::")[1],
                trackCircularOrbitList.get(Integer.valueOf(r2Name.split("::")[0])-1));
        TrackCircularOrbit tcoa = null,tcob = null;
        Track track1 = null,track2 = null;
        for(TrackCircularOrbit tco:trackCircularOrbitList) {
            Track tmp = tco.getTrackForPO(ra);
            if(tmp!=null) {
                tcoa = tco;
                track1 = tmp;
            }
            tmp = tco.getTrackForPO(rb);
            if(tmp!=null) {
                tcob = tco;
                track2 = tmp;
            }
        }
        assert tcoa!=null && tcob!=null: "有运动员不存在所有的比赛中";
        tcoa.removePhysicalObject(ra);
        tcob.removePhysicalObject(rb);
        tcoa.addPhysicalObject(rb,track1);
        tcob.addPhysicalObject(ra,track2);
        reLoadAll();
    }

    /**
     * 交换选手1和2的赛道
     * @param r1Name 选手1的名字，必须和选手2在同一个轨道里
     * @param r2Name 选手2的名字，必须和选手1在同一个轨道里
     */
    public void exchangeTrack(String r1Name,String r2Name) {
        TrackCircularOrbit circularOrbit  = trackCircularOrbitList.get(nowDisplayIndex-1);
        Runner runner1 = getPhysicalObjByObName(r1Name,circularOrbit);
        Runner runner2 = getPhysicalObjByObName(r2Name,circularOrbit);
        TrackCircularOrbit trackCircularOrbit = null;
        Track track1 = null,track2 = null;
        boolean flag = false;
        for(TrackCircularOrbit tco:trackCircularOrbitList) {
            if(tco.getTrackForPO(runner2)!=null && tco.getTrackForPO(runner1)!=null) {
                track1 = tco.getTrackForPO(runner1);
                track2 = tco.getTrackForPO(runner2);
                trackCircularOrbit = tco;
                flag = true;
                break;
            }
        }
        assert flag: "无法将两个运动员确定为同一轨道的两个物体";
        trackCircularOrbit.removePhysicalObject(runner1);
        trackCircularOrbit.removePhysicalObject(runner2);
        trackCircularOrbit.addPhysicalObject(runner1,track2);
        trackCircularOrbit.addPhysicalObject(runner2,track1);
        reLoadAll();
    }


}
