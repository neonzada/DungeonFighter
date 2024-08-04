import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI implements ActionListener{
    public enum windowState{
        START,
        CHAR_SELECTION,
        STAT_MENU,
        INGAME,
        POSTGAME,
        DEBUG
    }
    private String chosenClass;
    private JFrame frame, charFrame, ingameFrame, statFrame, postgameFrame, debugFrame;
    private JPanel panel, charPanel, ingamePanel, statPanel, postgamePanel, debugPanel;
    private JButton playButton, debugButton, quitButton, nextButton, readyButton;
    private JButton[][] tiles;
    private JLabel barbLabel, palLabel, warLabel;
    private JTextField nameBox;
    private JButton[] statInc, statDec;
    private JLabel[] statLabels;
    private ImageIcon barbIcon, palIcon, warIcon, botTileIcon, topTileIcon;
    private windowState currentWindow;
    private int[] statArray; //0: ATK, 1: DEF, 2: HP

    public UI(){
        chosenClass = null;
        // Initialize atr points with 0
        statArray = new int[3];
        for(int i = 0; i < 3; i++){
            statArray[i] = 0;
        }
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
                charPanel.setLayout(new GridLayout(2, 4));
                charPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

                // Creating icons for the heroes
                barbIcon = new ImageIcon("img/barbarian.png");
                palIcon = new ImageIcon("img/paladin.png");
                warIcon = new ImageIcon("img/warrior.png");


                // We have to update the text, as well as make the 'next' button
                // clickable only if there's a class selected. (name optional?)
                nextButton = new JButton("NEXT");
                nextButton.setEnabled(false);
                nextButton.addActionListener(this);

                // Let's try not adding the buttons to the class attributes,
                // but instead just using them in this specific context here
                JButton barbButton = new JButton(barbIcon);
                JButton palButton = new JButton(palIcon);
                JButton warButton = new JButton(warIcon);

                barbButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        chosenClass = "Barbarian";
                        nextButton.setEnabled(true);
                        System.out.println(chosenClass);
                    }
                });

                palButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        chosenClass = "Paladin";
                        nextButton.setEnabled(true);
                        System.out.println(chosenClass);
                    }
                });

                warButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        chosenClass = "Warrior";
                        nextButton.setEnabled(true);
                        System.out.println(chosenClass);
                    }
                });

                // exception handling with jtextfield maybe?
                nameBox = new JTextField("NAME");
                nameBox.setPreferredSize(new Dimension(100, 20));
                barbLabel = new JLabel("Barbarian", SwingConstants.CENTER);
                palLabel = new JLabel("Paladin", SwingConstants.CENTER);
                warLabel = new JLabel("Warrior", SwingConstants.CENTER);

                // add'em
                charPanel.add(barbButton);
                charPanel.add(palButton);
                charPanel.add(warButton);
                charPanel.add(nextButton);
                charPanel.add(barbLabel);
                charPanel.add(palLabel);
                charPanel.add(warLabel);
                charPanel.add(nameBox);

                charFrame.add(charPanel, BorderLayout.CENTER);
                charFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                charFrame.pack();
                charFrame.setVisible(true);
                charFrame.setLocationRelativeTo(null);
                break;
            
            case STAT_MENU:
                // Instantiate a class, get his HP and modify the attributes here, or on the engine
                charFrame.setVisible(false);
                charFrame.dispose();
                
                statFrame = new JFrame("Dungeon Fighter - Stat Menu");
                statPanel = new JPanel();
                statPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
                statPanel.setLayout(new GridLayout(0, 3));

                // Send ATK, DEF, HP as an int vector?
                //Local variable i defined in an enclosing scope must be final or effectively final
                statInc = new JButton[3];
                for(int i = 0; i < 3; i++){
                    statInc[i] = new JButton("+");
                    statPanel.add(statInc[i]);
                }

                statLabels = new JLabel[3];
                statLabels[0] = new JLabel("ATK: " + statArray[0], SwingConstants.CENTER);
                statLabels[1] = new JLabel("DEF: " + statArray[1], SwingConstants.CENTER);
                statLabels[2] = new JLabel("HP: " + statArray[2], SwingConstants.CENTER);
                for(int i = 0; i < 3; i++){
                    statPanel.add(statLabels[i]);
                }

                statDec = new JButton[3];
                for(int i = 0; i < 3; i++){
                    statDec[i] = new JButton("-");
                    statPanel.add(statDec[i]);
                }

                readyButton = new JButton("START GAME");
                readyButton.addActionListener(this);
                statPanel.add(readyButton);


                // TODO: fix this trash code Action listeners for everyone! YAY


                statFrame.add(statPanel, BorderLayout.CENTER);
                statFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                statFrame.setResizable(false);
                statFrame.pack();
                statFrame.setVisible(true);
                statFrame.setLocationRelativeTo(null);
                break;

            case INGAME:
                // start the engine, maybe multithread implementation
                // new Engine(chosenClass);
                charFrame.setVisible(false);
                charFrame.dispose();

                // Creating ingame frame
                ingameFrame = new JFrame("Dungeon Fighter - Ingame");

                // Creating ingame panel
                ingamePanel = new JPanel();
                ingamePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
                ingamePanel.setLayout(new GridLayout(5, 10));

                // 5x10 grid with top and bottom tiles!
                botTileIcon = new ImageIcon("img/bottile64.png");
                topTileIcon = new ImageIcon("img/toptile64.png");
                tiles = new JButton[5][10];
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 10; j++){
                        if(i < 4){
                            tiles[i][j] = new JButton(topTileIcon);
                            tiles[i][j].setBorder(BorderFactory.createEmptyBorder());
                        }else{
                            tiles[i][j] = new JButton(botTileIcon);
                            tiles[i][j].setBorder(BorderFactory.createEmptyBorder());
                        }
                        ingamePanel.add(tiles[i][j]);
                    }
                }


                ingameFrame.add(ingamePanel, BorderLayout.CENTER);
                ingameFrame.setSize(1280, 720);
                ingameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ingameFrame.setResizable(false);
                ingameFrame.pack();
                ingameFrame.setVisible(true);
                ingameFrame.setLocationRelativeTo(null);
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

    // que bagulho feio
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(playButton)){
            setCurrentWindow(windowState.CHAR_SELECTION);
            createWindow(currentWindow);
        }else if(e.getSource().equals(nextButton)){
            // start the engine here perhaps?
            // new Engine(chosenClass);
            setCurrentWindow(windowState.STAT_MENU);
            createWindow(currentWindow);
        }else if(e.getSource().equals(readyButton)){
            setCurrentWindow(windowState.INGAME);
            createWindow(currentWindow);
        }else if(e.getSource().equals(debugButton)){
            setCurrentWindow(windowState.DEBUG);
            createWindow(currentWindow);
        }else if(e.getSource().equals(quitButton)){
            // quit (should I call System.exit(0)?)
            // apparently EXIT_ON_CLOSE just calls System.exit(0) anyways lmao
            JOptionPane.showMessageDialog(null, "Quitting the game!");
            System.exit(0);
        }
    }

    // Update stat labels in the stat screen
    private void updateStatLabels(){
        statLabels[0].setText("ATK: " + statArray[0]);
        statLabels[1].setText("DEF: " + statArray[1]);
        statLabels[2].setText("HP: " + statArray[2]);
    }

    // Get and set current window
    public void setCurrentWindow(windowState window){
        this.currentWindow = window;
    }
    public windowState getCurrentWindow(){
        return this.currentWindow;
    }
}
