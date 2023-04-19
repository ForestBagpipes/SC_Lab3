package physicalObject;

/**
 *  PhysicalObject的抽象工厂类
 */
public abstract class PhysicalObjectFactory {
    /**
     *  生产一个具体的PhysicalObject对象
     * @param name 对象的名字
     * @return 新的PhysicalObject对象
     */
    public final PhysicalObject create(String name){
        return createPObject(name);
    }

    /**
     * 建立一个PhysicalObject对象
     * @param name 对象的名字
     * @return  新的PhysicalObject对象
     */
    protected abstract PhysicalObject createPObject(String name);
}
