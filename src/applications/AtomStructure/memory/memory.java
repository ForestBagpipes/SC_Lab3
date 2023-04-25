package applications.AtomStructure.memory;

import applications.AtomStructure.electron;
import track.Track;

/**
 *  记忆副本，用于保存历史记录，回退上一步时使用
 */

public class memory {
    electron e;
    Track Track1; //移动前所在的轨道
    Track Track2;  //移动后所在的轨道

    //生成记忆副本
    public memory(electron e,Track Track1,Track Track2) {
        this.e = e;
        this.Track1 = Track1;
        this.Track2 = Track2;
    }

    public electron getElectron() {
        return e;
    }

    public Track getTrack1() {
        return Track1;
    }

    public Track getTrack2() {
        return Track2;
    }

    @Override
    public String toString() {
        return String.format("Transit e from %.2f to %.2f", Track1.getRadius(), Track2.getRadius());
    }
}
