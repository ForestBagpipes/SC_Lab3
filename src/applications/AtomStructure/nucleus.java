package applications.AtomStructure;

import centralObject.ConcreteCObject;
import otherDirectory.Position;

public class nucleus extends ConcreteCObject {
    public nucleus(String name, Position pos) {
        super(name);
    }

    public nucleus() {
        super("nucleus");
    }
}
