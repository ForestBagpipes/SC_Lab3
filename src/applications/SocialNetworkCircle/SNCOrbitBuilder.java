package applications.SocialNetworkCircle;

import circularOrbit.ConcreteCircularOrbit;
import circularOrbit.ConcreteCircularOrbitBuilder;
import otherDirectory.Relation;
import track.Track;

import java.util.List;
import java.util.Map;

/**
 *  社交网络系统的创建工厂类
 */
public class SNCOrbitBuilder extends ConcreteCircularOrbitBuilder<centralUser,Friends> {
    @Override
    public ConcreteCircularOrbit<centralUser, Friends> getConcreteCircularOrbit() {
        return super.getConcreteCircularOrbit();
    }

    @Override
    public void createConcreteCircularOrbit(String sysName) {
        this.concreteCircularOrbit = new SocialNetworkCircularOrbit();
    }

    @Override
    public void buildTracks(List<Track> tracks) {
        super.buildTracks(tracks);
    }

    @Override
    public void buildObjects(centralUser centralObjects, Map<Track, List<Friends>> physicalObjMap) {
        super.buildObjects(centralObjects, physicalObjMap);
    }
    
    public void buildRelation(List<Relation<centralUser,Friends>> RelOfCT, Map<Friends,List<Relation<Friends,Friends>>> FriendsMap) {
        RelOfCT.stream().forEach((rel)->{
            concreteCircularOrbit.addRelationOfCP(rel.getobj1(),rel.getobj2(),rel.getWeight());
        });
        for(Friends Friends:FriendsMap.keySet()) {
            for(Relation<Friends,Friends> rel:FriendsMap.get(Friends)) {
                concreteCircularOrbit.addRelationOf2P(Friends,rel.getobj2(),rel.getWeight());
            }
        }
    }
}
