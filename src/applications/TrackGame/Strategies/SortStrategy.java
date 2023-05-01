package applications.TrackGame.Strategies;

import applications.TrackGame.Runner;
import track.Track;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  按照最好成绩分配赛道的策略
 */
public class SortStrategy extends Strategy {
    public SortStrategy(){
        this.StrategyName = "SortStrategy";
    }

    /**
     *  使用按最好成绩排序的策略，排序为升序，使得每一组成绩差的处于内侧赛道
     * @param tracks 所有的比赛轨道
     * @param runnerList 所有运动员组成的列表
     * @return 排序策略下各组的跑道与对应运动员构成的列表
     */
    @Override
    public List<Map<Track, Runner>> assign(List<Track> tracks, List<Runner> runnerList) {
        List<Runner> runners = runnerList.stream()
                .sorted(Runner::compareToWithScoreACS)
                .collect(Collectors.toList());    //得到运动员按照最好成绩排序的升序序列
        List<Map<Track,Runner>> ans = new ArrayList<>();
        int index = -1;
        int tsize = tracks.size();
        for (int i = 0; i < runners.size(); i++) {
            Runner runner = runners.get(i);
            Track track = tracks.get(i%tsize);
            if(i%tsize==0){
                index++;
                ans.add(new HashMap<>());
            }
            ans.get(index).put(track,runner);
        }
        return ans;
    }

    @Override
    public List<Map<Track, List<Runner>>> Assign(List<Track> tracks, List<Runner> runnerList) {
        return null;
    }
}
