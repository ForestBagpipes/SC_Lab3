package track;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConcreteTrackTest {

    // TODO test strategies
    //      GetRadius：比较轨道半径与预期是否相等
    //      CompareTo：1、两个轨道半径相等 2、两个轨道大小不一
    //      HashCode： 比较重写后的hashcode与预期的大小
    //      Equals：   1、两个轨道不相等   2、两个轨道相等
    @Test
    public void testGetRadius() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        assertEquals(2.0,track1.getRadius(),0.00001);
    }

    @Test
    public void testCompareTo() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        Track track2 = tf.create(3.0);
        Track track3 = tf.create(3.0);
        assertEquals(1,track2.compareTo(track1));
        assertEquals(0,track2.compareTo(track3));
        assertEquals(-1,track1.compareTo(track2));
    }

    @Test
    public void testHashCode() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        assertEquals(2,track1.hashCode());
    }

    @Test
    public void testEquals() {
        TrackFactory tf = new CTFactory();
        Track track1 = tf.create(2.0);
        Track track2 = tf.create(3.0);
        Track track3 = tf.create(3.0);
        assertEquals(true,track2.equals(track3));
    }
}