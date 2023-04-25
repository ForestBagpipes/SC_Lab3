package applications.AtomStructure;

import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.ConcreteCircularOrbitBuilder;

/**
 *  原子轨道系统的构造工厂类
 */
public class AtomCircularOrbitBuilder extends ConcreteCircularOrbitBuilder<nucleus,electron> {
    @Override
    public ConcreteCircularOrbit<nucleus,electron> getConcreteCircularOrbit() {
        return super.getConcreteCircularOrbit();
    }

    @Override
    public void createConcreteCircularOrbit(String sysName) {
        this.concreteCircularOrbit = new AtomCircularOrbit(sysName);
    }
}
