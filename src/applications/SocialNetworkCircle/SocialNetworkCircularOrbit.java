package applications.SocialNetworkCircle;

import circularOrbit.ConcreteCircularOrbit;
import otherDirectory.Painter;
import otherDirectory.Position;
import otherDirectory.Relation;
import track.CTFactory;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 *  社交网络系统的对象类
 */
public class SocialNetworkCircularOrbit extends ConcreteCircularOrbit<centralUser, Friends>
{

    /**
     *  返回两个物体的逻辑距离
     * @param e1 其中一个物体
     * @param e2 两个物体中另一个物体
     * @return 两个物体的逻辑距离
     */
    @Override
    public int getLogicalDistance(Friends e1, Friends e2) {
        //如果两个朋友是同一个，直接返回0
        if(e1.equalsObject(e2)) return 0;
        return super.getLogicalDistance(e1, e2);
    }


    /**
     *  运用广度优先搜索完成轨道物体与轨道的映射
     * @param sources 输入的源结点列表
     */
    private void getPhysicalMap(List<Friends> sources) {
        physicalObjectMap = new HashMap<>();
        Queue<Friends> queue = new LinkedBlockingQueue<>(); //广搜的队列
        List<Track> trackList = new ArrayList<>();
        CTFactory ctf = new CTFactory();
        trackList.add(ctf.create(1));
        Map<Friends,Integer> map = new HashMap<>();
        physicalObjectMap.put(trackList.get(0),new ArrayList<>());
        for(Friends friends:sources) {
            queue.offer(friends);
            map.put(friends, 1);
            physicalObjectMap.get(trackList.get(0)).add(friends);
        }
        int max = 1;
        while(!queue.isEmpty()) {
            Friends top = queue.poll();
            int Distance=map.get(top);
            List<Friends> neighborList = relationsOf2T.getOrDefault(top,new ArrayList<>())
                    .stream()
                    .map(Relation::getobj2).collect(Collectors.toList());
            for(Friends friend:neighborList) {
                if (!map.containsKey(friend)) {
                    map.put(friend, Distance + 1);
                    if (Distance == max) {
                        trackList.add(ctf.create(++max));
                        physicalObjectMap.put(trackList.get(max - 1), new ArrayList<>());
                    }
                    physicalObjectMap.get(trackList.get(Distance)).add(friend);
                    queue.offer(friend);
                }
            }
        }
    }

    /**
     *  改变轨道上朋友的物理位置
     */
    private void adjustFriendsLocation() {
        List<Friends> firstTrackFriends = relationsOfCT.stream()
                .map(x->x.getobj2()).collect(Collectors.toList());//得到一级好友的列表
        getPhysicalMap(firstTrackFriends); //对一级好友的映射进行创建
        CTFactory ctf = new CTFactory();
        Map<Friends,Integer> map = getSingleSourceDistances(firstTrackFriends);
        List<Track> trackList = physicalObjectMap.keySet().stream().sorted().collect(Collectors.toList());
        //将轨道物体的物理映射保存下来
        Map<Track,List<Friends>> copy = new HashMap<>();
        for(Track track:physicalObjectMap.keySet()) {
            copy.put(track,new ArrayList<>());
            for(Friends Friends:physicalObjectMap.get(track)) {
                copy.get(track).add(Friends);
            }
        }
        for(Track track:copy.keySet()) {
            Iterator<Friends> iterator = copy.get(track).iterator();
            while(iterator.hasNext()) {
                Friends friends = iterator.next();
                //如果该朋友已经不存在
                if(!map.containsKey(friends)) {
                    physicalObjectMap.get(track).remove(friends);
                } else {
                    int Dis = map.get(friends);
                    int i = physicalObjectMap.size()+1;
                    while(i<Dis) {
                        Track newTrack = ctf.create(i++);
                        physicalObjectMap.put(newTrack,new ArrayList<>());
                        addTrack(newTrack);
                    }
                    if(track.getRadius()!=(double)Dis) {
                        physicalObjectMap.get(track).remove(friends);
                        physicalObjectMap.get(ctf.create(Dis)).add(friends);
                    }
                }
            }
            getPhysicalMap(physicalObjectMap.get(track));
        }
    }

