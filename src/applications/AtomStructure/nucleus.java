package applications.AtomStructure;

import centralObject.ConcreteCObject;
import otherDirectory.Position;

/**
 *  原子核的具体对象类
 */
public class nucleus extends ConcreteCObject {
    public nucleus(String name, Position pos) {
        super(name);
    }

    public nucleus() {
        super("nucleus");
    }
}
