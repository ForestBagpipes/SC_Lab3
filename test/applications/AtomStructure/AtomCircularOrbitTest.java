package applications.AtomStructure;

import applications.AtomStructure.memory.careTaker;
import applications.AtomStructure.memory.memory;
import org.junit.Test;
import track.CTFactory;
import track.Track;

import java.util.List;

import static org.junit.Assert.*;

/**
 *   原子轨道系统的测试类
 */
public class AtomCircularOrbitTest {

    // TODO 测试reback功能：
    @Test
    public void testReback() {
        AtomCircularOrbit circularOrbit = new AtomCircularOrbit("Na");
        careTaker ct = new careTaker();
        CTFactory ctf = new CTFactory();
        electronFactory ef = new electronFactory();
        Track track1 = ctf.create(1);
        Track track2 = ctf.create(2);
        Track track3 = ctf.create(3);
        Track track4 = ctf.create(4);
        circularOrbit.addTrack(track1);
        circularOrbit.addTrack(track2);
        circularOrbit.addTrack(track3);
        circularOrbit.addTrack(track4);
        electron electron1 = ef.createElectron_2("e1");
        electron electron2 = ef.createElectron_2("e2");
        electron electron3 = ef.createElectron_2("e3");
        circularOrbit.addPhysicalObject(electron1,track1);
        circularOrbit.addPhysicalObject(electron2,track2);
        circularOrbit.addPhysicalObject(electron3,track3);
        circularOrbit.transit(electron1,electron1,track4);
        ct.addMemory(electron1,track1,track4);
        circularOrbit.transit(electron2,electron2,track3);
        ct.addMemory(electron2,track2,track3);
        circularOrbit.transit(electron3,electron3,track1);
        ct.addMemory(electron3,track3,track1);
        circularOrbit.transit(electron2,electron2,track1);
        ct.addMemory(electron2,track3,track1);
        List<memory> mementoList = ct.rebackMemory(3);
        circularOrbit.reback(mementoList);

        assertEquals(4.0,circularOrbit.getTrackForPO(electron1).getRadius(),0.0001);
        assertEquals(3.0,circularOrbit.getTrackForPO(electron2).getRadius(),0.0001);
        assertEquals(1.0,circularOrbit.getTrackForPO(electron3).getRadius(),0.0001);
    }
}