package track;

/**
 * ConcreteTrack的具体工厂类
 */
public class CTFactory extends TrackFactory {

    @Override
    protected Track createTrack(double radius) {
        return new ConcreteTrack(radius);
    }
}
