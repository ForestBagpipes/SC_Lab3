package applications.TrackGame.Strategies;

import applications.TrackGame.Runner;
import track.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelayStrategy extends Strategy{
    public RelayStrategy(){
        this.StrategyName = "RelayStrategy";
    }
    /**
     *  使用接力策略进行分组，每个轨道分4个人
     * @param tracks 所有的比赛轨道
     * @param runnerList 所有运动员组成的列表
     * @return 排序策略下各组的跑道与对应运动员构成的列表
     */
    public List<Map<Track, List<Runner>>> Assign(List<Track> tracks, List<Runner> runnerList) {
        int numOfTracks = tracks.size();
        List<Map<Track,List<Runner>>> assignment = new ArrayList<>();
        int groupsCount = -1;
        int tmp = 0;
        int i,relax;
        for(i=0;i*4<(runnerList.size()/4)*4;i++) {
            List<Runner> RunnerList = new ArrayList<>();
            RunnerList.add(runnerList.get(4*i));
            RunnerList.add(runnerList.get(4*i+1));
            RunnerList.add(runnerList.get(4*i+2));
            RunnerList.add(runnerList.get(4*i+3));
            Track track = tracks.get(i%numOfTracks);
            if((i)%numOfTracks==0) {
                groupsCount++;
                assignment.add(new HashMap<>());
            }
            assignment.get(groupsCount).put(track,RunnerList);
            tmp = groupsCount;
        }
        if((i)%numOfTracks==0) {
            tmp++;
            assignment.add(new HashMap<>());
        }
        if(runnerList.size()%4!=0){
            relax = runnerList.size()%4;
            List<Runner> RunnerList = new ArrayList<>();
            Track track = tracks.get(i%numOfTracks);
            for(int j = 0;j<relax;j++){
                RunnerList.add(runnerList.get(4*i+j));
            }
            assignment.get(tmp).put(track,RunnerList);
        }
        return assignment;
    }
    @Override
    public List<Map<Track, Runner>> assign(List<Track> tracks, List<Runner> runnerList) {
        return null;
    }
}
