package circularOrbit;

import otherDirectory.Difference;
import otherDirectory.Position;
import otherDirectory.Relation;
import physicalObject.PhysicalObject;
import track.Track;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * 轨道系统的具体实现类
 *
 * @param <L> 中心物体类
 * @param <E> 轨道物体类
 */
public class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {
    protected L centralObject = null; //中心物体
    protected Map<Track, List<E>> physicalObjectMap = new HashMap<>(); //轨道与轨道物体的映射
    protected List<Relation<L, E>> relationsOfCT = new ArrayList<>(); //中心物体和轨道物体的关系列表
    protected Map<E, List<Relation<E, E>>> relationsOf2T = new HashMap<>(); //两个轨道物体间的关系列表

    // Abstraction function:
    //   TODO  centralObject：中心物体  physicalObjectMap：轨道与轨道物体的映射
    //         relationsOfCT: 中心物体和轨道物体的关系列表
    //         relationsOf2T: 两个轨道物体间的关系列表(键代表物体，值代表以该物体为起始点的关系)
    // Representation invariant:
    //   TODO  physicalObjectMap、relationsOfCT、relationsOf2T不为null
    // Safety from rep exposure:
    //   TODO 用protected限定physicalObjectMap、relationsOfCT、relationsOf2T，防止表示泄露

    //checkRep
    private void checkRep() {
        assert relationsOfCT != null;
        assert relationsOf2T != null;
        assert physicalObjectMap != null;
    }

    @Override
    public L addCentralObject(L centralObject) {
        if (centralObject == null) {
            this.centralObject = centralObject;
            checkRep();
            return null;
        }
        L obj = this.centralObject;
        this.centralObject = centralObject;
        checkRep();
        return obj;
    }

    @Override
    public boolean addTrack(Track track) {
        if (physicalObjectMap.containsKey(track)) {
            checkRep();
            return false;
        }
        physicalObjectMap.put(track, new ArrayList<>());
        checkRep();
        return true;
    }

    @Override
    public boolean removeTrack(Track track) {
        List<E> objList = new ArrayList<>();//轨道上的物体的列表
        //如果轨道不存在，删除失败
        if (!physicalObjectMap.containsKey(track)) {
            checkRep();
            return false;
        }
        //删除轨道上的所有物体
        for (E obj : physicalObjectMap.get(track)) {
            objList.add(obj);
        }
        for (E obj : objList) {
            removePhysicalObject(obj);
        }
        physicalObjectMap.remove(track);
        checkRep();
        return true;
    }

    @Override
    public boolean addPhysicalObject(E object, Track track) {
        if (!physicalObjectMap.containsKey(track)) {
            System.out.println("不存在该轨道");
            checkRep();
            return false;
        }
        Set<Map.Entry<Track, List<E>>> entrySet = physicalObjectMap.entrySet();
        Iterator<Map.Entry<Track, List<E>>> a = entrySet.iterator();
        while (a.hasNext()) {
            Map.Entry<Track, List<E>> it = a.next();
            if (it.getValue().contains(object)) {
                System.out.println("其他轨道中包含了该物体");
                checkRep();
                return false;
            }
        }
        //该轨道中已经包括了该物体
        if (physicalObjectMap.get(track).contains(object)) {
            checkRep();
            return false;
        }
        physicalObjectMap.get(track).add(object);
        if (!relationsOf2T.containsKey(object)) {
            relationsOf2T.put(object, new ArrayList<>());
        }
        checkRep();
        return true;

    }

