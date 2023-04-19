package physicalObject;

import org.junit.Test;
import otherDirectory.Position;

import static org.junit.Assert.*;

public class ConcretePObjectTest {
    // TODO test strategies
    //      equalsObject：1、两个物体相等  2、两个物体不相等
    //      compareTo：1、两个物体位置角度相等 2、两个物体位置偏转角不等
    @Test
    public void equalsObject() {
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject obj1 = pof.create("1");
        PhysicalObject obj2 = pof.create("1");
        PhysicalObject obj3 = pof.create("3");
        assertEquals(true,obj1.equalsObject(obj2));
        assertEquals(false,obj1.equalsObject(obj3));
    }

    @Test
    public void compareTo() {
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject obj1 = pof.create("1");
        PhysicalObject obj2 = pof.create("1");
        PhysicalObject obj3 = pof.create("3");
        obj1.setPos(new Position(1,1));
        obj2.setPos(new Position(2,1));
        obj3.setPos(new Position(2,2));
        assertEquals(1,obj1.compareTo(obj2));
        assertEquals(0,obj1.compareTo(obj3));
        assertEquals(-1,obj2.compareTo(obj1));
    }
}