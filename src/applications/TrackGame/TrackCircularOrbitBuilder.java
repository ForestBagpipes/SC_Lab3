package applications.TrackGame;

import centralObject.CentralObject;
import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.ConcreteCircularOrbitBuilder;

/**
 *  创建TrackCircularOrbit系统类
 */
public class TrackCircularOrbitBuilder extends ConcreteCircularOrbitBuilder<CentralObject,Runner> {
    @Override
    public ConcreteCircularOrbit<CentralObject, Runner> getConcreteCircularOrbit() {
        return this.concreteCircularOrbit;
    }

    @Override
    public void createConcreteCircularOrbit(String name) {
        this.concreteCircularOrbit = new TrackCircularOrbit();
    }

}
