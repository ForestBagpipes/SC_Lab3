package applications.AtomStructure;

import java.util.Random;
import centralObject.ConcreteCObject;
import otherDirectory.Position;

/**
 *  原子核的具体对象类
 */
public class nucleus extends ConcreteCObject {

    public nucleus() {
        super("nucleus");
        Random random = new Random();
        String str = "["+random.nextInt(50)+"P"+":"+random.nextInt(50)+"N"+"]";
        setName(str);
    }

}
