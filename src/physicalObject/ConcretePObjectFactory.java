package physicalObject;

/**
 *  ConcretePObject的具体工厂类
 */
public class ConcretePObjectFactory extends PhysicalObjectFactory{
    @Override
    protected PhysicalObject createPObject(String name) {
        return new ConcretePObject(name);
    }
}
