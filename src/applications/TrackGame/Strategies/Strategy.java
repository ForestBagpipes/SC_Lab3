package applications.TrackGame.Strategies;

import applications.TrackGame.Runner;
import track.Track;

import java.util.List;
import java.util.Map;

/**
 *  比赛分组策略抽象类
 */
public abstract class Strategy {
    protected String StrategyName; //策略的名字

    /**
     *  安排比赛的分组策略
     * @param tracks 所有的比赛轨道
     * @param runnerList 所有运动员组成的列表
     * @return 各组的跑道与对应运动员构成的列表
     */
    abstract public List<Map<Track, Runner>> assign(List<Track> tracks, List<Runner> runnerList) ;
    abstract public List<Map<Track, List<Runner>>> Assign(List<Track> tracks, List<Runner> runnerList) ;

    public String getStrategyName() {
        return StrategyName;
    }
}
