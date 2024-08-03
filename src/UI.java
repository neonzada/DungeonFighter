import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI implements ActionListener{
    public enum windowState{
        START,
        CHAR_SELECTION,
        INGAME,
        POSTGAME,
        DEBUG
    }
    private String chosenClass;
    private JFrame frame, charFrame, ingameFrame, postgameFrame, debugFrame;
    private JPanel panel, charPanel, ingamePanel, postgamePanel, debugPanel;
    private JButton playButton, debugButton, quitButton;
    private JLabel userLabel;
    private ImageIcon barbIcon, palIcon, warIcon;
    private windowState currentWindow;

    public UI(){
        chosenClass = null;
        currentWindow = windowState.START;
        createWindow(currentWindow);
    }

    public void createWindow(windowState currentWindow){
        System.out.println(getCurrentWindow());
        switch(currentWindow){
            case START:
                // Start screen
                frame = new JFrame("Dungeon Fighter - Main Menu");

                // Create a new panel
                panel = new JPanel();
                panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
                panel.setLayout((new GridLayout(3,1)));

                // Not worth creating an array of buttons for those three
                // Play button
                playButton = new JButton("PLAY");
                playButton.setPreferredSize(new Dimension(150, 50));
                playButton.addActionListener(this);
                panel.add(playButton);

                // Debug button
                debugButton = new JButton("DEBUG");
                debugButton.setPreferredSize(new Dimension(150, 50));
                debugButton.addActionListener(this);
                panel.add(debugButton);

                // Quit button
                quitButton = new JButton("QUIT");
                quitButton.setPreferredSize(new Dimension(150, 50));
                quitButton.addActionListener(this);
                panel.add(quitButton);

                // Set visible
                frame.add(panel, BorderLayout.CENTER);
                //frame.setSize(800, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null); // Centers the frame
                break;
            case CHAR_SELECTION:
                frame.setVisible(false);
                frame.dispose();
                charFrame = new JFrame("Dungeon Fighter - Character Selection");

                charPanel = new JPanel();
                charPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

                // Creating icons for the heroes
                barbIcon = new ImageIcon("img/barbarian.png");
                palIcon = new ImageIcon("img/paladin.png");
                warIcon = new ImageIcon("img/warrior.png");

                // Let's try not adding the buttons to the class attributes,
                // but instead just using them in this specific context here
                JButton barbButton = new JButton(barbIcon);
                JButton palButton = new JButton(palIcon);
                JButton warButton = new JButton(warIcon);

                barbButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        chosenClass = "Barbarian";
                        System.out.println(chosenClass);
                    }
                });

                palButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        chosenClass = "Paladin";
                        System.out.println(chosenClass);
                    }
                });

                warButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        chosenClass = "Warrior";
                        System.out.println(chosenClass);
                    }
                });

                // add'em
                charPanel.add(barbButton);
                charPanel.add(palButton);
                charPanel.add(warButton);

                // We have to update the text, as well as make the 'next' button
                // clickable only if there's a name and a class selected.


                charFrame.add(charPanel, BorderLayout.CENTER);
                charFrame.setSize(1280, 720);
                charFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                charFrame.pack();
                charFrame.setVisible(true);
                charFrame.setLocationRelativeTo(null);
                break;
            
            case INGAME:
                // start the engine, maybe multithread implementation
                // new Engine(chosenClass);
                frame.setVisible(false);
                frame.dispose();

                // Creating ingame frame
                ingameFrame = new JFrame("Dungeon Fighter - Ingame");

                // Creating ingame panel
                ingamePanel = new JPanel();
                ingamePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
                ingamePanel.setLayout(new GridLayout(5, 10));
                break;
            
            case POSTGAME:
                // show if the user won or lost, as well as buttons to restart game
                // (same mob pos(and count?)) or new game (new mob pos (and count?))
                break;
            
            case DEBUG:
                // literally just makes the mobs visible and plays like a normal game
                break;
            
            default:
                // default case
        }
    }
    
    // meh, i wanted to do it the cool way:
    // playButton.addActionListener(new ActionListener(){
    //  @Override
    //  public void actionPerformed(ActionEvent e){
    //    setCurrentWindow(windowState.INGAME);
    //    createWindow(currentWindow);
    //  }
    //});

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(playButton)){
            setCurrentWindow(windowState.CHAR_SELECTION);
            createWindow(currentWindow);
        }else if(e.getSource().equals(debugButton)){
            setCurrentWindow(windowState.DEBUG);
            createWindow(currentWindow);
        }else if(e.getSource().equals(quitButton)){
            // quit (should I call System.exit(0)?)
            JOptionPane.showMessageDialog(null, "Quitting the game!");
            System.exit(0);
        }
    }
    // Get and set current window
    public void setCurrentWindow(windowState window){
        this.currentWindow = window;
    }
    public windowState getCurrentWindow(){
        return this.currentWindow;
    }
}
