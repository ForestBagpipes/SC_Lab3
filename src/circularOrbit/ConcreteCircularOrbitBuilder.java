package circularOrbit;

import otherDirectory.Relation;
import track.Track;

import java.util.List;
import java.util.Map;

/**
 *    轨道系统创建抽象类
 */
public abstract class ConcreteCircularOrbitBuilder<L,E> {
    protected ConcreteCircularOrbit<L,E> concreteCircularOrbit;

    /**
     *  返回轨道系统
     * @return 该轨道系统
     */
    public ConcreteCircularOrbit<L,E> getConcreteCircularOrbit(){
        return concreteCircularOrbit;
    }

    /**
     * 创建对象
     */
    public abstract void createConcreteCircularOrbit(String name) ;


    /**
     * 设置轨道系统中的轨道
     * @param tracks 需要添加的所有轨道
     */
    public void buildTracks(List<Track> tracks) {
        for(Track track:tracks){
            concreteCircularOrbit.addTrack(track);
        }
    }

    /**
     *  向轨道系统中添加中心物体和轨道物体
     */
    public void buildObjects(L centralObjects, Map<Track,List<E>> physicalObjMap) {
        concreteCircularOrbit.addCentralObject(centralObjects);
        for(Map.Entry<Track, List<E>> entry:physicalObjMap.entrySet()) {
            for(E obj:entry.getValue()) {
                concreteCircularOrbit.addPhysicalObject(obj,entry.getKey());
            }
        }
    }

}
