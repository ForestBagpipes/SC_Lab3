/*
 * Created by JFormDesigner on Sat Apr 20 08:53:36 CST 2019
 */

package applications.TrackGame.GUI;

import applications.TrackGame.TrackGame;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *  TrackGame的面板
 */
public class TrackGamePanel extends JPanel {
    TrackGame trackGame;
    List<String> trackList;  //轨道列表

    private JLabel labelTitle;
    private JLabel labelStrategy;
    private JComboBox<String> strategyCombox;
    private JLabel labelChangeGroup;
    private JComboBox groupSelectCombox;
    private JPanel drawPanel;
    private JPanel rightPanel;
    private JPanel infoPanel;
    private JLabel label2;
    private JLabel labelState;
    private JLabel label1;
    private JLabel labelEntropy;
    private JPanel  operapanel;
    private JLabel label11;
    private JSeparator separator2;
    private JLabel label4;
    private JComboBox<String> removeTrackCombox;
    private JButton buttonRemoveTrack;
    private JLabel label5;
    private JTextField textFieldAddTrack;
    private JButton buttonAddTrack;
    private JLabel label6;
    private JButton buttonAddPO;
    private JLabel label7;
    private JComboBox<String> removePOCombox;
    private JButton buttonRemovePO;
    private JLabel label10;
    private JSeparator separator1;
    private JLabel label8;
    private JComboBox<String> POTrack1Combox;
    private JComboBox<String> POTrack2Combox;
    private JButton buttonChangePOTrack;
    private JLabel label9;
    private JComboBox<String> POGroup1Combox;
    private JComboBox<String> POGroup2Combox;
    private JButton buttonChangePOGroup;
    //创建TrackGame的面板
    public TrackGamePanel(TrackGame trackGame) {
        this.trackGame = trackGame;
        initComponents();
    }


    /**
     *  更新游戏的信息
     * @param state 系统是否合法
     * @param entropy 轨道系统的熵
     * @param trackList 轨道列表
     * @param poList 运动员列表
     * @param otherPoList 运动员列表
     */
    public void reloadGameInfo(boolean state, double entropy, List<String> trackList, List<String> poList,List<String> otherPoList) {
        this.trackList = trackList;
        String sysState = state?"合法":"不合法"; //state = true时合法
        this.labelState.setText(sysState);
        this.labelEntropy.setText(Double.toString(entropy));
        this.removeTrackCombox.removeAllItems();
        for(String track:trackList) {
            this.removeTrackCombox.addItem(track);
        }
        this.removePOCombox.removeAllItems();
        this.POTrack1Combox.removeAllItems();
        this.POTrack2Combox.removeAllItems();
        this.POGroup1Combox.removeAllItems();
        this.POGroup2Combox.removeAllItems();
        for(String po:poList) {
            this.removePOCombox.addItem(po);
            this.POTrack1Combox.addItem(po);
            this.POTrack2Combox.addItem(po);
            this.POGroup1Combox.addItem(po);
        }
        for(String str:otherPoList) {
            this.POGroup2Combox.addItem(str);
        }

    }

    /**
     *  比赛策略选择改变
     * @param e 项目事件
     */
    private void comboBoxStrategyItemStateChanged(ItemEvent e) {
        switch(e.getStateChange()) {
            case ItemEvent.SELECTED:
                trackGame.selectedGameStrategies((String) e.getItem());
                break;
        }
    }

    /**
     *  选组改变
     * @param e 项目事件
     */
    private void comboBoxGroupSelectItemStateChanged(ItemEvent e) {
        switch(e.getStateChange()) {
            case ItemEvent.SELECTED:
                trackGame.visualizeOrbit(Integer.valueOf((String)e.getItem()));
                break;
        }
    }

    /**
     *  初始化运动员选组组合框
     * @param items 各组合列表
     */
    public void initComboBoxGroupSelectItems(List<String> items) {
        groupSelectCombox.removeAllItems();
        for(String item:items) {
            groupSelectCombox.addItem(item);
        }
    }

    /**
     *  初始化比赛策略选则的选项
     * @param items
     */
    public void initComboBoxStrategyItems(List<String> items) {
        strategyCombox.removeAllItems();
        for(String item:items) {
            strategyCombox.addItem(item);
        }
    }

    /**
     *  得到要绘制的面板
     * @return JPanel类的绘制面板
     */
    public JPanel getDrawPanel() {
        return drawPanel;
    }

