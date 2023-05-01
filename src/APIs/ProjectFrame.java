package APIs;

import applications.AtomStructure.AtomStructure;
import applications.AtomStructure.electronFactory;
import applications.SocialNetworkCircle.SocialNetworkCircle;
import applications.TrackGame.TrackGame;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProjectFrame extends JFrame {

    static final int WIDTH = 1600;
    static final int HEIGHT = 1200;
    static String[] trackGame = {"src/configFile/TrackGame.txt",
            "src/configFile/TrackGame_Medium.txt",
            "src/configFile/TrackGame_Larger.txt"};

    static String[] atomStructure = {"src/configFile/AtomicStructure.txt",
            "src/configFile/AtomicStructure_Medium.txt"
    };

    static String[] socialNetwork = {"src/configFile/SocialNetworkCircle.txt",
            "src/configFile/SocialNetworkCircle_Medium.txt",
            "src/configFile/SocialNetworkCircle_Larger.txt",
            "src/configFile/SocialNetworkCircle_Normal.txt",
    };
    private JLabel label1;
    private JSeparator separator;
    private JPanel infopanel;

    private JButton buttonAtomStructure;
    private JButton buttonSocialNetworkCircle;
    private JButton buttonTrackGame;

    public ProjectFrame(){
        initComponents();
    }
    private void buttonAtomStructureMousePressed(MouseEvent e) {
        JFrame atomStructureFrame = new JFrame();
        atomStructureFrame.setSize(WIDTH,HEIGHT);
        atomStructureFrame.setTitle("AtomStructure");
        AtomStructure atomstructure = new AtomStructure(atomStructure[1]);
        atomstructure.initialize();
        atomstructure.draw(atomStructureFrame);
        atomStructureFrame.setVisible(true);
    }

    private void buttonSocialNetworkCircleMousePressed(MouseEvent e) {
        JFrame socialNetworkFrame = new JFrame();
        socialNetworkFrame.setSize(WIDTH,HEIGHT);
        socialNetworkFrame.setTitle("SocialNetwork");
        SocialNetworkCircle game = new SocialNetworkCircle(socialNetwork[0]);
        game.initialize();
        game.draw(socialNetworkFrame);
        socialNetworkFrame.setVisible(true);
    }

    private void buttonTrackGameMousePressed(MouseEvent e) {
        //运动员跑步比赛
        JFrame trackGameFrame = new JFrame();
        trackGameFrame.setTitle("TrackGame");
        trackGameFrame.setSize(WIDTH,HEIGHT);
        TrackGame trackgame = new TrackGame(trackGame[0]);
        trackgame.initialize();
        trackgame.draw(trackGameFrame);
        trackGameFrame.setVisible(true);
    }

    private void initComponents() {
        label1 = new JLabel();
        separator = new JSeparator();
        infopanel = new JPanel();
        buttonAtomStructure = new JButton();
        buttonSocialNetworkCircle = new JButton();
        buttonTrackGame = new JButton();
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

        //项目选择面板标签
        label1.setText("选择项目");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 12f));
        contentPane.add(label1, "cell 4 0");

        //社交关系系统按钮
        buttonSocialNetworkCircle.setText("SocialNetworkCircle");
        buttonSocialNetworkCircle.setPreferredSize(new Dimension(150, 50));
        buttonSocialNetworkCircle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonSocialNetworkCircleMousePressed(e);
            }
        });
        contentPane.add(buttonSocialNetworkCircle, "cell 4 4");

        //轨道运动比赛系统按钮
        buttonTrackGame.setText("TrackGame");
        buttonTrackGame.setPreferredSize(new Dimension(150, 50));
        buttonTrackGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonTrackGameMousePressed(e);
            }
        });
        contentPane.add(buttonTrackGame, "cell 4 6");

        //原子轨道系统按钮
        buttonAtomStructure.setText("AtomStructure");
        buttonAtomStructure.setPreferredSize(new Dimension(150, 50));
        buttonAtomStructure.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonAtomStructureMousePressed(e);
            }
        });
        contentPane.add(buttonAtomStructure, "cell 4 8");
        pack();
        setLocationRelativeTo(getOwner());
    }
}
