package applications.AtomStructure;

import physicalObject.ConcretePObject;
import physicalObject.PhysicalObject;

/**
 *  电子的具体类
 */
public class electron extends ConcretePObject {
    // TODO constructor
    //带参构造
    public electron(String name) {
        super(name);
    }

    //无参构造
    public electron() {
        super("e");
    }
    @Override
    public boolean equalsObject(Object obj) {
        return super.equalsObject(obj);
    }
    @Override
    public int compareTo(PhysicalObject that) {
        return this.getName().compareTo(that.getName());
    }
}
