package circularOrbit;

import centralObject.CentralObject;
import centralObject.CentralObjectFactory;
import centralObject.ConcreteCObject;
import centralObject.ConcreteCObjectFactory;
import org.junit.Test;
import physicalObject.ConcretePObject;
import physicalObject.ConcretePObjectFactory;
import physicalObject.PhysicalObject;
import physicalObject.PhysicalObjectFactory;
import track.CTFactory;
import track.Track;
import track.TrackFactory;

import static org.junit.Assert.*;

public class ConcreteCircularOrbitTest {

    //  TODO test strategies
    //        testAddTrack:  1、添加指定轨道看是否成功
    //        testRemoveTrack:  1、删除存在的轨道   2、删除系统中不存在的轨道
    //        testAddCentralObject: 1、原来有中心物体 2、原来没有中心物体
    //        testAddPhysicalObject: 1、物体加到不存在的轨道上  2、不同的物体加到不同的轨道上
    //                     3、相同的物体加到不同的轨道上 4、相同的物体加到相同的轨道上
    //        testRemovePhysicalObject(): 1、要删除的物体存在  2、要删除的物体不存在
    //        testTransit： 1、要移动的物体不存在，轨道存在 2、要移动的物体存在，轨道存在 3、要移动的物体存在，轨道不存在
    //        testMove: 1、 要移动的轨道物体存在 2、物体不存在
    //        testAddRelationOf CP: 1、关系存在  2、关系不存在
    //        testAddRelationOf2P: 1、关系存在  2、关系不存在
    //        testRemoveRelationOf2P: 1、关系存在  2、关系不存在
    //        testGetTrackForPO：1、物体存在 2、物体不存在

    @Test
    public void testAddTrack() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        assertEquals(true,concreteCircularOrbit.addTrack(track1));
    }

    @Test
    public void testRemoveTrack() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        Track track2 = tf.create(1.0);
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        assertEquals(false,concreteCircularOrbit.removeTrack(track2));
        assertEquals(true,concreteCircularOrbit.removeTrack(track1));
    }

    @Test
    public void testAddCentralObject() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(1.0);
        Track track2 = tf.create(2.0);
        Track track3 = tf.create(3.0);
        CentralObjectFactory cof = new ConcreteCObjectFactory();
        CentralObject co1 = new ConcreteCObject("1");
        CentralObject co2 = new ConcreteCObject("2");
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1, track1);
        assertEquals(null,concreteCircularOrbit.addCentralObject(co1));
        assertEquals(co1,concreteCircularOrbit.addCentralObject(co2));
    }

    @Test
    public void testAddPhysicalObject() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        Track track2 = tf.create(1.0);
        Track track3 = tf.create(3.0);
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track3);
        assertEquals(true,concreteCircularOrbit.addPhysicalObject(po1,track1));
        assertEquals(false,concreteCircularOrbit.addPhysicalObject(po2,track2));
        assertEquals(false,concreteCircularOrbit.addPhysicalObject(po1,track3));
        assertEquals(false,concreteCircularOrbit.addPhysicalObject(po1,track1));
    }

    @Test
    public void testRemovePhysicalObject() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        Track track2 = tf.create(1.0);
        Track track3 = tf.create(3.0);
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1,track1);
        assertEquals(false,concreteCircularOrbit.removePhysicalObject(po2));
        assertEquals(true,concreteCircularOrbit.removePhysicalObject(po1));
    }

    @Test
    public void testAddRelationOfCP() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(1.0);
        Track track2 = tf.create(2.0);
        Track track3 = tf.create(3.0);
        CentralObjectFactory cof = new ConcreteCObjectFactory();
        CentralObject co1 = new ConcreteCObject("1");
        CentralObject co2 = new ConcreteCObject("2");
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1, track1);
        assertEquals(false,concreteCircularOrbit.addRelationOfCP(null,po1,1));
        concreteCircularOrbit.addCentralObject(co1);
        assertEquals(false,concreteCircularOrbit.addRelationOfCP(co1,po1,-1));
        assertEquals(true,concreteCircularOrbit.addRelationOfCP(co1,po1,1));
    }

    @Test
    public void testAddRelationOf2P() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        Track track2 = tf.create(1.0);
        Track track3 = tf.create(3.0);
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        PhysicalObject po3 = new ConcretePObject("3");
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1,track1);
        concreteCircularOrbit.addPhysicalObject(po2,track2);
        assertEquals(true,concreteCircularOrbit.addRelationOf2P(po1,po3,1.0));
        assertEquals(true,concreteCircularOrbit.addRelationOf2P(po1,po2,1.0));
        concreteCircularOrbit.addPhysicalObject(po3,track1);
        assertEquals(false,concreteCircularOrbit.addRelationOf2P(po1,po3,1.0));

    }

    @Test
    public void testRemoveRelationOf2P() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        Track track2 = tf.create(1.0);
        Track track3 = tf.create(3.0);
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        PhysicalObject po3 = new ConcretePObject("3");
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track2);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1,track1);
        concreteCircularOrbit.addPhysicalObject(po2,track2);
        concreteCircularOrbit.addRelationOf2P(po1,po3,1.0);
        assertEquals(false,concreteCircularOrbit.removeRelationOf2P(po1,po2));
        assertEquals(true,concreteCircularOrbit.removeRelationOf2P(po1,po3));
    }

    @Test
    public void testTransit() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        Track track2 = tf.create(1.0);
        Track track3 = tf.create(3.0);
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("1");
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1,track1);
        assertEquals(false,concreteCircularOrbit.transit(po1,po2,track2));
        assertEquals(false,concreteCircularOrbit.transit(po2,po1,track2));
        assertEquals(false,concreteCircularOrbit.transit(po2,po1,track1));
        assertEquals(true,concreteCircularOrbit.transit(po1,po2,track3));
    }

    @Test
    public void testMove() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(1.0);
        Track track2 = tf.create(2.0);
        Track track3 = tf.create(3.0);
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1, track1);
        PhysicalObject obj2 = new ConcretePObject(po2.getName());
        concreteCircularOrbit.move(po2,obj2);
        assertEquals(null,concreteCircularOrbit.getTrackForPO(obj2));
        PhysicalObject obj1 = new ConcretePObject(po1.getName());
        concreteCircularOrbit.move(po1,obj1);
        assertEquals(track1,concreteCircularOrbit.getTrackForPO(obj1));
    }
    @Test
    public void testGetTrackForPO() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(1.0);
        Track track2 = tf.create(2.0);
        Track track3 = tf.create(3.0);
        PhysicalObjectFactory pof = new ConcretePObjectFactory();
        PhysicalObject po1 = new ConcretePObject("1");
        PhysicalObject po2 = new ConcretePObject("2");
        ConcreteCircularOrbit<CentralObject, PhysicalObject> concreteCircularOrbit = new ConcreteCircularOrbit<>();
        concreteCircularOrbit.addTrack(track1);
        concreteCircularOrbit.addTrack(track3);
        concreteCircularOrbit.addPhysicalObject(po1, track1);
        assertEquals(null,concreteCircularOrbit.getTrackForPO(po2));
        assertEquals(track1,concreteCircularOrbit.getTrackForPO(po1));
    }
}