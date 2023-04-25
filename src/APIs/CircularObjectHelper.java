package APIs;

import applications.AtomStructure.AtomStructure;
import applications.TrackGame.TrackGame;
import circularOrbit.CircularOrbit;

import javax.swing.*;

public class CircularObjectHelper {
    static final int WIDTH = 1600;
    static final int HEIGHT = 1200;

    static String[] trackGame = {"src/configFile/TrackGame.txt",
            "src/configFile/TrackGame_Medium.txt",
            "src/configFile/TrackGame_Larger.txt"};

    static String[] atomStructure = {"src/configFile/AtomicStructure.txt",
            "src/configFile/AtomicStructure_Medium.txt"
    };

    public static void visualize(CircularOrbit c) {
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.getContentPane().add(c.visualizeContentPanel());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //运动员跑步比赛
//        JFrame trackGameFrame = new JFrame();
//        trackGameFrame.setTitle("TrackGame");
//        trackGameFrame.setSize(WIDTH,HEIGHT);
//        TrackGame trackgame = new TrackGame(trackGame[2]);
//        trackgame.initialize();
//        trackgame.draw(trackGameFrame);
//        trackGameFrame.setVisible(true);
        //轨道电子系统
        JFrame atomStructureFrame = new JFrame();
        atomStructureFrame.setSize(WIDTH,HEIGHT);
        atomStructureFrame.setTitle("AtomStructure");
        AtomStructure atomstructure = new AtomStructure(atomStructure[1]);
        atomstructure.initialize();
        atomstructure.draw(atomStructureFrame);
        atomStructureFrame.setVisible(true);
    }
}
