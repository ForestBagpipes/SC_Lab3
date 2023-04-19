package track;

public class ConcreteTrack extends Track{
    private final double radius; //轨道的半径,要求大于0
    // Abstraction function:
    //   TODO  radius：轨道半径
    // Representation invariant:
    //   TODO  要求radius>0
    // Safety from rep exposure:
    //   TODO 用final和private限定radius，防止表示泄露

    // TODO constructor
    /**
     * Track具体实现类的带参构造
     * @param radius 输入的半径大小，要求>0
     * @param num  输入的轨道编号
     */
    public ConcreteTrack(double radius){
        this.radius = radius;
    }
    // TODO checkRep
    private void checkRep(){
        assert radius>0;
    }

    @Override
    public double getRadius() {
        checkRep();
        return radius;
    }

    /**
     * 比较本Track与输入的Track之间的半径长度
     * @param that 要比较的track
     * @return 比较结果：比对方半径大返回1；相等返回0；小返回-1
     */
    @Override
    public int compareTo(Track that) {
        if(this.getRadius()<that.getRadius()) {
            checkRep();
            return -1;
        }
        else if(this.getRadius()==that.getRadius()) {
            checkRep();
            return 0;
        }
        else {
            checkRep();
            return 1;
        }
    }

    /**
     *  重写hashcode
     * @return 重写后的hashcode值
     */
    @Override
    public int hashCode() {
        return (int)this.radius;
    }

    /**
     *  比较两个轨道是否相同
     * @param obj 输入的轨道
     * @return 相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        Track that;
        if(obj instanceof Track){
            that = (Track) obj;
        }else {
            return false;
        }
        return that.getRadius() == this.radius;
    }
}