    /**
     *  移除朋友
     * @param Obj 指定轨道上物体，不为null
     * @return 成功返回true，否则返回false
     */
    @Override
    public boolean removePhysicalObject(Friends Obj) {
        boolean ans = super.removePhysicalObject(Obj);
        adjustFriendsLocation();
        return ans;
    }

    /**
     *  添加中心用户和轨道朋友之间的关系
     * @param co 中心用户
     * @param po 轨道朋友
     * @param weight 关系的权值
     * @return 添加成功返回true，否则返回false
     */
    public boolean addRelationOfCT(centralUser co, Friends po, double weight) {
        boolean ans = super.addRelationOfCP(co, po, weight);
        adjustFriendsLocation();
        return ans;
    }

    /**
     *  删除中心用户和轨道朋友之间的关系
     * @param co 中心用户
     * @param po 轨道朋友
     * @return 删除成功返回true，否则返回false
     */
    public boolean removeRelationOfCT(centralUser co, Friends po) {
        //由于只有1级好友与中心用户有之间社交关系，权值之间设为1即可
        boolean ans = super.removeRelationOfCP(co, po,1);
        adjustFriendsLocation();
        return ans;
    }

    /**
     *  添加两个朋友之间的关系
     * @param frd1 其中一个朋友
     * @param frd2 另外一个朋友
     * @param weight 关系的权值
     * @return 添加成功返回true，否则返回false
     */
    public boolean addRelationOf2T(Friends frd1, Friends frd2, double weight) {
        if(getTrackForPO(frd1).compareTo(getTrackForPO(frd2))>=0){
            return true;
        }
        boolean ans = super.addRelationOf2P(frd1, frd2, weight);
        adjustFriendsLocation();
        return ans;
    }

    /**
     *  添加两个朋友之间的关系
     * @param frd1 其中一个朋友
     * @param frd2 另外一个朋友
     * @return 删除成功返回true，否则返回false
     */
    public boolean removeRelationOf2T(Friends frd1, Friends frd2) {
        boolean ans = super.removeRelationOf2P(frd1, frd2);
        adjustFriendsLocation();
        return ans;
    }

    /**
     *  检测该社交网络系统的可用性
     * @return 检测可用返回true，否则返回false
     */
    @Override
    public boolean checkOribitAvailable() {
        List<Friends> firstTrackFriends = relationsOfCT.stream()
                .map(x->x.getobj2()).collect(Collectors.toList());
        Map<Friends,Integer> map = getSingleSourceDistances(firstTrackFriends);
        for(List<Friends> FriendsList:physicalObjectMap.values()) {
            for(Friends Friends:FriendsList)
                if(!map.containsKey(Friends)) {
                    return false;
                }
        }
        return true;
    }

