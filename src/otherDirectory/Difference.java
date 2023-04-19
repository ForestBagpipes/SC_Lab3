package otherDirectory;

import physicalObject.PhysicalObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import physicalObject.PhysicalObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Difference<E extends PhysicalObject> {
    int trackIndex = 0; //轨道的编号
    private int trackNumDiff;//轨道数目差异
    private List<trackDifference> trackDiffList = new ArrayList<>();//各条轨道差异组成的列表

    public Difference(int trackNumDiff) {
        this.trackNumDiff = trackNumDiff;
    }

    /** 
     *
     * @param list1
     * @param list2
     */
    public void addTrackSet(List<E> list1, List<E> list2) {
        //系统1的轨道物体集合
        List<E> Orbit1Objs = new ArrayList<>(list1);
        //系统2的轨道物体集合
        List<E> Orbit2Objs = new ArrayList<>(list2);
        trackIndex++;
        int NumDiff = Orbit1Objs.size()-Orbit2Objs.size();
        Set<E> deleteObjs = new HashSet<>();
        for(E obj1:Orbit1Objs) {
            for(E obj2:Orbit2Objs) {
                if(obj1.equalsObject(obj2)) {
                    deleteObjs.add(obj2);
                }
            }
        }
        //删去两个列表中相同的物体
        for(E obj:deleteObjs) {
            Orbit1Objs.removeIf((x)->x.equalsObject(obj));
            Orbit2Objs.removeIf((x)->x.equalsObject(obj));
        }
        //创建对应轨道的轨道差异
        trackDiffList.add(new trackDifference(trackIndex,NumDiff,
                new HashSet<>(Orbit1Objs),new HashSet<>(Orbit2Objs)));

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("轨道数量差异：%d\n",trackNumDiff));
        for(trackDifference trackdiff:trackDiffList) {
            sb.append(trackdiff.toString()+"\n");
        }
        return sb.toString();
    }

    //私有类：表示两条轨道间的差异
    private class trackDifference {
        int index; //轨道的编号
        int objNumDiff; //物体的数量差异
        Set<E> orbit1Objs,orbit2Objs; //两个轨道系统的物体集合

        //带参构造
        public trackDifference(int index,int objNumDiff,Set<E> orbit1Objs,Set<E> orbit2Objs) {
            this.index = index;
            this.objNumDiff = objNumDiff;
            this.orbit1Objs = orbit1Objs;
            this.orbit2Objs = orbit2Objs;
        }

        //得到物体名字的集合
        private String getObjectString(Set<E> orbitObjs) {
            StringBuilder sb = new StringBuilder();
            if(orbitObjs.size()==0) sb.append("无");
            else if(orbitObjs.size()==1) {
                for(E obj:orbitObjs) {
                    sb.append(obj.toString());
                }
            } else {
                sb.append("{");
                boolean flag = false;
                for(E obj:orbitObjs) {
                    if(!flag) {
                        flag = true;
                        sb.append(obj.toString());
                    } else {
                        sb.append(","+obj.toString());
                    }
                }
                sb.append("}");
            }
            return sb.toString();
        }

        //以指定格式打印轨道差异
        @Override
        public String toString() {
            String tmp = getObjectString(orbit1Objs)+"-"+getObjectString(orbit2Objs);
            if(tmp.equals("无-无"))
                return String.format("轨道%d的物体数量差异：%d ；",index,objNumDiff);
            else
                return String.format("轨道%d的物体数量差异：%d ；  物体差异：%s",index,objNumDiff,tmp);
        }
    }
}
