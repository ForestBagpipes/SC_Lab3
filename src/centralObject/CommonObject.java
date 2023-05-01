package centralObject;

import otherDirectory.Painter;
import otherDirectory.Position;

import java.awt.*;

/**
 *  一般物体的抽象类
 */
public abstract class CommonObject {
    protected String name;  //物体的名字
    protected Position pos; //物体的位置

    // TODO constructor
    public CommonObject(String name) {
        this.name = name;
    }

    /**
     *  更改物体的名字
     * @param name 要改的名字
     */
    protected  void setName(String name){
        this.name = name;
    }
    /**
     * 得到物体的名字
     * @return 物体的名称name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置物体的位置
     * @param pos 输入的物体的位置
     */
    public void setPos(Position pos){
        this.pos = pos;
    }

    public void setPos(double x,double y){
        pos = new Position(x,y);
        this.pos = pos;
    }

    /**
     *  得到物体的位置
     * @return 物体的位置pos
     */
    public Position getPos() {
        return pos;
    }

    /**
     *  重写物体的hashcode编码
     * @return 新的hashcode编码
     */
    @Override
    public int hashCode() {
        return name.hashCode()*31;
    }

    /**
     *  打印物体的名字
     * @return 物体的名字的字符串；
     */
    @Override
    public String toString() {
        System.out.println(name);
        return name;
    }

    /**
     *  绘制物体
     * @param g 要绘制的图形
     * @param painter 物体圆形图像类
     */
    public void draw(Graphics g, Painter painter) {
        int x = painter.getX(),y = painter.getY();
        int radius = painter.getRadius();
        g.setColor(painter.getOvalColor());
        g.fillOval(x-radius,y-radius,2*radius,2*radius);
        g.setColor(painter.getTextBackColor());
        if(painter.getFont()==null) {
            g.setFont(null);
            g.drawString(name, x - radius - 5, y - radius - 5);
        }
        else {
            g.setFont(painter.getFont());
            g.drawString(name, x - radius - 5, y - radius - 5);
        }
        this.pos = new Position(x,y);
    }
}
