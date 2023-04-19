package centralObject;

import physicalObject.PhysicalObject;

public abstract class CentralObjectFactory {
    /**
     *  生产一个具体的CentralObject对象
     * @param name 对象的名字
     * @return 新的CentralObject对象
     */
    public final CentralObject create(String name){
        return createCObject(name);
    }

    /**
     * 建立一个CentralObject对象
     * @param name 对象的名字
     * @return  新的CentralObject对象
     */
    protected abstract CentralObject createCObject(String name);
}
