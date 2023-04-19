package otherDirectory;

/**
 * 物体的位置类
 */
public class Position {
    private double x = 0.0; //位置的x坐标
    private double y = 0.0; //位置的y坐标

    // Abstraction function:
    //   TODO  x：位置的x坐标    y：位置的y坐标
    // Representation invariant:
    //   TODO  要求x，y>0
    // Safety from rep exposure:
    //   TODO 用private限定x和y，防止表示泄露

    //   TODO constructor
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * 得到x坐标
     *
     * @return 物体的x坐标
     */
    public double getX() {
        return x;
    }

    /**
     * 设置x坐标
     *
     * @param x 要设的x坐标
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * 得到y坐标
     *
     * @return 物体的y坐标
     */
    public double getY() {
        return y;
    }

    /**
     * 设置Y
     *
     * @param y 要设置的y坐标
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * 比较两个位置是否相等
     *
     * @param obj 输入的物体位置
     * @return 相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position)
        {
            Position that = (Position) obj;
            if (that.x == this.x && that.y == this.y) {
                return true;
            }
        }
        return false;
    }

    /**
     * 重写hashcode
     *
     * @return 重写后的hashcode值
     */
    @Override
    public int hashCode() {
        return (int) (x * y) % 113;
    }

    /**
     * 计算位置与原点形成的直线与x轴正半轴直接的夹角
     *
     * @return 角度 Theta ,要求0<=Theta<360
     */
    public double getSitha() {
        double sitha;
        if (x > 0 && y > 0) {
            sitha = Math.atan2(y, x) / Math.PI * 180;
        } else if (x < 0 && y > 0) {
            sitha = Math.atan2(y, x) / Math.PI * 180 + 180.0;
        } else if (x < 0 && y < 0) {
            sitha = Math.atan2(y, x) / Math.PI * 180 + 180.0;
        } else if (x > 0 && y < 0) {
            sitha = Math.atan2(y, x) / Math.PI * 180 + 360.0;
        } else if (x == 0 && y > 0) {
            sitha = 90.0;
        } else if (y == 0 && x < 0) {
            sitha = 180.0;
        } else if (x == 0 && y < 0) {
            sitha = 270.0;
        } else if (y == 0 && x > 0) {
            sitha = 0.0;
        } else {
            sitha = 0.0;
        }
        return sitha;

    }
}
