package circularOrbit;

import centralObject.CentralObject;
import physicalObject.PhysicalObject;
import track.Track;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;

/**
 * 设计一个轨道系统，该轨道系统由一个中心物体和多条轨道组成，可以看做在直角坐标系上以中心物体为（0,0）点的同心圆
 * 轨道的编号由里向外从0到正无穷递增，各条轨道有不同的编号
 * 轨道上可以有多个物体，物体可以保持运动或静止，轨道物体之间、轨道物体与中心物体之间可以相互联系
 * 系统定义直角坐标系中，x轴正方向角度为0
 */
public interface CircularOrbit<L,E> extends Iterable<E>{
    // 模仿Lab2，构造一个静态方法生成CircularOrbit的具体类
    public static CircularOrbit<CentralObject, PhysicalObject> empty() {
        return new ConcreteCircularOrbit<CentralObject,PhysicalObject>();
    }
    /**
     * 向系统添加中心物体
     *
     * @param centralObject 要添加的中心物体，中心物体非空，类型为泛型L
     * @return 如果系统之前没有中心物体则返回null，否则如果本来存在中心物体则返回原来的的中心物体。
     */
    L addCentralObject(L centralObject);

    /**
     * 向系统中添加一条track
     *
     * @param track 轨道的半径
     * @return 添加成功则返回true，失败则返回false
     */
    boolean addTrack(Track track);

    /**
     * 移除系统中的某条轨道
     *
     * @param track 待移除的轨道
     * @return 如果移除成功，则返回true，否则返回false
     */
    boolean removeTrack(Track track);


    /**
     * 向指定轨道添加物体，添加的物体在track中按照先后的存放顺序存储。
     *
     * @param object 需要添加的轨道物体，物体的类型必须为PhysicalObject类型物体或其子类，不为null
     * @param track  需要将物体添加到的轨道，轨道在系统中需要存在
     * @return 返回true如果添加成功，否则返回false
     */
    boolean addPhysicalObject(E object, Track track);


    /**
     * 移除指定的物体，将指定物体从系统中移除，所属轨道将不包含物体。
     * 特别的，如果系统中所有物体都一样（比如说电子），那么此方法会从最内层移除一个物体，如果物体包含角度，
     * 将从初始角度最小的开始移除。
     *
     * @param object 指定轨道上物体，不为null
     * @return 如果移除成功，返回true，否则，若物体不存在，返回false
     */
    boolean removePhysicalObject(E object);

    /**
     * 修改指定轨道上某物体所在的轨道，例如指定属于track1的a，修改为a属于track2。
     * 新的轨道必须在轨道系统中存在，轨道物体也必须存在，修改后返回轨道物体之前所属的track对象的编号，否则返回-1。
     * 如果所有物体都一样（如电子），则从最内层存在物体的轨道上随机选择一个物体跃迁。
     *
     * @param object1  需要修改的轨道物体对象
     * @param object2  需要加入新轨道的物体对象（由于PhysicalObject类为不可变对象 ，所以要创建新的对象）
     * @param track 新的轨道
     * @return 返回true转移成功，否则返回false
     */
    boolean transit(E object1, E object2,Track track);


    /**
     * 在ADT内重写iterator方法
     * 生成迭代器用于遍历轨道，顺序为从内轨道逐步向外，同一轨道物体按角度从小到大次序（如果没有角度就随机）
     *
     * @return 遍历轨道物体的迭代器
     */
    Iterator<E> iterator();

    /**
     *  把物体从一个位置移动到新的位置
     * @param oldObject 原来物体的位置
     * @param newObject 新的物体的位置
     */
    public void move(E oldObject, E newObject);

    /**
     *  计算多轨道系统中各轨道上物体分布的熵值
     * @return 计算出的熵值
     */
    public double getObjectDistributionEntropy() ;

    /**
     *  计算任意两个物体之间的最短逻辑距离。
     * @param e1 其中一个物体
     * @param e2 两个物体中另一个物体
     * @return 计算出的最短逻辑距离
     */
    public int getLogicalDistance(E e1,E e2);

    /**
     *  得到两个轨道物体之间的物理距离
     * @param e1 其中一个轨道物体
     * @param e2 另一个轨道物体
     * @param <E> 限定泛型为轨道物体类型的继承子类
     * @return 两个轨道物体之间的物理距离
     */
    <E extends PhysicalObject> double getPhysicalDistance(E e1, E e2);

    /**
     *  得到排序之后的轨道列表
     * @return 有序轨道列表
     */
    public List<Track> getSortedTrack();

    /**
     * 得到轨道上的所有物体
     * @param tk 指定的轨道编号，要求合法
     * @return 轨道上的物体的列表
     */
    public List<E> getPhysicalObjectsOnTrack(Track tk);

    /**
     *  检查系统是否可获得
     * @return 若可获得，返回true
     */
    public boolean checkOribitAvailable();

    /**
     *  返回轨道的半径列表
     * @return 轨道的半径列表
     */
    public List<String> getTrackRadiusList() ;

    /**
     *  在中心物体和轨道物体间建立关系
     * @param centralObject 中心物体
     * @param physicalObject 轨道物体
     * @param weight 关系的权值
     * @return 是否成功添加关系，是的话返回True,否则返回false
     */
    public boolean addRelationOfCP(L centralObject,E physicalObject,double weight);

    /**
     *  在两个轨道物体间建立关系
     * @param physicalObj1 第一个轨道物体
     * @param physicalObj2 第二个轨道物体
     * @param weight 关系的权值
     * @return 是否成功添加关系，是的话返回True,否则返回false
     */
    public boolean addRelationOf2P(E physicalObj1,E physicalObj2,double weight);

    /**
     *  实现面板可视化
     * @param panel 要可视化的面板
     */
    public void visualize(JPanel panel) ;

    /**
     *  得到要可视化的面板
     * @return 要可视化的面板
     */
    public JPanel visualizeContentPanel();
}
