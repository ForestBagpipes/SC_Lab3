package applications.SocialNetworkCircle.GUI;

import applications.SocialNetworkCircle.SocialNetworkCircle;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SocialNetworkCirclePanel extends JPanel {
    SocialNetworkCircle socialNetworkCircle;
    List<String> trackList;
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
    
    private JLabel labelTitle;
    private JPanel drawPanel;
    private JPanel rightPanel;
    private JPanel infoPanel;
    private JLabel labelState;
    private JLabel labelEntropy;
    private JPanel operaPanel;
    private JSeparator separator1;
    private JSeparator separator2;
    private JComboBox<String> RemoveTrackCombox;
    private JButton buttonRemoveTrack;
    private JTextField textFieldAddTrack;
    private JButton buttonAddTrack;
    private JButton buttonAddPO;
    private JComboBox<String> RemovePOCombox;
    private JButton buttonRemovePO;
    private JComboBox InfoDiffCombox;
    private JLabel labelInfoDiff;
    private JComboBox<String> comboBoxRelationU;
    private JComboBox<String> comboBoxRelationV;
    private JTextField textFieldDensity;
    private JButton buttonAddRelation;
    private JComboBox RmRelationUCombox;
    private JComboBox RmRelationVCombox;
    private JButton buttonRmRelation;
    private JComboBox<String> DisUCombox;
    private JComboBox<String> DisVCombox;
    private JLabel labelDistant;
    public SocialNetworkCirclePanel(SocialNetworkCircle socialNetworkCircle) {
        this.socialNetworkCircle = socialNetworkCircle;
        initComponents();
    }

    /**
     *  重新加载系统信息
     * @param state 系统状态
     * @param entropy 系统熵
     * @param trackList 系统的轨道列表
     * @param poList 系统的轨道物体列表
     * @param relObjects 系统关联物体列表
     * @param firstTrackPOs 1级好友列表
     */
    public void reloadGameInfo(boolean state, double entropy, List<String> trackList, List<String> poList,List<String> relObjects,List<String> firstTrackPOs) {
        this.trackList = trackList;
        String sysState = state?"合法":"不合法";
        this.labelState.setText(sysState);
        this.labelEntropy.setText(Double.toString(entropy));
        this.RemoveTrackCombox.removeAllItems();
        for(String str:trackList) {
            this.RemoveTrackCombox.addItem(str);
        }
        this.RemovePOCombox.removeAllItems();
        this.DisVCombox.removeAllItems();
        this.DisUCombox.removeAllItems();
        this.RmRelationUCombox.removeAllItems();
        for(String str:poList) {
            this.RemovePOCombox.addItem(str);
            this.DisVCombox.addItem(str);
            this.DisUCombox.addItem(str);
        }
        this.comboBoxRelationU.removeAllItems();
        this.comboBoxRelationV.removeAllItems();
        for(String str:relObjects) {
            this.comboBoxRelationU.addItem(str);
            this.comboBoxRelationV.addItem(str);
            this.RmRelationUCombox.addItem(str);
        }
        this.InfoDiffCombox.removeAllItems();
        for(String str:firstTrackPOs) {
            this.InfoDiffCombox.addItem(str);
        }
    }

    /**
     *  删除轨道按钮事件
     * @param e 鼠标事件
     */
    private void buttonRemoveTrackMousePressed(MouseEvent e) {
        double rmRadius = Double.valueOf((String) this.RemoveTrackCombox.getSelectedItem());
        socialNetworkCircle.removeTrack(rmRadius);
    }

    /**
     *  添加轨道按钮事件
     * @param e 鼠标事件
     */
    private void buttonAddTrackMousePressed(MouseEvent e) {
        double addRadius = Double.valueOf(this.textFieldAddTrack.getText());
        socialNetworkCircle.addTrack(addRadius);
    }

    /**
     *  添加轨道物体按钮事件
     * @param e 鼠标事件
     */
    private void buttonAddPOMousePressed(MouseEvent e) {
        JFrame inputframe = new FriendsFrame(this.socialNetworkCircle,this.trackList);
        inputframe.setVisible(true);
        inputframe = null;
    }

    /**
     *  删除轨道物体按钮事件
     * @param e 鼠标事件
     */
    private void buttonRemovePOMousePressed(MouseEvent e) {
        String rmPO = (String) this.RemovePOCombox.getSelectedItem();
        socialNetworkCircle.removePhysicalObject(rmPO);
    }

    /**
     *  添加关系按钮事件
     * @param e 鼠标事件
     */
    private void buttonAddRelationMousePressed(MouseEvent e) {
        String nameU = (String) this.comboBoxRelationU.getSelectedItem();
        String nameV = (String) this.comboBoxRelationV.getSelectedItem();
        double density = Double.valueOf(this.textFieldDensity.getText());
        socialNetworkCircle.addRelation(nameU,nameV,density);
    }

    /**
     *  更新好友之间的距离
     */
    private void refreshDistant() {
        String nameU = (String) this.DisUCombox.getSelectedItem();
        String nameV = (String) this.DisVCombox.getSelectedItem();
        if(nameU==null || nameV==null) return ;
        int dis = this.socialNetworkCircle.getLogicalDistance(nameU,nameV);
        String displayText = dis==Integer.MAX_VALUE? "INF":Integer.toString(dis-1);
        this.labelDistant.setText(displayText);
    }

    /**
     *  好友U距离更新按钮事件，为关系发出者
     * @param e 鼠标事件
     */
    private void comboBoxDisUItemStateChanged(ItemEvent e) {
        refreshDistant();
    }

    /**
     *  好友U距离更新按钮事件，为关系接受者
     * @param e 鼠标事件
     */
    private void comboBoxDisVItemStateChanged(ItemEvent e) {
        refreshDistant();
    }

    /**
     *  删除关系按钮事件
     * @param e 鼠标事件
     */
    private void buttonRmRelationMousePressed(MouseEvent e) {
        String nameU = (String) this.RmRelationUCombox.getSelectedItem();
        String nameV = (String) this.RmRelationVCombox.getSelectedItem();
        this.socialNetworkCircle.removeRelation(nameU,nameV);
    }

    /**
     * 根据关系修改RelationV按钮事件
     * @param e 鼠标事件
     */
    private void comboBoxRmRelationUItemStateChanged(ItemEvent e) {
        String nameU = (String) e.getItem();
        if(nameU==null) return ;
        List<String> surroudings = socialNetworkCircle.getSurroudings(nameU);
        this.RmRelationVCombox.removeAllItems();
        for(String str:surroudings) {
            this.RmRelationVCombox.addItem(str);
        }
    }

    /**
     *  所有组件的初始化
     */
    private void initComponents() {
        labelTitle = new JLabel();
        drawPanel = new JPanel();
        rightPanel = new JPanel();
        infoPanel = new JPanel();
        label2 = new JLabel();
        labelState = new JLabel();
        label1 = new JLabel();
        labelEntropy = new JLabel();
        operaPanel = new JPanel();
        label11 = new JLabel();
        separator2 = new JSeparator();
        label4 = new JLabel();
        RemoveTrackCombox = new JComboBox<>();
        buttonRemoveTrack = new JButton();
        label5 = new JLabel();
        textFieldAddTrack = new JTextField();
        buttonAddTrack = new JButton();
        label6 = new JLabel();
        buttonAddPO = new JButton();
        label7 = new JLabel();
        RemovePOCombox = new JComboBox<>();
        buttonRemovePO = new JButton();
        label10 = new JLabel();
        separator1 = new JSeparator();
        label3 = new JLabel();
        InfoDiffCombox = new JComboBox();
        labelInfoDiff = new JLabel();
        label8 = new JLabel();
        comboBoxRelationU = new JComboBox<>();
        comboBoxRelationV = new JComboBox<>();
        textFieldDensity = new JTextField();
        buttonAddRelation = new JButton();
        label12 = new JLabel();
        RmRelationUCombox = new JComboBox();
        RmRelationVCombox = new JComboBox();
        buttonRmRelation = new JButton();
        label9 = new JLabel();
        DisUCombox = new JComboBox<>();
        DisVCombox = new JComboBox<>();
        labelDistant = new JLabel();
        
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
        labelTitle.setText("SocialNetwork-轨道系统");
        labelTitle.setFont(labelTitle.getFont().deriveFont(labelTitle.getFont().getStyle() | Font.BOLD, labelTitle.getFont().getSize() + 9f));
        add(labelTitle, "cell 7 5 3 4");

        //绘画面板布局
        {
            drawPanel.setPreferredSize(new Dimension(800, 800));
            drawPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        }
        add(drawPanel, "cell 0 15 30 28");

        //右侧面板布局
        {
            rightPanel.setPreferredSize(new Dimension(400, 800));
            rightPanel.setBackground(new Color(150, 150, 150));
            rightPanel.setLayout(new MigLayout(
                    "hidemode 3",
                    "[fill]" +
                            "[fill]",
                    "[]" +
                            "[]" +
                            "[]" +
                            "[]" +
                            "[]"));

            //信息面板布局
            {
                infoPanel.setPreferredSize(new Dimension(400, 200));
                infoPanel.setBackground(new Color(153, 153, 153));
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

                //状态标签
                label2.setText("状态");
                label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() | Font.BOLD, label2.getFont().getSize() + 8f));
                label2.setForeground(Color.black);
                infoPanel.add(label2, "cell 1 1");

                //状态显示标签
                labelState.setFont(labelState.getFont().deriveFont(labelState.getFont().getSize() + 6f));
                infoPanel.add(labelState, "cell 4 1");

                //熵标签
                label1.setText("熵");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 8f));
                label1.setForeground(Color.black);
                infoPanel.add(label1, "cell 1 5");

                //熵值显示标签
                labelEntropy.setFont(labelEntropy.getFont().deriveFont(labelEntropy.getFont().getSize() + 6f));
                infoPanel.add(labelEntropy, "cell 4 5");
            }
            rightPanel.add(infoPanel, "cell 0 0 2 2");

            // 操作面板布局
            {
                operaPanel.setPreferredSize(new Dimension(400, 600));
                operaPanel.setFont(operaPanel.getFont().deriveFont(operaPanel.getFont().getSize() + 8f));
                operaPanel.setLayout(new MigLayout(
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
                                "[]"));

                //系统改变标签
                label11.setText("更改系统");
                label11.setFont(label11.getFont().deriveFont(label11.getFont().getSize() + 12f));
                operaPanel.add(label11, "cell 1 1");
                operaPanel.add(separator2, "cell 1 2 11 1");

                //删除轨道事件
                label4.setText("删除轨道");
                operaPanel.add(label4, "cell 1 3");
                operaPanel.add(RemoveTrackCombox, "cell 6 3");

                //删除轨道确认按钮
                buttonRemoveTrack.setText("确认");
                buttonRemoveTrack.setMinimumSize(new Dimension(50, 30));
                buttonRemoveTrack.setPreferredSize(new Dimension(50, 30));
                buttonRemoveTrack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonRemoveTrackMousePressed(e);
                    }
                });
                operaPanel.add(buttonRemoveTrack, "cell 10 3");

                //添加轨道标签
                label5.setText("添加轨道");
                operaPanel.add(label5, "cell 1 4");

                //添加轨道半径输入文本框
                textFieldAddTrack.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                textFieldAddTrack.setToolTipText("轨道半径");
                operaPanel.add(textFieldAddTrack, "cell 6 4");

                //添加轨道确认按钮
                buttonAddTrack.setText("确认");
                buttonAddTrack.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonAddTrackMousePressed(e);
                    }
                });
                operaPanel.add(buttonAddTrack, "cell 10 4");

                //添加好友标签
                label6.setText("添加好友");
                operaPanel.add(label6, "cell 1 6");

                //添加好友按钮
                buttonAddPO.setText("输入：");
                buttonAddPO.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonAddPOMousePressed(e);
                    }
                });
                operaPanel.add(buttonAddPO, "cell 10 6");

                //删除好友标签
                label7.setText("删除好友");
                operaPanel.add(label7, "cell 1 8");
                operaPanel.add(RemovePOCombox, "cell 6 8");

                //删除好友确认按钮
                buttonRemovePO.setText("确认");
                buttonRemovePO.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonRemovePOMousePressed(e);
                    }
                });
                operaPanel.add(buttonRemovePO, "cell 10 8");

                //系统功能标签
                label10.setText("系统功能");
                label10.setFont(label10.getFont().deriveFont(label10.getFont().getSize() + 11f));
                operaPanel.add(label10, "cell 1 14");
                operaPanel.add(separator1, "cell 1 16 15 1");

                //添加关系文本框
                label8.setText("添加关系");
                operaPanel.add(label8, "cell 1 18");
                operaPanel.add(comboBoxRelationU, "cell 6 18");
                operaPanel.add(comboBoxRelationV, "cell 7 18");

                //添加关系确认按钮
                buttonAddRelation.setText("确认");
                buttonAddRelation.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonAddRelationMousePressed(e);
                    }
                });
                operaPanel.add(buttonAddRelation, "cell 10 18");

                //删除关系文本框
                label12.setText("删除关系");
                operaPanel.add(label12, "cell 1 20");

                //删除关系选项框
                RmRelationUCombox.addItemListener(e -> comboBoxRmRelationUItemStateChanged(e));
                operaPanel.add(RmRelationUCombox, "cell 6 20");
                operaPanel.add(RmRelationVCombox, "cell 7 20");

                //删除关系确认按钮
                buttonRmRelation.setText("确认");
                buttonRmRelation.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonRmRelationMousePressed(e);
                    }
                });
                operaPanel.add(buttonRmRelation, "cell 10 20");

                //逻辑距离文本框
                label9.setText("逻辑距离");
                operaPanel.add(label9, "cell 1 21");

                //U的距离组合框
                DisUCombox.addItemListener(e -> comboBoxDisUItemStateChanged(e));
                operaPanel.add(DisUCombox, "cell 6 21");

                //V的距离组合框
                DisVCombox.setDoubleBuffered(true);
                DisVCombox.addItemListener(e -> comboBoxDisVItemStateChanged(e));
                operaPanel.add(DisVCombox, "cell 7 21");

                //距离显示标签
                labelDistant.setText("无");
                labelDistant.setHorizontalAlignment(SwingConstants.CENTER);
                operaPanel.add(labelDistant, "cell 10 21");
            }
            rightPanel.add(operaPanel, "cell 0 2 2 3");
        }
        add(rightPanel, "cell 30 15 1 28");
    }

    /**
     *  得到要绘制面板
     * @return 绘制面板
     */
    public JPanel getDrawPanel() {
        return drawPanel;
    }
}
