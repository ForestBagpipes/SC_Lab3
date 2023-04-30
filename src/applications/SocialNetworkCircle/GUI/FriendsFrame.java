package applications.SocialNetworkCircle.GUI;

import applications.SocialNetworkCircle.SocialNetworkCircle;
import applications.SocialNetworkCircle.friendsFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *  显示朋友信息的面板
 */
public class FriendsFrame extends JFrame {
    //定义各组件
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JSeparator separator1;
    private JPanel infopanel;
    private JTextField textFieldName;
    private JTextField textFieldAge;
    private JComboBox<String> GenderComBox;
    private JComboBox<String> TrackListComBox;
    private JButton buttonSummit;

    SocialNetworkCircle socialNetworkCircle;
    List<String> trackList;

    // TODO constructor
    public FriendsFrame(SocialNetworkCircle socialNetworkCircle, List<String> trackList) {
        this.socialNetworkCircle = socialNetworkCircle;
        this.trackList = trackList;
        initComponents();
        GenderComBox.addItem("M");
        GenderComBox.addItem("F");
        this.TrackListComBox.removeAllItems();
        for(String str:trackList) {
            this.TrackListComBox.addItem(str);
        }
    }

    /**
     *  通过提交按钮提交信息
     * @param e 鼠标事件
     */
    private void buttonSummitMousePressed(MouseEvent e) {
        friendsFactory fsf = new friendsFactory();
        String name = this.textFieldName.getText();
        int age = Integer.valueOf(this.textFieldAge.getText());
        String gender = (String) this.GenderComBox.getSelectedItem();
        double trackRadius = Double.valueOf((String) this.TrackListComBox.getSelectedItem());
        socialNetworkCircle.addPhysicalObject(fsf.createFriends_2(name,gender,age),trackRadius);
        this.setVisible(false);
    }

    /**
     *  各组件初始化
     */
    private void initComponents() {
        label1 = new JLabel();
        separator1 = new JSeparator();
        infopanel = new JPanel();
        label2 = new JLabel();
        textFieldName = new JTextField();
        label4 = new JLabel();
        textFieldAge = new JTextField();
        label3 = new JLabel();
        GenderComBox = new JComboBox<>();
        label5 = new JLabel();
        TrackListComBox = new JComboBox<>();
        buttonSummit = new JButton();
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
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
                        "[fill]",
                "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]"));

        //详细信息标签
        label1.setText("详细信息");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 12f));
        contentPane.add(label1, "cell 4 0");
        contentPane.add(separator1, "cell 5 1");
        //信息面板
        {
            infopanel.setPreferredSize(new Dimension(520, 401));
            infopanel.setBackground(Color.white);
            infopanel.setLayout(new MigLayout(
                    "hidemode 3",
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
                            "[]"));

            //名称标签
            label2.setText("名称");
            label2.setForeground(Color.black);
            label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 6f));
            infopanel.add(label2, "cell 1 1");

            //名称文本框
            textFieldName.setBackground(Color.white);
            textFieldName.setPreferredSize(new Dimension(100, 27));
            infopanel.add(textFieldName, "cell 8 1");

            //年龄标签
            label4.setText("年龄");
            label4.setForeground(Color.black);
            label4.setFont(label4.getFont().deriveFont(label4.getFont().getSize() + 6f));
            infopanel.add(label4, "cell 1 3");

            //年龄文本框
            textFieldAge.setPreferredSize(new Dimension(100, 27));
            infopanel.add(textFieldAge, "cell 8 3");

            //性别标签
            label3.setText("性别");
            label3.setForeground(Color.black);
            label3.setFont(label3.getFont().deriveFont(label3.getFont().getStyle() & ~Font.BOLD, label3.getFont().getSize() + 6f));
            infopanel.add(label3, "cell 1 5");

            //性别选择组合框
            GenderComBox.setOpaque(false);
            GenderComBox.setPreferredSize(new Dimension(120, 38));
            infopanel.add(GenderComBox, "cell 8 5");
        }
        contentPane.add(infopanel, "cell 4 2");

        //添加标签
        label5.setText("添加到：");
        label5.setForeground(Color.black);
        label5.setFont(label5.getFont().deriveFont(label5.getFont().getSize() + 6f));
        contentPane.add(label5, "cell 4 3");
        contentPane.add(TrackListComBox, "cell 4 3");

        //提交按钮
        buttonSummit.setText("确认");
        buttonSummit.setPreferredSize(new Dimension(150, 50));
        buttonSummit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonSummitMousePressed(e);
            }
        });
        contentPane.add(buttonSummit, "cell 2 6 5 1");
        pack();
        setLocationRelativeTo(getOwner());
    }
}
