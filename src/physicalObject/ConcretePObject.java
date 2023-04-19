package physicalObject;

import otherDirectory.Position;

/**
 * PhysicalObject的一个具体实现类
 */
public class ConcretePObject extends PhysicalObject implements Comparable<PhysicalObject>{
    private String name;
    private Position pos;
    // Abstraction function:
    //   TODO  name：物体的名字  pos：物体的位置
    // Representation invariant:
    //   TODO  name!=null
    // Safety from rep exposure:
    //   TODO 用private限定name和pos，防止表示泄露

    // TODO constructor
    public ConcretePObject(String name){
        super(name);
    }

    @Override
    public boolean equalsObject(Object obj) {
        PhysicalObject that = (PhysicalObject) obj;
        return that.getName().equals(this.getName());
    }

    @Override
    public int compareTo(PhysicalObject that) {
        if(this.getPos().getSitha()<that.getPos().getSitha())
            return -1;
        else if(this.getPos().getSitha()==that.getPos().getSitha())
           return 0;
        else return 1;
    }
}
