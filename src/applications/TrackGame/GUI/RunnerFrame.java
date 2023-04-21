/*
 * Created by JFormDesigner on Sun Apr 21 16:35:47 CST 2019
 */

package applications.TrackGame.GUI;

import applications.TrackGame.Runner;
import applications.TrackGame.TrackGame;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *  选手信息框架
 */
public class RunnerFrame extends JFrame {
    TrackGame trackGame;
    //定义各组件
    private JLabel label1;
    private JSeparator separator1;
    private JPanel informationPanel;
    private JLabel label2;
    private JTextField NameText;
    private JLabel label4;
    private JTextField IDText;
    private JLabel label5;
    private JTextField NationText;
    private JLabel label6;
    private JTextField AgeText;
    private JLabel label7;
    private JTextField BestScoreText;
    private JLabel label8;
    private JComboBox<String> comboBoxTrackList;
    private JButton buttonSummit;
    // TODO constructor
    public RunnerFrame(TrackGame trackGame, List<String> trackList) {
        this.trackGame = trackGame;
        initComponents();
        this.comboBoxTrackList.removeAllItems();
        for(String str:trackList) {
            this.comboBoxTrackList.addItem(str);
        }
    }

    /**
     *  通过鼠标单击提交按钮提交选手信息
     * @param e 鼠标事件：单击
     */
    private void buttonSummitClick(MouseEvent e) {
        String obName = this.NameText.getText();
        int id = Integer.valueOf(this.IDText.getText());
        String nation = this.NationText.getText();
        int age = Integer.valueOf(this.AgeText.getText());
        double bestScore = Double.valueOf(this.BestScoreText.getText());
        Runner runner = new Runner(obName,nation,id,age,bestScore);
        double trackRadius = Double.valueOf((String) this.comboBoxTrackList.getSelectedItem());
        trackGame.addPhysicalObject(runner,trackRadius);
        this.setVisible(false);
    }

    /**
     *  初始化各组件
     */
    private void initComponents() {
        label1 = new JLabel();
        separator1 = new JSeparator();
        informationPanel = new JPanel();
        label2 = new JLabel();
        NameText = new JTextField();
        label4 = new JLabel();
        IDText = new JTextField();
        label5 = new JLabel();
        NationText = new JTextField();
        label6 = new JLabel();
        AgeText = new JTextField();
        label7 = new JLabel();
        BestScoreText = new JTextField();
        label8 = new JLabel();
        comboBoxTrackList = new JComboBox<>();
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
        label1.setText("运动员信息");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 12f));
        contentPane.add(label1, "cell 4 0");
        contentPane.add(separator1, "cell 5 1");
        {
            informationPanel.setPreferredSize(new Dimension(600, 500));
            informationPanel.setBackground(Color.white);
            informationPanel.setLayout(new MigLayout(
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
                            "[]"));

            label2.setText("姓名");
            label2.setForeground(Color.black);
            label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 6f));
            informationPanel.add(label2, "cell 1 1");

            NameText.setBackground(Color.white);
            NameText.setMinimumSize(new Dimension(100, 30));
            informationPanel.add(NameText, "cell 8 1");

            label4.setText("ID：");
            label4.setForeground(Color.black);
            label4.setFont(label4.getFont().deriveFont(label4.getFont().getSize() + 6f));
            informationPanel.add(label4, "cell 1 2");
            informationPanel.add(IDText, "cell 8 2");
            label5.setText("国家：");
            label5.setForeground(Color.black);
            label5.setFont(label5.getFont().deriveFont(label5.getFont().getSize() + 6f));
            informationPanel.add(label5, "cell 1 3");
            informationPanel.add(NationText, "cell 8 3");
            label6.setText("年龄：");
            label6.setForeground(Color.black);
            label6.setFont(label6.getFont().deriveFont(label6.getFont().getSize() + 6f));
            informationPanel.add(label6, "cell 1 4");
            informationPanel.add(AgeText, "cell 8 4");
            label7.setText("最好成绩：");
            label7.setForeground(Color.black);
            label7.setFont(label7.getFont().deriveFont(label7.getFont().getSize() + 6f));
            informationPanel.add(label7, "cell 1 5");
            informationPanel.add(BestScoreText, "cell 8 5");
        }
        contentPane.add(informationPanel, "cell 4 2");
        label8.setText("添加到：");
        label8.setForeground(Color.black);
        label8.setFont(label8.getFont().deriveFont(label8.getFont().getSize() + 6f));
        contentPane.add(label8, "cell 4 3");
        contentPane.add(comboBoxTrackList, "cell 4 3");
        buttonSummit.setText("提交");
        buttonSummit.setPreferredSize(new Dimension(150, 50));
        buttonSummit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonSummitClick(e);
            }
        });
        contentPane.add(buttonSummit, "cell 2 6 5 1");
        pack();
        setLocationRelativeTo(getOwner());
    }
}
