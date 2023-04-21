package applications.TrackGame.Strategies;

import applications.TrackGame.Runner;
import track.Track;

import java.util.*;

/**
 *  随机分组、分配赛道的策略
 */

public class RandomStrategy extends Strategy{

    public RandomStrategy(){
        this.StrategyName = "RandomStrategy";
    }

    /**
     *  使用随机分组的策略
     * @param tracks 所有的比赛轨道
     * @param runnerList 所有运动员组成的列表
     * @return 随机分组策略下各组的跑道与对应运动员构成的列表
     */
    @Override
    public List<Map<Track, Runner>> assign(List<Track> tracks, List<Runner> runnerList) {
        Collections.shuffle(runnerList); //使用集成工具打乱运动员列表
        List<Map<Track,Runner>> ans = new ArrayList<>();
        int index = -1;
        int tsize = tracks.size();
        for (int i = 0; i < runnerList.size(); i++) {
            Runner runner = runnerList.get(i);
            Track track = tracks.get(i%tsize);
            if(i%tsize==0){
                index++;
                ans.add(new HashMap<>());
            }
            ans.get(index).put(track,runner);
        }
        return ans;
    }
}
