package centralObject;


import otherDirectory.Position;

public class ConcreteCObject extends CentralObject{
    private String name;
    private Position pos;
    // Abstraction function:
    //   TODO  name：物体的名字  pos：物体的位置
    // Representation invariant:
    //   TODO  name!=null
    // Safety from rep exposure:
    //   TODO 用private限定name和pos，防止表示泄露
    public ConcreteCObject(String name) {
        super(name);
    }

    @Override
    public boolean equalsObject(Object obj) {
        CommonObject that = (CommonObject) obj;
        return that.getName().equals(this.getName())&& that.getPos().equals(this.getPos());
    }
}