    @Override
    public boolean removePhysicalObject(E object) {
        boolean flag = false;
        Set<Map.Entry<Track, List<E>>> entrySet = physicalObjectMap.entrySet();
        Iterator<Map.Entry<Track, List<E>>> a = entrySet.iterator();
        while (a.hasNext()) {
            Map.Entry<Track, List<E>> it = a.next();
            if (it.getValue().contains(object)) {
                it.getValue().remove(object);
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("找不到目标物体");
            checkRep();
            return flag;
        }
        //在中心物体与轨道物体关系列表中删去与object有关的关系
        relationsOfCT.removeIf((x) -> (x.getobj1() == object || x.getobj2() == object));
        if (relationsOf2T.keySet().contains(object)) {
            relationsOf2T.remove(object);
        }
        for (E obj : relationsOf2T.keySet()) {
            relationsOf2T.get(obj).removeIf((x) -> (x.getobj1() == object || x.getobj2() == object));
        }
        checkRep();
        return flag;
    }

    @Override
    public boolean addRelationOfCP(L centralObject, E physicalObject, double weight) {
        if (centralObject == null) {
            return false;
        }
        if (weight <= 0) {
            return false;
        }
        checkRep();
        return relationsOfCT.add(new Relation<>(centralObject, physicalObject, weight));
    }

    /**
     * 去除从中心物体到轨道物体的关系
     *
     * @param centralObject  中心物体
     * @param physicalObject 轨道物体
     * @param weight         关系的权值,必须大于0
     * @return true如果删除成功，否则返回false
     */
    public boolean removeRelationOfCP(L centralObject, E physicalObject, double weight) {
        if (centralObject == null) {
            return false;
        }
        if (weight <= 0) {
            return false;
        }
        checkRep();
        return relationsOfCT.removeIf(relation -> relation.getobj2().equals(physicalObject));
    }

    @Override
    public boolean addRelationOf2P(E physicalObj1, E physicalObj2, double weight) {
        //关系图中不包含physicalObj1
        if (!relationsOf2T.containsKey(physicalObj1)) {
            relationsOf2T.put(physicalObj1, new ArrayList<>());
        }
        //关系图已经有了一样的有向边
        if (relationsOf2T.get(physicalObj1).contains(new Relation<>(physicalObj1, physicalObj2, weight))) {
            checkRep();
            return false;
        }
        relationsOf2T.get(physicalObj1).add(new Relation<>(physicalObj1, physicalObj2, weight));
        checkRep();
        return true;
    }

    /**
     * 移除两个轨道物体间的一个关系
     *
     * @param physicalObj1 起始的轨道物体
     * @param physicalObj2 终结的轨道物体
     * @return true如果删除成功，否则返回false
     */
    public boolean removeRelationOf2P(E physicalObj1, E physicalObj2) {
        boolean flag = false;
        Set<Map.Entry<E, List<Relation<E, E>>>> entrySet = relationsOf2T.entrySet();
        Iterator<Map.Entry<E, List<Relation<E, E>>>> a = entrySet.iterator();
        while (a.hasNext()) {
            Map.Entry<E, List<Relation<E, E>>> it = a.next();
            if (it.getKey().equals(physicalObj1)) {
                for (Relation<E, E> rel : it.getValue()) {
                    if (rel.getobj2().equals(physicalObj2)) {
                        flag = true;
                    }
                }
            }
        }
        if (!flag) {
            System.out.println("未找到对应关系");
            checkRep();
            return false;
        }
        checkRep();
        return relationsOf2T.get(physicalObj1).removeIf(rel -> rel.getobj2().equals(physicalObj2));
    }

    @Override
    public boolean transit(E object1, E object2, Track track) {
        boolean flag1, flag2;
        flag1 = removePhysicalObject(object1);
        flag2 = addPhysicalObject(object2, track);
        return flag1 && flag2;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator(physicalObjectMap);
    }

    @Override
    public void move(E oldObject, E newObject) {
        boolean flag = false;
        Set<Map.Entry<Track, List<E>>> entrySet = physicalObjectMap.entrySet();
        Iterator<Map.Entry<Track, List<E>>> a = entrySet.iterator();
        while (a.hasNext()) {
            Map.Entry<Track, List<E>> it = a.next();
            if (it.getValue().contains(oldObject)) {
                it.getValue().remove(oldObject);
                it.getValue().add(newObject);
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("没有找到要移动的物体");
        }
    }

    @Override
    public double getObjectDistributionEntropy() {
        //得到总的轨道物体数
        double allNum = physicalObjectMap.values().stream()
                .mapToDouble(List::size).sum();
        //得到熵值
        double ans = physicalObjectMap.values().stream()
                .mapToDouble(List::size)
                .reduce(0.0, (res, item) -> {
                    double pi = item / allNum;
                    if (item > 0) {
                        res = res - pi * Math.log(pi) / Math.log(2.0);
                    }
                    return res;
                });
        return ans;
    }

    /**
     * 得到点和源点距离的映射
     *
     * @param sources 作为源点的轨道物体列表
     * @return 点和最短源点距离的映射
     */
    protected Map<E, Integer> getSingleSourceDistances(List<E> sources) {
        //确定各个源点在系统关系图中存在
        for (E source : sources) {
            if (!relationsOf2T.keySet().contains(source)) {
                System.out.println("图中不包含" + source.toString());
                return null;
            }
        }
        Queue<E> queue = new LinkedBlockingQueue<>(); //创建队列
        Map<E, Integer> disMap = new HashMap<>(); //保存点和源点距离的映射
        for (E source : sources) {
            queue.offer(source);   //入队
            disMap.put(source, 0);
        }
        while (!queue.isEmpty()) {
            E top = queue.poll(); //队列顶点出队
            int nowDis = disMap.get(top);
            //得到顶点的邻接点的列表
            List<E> neighborList = relationsOf2T.getOrDefault(top, new ArrayList<>())
                    .stream()
                    .map(Relation::getobj2).collect(Collectors.toList());
            for (E ps : neighborList) {
                if (!disMap.containsKey(ps)) {
                    disMap.put(ps, nowDis + 1);
                    queue.offer(ps);
                }
            }
        }
        return disMap;
    }

    @Override
    public int getLogicalDistance(E e1, E e2) {
        Map<E, Integer> distanceMap = getSingleSourceDistances(Arrays.asList(e1)); //各个点与其到e1的最短单源距离的映射
        //e1点与e2点之间没有关系
        if (!distanceMap.keySet().contains(e2)) {
            return Integer.MAX_VALUE;
        }
        return distanceMap.get(e2);
    }

    @Override
    public <E extends PhysicalObject> double getPhysicalDistance(E e1, E e2) {
        Position pos1 = e1.getPos(), pos2 = e2.getPos();
        double x1 = pos1.getX(),
                y1 = pos1.getY();
        double x2 = pos2.getX(),
                y2 = pos2.getY();
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    @Override
    public Difference getDifference(CircularOrbit<L, E> that) {
        List<Track> thisTracks = this.getSortedTrack(); //本系统的轨道列表
        List<Track> thatTracks = that.getSortedTrack(); //另一个系统的轨道列表
        Difference res = new Difference(thisTracks.size() - thatTracks.size());

        //保证两个列表的长度相同
        if (thisTracks.size() < thatTracks.size()) {
            for (int i = 0; i < thatTracks.size() - thisTracks.size(); i++) {
                thisTracks.add(null);
            }
        }
        if (thatTracks.size() < thisTracks.size()) {
            for (int i = 0; i < thatTracks.size() - thisTracks.size(); i++) {
                thatTracks.add(null);
            }
        }
        //比较生成每个轨道的差异对象，并将其加入到总的系统差异中去
        for (int i = 0; i < thisTracks.size(); i++) {
            Track thisTrack = thisTracks.get(i);
            Track thatTrack = thatTracks.get(i);
            res.addTrackSet(this.getPhysicalObjectsOnTrack(thisTrack),
                    that.getPhysicalObjectsOnTrack(thatTrack));
        }
        return res;
    }

    @Override
    public List<String> getTrackRadiusList() {
        return physicalObjectMap.keySet().stream()     //转换为轨道的流
                .map(x -> Double.toString(x.getRadius()))//给轨道流按半径排序
                .sorted().collect(Collectors.toList());//运用方法体将流上的元素收集为列表
    }

    @Override
    public List<Track> getSortedTrack() {
        return physicalObjectMap.keySet().stream()//转换为轨道的流
                .sorted()                         //给轨道流按照编号排序
                .collect(Collectors.toList());    //运用方法体将流上的元素收集为列表
    }

    @Override
    public List<E> getPhysicalObjectsOnTrack(Track tk) {
        if (!physicalObjectMap.containsKey(tk)) {
            return new ArrayList<>();
        }
        //得到一个不可修改的轨道物体列表
        return Collections.unmodifiableList(physicalObjectMap.get(tk));
    }

    /**
     * 返回obj所在的轨道Track
     *
     * @param obj 轨道物体
     * @return obj所在的轨道，如果不在该轨道系统中则返回null
     */
    public Track getTrackForPO(PhysicalObject obj) {
        for (Track track : physicalObjectMap.keySet()) {
            if (physicalObjectMap.get(track).contains(obj)) {
                checkRep();
                return track;
            }
        }
        checkRep();
        return null;
    }

    @Override
    public boolean checkOribitAvailable() {
        checkRep();
        return true;
    }

    private class MyIterator<E extends Comparable> implements Iterator<E> {
        private List<E> physicalList;//轨道物体列表
        private int ite;//保存遍历下标
        private int size;//轨道物体的数量

        public MyIterator(Map<Track, List<E>> physicalMap) {
            ite = 0;//下标初始化为0
            size = physicalMap.values().stream()
                    .mapToInt(List::size).sum();
            physicalList = physicalMap.keySet().stream()
                    .sorted()
                    .map(physicalMap::get)
                    .reduce(new ArrayList<>(), (acc, item) -> {
                        Collections.sort(item);
                        acc.addAll(item);
                        return acc;
                    });
        }

        @Override
        public boolean hasNext() {
            return ite < size;
        }

        @Override
        public E next() {
            return physicalList.get(ite++);
        }
    }

}