    //通过鼠标事件移除轨道
    private void buttonRemoveTrackClick(MouseEvent e) {
        double rmRadius = Double.valueOf((String) this.removeTrackCombox.getSelectedItem());
        trackGame.removeTrack(rmRadius);
    }

    //通过鼠标事件添加轨道
    private void buttonAddTrackClick(MouseEvent e) {
        double addRadius = Double.valueOf(this.textFieldAddTrack.getText());
        trackGame.addTrack(addRadius);
    }

    //通过鼠标事件添加向轨道上添加运动员
    private void buttonAddPOClick(MouseEvent e) {
        JFrame inputframe = new applications.TrackGame.GUI.RunnerFrame(this.trackGame,this.trackList);
        inputframe.setVisible(true);
        inputframe = null;
    }

    //通过鼠标事件把运动员从轨道上删除
    private void buttonRemovePOClick(MouseEvent e) {
        String rmPO = (String) this.removePOCombox.getSelectedItem();
        trackGame.removePhysicalObject(rmPO);
    }

    //通过鼠标事件改变运动员轨道
    private void buttonChangePOTrackClick(MouseEvent e) {
        String raName = (String) this.POTrack1Combox.getSelectedItem();
        String rbName = (String) this.POTrack2Combox.getSelectedItem();
        trackGame.exchangeTrack(raName,rbName);
    }

    /**
     *  通过鼠标事件完成轨道变更
     * @param e 鼠标事件
     */
    private void buttonChangePOGroupClick(MouseEvent e) {
        String raName = (String) this.POGroup1Combox.getSelectedItem();
        String rbName = (String) this.POGroup2Combox.getSelectedItem();
        trackGame.exchangeGroup(raName,rbName);
    }

