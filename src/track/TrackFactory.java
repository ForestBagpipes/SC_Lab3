package track;

/**
 * 生产track的工厂抽象类
 */
public abstract class TrackFactory {
    /**
     * 生产一个具体的track对象
     * @param radius Track的半径
     * @return 返回一个Track对象
     */
    public final Track create(double radius) {
        Track track = createTrack(radius);
        return track;
    }

    /**
     * 建立一个Track对象
     * @param radius 半径 >= 0
     * @return  返回一个Track对象
     */
    protected abstract Track createTrack(double radius);
}
