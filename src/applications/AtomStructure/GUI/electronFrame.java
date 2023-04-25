package applications.AtomStructure.GUI;

import applications.AtomStructure.AtomStructure;
import applications.AtomStructure.electronFactory;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class electronFrame extends JFrame {
    private JLabel label1;
    private JSeparator separator;
    private JPanel infopanel;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JComboBox<String> TrackListCombox;
    private JButton buttonSummit;
    AtomStructure atomStructure;
    public electronFrame(AtomStructure atomStructure, List<String> trackList) {
        this.atomStructure= atomStructure;
        initComponents();
        this.TrackListCombox.removeAllItems();
        for(String str:trackList) {
            this.TrackListCombox.addItem(str);
        }
    }

    private void buttonSummitMousePressed(MouseEvent e) {
        double trackRadius = Double.valueOf((String) this.TrackListCombox.getSelectedItem());
        electronFactory ef = new electronFactory();
        atomStructure.addPhysicalObject(ef.createElectron_1(),trackRadius);
        this.setVisible(false);
    }

    private void initComponents() {
        label1 = new JLabel();
        separator = new JSeparator();
        infopanel = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        TrackListCombox = new JComboBox<>();
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
                        "[]"));

        //信息面板标签
        label1.setText("信息面板");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 12f));
        contentPane.add(label1, "cell 4 0");
        contentPane.add(separator, "cell 5 1");
        {
            infopanel.setPreferredSize(new Dimension(600, 450));
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
                            "[]"));

            //名称标签
            label2.setText("名称");
            label2.setForeground(Color.black);
            label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 6f));
            infopanel.add(label2, "cell 1 1");
            label3.setText("e");
            label3.setForeground(Color.black);
            label3.setFont(label3.getFont().deriveFont(label3.getFont().getSize() + 12f));
            infopanel.add(label3, "cell 9 1");
        }
        contentPane.add(infopanel, "cell 4 2");

        //添加到标签
        label4.setText("添加到");
        label4.setForeground(Color.black);
        label4.setFont(label4.getFont().deriveFont(label4.getFont().getSize() + 6f));
        contentPane.add(label4, "cell 4 3");
        contentPane.add(TrackListCombox, "cell 4 3");

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
