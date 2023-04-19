package track;

/**
 *  轨道的抽象类
 */
public abstract class Track implements Comparable<Track> {
    double radius; //轨道的半径,要求大于0
    /**
     * 获得轨道的半径
     * @return 轨道半径
     */
    public abstract double getRadius();
}