    /**
     *  面板内的组件初始化
     */
    private void initComponents() {
        labelTitle = new JLabel();
        labelStrategy = new JLabel();
        strategyCombox = new JComboBox<>();
        labelChangeGroup = new JLabel();
        groupSelectCombox = new JComboBox();
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
        removeTrackCombox = new JComboBox<>();
        buttonRemoveTrack = new JButton();
        label5 = new JLabel();
        textFieldAddTrack = new JTextField();
        buttonAddTrack = new JButton();
        label6 = new JLabel();
        buttonAddPO = new JButton();
        label7 = new JLabel();
        removePOCombox = new JComboBox<>();
        buttonRemovePO = new JButton();
        label10 = new JLabel();
        separator1 = new JSeparator();
        label8 = new JLabel();
        POTrack1Combox = new JComboBox<>();
        POTrack2Combox = new JComboBox<>();
        buttonChangePOTrack = new JButton();
        label9 = new JLabel();
        POGroup1Combox = new JComboBox<>();
        POGroup2Combox = new JComboBox<>();
        buttonChangePOGroup = new JButton();
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

        labelTitle.setText("TrackGame轨道系统");
        labelTitle.setFont(labelTitle.getFont().deriveFont(labelTitle.getFont().getStyle() | Font.BOLD, labelTitle.getFont().getSize() + 9f));
        add(labelTitle, "cell 7 5 3 4");
        labelStrategy.setText("比赛策略选择");
        add(labelStrategy, "cell 18 7");
        strategyCombox.addItemListener(e -> comboBoxStrategyItemStateChanged(e));
        add(strategyCombox, "cell 20 7");
        labelChangeGroup.setText("选择小组");
        add(labelChangeGroup, "cell 23 7");
        groupSelectCombox.addItemListener(e -> comboBoxGroupSelectItemStateChanged(e));
        add(groupSelectCombox, "cell 25 7");
        {
            drawPanel.setPreferredSize(new Dimension(800, 800));
            drawPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        }
        add(drawPanel, "cell 0 15 30 28");
        {
            rightPanel.setPreferredSize(new Dimension(400, 800));
            rightPanel.setBackground(new Color(180, 180, 180));
            rightPanel.setLayout(new MigLayout(
                    "hidemode 3",
                    "[fill]" +
                            "[fill]",
                    "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]"));
            {
                infoPanel.setPreferredSize(new Dimension(400, 200));
                infoPanel.setBackground(new Color(204, 204, 120));
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
                label2.setText("\u72b6\u6001\uff1a");
                label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() | Font.BOLD, label2.getFont().getSize() + 8f));
                label2.setForeground(Color.black);
                infoPanel.add(label2, "cell 1 1");
                labelState.setFont(labelState.getFont().deriveFont(labelState.getFont().getSize() + 6f));
                infoPanel.add(labelState, "cell 4 1");
                label1.setText("\u71b5\u503c\uff1a");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 8f));
                label1.setForeground(Color.black);
                infoPanel.add(label1, "cell 1 5");
                labelEntropy.setFont(labelEntropy.getFont().deriveFont(labelEntropy.getFont().getSize() + 6f));
                infoPanel.add(labelEntropy, "cell 4 5");
            }
            rightPanel.add(infoPanel, "cell 0 0 2 2");
            {
                 operapanel.setPreferredSize(new Dimension(400, 600));
                 operapanel.setFont( operapanel.getFont().deriveFont( operapanel.getFont().getSize() + 8f));
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
                                "[]"));

                label11.setText("跑步比赛系统操作");
                label11.setFont(label11.getFont().deriveFont(label11.getFont().getSize() + 12f));
                 operapanel.add(label11, "cell 1 1");
                 operapanel.add(separator2, "cell 1 2 10 1");

                //删除轨道标签
                label4.setText("删除轨道");
                 operapanel.add(label4, "cell 1 3");
                 operapanel.add(removeTrackCombox, "cell 6 3");

                //删除轨道按钮
                buttonRemoveTrack.setText("确认");
                buttonRemoveTrack.setMinimumSize(new Dimension(50, 30));
                buttonRemoveTrack.setPreferredSize(new Dimension(50, 30));
                buttonRemoveTrack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonRemoveTrackClick(e);
                    }
                });
                 operapanel.add(buttonRemoveTrack, "cell 9 3");

                //添加轨道标签
                label5.setText("添加轨道");
                 operapanel.add(label5, "cell 1 4");

                //轨道半径添加文本框
                textFieldAddTrack.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                textFieldAddTrack.setToolTipText("轨道半径");
                 operapanel.add(textFieldAddTrack, "cell 6 4");

                //添加轨道按钮
                buttonAddTrack.setText("确认");
                buttonAddTrack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonAddTrackClick(e);
                    }
                });
                 operapanel.add(buttonAddTrack, "cell 9 4");

                //添加运动员标签
                label6.setText("添加运动员标签");
                 operapanel.add(label6, "cell 1 6");

                //添加运动员按钮
                buttonAddPO.setText("输入");
                buttonAddPO.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonAddPOClick(e);
                    }
                });
                 operapanel.add(buttonAddPO, "cell 9 6");

                //删除运动员标签
                label7.setText("删除运动员");
                 operapanel.add(label7, "cell 1 8");
                 operapanel.add(removePOCombox, "cell 6 8");

                //移除运动员按钮
                buttonRemovePO.setText("确认");
                buttonRemovePO.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonRemovePOClick(e);
                    }
                });
                 operapanel.add(buttonRemovePO, "cell 9 8");

                //系统功能操作标签
                label10.setText("系统功能");
                label10.setFont(label10.getFont().deriveFont(label10.getFont().getSize() + 11f));
                operapanel.add(label10, "cell 1 14");
                operapanel.add(separator1, "cell 1 16 14 1");

                //交换运动员轨道标签
                label8.setText("交换运动员轨道");
                 operapanel.add(label8, "cell 1 18");
                 operapanel.add(POTrack1Combox, "cell 4 18");
                 operapanel.add(POTrack2Combox, "cell 6 18");

                //交换运动员轨道按钮
                buttonChangePOTrack.setText("确认");
                buttonChangePOTrack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonChangePOTrackClick(e);
                    }
                });
                 operapanel.add(buttonChangePOTrack, "cell 9 18");

                //运动员组标签
                label9.setText("交换运动员的组");
                 operapanel.add(label9, "cell 1 21");
                 operapanel.add(POGroup1Combox, "cell 4 21");
                 operapanel.add(POGroup2Combox, "cell 6 21");

                //交换运动员的组的按钮
                buttonChangePOGroup.setText("确认");
                buttonChangePOGroup.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonChangePOGroupClick(e);
                    }
                });
                 operapanel.add(buttonChangePOGroup, "cell 9 21");
            }
            rightPanel.add( operapanel, "cell 0 2 2 3");
        }
        add(rightPanel, "cell 30 15 1 28");
    }
}
