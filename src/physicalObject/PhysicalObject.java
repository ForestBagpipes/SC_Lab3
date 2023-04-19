package physicalObject;

import centralObject.CommonObject;
import otherDirectory.Position;

/**
 * 轨道物体的抽象类
 */
public abstract class PhysicalObject extends CommonObject {
    public PhysicalObject(String name) {
        super(name);
    }

    /**
     * 比较该物体与某一个是否相同
     * @param obj 另一个要比较的物体
     * @return 相同返回true，否则返回false
     */
    public abstract boolean equalsObject(Object obj);

    /**
     * 比较两个物体
     * @param that 要比较的物体
     * @return 比较结果：比对方位置大返回1；相等返回0；小返回-1
     */
    public abstract int compareTo(PhysicalObject that);

}
