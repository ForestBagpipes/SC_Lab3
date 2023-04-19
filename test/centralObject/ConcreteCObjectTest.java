package centralObject;

import org.junit.Test;
import otherDirectory.Position;
import physicalObject.ConcretePObjectFactory;
import physicalObject.PhysicalObject;
import physicalObject.PhysicalObjectFactory;

import static org.junit.Assert.*;

public class ConcreteCObjectTest {
    // TODO test strategies
    //      equalsObject: name:1、相等  2、不等
    //                    pos:1、相等  2、不等
    @Test
    public void equalsObject() {
        CentralObjectFactory cof = new ConcreteCObjectFactory();
        CentralObject obj1 = cof.create("1");
        CentralObject obj2 = cof.create("1");
        CentralObject obj3 = cof.create("3");
        CentralObject obj4 = cof.create("3");
        obj1.setPos(new Position(2,2));
        obj2.setPos(new Position(2,1));
        obj3.setPos(new Position(2,2));
        obj4.setPos(new Position(2,2));
        assertEquals(false,obj1.equalsObject(obj2));
        assertEquals(false,obj1.equalsObject(obj3));
        assertEquals(true,obj3.equalsObject(obj4));
    }
}