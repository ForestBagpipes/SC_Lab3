package APIs;

import centralObject.CentralObject;
import circularOrbit.ConcreteCircularOrbit;
import org.junit.Test;
import physicalObject.ConcretePObject;
import physicalObject.ConcretePObjectFactory;
import physicalObject.PhysicalObject;
import physicalObject.PhysicalObjectFactory;
import track.CTFactory;
import track.Track;
import track.TrackFactory;

import static org.junit.Assert.*;

public class CircularOrbirAPITest {
    // TODO test strategies:
    //      testGetObjectDistributionEntropy: 比较获得的熵值与预期量
    //      testGetLogicalDistance：比较获得的逻辑距离与预期量
    //      testGetPhysicalDistance： 比较获得的物理距离与预期量
    //      testGetDifference： 比较获得的差异与预期量

    @Test
    public void testGetObjectDistributionEntropy() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(1.0);
        Track track2 = tf.create(2.0);
        Track track3 = tf.create(3.0);
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        PhysicalObject po3 = new ConcretePObject("3");
        CircularOrbitAPI<CentralObject, PhysicalObject> cAPI = new CircularOrbitAPI<>();
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1,track1);
        concreteCircularOrbit.addPhysicalObject(po2,track1);
        concreteCircularOrbit.addPhysicalObject(po3,track1);
        assertEquals(0.0,cAPI.getObjectDistributionEntropy(concreteCircularOrbit),0.000001);
    }

    @Test
    public void testGetLogicalDistance() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(1.0);
        Track track2 = tf.create(2.0);
        Track track3 = tf.create(3.0);
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        PhysicalObject po3 = new ConcretePObject("3");
        PhysicalObject po4 = new ConcretePObject("4");
        PhysicalObject po5 = new ConcretePObject("5");
        CircularOrbitAPI<CentralObject, PhysicalObject> cAPI = new CircularOrbitAPI<>();
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1,track1);
        concreteCircularOrbit.addPhysicalObject(po2,track2);
        concreteCircularOrbit.addPhysicalObject(po3,track3);
        concreteCircularOrbit.addPhysicalObject(po4,track1);
        concreteCircularOrbit.addPhysicalObject(po5,track2);
        concreteCircularOrbit.addRelationOf2P(po1,po2,1.0);
        concreteCircularOrbit.addRelationOf2P(po1,po3,1.0);
        concreteCircularOrbit.addRelationOf2P(po4,po2,1.0);
        concreteCircularOrbit.addRelationOf2P(po3,po4,1.0);
        concreteCircularOrbit.addRelationOf2P(po4,po5,1.0);
        assertEquals(1,cAPI.getLogicalDistance(concreteCircularOrbit,po1,po3));
        assertEquals(2,cAPI.getLogicalDistance(concreteCircularOrbit,po3,po5));
        assertEquals(3,cAPI.getLogicalDistance(concreteCircularOrbit,po1,po5));
    }

    @Test
    public void testGetPhysicalDistance() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(1.0);
        Track track2 = tf.create(2.0);
        Track track3 = tf.create(3.0);
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        PhysicalObject po3 = new ConcretePObject("3");
        PhysicalObject po4 = new ConcretePObject("4");
        PhysicalObject po5 = new ConcretePObject("5");
        CircularOrbitAPI<CentralObject, PhysicalObject> cAPI = new CircularOrbitAPI<>();
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1,track1);
        concreteCircularOrbit.addPhysicalObject(po2,track2);
        concreteCircularOrbit.addPhysicalObject(po3,track3);
        concreteCircularOrbit.addPhysicalObject(po4,track1);
        concreteCircularOrbit.addPhysicalObject(po5,track2);
        po1.setPos(-1,0);
        po2.setPos(-2,0);
        po3.setPos(-3,0);
        po4.setPos(1,0);
        po5.setPos(2,0);
        assertEquals(1,cAPI.getPhysicalDistance(concreteCircularOrbit,po1,po2),0.0000001);
        assertEquals(2,cAPI.getPhysicalDistance(concreteCircularOrbit,po1,po3),0.0000001);
    }
}