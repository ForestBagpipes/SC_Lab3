package applications.SocialNetworkCircle;

import org.junit.Test;
import track.CTFactory;
import track.Track;

import static org.junit.Assert.*;

public class SocialNetworkCircularOrbitTest {

    @Test
    public void testRemoveRelation2TrackObjs() {
        SocialNetworkCircle socialNetworkCircle = new SocialNetworkCircle("src/configFile/SocialNetworkCircle.txt");
        centralUserFactory cf = new centralUserFactory();
        centralUser centralUser = cf.createCentralUser_1("central");
        friendsFactory ff = new friendsFactory();
        Friends frA = ff.createFriends_1("A");
        Friends frB = ff.createFriends_1("B");
        Friends frC = ff.createFriends_1("C");
        Friends frD = ff.createFriends_1("D");
        Friends frE = ff.createFriends_1("E");
        Friends frF = ff.createFriends_1("F");
        CTFactory ctf = new CTFactory();
        Track tk1 = ctf.create(1);
        Track tk2 = ctf.create(2);
        Track tk3 = ctf.create(3);
        Track tk4 = ctf.create(4);
        SocialNetworkCircularOrbit circularOrbit = new SocialNetworkCircularOrbit();
        circularOrbit.addCentralObject(centralUser);
        circularOrbit.addTrack(tk1); circularOrbit.addTrack(tk2);
        circularOrbit.addTrack(tk3); circularOrbit.addTrack(tk4);
        circularOrbit.addPhysicalObject(frA,tk1);
        circularOrbit.addPhysicalObject(frB,tk1);
        circularOrbit.addPhysicalObject(frC,tk2);
        circularOrbit.addPhysicalObject(frD,tk3);
        circularOrbit.addPhysicalObject(frE,tk3);
        circularOrbit.addPhysicalObject(frF,tk3);
        circularOrbit.addRelationOf2T(frA,frC,1);
        circularOrbit.addRelationOf2T(frB,frC,1);
        circularOrbit.addRelationOf2T(frC,frD,1);
        circularOrbit.addRelationOf2T(frC,frE,1);
        circularOrbit.addRelationOf2T(frC,frF,1);
        System.out.println(circularOrbit.removeRelationOf2T(frA,frC));
    }
}