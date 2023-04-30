package applications.TrackGame.Strategies;

import applications.TrackGame.Runner;
import com.sun.corba.se.impl.orbutil.CacheTable;
import org.junit.Test;
import track.CTFactory;
import track.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *  根据最好成绩分组策略测试
 */
public class SortStrategyTest {

    @Test
    public void testAssign() {
        List<Track> tracks = new ArrayList<>();
        CTFactory ctf = new CTFactory();
        tracks.add(ctf.create(1.0));
        tracks.add(ctf.create(2.0));
        tracks.add(ctf.create(3.0));
        tracks.add(ctf.create(4.0));
        tracks.add(ctf.create(5.0));
        List<Runner> runners = new ArrayList<>();
        runners.add(Runner.getInstance("A",1,12,5,"CN"));
        runners.add(Runner.getInstance("B",2,12,7,"UK"));
        runners.add(Runner.getInstance("C",3,12,3,"UN"));
        runners.add(Runner.getInstance("D",4,12,6,"US"));
        runners.add(Runner.getInstance("E",5,12,1,"GE"));

        runners.add(Runner.getInstance("A1",11,12,15,"CN"));
        runners.add(Runner.getInstance("B1",12,12,17,"UK"));
        runners.add(Runner.getInstance("C1",13,12,13,"UN"));
        List<Map<Track,Runner>> groups = new SortStrategy().assign(tracks,runners);
        for(Map<Track,Runner> group:groups) {
            group.keySet().stream()
                    .sorted()
                    .forEach((x)->{
                        System.out.println(x.getRadius()+" :: "+group.get(x).getName());
                    });
            System.out.println();
        }
    }
}
