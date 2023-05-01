package applications.AtomStructure.GUI;

import applications.AtomStructure.AtomStructure;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AtomstructurePanel extends JPanel {
    AtomStructure atomStructure;
    List<String> trackList;

    // 定义各组件
    private JLabel labelTitle;
    private JPanel drawPanel;
    private JPanel rightPanel;
    private JPanel infoPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    private JLabel label12;
    private JLabel labelState;
    private JLabel labelEntropy;
    private JPanel operapanel;
    private JSeparator separator2;
    private JComboBox<String> comBoxRemoveTrack;
    private JButton buttonRemoveTrack;
    private JTextField textFieldAddTrack;
    private JButton buttonAddTrack;
    private JButton buttonAddPO;
    private JComboBox<String> comBoxRemovePO;
    private JButton buttonRemovePO;
    private JSeparator separator1;
    private JComboBox comBoxSource;
    private JComboBox comBoxTarget;
    private JButton buttonTransit;
    private JComboBox comBoxHistory;
    private JButton buttonReback;

    public AtomstructurePanel(AtomStructure atomStructure) {
        this.atomStructure = atomStructure;
        initComponents();
    }

    /**
     *  更新轨道系统信息
     * @param state 轨道系统的状态
     * @param entropy 轨道系统的熵
     * @param trackList 轨道列表
     * @param poList 轨道物体的列表
     * @param history 轨道系统的历史记录
     */
    public void reloadGameInfo(boolean state, double entropy, java.util.List<String> trackList, java.util.List<String> poList,List<String> history) {
        this.trackList = trackList;
        String sysState = state?"合法":"不合法";
        this.labelState.setText(sysState);
        this.labelEntropy.setText(Double.toString(entropy));
        this.comBoxRemovePO.removeAllItems();
        this.comBoxRemoveTrack.removeAllItems();
        this.comBoxSource.removeAllItems();
        this.comBoxTarget.removeAllItems();
        for(String str:trackList) {
            this.comBoxRemoveTrack.addItem(str);
            this.comBoxRemovePO.addItem(str);
            this.comBoxSource.addItem(str);
            this.comBoxTarget.addItem(str);
        }
        this.comBoxHistory.removeAllItems();
        for(String str:history) {
            this.comBoxHistory.addItem(str);
        }
    }


    public JPanel getDrawPanel() {
        return drawPanel;
    }

    //点击按钮移除轨道
    private void buttonRemoveTrackMousePressed(MouseEvent e) {
        double rmRadius = Double.valueOf((String) this.comBoxRemoveTrack.getSelectedItem());
        atomStructure.removeTrack(rmRadius);
    }

    //点击按钮添加轨道
    private void buttonAddTrackMousePressed(MouseEvent e) {
        double addRadius = Double.valueOf(this.textFieldAddTrack.getText());
        atomStructure.addTrack(addRadius);
    }

    //点击按钮添加物体
    private void buttonAddPOMousePressed(MouseEvent e) {
        JFrame inputframe = new electronFrame(this.atomStructure,this.trackList);
        inputframe.setVisible(true);
        inputframe = null;
    }

    //点击按钮移除轨道上的物体
    private void buttonRemovePOMousePressed(MouseEvent e) {
        double rmRadius = Double.valueOf((String) this.comBoxRemovePO.getSelectedItem());
        atomStructure.removePhysicalObject(rmRadius);
    }

    //点击按钮移动物体
    private void buttonTransitMousePressed(MouseEvent e) {
        System.out.println(this.comBoxSource.getSelectedItem());
        double source = Double.valueOf((String) this.comBoxSource.getSelectedItem());
        double target = Double.valueOf((String) this.comBoxTarget.getSelectedItem());
        atomStructure.electronTransit(source,target);
    }

    //点击按钮回退历史
    private void buttonRebackMousePressed(MouseEvent e) {
        int index = this.comBoxHistory.getSelectedIndex();
        this.atomStructure.rebackHistory(index);
    }

    //各组件初始化
    private void initComponents() {
        labelTitle = new JLabel();
        drawPanel = new JPanel();
        rightPanel = new JPanel();
        infoPanel = new JPanel();
        label2 = new JLabel();
        labelState = new JLabel();
        label1 = new JLabel();
        labelEntropy = new JLabel();
        operapanel = new JPanel();
        label11 = new JLabel();
        separator2 = new JSeparator();
        label4 = new JLabel();
        comBoxRemoveTrack = new JComboBox<>();
        buttonRemoveTrack = new JButton();
        label5 = new JLabel();
        textFieldAddTrack = new JTextField();
        buttonAddTrack = new JButton();
        label6 = new JLabel();
        buttonAddPO = new JButton();
        label7 = new JLabel();
        comBoxRemovePO = new JComboBox<>();
        buttonRemovePO = new JButton();
        label10 = new JLabel();
        separator1 = new JSeparator();
        label8 = new JLabel();
        label3 = new JLabel();
        comBoxSource = new JComboBox();
        label9 = new JLabel();
        comBoxTarget = new JComboBox();
        buttonTransit = new JButton();
        label12 = new JLabel();
        comBoxHistory = new JComboBox();
        buttonReback = new JButton();
        setLayout(new MigLayout(
                "hidemode 3",
                "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]",
                "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]"));

        //系统标题标签
        labelTitle.setText("AtomStructure-轨道系统");
        labelTitle.setFont(labelTitle.getFont().deriveFont(labelTitle.getFont().getStyle() | Font.BOLD, labelTitle.getFont().getSize() + 9f));
        add(labelTitle, "cell 7 5 3 4");

        //轨道图形绘制面板
        {
            drawPanel.setPreferredSize(new Dimension(800, 800));
            drawPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        }
        add(drawPanel, "cell 0 15 30 28");

        //右侧面板
        {
            rightPanel.setPreferredSize(new Dimension(400, 800));
            rightPanel.setBackground(new Color(204, 204, 204));
            rightPanel.setLayout(new MigLayout(
                    "hidemode 3",
                    "[fill]" +
                            "[fill]",
                    "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]"));

            //信息面板
            {
                infoPanel.setPreferredSize(new Dimension(400, 200));
                infoPanel.setBackground(new Color(255, 255, 255));
                infoPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]",
                        "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]"));

                //系统状态标签
                label2.setText("状态");
                label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() | Font.BOLD, label2.getFont().getSize() + 8f));
                label2.setForeground(Color.black);
                infoPanel.add(label2, "cell 1 1");

                //系统状态显示
                labelState.setFont(labelState.getFont().deriveFont(labelState.getFont().getSize() + 6f));
                infoPanel.add(labelState, "cell 4 1");

                //熵标签
                label1.setText("熵");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 8f));
                label1.setForeground(Color.black);
                infoPanel.add(label1, "cell 1 5");

                //熵显示
                labelEntropy.setFont(labelEntropy.getFont().deriveFont(labelEntropy.getFont().getSize() + 6f));
                infoPanel.add(labelEntropy, "cell 4 5");
            }
            rightPanel.add(infoPanel, "cell 0 0 2 2");

            //系统操作面板
            {
                operapanel.setPreferredSize(new Dimension(400, 600));
                operapanel.setFont(operapanel.getFont().deriveFont(operapanel.getFont().getSize() + 8f));
                operapanel.setLayout(new MigLayout(
                        "hidemode 3",
                        "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]" +
                                "[fill]",
                        "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]"));

                //系统功能标签
                label11.setText("系统功能");
                label11.setFont(label11.getFont().deriveFont(label11.getFont().getSize() + 12f));
                operapanel.add(label11, "cell 1 1");
                operapanel.add(separator2, "cell 1 2 17 1");

                //删除轨道标签
                label4.setText("删除轨道");
                operapanel.add(label4, "cell 1 3");
                operapanel.add(comBoxRemoveTrack, "cell 8 3");

                //删除轨道按钮
                buttonRemoveTrack.setText("确认");
                buttonRemoveTrack.setMinimumSize(new Dimension(50, 30));
                buttonRemoveTrack.setPreferredSize(new Dimension(50, 30));
                buttonRemoveTrack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonRemoveTrackMousePressed(e);
                    }
                });
                operapanel.add(buttonRemoveTrack, "cell 17 3");

                //添加轨道标签
                label5.setText("添加轨道");
                operapanel.add(label5, "cell 1 4");

                //轨道半径输入文本框
                textFieldAddTrack.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                textFieldAddTrack.setToolTipText("轨道半径");
                operapanel.add(textFieldAddTrack, "cell 8 4");

                //添加轨道按钮
                buttonAddTrack.setText("确认");
                buttonAddTrack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonAddTrackMousePressed(e);
                    }
                });
                operapanel.add(buttonAddTrack, "cell 17 4");

                //添加轨道物体标签
                label6.setText("添加电子");
                operapanel.add(label6, "cell 1 6");

                //添加轨道物体电子按钮
                buttonAddPO.setText("输入");
                buttonAddPO.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonAddPOMousePressed(e);
                    }
                });
                operapanel.add(buttonAddPO, "cell 17 6");

                //删除物体标签
                label7.setText("删除轨道上任一物体");
                operapanel.add(label7, "cell 1 8");
                operapanel.add(comBoxRemovePO, "cell 8 8");

                //删除物体按钮
                buttonRemovePO.setText("确认");
                buttonRemovePO.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonRemovePOMousePressed(e);
                    }
                });
                operapanel.add(buttonRemovePO, "cell 17 8");

                //系统功能标签
                label10.setText("系统功能");
                label10.setFont(label10.getFont().deriveFont(label10.getFont().getSize() + 11f));
                operapanel.add(label10, "cell 1 14");
                operapanel.add(separator1, "cell 1 15 17 2");

                //电子跃迁标签
                label8.setText("轨道电子跃迁");
                operapanel.add(label8, "cell 1 17");

                //设置原来轨道的标签
                label3.setText("原来的轨道");
                operapanel.add(label3, "cell 2 17");
                operapanel.add(comBoxSource, "cell 4 17");

                //设置目标轨道的标签
                label9.setText("目标轨道");
                operapanel.add(label9, "cell 6 17");
                operapanel.add(comBoxTarget, "cell 8 17");

                //交换物体按钮
                buttonTransit.setText("确认");
                buttonTransit.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonTransitMousePressed(e);
                    }
                });
                operapanel.add(buttonTransit, "cell 17 17");

                //历史回退标签
                label12.setText("回退历史");
                operapanel.add(label12, "cell 1 19");
                operapanel.add(comBoxHistory, "cell 4 19");

                //历史回退按钮
                buttonReback.setText("确认");
                buttonReback.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonRebackMousePressed(e);
                    }
                });
                operapanel.add(buttonReback, "cell 17 19");
            }
            rightPanel.add(operapanel, "cell 1 2 1 3");
        }
        add(rightPanel, "cell 30 15 1 28");
    }
}
