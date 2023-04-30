package applications.TrackGame.Strategies;

import applications.TrackGame.Runner;
import org.junit.Test;
import track.CTFactory;
import track.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *  测试随机分组策略
 */
public class RandomStrategyTest {

    @Test
    public void testAssign() {
        List<Track> tracks = new ArrayList<>();
        CTFactory ctf = new CTFactory();
        tracks.add(ctf.create(1.0));
        tracks.add(ctf.create(2.0));
        tracks.add(ctf.create(3.0));
        List<Runner> runners = new ArrayList<>();
        runners.add(Runner.getInstance("A",1,12,98.0,"CN"));
        runners.add(Runner.getInstance("B",2,12,98.0,"UK"));
        runners.add(Runner.getInstance("C",3,12,98.0,"UN"));
        runners.add(Runner.getInstance("D",4,12,98.0,"US"));
        runners.add(Runner.getInstance("E",5,12,98.0,"GE"));
        runners.add(Runner.getInstance("F",6,12,98.0,"JP"));
        runners.add(Runner.getInstance("G",6,12,98.0,"JP"));
        List<Map<Track,Runner>> groups = new RandomStrategy().assign(tracks,runners);
        for(Map<Track,Runner> group:groups) {
            for(Map.Entry<Track,Runner> entry:group.entrySet()) {
                System.out.println(entry.getKey().getRadius()+" :: "+entry.getValue().getName());
            }
            System.out.println();
        }
    }
}