    @Override
    public JPanel visualizeContentPanel() {
        return new JPanel() {
            //序列号（可省略）
            private static final long serialVersionUID = 1L;

            // 重写paint方法
            @Override
            public void paint(Graphics graphics) {
                final int pnLength = 800;
                final int padding = 100;
                int tsize  = physicalObjectMap.size();
                int[] trackRadius = new int[tsize];
                for(int i=0;i<tsize;i++) {
                    trackRadius[i] = (pnLength-padding)/(2*tsize)*(i+1);
                }
                super.paint(graphics);
                int centerX = pnLength/2,centerY = pnLength/2;
                int centerObjRadius = 20;
                Painter ctpainter = new Painter();
                ctpainter.setX(centerX); ctpainter.setY(centerY);
                ctpainter.setRadius(centerObjRadius);
                ctpainter.setOvalColor(Color.red);
                centralObject.draw(graphics,ctpainter);
                List<Track> trackList = physicalObjectMap.keySet().stream()
                        .sorted().collect(Collectors.toList());
                for(int id=1;id<=tsize;id++) {
                    Track track = trackList.get(id-1);
                    int r = trackRadius[id-1];
                    int width = 2*r,height = 2*r;
                    graphics.drawOval(centerX-r,centerY-r,
                            width,height);
                    List<Friends> Friends= physicalObjectMap.get(track);
                    int sz = physicalObjectMap.get(track).size();
                    for(int i=0;i<sz;i++) {
                        Painter painter = new Painter();
                        double theta = i*(2*Math.PI/(sz));
                        int cx = (int)(r*Math.cos(theta))+centerX,cy = (int)(r*Math.sin(theta))+centerY;
                        painter.setX(cx); painter.setY(cy);
                        painter.setRadius(5);
                        //设置字体
                        painter.setFont(new Font("TimesRoman",Font.PLAIN,10));
                        Friends.get(i).draw(graphics,painter);
                    }
                }
                //完成中心用户和一级好友之间的连线
                for(Relation<centralUser,Friends> rel:relationsOfCT) {
                    Position pos1 = rel.getobj1().getPos();
                    Position pos2 = rel.getobj2().getPos();
                    int x1 = (int)pos1.getX();
                    int x2 = (int)pos2.getX();
                    int y1 = (int) pos1.getY();
                    int y2 = (int) pos2.getY();
                    graphics.setColor(Color.red);
                    graphics.drawLine(x1,y1,x2,y2);
                    graphics.drawString(Double.toString(rel.getWeight()),(x1+x2)/2,(y1+y2)/2);
                    graphics.setColor(Color.black);
                }
                //完成朋友与朋友之间的关系连线
                for(Map.Entry<Friends,List<Relation<Friends,Friends>>> entry:relationsOf2T.entrySet()) {
                    for(Relation<Friends,Friends> rel:entry.getValue()) {
                        if(getTrackForPO(rel.getobj1()).compareTo(getTrackForPO(rel.getobj2()))>=0){
                            continue;
                        }
                        Position pos1 = rel.getobj1().getPos();
                        Position pos2 = rel.getobj2().getPos();
                        int x1 = (int)pos1.getX();
                        int x2 = (int)pos2.getX();
                        int y1 = (int) pos1.getY();
                        int y2 = (int) pos2.getY();
                        graphics.drawLine(x1,y1,x2,y2);
                        graphics.drawString(Double.toString(rel.getWeight()),(x1+x2)/2,(y1+y2)/2);
                    }
                }
            }
        };
    }

    /**
     *  根据输入的面板进行可视化
     * @param panel 要可视化的面板
     */
    @Override
    public void visualize(JPanel panel) {
        JPanel contentPanel = visualizeContentPanel();//将要显示的内容先放在一个面板上
        panel.removeAll();//把原来面板上的所有组件移除
        contentPanel.setPreferredSize(new Dimension(800,800));//给该面板分配首选空间大小
        panel.add(contentPanel);//把要显示的面板内容添加到该面板上
        panel.validate();//验证该面板和其组件是否有效
        panel.repaint();//重新绘制该组件
    }

    /**
     * 得到一级好友的列表
     * @return 一级好友的列表
     */
    public List<String> getFirstTrackFriends() {
        return relationsOfCT.stream()
                .map(rel->rel.getobj2().getName()).collect(Collectors.toList());
    }

    /**
     *  判断中心用户是否存在
     * @param name 中心用户的名字
     * @return 是则返回true 否则返回false
     */
    public boolean isCenterUser(String name) {
        return this.centralObject.getName().equals(name);
    }

    /**
     *  判断该朋友是否存在
     * @param name 朋友的名字
     * @return 是则返回true，否则返回false
     */
    public boolean isFriends(String name) {
        Iterator<Friends> ite = this.iterator();
        while(ite.hasNext()) {
            if(ite.next().getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  得到中心用户的对象
     * @return 中心用户
     */
    public centralUser getcentralUser() {
        return this.centralObject;
    }

    /**
     *  得到相关的朋友
     * @param Friends 作为源的朋友
     * @return 相关的朋友的列表
     */
    public List<String> getSurroundings(Friends Friends) {
        return relationsOf2T.get(Friends).stream()
                .map(rel->rel.getobj2().getName()).collect(Collectors.toList());
    }
}
