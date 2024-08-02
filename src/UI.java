import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UI extends JFrame{
    UI(){
        // Start screen
        JFrame startScreen = new JFrame();
        startScreen.setSize(800, 600);
        Container screen = getContentPane();
        JButton playButton = new JButton("Jogar");
        JButton debugButton = new JButton("DEBUG");
        JButton quitButton = new JButton("Sair");
    }
}
