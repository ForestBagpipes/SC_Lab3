package applications.AtomStructure.memory;

import applications.AtomStructure.electron;
import track.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * 回溯操作负责人
 */
public class careTaker {
    List<memory> histories = new ArrayList<>();

    public void addMemory(electron e, Track fromTrack, Track toTrack) {
        histories.add(new memory(e,fromTrack,toTrack));
    }

    /**
     *  进行记录的回退，同时返回区间内部的操作历史
     * @param step 将指定步数之后的所有操作都回退
     * @return 回退点之后的所有操作历史
     */
    public List<memory> rebackMemory(int step) {
        assert step<histories.size() && step>=0:"回退步数不合法";

        if(histories.size()==0) return null;
        else {
            List<memory> ans = histories.subList(step,histories.size());
            histories = histories.subList(0,step);
            return ans;
        }
    }

    /**
     *  得到所有的历史记录
     * @return 保存每一步历史记录的字符串
     */
    public List<String> getAllHistory() {
        List<String> history = new ArrayList<>();
        int n =0;
        for(memory Memory:histories) {
            history.add(String.format("[%d] %s",n++,Memory.toString()));
        }
        return history;
    }
}
