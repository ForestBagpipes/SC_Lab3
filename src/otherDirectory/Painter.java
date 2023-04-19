package otherDirectory;

import java.awt.*;

/**
 *  圆形绘制类
 */
public class Painter {
    int x; // 圆的x坐标
    int y; // 圆的y坐标
    int radius; // 半径长度
    Color ovalColor; //圆的颜色
    Color textBackColor; //字体的颜色
    Font font; //字体

    public Painter() {
        x = 0;
        y = 0;
        radius = 0;
        ovalColor = Color.black;
        textBackColor = Color.black;
        font = null;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getOvalColor() {
        return ovalColor;
    }

    public void setOvalColor(Color ovalColor) {
        this.ovalColor = ovalColor;
    }

    public Color getTextBackColor() {
        return textBackColor;
    }

    public void setTextBackColor(Color textBackColor) {
        this.textBackColor = textBackColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font ptFont) {
        this.font = ptFont;
    }

}
