package centralObject;

public abstract class CentralObject extends CommonObject {
    public CentralObject(String name) {
        super(name);
    }
    /**
     * 比较该物体与某一个是否相同(中心物体只有一个，所以还要比较位置)
     * @param obj 另一个要比较的物体
     * @return 相同返回true，否则返回false
     */
    public abstract boolean equalsObject(Object obj);

}
