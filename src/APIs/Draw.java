package APIs;

import javax.swing.*;

/**
 *  图像可绘制接口
 */
public interface Draw {
    /**
     *  初始化
     */
    void initialize();

    /**
     *  绘制框架
     * @param frame 输入的要绘制的框架
     */
    void draw(JFrame frame);
}
