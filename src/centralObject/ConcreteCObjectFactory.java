package centralObject;

public class ConcreteCObjectFactory extends CentralObjectFactory{
    @Override
    protected CentralObject createCObject(String name) {
        return new ConcreteCObject(name);
    }
}
