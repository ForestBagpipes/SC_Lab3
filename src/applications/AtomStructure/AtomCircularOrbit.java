package applications.AtomStructure;

import applications.AtomStructure.memory.memory;
import circularOrbit.ConcreteCircularOrbit;
import otherDirectory.Painter;
import physicalObject.PhysicalObject;
import track.Track;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  原子轨道系统的具体类
 */
public class AtomCircularOrbit extends ConcreteCircularOrbit<nucleus,electron> {
    private String elementName;

    public AtomCircularOrbit(String elementName) {
        this.elementName = elementName;
    }

    /**
     * 进行记录的回退
     * @param memoryList 保存所有操作历史的列表
     */
    public void reback(List<memory> memoryList) {
        //保存原来的关系的副本
        Map<Track,List<electron>> copy = new HashMap<>();
        for(Track track:this.physicalObjectMap.keySet()) {
            copy.put(track,new ArrayList<>());
            for(electron electron:physicalObjectMap.get(track)) {
                copy.get(track).add(electron);
            }
        }
        //回退历史
        int len = memoryList.size();
        for(int i=len-1;i>=0;i--) {
            memory mem = memoryList.get(i);
            assert getTrackForPO(mem.getElectron()).equals(mem.getTrack2()):"操作历史记录错误";
            transit(mem.getElectron(),mem.getElectron(),mem.getTrack1());
        }
    }

    /**
     *  生成一个正方形的可视化面板
     * @return 生成的JPanel类型面板
     */
    @Override
    public JPanel visualizeContentPanel() {
        return new JPanel() {
            //重写paint方法
            @Override
            public void paint(Graphics graphics) {
                final int pnLength = 800;
                final int padding = 100;
                int tsize  = physicalObjectMap.size();
                int[] trackRadius = new int[tsize];
                for(int i=0;i<tsize;i++) {
                    trackRadius[i] = (pnLength-padding)/(2*tsize)*(i+1);
                }
                super.paint(graphics);
                int centerX = pnLength/2,centerY = pnLength/2;  //圆心的x坐标和Y坐标
                //画一个原子核
                graphics.setColor(Color.red); //设置画笔颜色
                Painter npainter = new Painter();
                npainter.setX(centerX); npainter.setY(centerY);
                npainter.setRadius(20);
                npainter.setOvalColor(Color.red);
                centralObject.draw(graphics,npainter);
                //得到轨道的列表
                List<Track> trackList = physicalObjectMap.keySet().stream()
                        .sorted().collect(Collectors.toList());
                for(int i = 1;i<=tsize;i++) {
                    Track track = trackList.get(i-1);
                    int tr = trackRadius[i-1]; //轨道的半径
                    int width = 2*tr,height = 2*tr;
                    graphics.drawOval(centerX-tr,centerY-tr,
                            width,height); //画轨道
                    // 画各个轨道上的电子
                    List<electron> electrons = physicalObjectMap.get(track);
                    int size = physicalObjectMap.get(track).size();
                    for(int j=0;j<size;j++) {
                        Painter painter = new Painter();
                        double theta = j*(2*Math.PI/(size));
                        int cx = (int)(tr*Math.cos(theta))+centerX; //电子的圆心X坐标
                        int cy = (int)(tr*Math.sin(theta))+centerY; //电子的圆心Y坐标
                        painter.setX(cx); painter.setY(cy);
                        painter.setRadius(2);
                        electrons.get(j).draw(graphics,painter);
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

    /**
     *  重写添加轨道系统物体的代码,因为电子每个轨道上都相同，不能像原来那样判定
     * @param object 需要添加的轨道物体，物体的类型必须为PhysicalObject类型物体或其子类，不为null
     * @param track  需要将物体添加到的轨道，轨道在系统中需要存在
     * @return true 如果添加成功，反之返回false
     */
    @Override
    public boolean addPhysicalObject(electron object, Track track) {
        if (!physicalObjectMap.containsKey(track)) {
            System.out.println("不存在该轨道");
            return false;
        }
        physicalObjectMap.get(track).add(object);
        if (!relationsOf2T.containsKey(object)) {
            relationsOf2T.put(object, new ArrayList<>());
        }
        return true;
    }
}
