package applications.TrackGame;

import applications.TrackGame.Runner;
import centralObject.CentralObject;
import circularOrbit.ConcreteCircularOrbit;
import otherDirectory.Painter;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class TrackCircularOrbit extends ConcreteCircularOrbit<CentralObject, Runner> {
    // TODO constructor
    //空参构造
    public TrackCircularOrbit() {
    }

    /**
     *  检查该系统是否正常
     * @return 正常则返回true，否则返回false
     */
    @Override
    public boolean checkOribitAvailable() {
        boolean flag = true;
        //检查中心物体
        flag = flag && centralObject == null;
        //检查跑道数是否在4-10之间
        int numOfTrack = physicalObjectMap.keySet().size();
        flag = flag && numOfTrack >= 4 && numOfTrack <= 10;
        return flag;
    }


    /**
     *  生成一个正方形的可视化面板
     * @return 生成的JPanel类型面板
     */
    public JPanel visualizeContentPanel() {
        return new JPanel() {
            //面板序列号
            private static final long serialVersionUID = 1L;

            // 重写paint方法
            @Override
            public void paint(Graphics graphics) {
                final int Leng = 800;   //边框长
                final int padding = 100;    //元素与边框直接的空间大小
                int tsize  = physicalObjectMap.size(); //轨道数目
                int[] trackRadius = new int[tsize];    //轨道半径数列
                for(int i=0;i<tsize;i++) {
                    trackRadius[i] = (Leng-padding)/(2*tsize)*(i+1);
                }
                super.paint(graphics);
                int centerX = Leng/2,centerY = Leng/2; //圆心的x坐标和Y坐标
                List<Track> trackList = physicalObjectMap.keySet().stream()
                        .sorted().collect(Collectors.toList());
                for(int id=1;id<=tsize;id++) {
                    Track track = trackList.get(id-1);
                    int tr = trackRadius[id-1];
                    int width = 2*tr,height = 2*tr;
                    graphics.drawOval(centerX-tr,centerY-tr,
                            width,height); //画轨道的圆

                    List<Runner> poList = physicalObjectMap.get(track);
                    int sz = poList.size();
                    //按照轨道上物体的数目使物体平均出现在轨道上
                    for(int i=0;i<sz;i++) {
                        Runner runner = poList.get(i);
                        otherDirectory.Painter painter = new Painter();
                        double sitha = i*(2*Math.PI/(sz));
                        int cx = (int)(tr*Math.cos(sitha))+centerX;
                        int cy = (int)(tr*Math.sin(sitha))+centerY;
                        painter.setX(cx);
                        painter.setY(cy);
                        painter.setRadius(10); //代表运动员的圆形半径
                        runner.draw(graphics,painter);
                    }
                }
            }
        };
    }

    /**
     *  根据输入的面板进行可视化
     * @param panel 要可视化的面板
     */
    public void visualize(JPanel panel) {
        JPanel contentPanel = visualizeContentPanel(); //将要显示的内容先放在一个面板上
        panel.removeAll(); //把原来面板上的所有组件移除
        contentPanel.setPreferredSize(new Dimension(800,800)); //给该面板分配首选空间大小
        panel.add(contentPanel); //把要显示的面板内容添加到该面板上
        panel.validate(); //验证该面板和其组件是否有效
        panel.repaint(); //重新绘制该组件
    }
}