package applications.AtomStructure;

import APIs.CircularOrbitAPI;
import APIs.Draw;
import applications.AtomStructure.GUI.AtomstructurePanel;
import applications.AtomStructure.memory.careTaker;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import track.CTFactory;
import track.Track;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AtomStructure implements Draw {
    private String filename;

    //    读入的文件配置
    String elementName;
    int numofTracks;
    List<Integer> trackIdList = new ArrayList<>();
    List<Integer> electronNumList = new ArrayList<>();
    Map<Track,List<electron>> physicalObjMap;

    //    生成的构造配置
    List<Track> trackList = new ArrayList<>();
    AtomCircularOrbit atomCircularOrbit;

    AtomstructurePanel panel;
    careTaker ct = new careTaker();


    public AtomStructure(String filename) {
        this.filename = filename;
    }

    /**
     *  根据读入的配置得到系统的各种信息
     */
    public void loadConfig() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader( new File(filename)));
            String line = reader.readLine().trim();
            String[] splitStrs;
            while(line!=null) {
                if(line.length()==0) {
                    line = reader.readLine();
                    continue;
                }
                //根据文件读入输入正则表达式
                String elementNameRegExp = "ElementName\\s*::=\\s*([A-Z]{1}[a-z]{0,1})";
                String tracksRegExp = "NumberOfTracks\\s*::=\\s*(\\d+)";
                String electronRegExp = "NumberOfElectron\\s*::=\\s*((?:(?:\\d+\\/\\d+)|;)+)";
                //根据正则表达式进行匹配
                Matcher electronMatcher = Pattern.compile(electronRegExp).matcher(line);
                Matcher elementNameMatcher = Pattern.compile(elementNameRegExp).matcher(line);
                Matcher tracksMatcher = Pattern.compile(tracksRegExp).matcher(line);
                //匹配成功
                if(electronMatcher.find()) {
                    splitStrs = electronMatcher.group(1).trim().split(";");
                    System.out.println(666);
                    for(int i=0;i<splitStrs.length;i++) {
                        int trackId = Integer.valueOf(splitStrs[i].split("/")[0]);
                        int electronNum = Integer.valueOf(splitStrs[i].split("/")[1]);
                        trackIdList.add(trackId);
                        electronNumList.add(electronNum);
                    }
                } else if(elementNameMatcher.find()) {
                    elementName = elementNameMatcher.group(1).trim();
                } else if(tracksMatcher.find()) {
                    numofTracks = Integer.valueOf(tracksMatcher.group(1).trim());
                } else {
                    //匹配失败
                    assert false:"匹配失败";
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
     * 读取文件配置初始化轨道系统
     */
    public void initCircularOrbit() {
        AtomCircularOrbitBuilder builder = new AtomCircularOrbitBuilder();
        builder.createConcreteCircularOrbit(elementName);
        for(int i=0;i<numofTracks;i++) {
            CTFactory ctf = new CTFactory();
            trackList.add(ctf.create(i+1));
        }
        builder.buildTracks(trackList);
        physicalObjMap = new HashMap<>();

        for(int i=0;i<trackIdList.size();i++) {
            Track track = trackList.get(trackIdList.get(i)-1);
            int Enum = electronNumList.get(i);
            if(!physicalObjMap.containsKey(track)) {
                physicalObjMap.put(track,new ArrayList<>());
            }
            for(int j=0;j<Enum;j++) {
                electronFactory ef = new electronFactory();
                physicalObjMap.get(track).add(ef.createElectron_1());
            }
        }
        nucleusFactory nf = new nucleusFactory();
        builder.buildObjects(nf.createNucleus(),physicalObjMap);
        atomCircularOrbit = (AtomCircularOrbit) builder.getConcreteCircularOrbit();
    }

    //得到轨道物体
    private electron getPhysicalObj(Predicate<electron> predicate, ConcreteCircularOrbit circularOrbit) {
        Iterator<electron> it = circularOrbit.iterator();
        List<electron>  physicalObjs = new ArrayList<>();
        while(it.hasNext()) {
            electron tmp = it.next();
            physicalObjs.add(tmp);
        }
        Collections.shuffle(physicalObjs);//打乱轨道物体
        return physicalObjs.stream().filter(predicate)
                .collect(Collectors.toList()).get(0);
    }

    /**
     * 向轨道系统中添加轨道
     */
    public void addTrack(double Radius) {
        CTFactory ctf = new CTFactory();
        atomCircularOrbit.addTrack(ctf.create(Radius));
        reLoadAll();
    }
    /**
     * 在轨道系统中移除轨道
     */
    public void removeTrack(double Radius) {
        CTFactory ctf = new CTFactory();
        atomCircularOrbit.removeTrack(ctf.create(Radius));
        reLoadAll();
    }
    /**
     * 向轨道系统的轨道添加电子
     */
    public void addPhysicalObject(electron electron,double tkRadius) {
        CTFactory ctf = new CTFactory();
        atomCircularOrbit.addPhysicalObject(electron, ctf.create(tkRadius));
        reLoadAll();
    }

    /**
     * 在轨道系统中移除电子
     */
    public void removePhysicalObject(double Radius) {
        CTFactory ctf = new CTFactory();
        Track tk = ctf.create(Radius);
        electron electron = getPhysicalObj(x->atomCircularOrbit.getTrackForPO(x).equals(tk)
                ,atomCircularOrbit);
        atomCircularOrbit.removePhysicalObject(electron);
        reLoadAll();
    }

    /**
     *  初始化系统
     */
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
        panel = new AtomstructurePanel(this);
        frame.getContentPane().add(panel);
        atomCircularOrbit.visualize(panel.getDrawPanel());
        reLoadAll();
    }

    /**
     *  刷新系统及GUI
     */
    public void reLoadAll() {
        CircularOrbitAPI<nucleus,electron> apis = new CircularOrbitAPI<>();
        CircularOrbit<nucleus, electron> nowCircularOrbit = atomCircularOrbit;
        boolean state = apis.checkOrbitAvailable(nowCircularOrbit);
        double entropy = apis.getObjectDistributionEntropy(nowCircularOrbit);
        List<String> trackList = nowCircularOrbit.getTrackRadiusList();
        Iterator<electron> ite = ((AtomCircularOrbit) nowCircularOrbit).iterator();
        List<String> physicalObjs = new ArrayList<>();
        while(ite.hasNext()) {
            physicalObjs.add(ite.next().getName());
        }
        panel.reloadGameInfo(state,entropy,trackList,physicalObjs,ct.getAllHistory());
        nowCircularOrbit.visualize(panel.getDrawPanel());
    }

    /**
     *  电子跃迁
     * @param source 跃迁的电子原来的轨道
     * @param target 跃迁的目标轨道
     */
    public void electronTransit(double source,double target) {
        CTFactory ctf = new CTFactory();
        Track sourceTrack = ctf.create(source);
        Track targetTrack = ctf.create(target);

        electron electron = getPhysicalObj(x->atomCircularOrbit.getTrackForPO(x).equals(sourceTrack)
                ,atomCircularOrbit);
        atomCircularOrbit.removePhysicalObject(electron);
        atomCircularOrbit.addPhysicalObject(electron,targetTrack);
        ct.addMemory(electron,sourceTrack,targetTrack);
        reLoadAll();
    }

    /**
     *  回退历史
     * @param index 回退历史的步数
     */
    public void rebackHistory(int index) {
        atomCircularOrbit.reback(ct.rebackMemory(index));
        reLoadAll();
    }
}
