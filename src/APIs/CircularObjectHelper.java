package APIs;

import applications.AtomStructure.AtomStructure;
import applications.SocialNetworkCircle.SocialNetworkCircle;
import applications.TrackGame.TrackGame;
import circularOrbit.CircularOrbit;

import javax.swing.*;

public class CircularObjectHelper {
    public static void main(String[] args) {
        JFrame projectSelectFrame = new ProjectFrame();
        projectSelectFrame.setSize(400,400);
        projectSelectFrame.setTitle("功能选择");
        projectSelectFrame.setVisible(true);
    }
}
