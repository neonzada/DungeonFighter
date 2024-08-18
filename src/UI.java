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
        POSTGAME
    }

    private Engine gameEngine;
    private boolean debugMode, retryGame;

    private String chosenClass;
    private JFrame frame, charFrame, ingameFrame, statFrame, postgameFrame;
    private JPanel panel, charPanel, ingamePanel, statPanel, postgamePanel;
    private JButton playButton, debugButton, quitButton, nextButton, readyButton, tipButton, forfeitButton;
    private static JButton moveButton;
    private static JButton[][] tiles;
    private JLabel barbLabel, palLabel, warLabel, elixirLabel;
    private JTextField nameBox;
    private JButton[] statInc, statDec;
    private JLabel[] statLabels;
    private static ImageIcon barbIcon, palIcon, warIcon, botTileIcon, topTileIcon;
    private static ImageIcon slimeIcon, goblinIcon, skellyIcon, orcIcon, kingIcon, deathIcon;
    private static ImageIcon trapIcon, rngTrapIcon, elixirIcon;
    private windowState currentWindow;
    private Hero hero;

    public UI(){
        chosenClass = null;
        debugMode = false;
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
                frame.setResizable(false);
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
                charFrame.setResizable(false);
                charFrame.setLocationRelativeTo(null);
                break;
            
            case STAT_MENU:
                // Instantiate a class, get his HP and modify the attributes here, or on the engine
                switch(chosenClass){
                    case "Paladin":
                        hero = new Paladin(7, 7, 6, 0, 2, chosenClass);
                        break;
                    case "Warrior":
                        hero = new Warrior(7, 7, 6, 0, 2, chosenClass);
                        break;
                    default:
                        hero = new Barbarian(7, 7, 6, 0, 2, chosenClass);
                        break;
                }
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
                    statInc[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e){
                            incrementaAtributo(e);
                        }
                    });
                    statPanel.add(statInc[i]);
                }

                statLabels = new JLabel[3];
                for (int i = 0; i < 3; i++) {
                    statLabels[i] = new JLabel();
                }
                updateStatLabels(hero, statLabels);
                for(int i = 0; i < 3; i++){
                    statPanel.add(statLabels[i]);
                }

                statDec = new JButton[3];
                for(int i = 0; i < 3; i++){
                    statDec[i] = new JButton("-");
                    statDec[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e){
                            decrementaAtributo(e);
                        }
                    });
                    statPanel.add(statDec[i]);
                }

                readyButton = new JButton("START GAME");
                readyButton.addActionListener(this);
                statPanel.add(readyButton);


                statFrame.add(statPanel, BorderLayout.CENTER);
                statFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                statFrame.setResizable(false);
                statFrame.pack();
                statFrame.setVisible(true);
                statFrame.setLocationRelativeTo(null);
                break;

            case INGAME:
                statFrame.setVisible(false);
                statFrame.dispose();

                // Creating ingame frame
                ingameFrame = new JFrame("Dungeon Fighter - Ingame");

                // Creating ingame panel
                ingamePanel = new JPanel();
                ingamePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
                ingamePanel.setLayout(new GridLayout(0, 10));

                // 5x10 grid with top and bottom tiles!
                botTileIcon = new ImageIcon("img/bottile64.png");
                topTileIcon = new ImageIcon("img/toptile64.png");
                tiles = new JButton[5][10];
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 10; j++){
                        if(i < 4){
                            tiles[i][j] = new JButton(topTileIcon);
                            tiles[i][j].setBorder(BorderFactory.createEmptyBorder());
                            //tiles[i][j].addActionListener(new ButtonClickListener(i, j));
                        }else{
                            tiles[i][j] = new JButton(botTileIcon);
                            tiles[i][j].setBorder(BorderFactory.createEmptyBorder());
                        }
                        ingamePanel.add(tiles[i][j]);
                    }
                }

                // dica numero 1, e um pais da europa
                tipButton = new JButton("hnt");
                tipButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        // show tips, call engine function maybe
                    }
                });
                ingamePanel.add(tipButton);

                // start the engine, maybe multithread implementation
                instantiateIcons();
                gameEngine = new Engine(hero, debugMode, retryGame);
                gameEngine.startGame();

                moveButton = new JButton("mov");
                moveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        highlightAvailableMoves(gameEngine.getHero());
                        moveButton.setEnabled(false);
                        // movestate: highlights possible movements
                        // check if a move if valid before moving
                    }
                });
                ingamePanel.add(moveButton);

                // what a coward
                forfeitButton = new JButton("hlt");
                forfeitButton.addActionListener(this);
                ingamePanel.add(forfeitButton);
                
                // stat labels momento
                for(int i = 0; i < 3; i++){
                    ingamePanel.add(statLabels[i]);
                }
                
                elixirLabel = new JLabel("ELX: 3");
                ingamePanel.add(elixirLabel);

                ingameFrame.add(ingamePanel, BorderLayout.CENTER);
                ingameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ingameFrame.setResizable(false);
                ingameFrame.pack();
                ingameFrame.setVisible(true);
                ingameFrame.setLocationRelativeTo(null);

                break;
            
            case POSTGAME:
                ingameFrame.setVisible(false);
                ingameFrame.dispose();
                
                // Creating postgame frame
                postgameFrame = new JFrame("Dungeon Fighter - Postgame");

                // Creating postgame panel
                postgamePanel = new JPanel();
                postgamePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
                postgamePanel.setLayout(new GridLayout(0, 10));
                
                // show if the user won or lost, as well as buttons to restart game
                // (same mob pos(and count?)) or new game (new mob pos (and count?))
                // gameEngine.getFinalState() returns 1 if won, 0 if lost
                if(gameEngine.getFinalState()){
                    // do win shit
                }else{
                    // do lose shit
                }
                postgameFrame.add(postgamePanel);
                postgameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                postgameFrame.setResizable(false);
                postgameFrame.pack();
                postgameFrame.setVisible(true);
                postgameFrame.setLocationRelativeTo(null);
                break;
            
            default:
                // default case
                System.out.println("nao sei como chegou aqui lol");
        }
    }

    // que bagulho feio
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(playButton)){
            setCurrentWindow(windowState.CHAR_SELECTION);
            createWindow(currentWindow);
        }else if(e.getSource().equals(nextButton)){
            setCurrentWindow(windowState.STAT_MENU);
            createWindow(currentWindow);
        }else if(e.getSource().equals(readyButton)){
            if(hero.getHP() > 0){
                retryGame = false;
                setCurrentWindow(windowState.INGAME);
                createWindow(currentWindow);
            }else{
                JOptionPane.showMessageDialog(null, "Por favor, aloque pelo menos um ponto em HP");
            }
        }else if(e.getSource().equals(debugButton)){
            debugMode = !debugMode;
            System.out.println("Debug mode: " + debugMode);
        }else if(e.getSource().equals(forfeitButton)){
            setCurrentWindow(windowState.POSTGAME);
            createWindow(currentWindow);
        }else if(e.getSource().equals(quitButton)){
            // quit (should I call System.exit(0)?)
            // apparently EXIT_ON_CLOSE just calls System.exit(0) anyways lmao
            JOptionPane.showMessageDialog(null, "Quitting the game!");
            System.exit(0);
        }
    }

    // Update stat labels in the stat screen
    private void updateStatLabels(Mob m, JLabel[] stats){
        stats[0].setText("ATK: " + m.getATK());
        stats[1].setText("DEF: " + m.getDEF());
        stats[2].setText("HP: " + m.getHP());
    }

    public static void removeAllListeners(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 10; j++){
                for (ActionListener al : tiles[i][j].getActionListeners()) {
                    tiles[i][j].removeActionListener(al);
                }
            }
        }
    }

    // bad code incoming
    public void highlightAvailableMoves(Hero h){
        removeAllListeners();
        System.out.println("X: " + h.getXPos() + "Y: " + h.getYPos());
        if(h.getXPos() < 9){
            // TODO: maybe get some nice icons to highlight and to indicate path already traveled (minecraft torch)
            // basically just replace null with new highlight icon, and then if actionPerformed replace others
            tiles[h.getYPos()][h.getXPos() + 1].setIcon(null);
            tiles[h.getYPos()][h.getXPos() + 1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    // defaultIcon indicates path already discovered
                    tiles[h.getYPos()][h.getXPos()].setIcon(null);
                    h.setPos(h.getXPos() + 1, h.getYPos());
                    drawSprite(h);
                    removeAllListeners();
                    System.out.println("RIGHT");
                    moveButton.setEnabled(true);

                    // now this is some baaad code
                    if(Engine.checkMonster(h.getXPos(), h.getYPos())){
                        if(Engine.getMonster(h.getXPos(), h.getYPos()).getHP() > 0) startBattle(h, Engine.getMonster(h.getXPos(), h.getYPos()));
                    }
                }
            });;
        }
        if(h.getXPos() > 0){
            tiles[h.getYPos()][h.getXPos() - 1].setIcon(null);
            tiles[h.getYPos()][h.getXPos() - 1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    // TODO: minecraft torch for the path already cleared
                    tiles[h.getYPos()][h.getXPos()].setIcon(null);
                    h.setPos(h.getXPos() - 1, h.getYPos());
                    drawSprite(h);
                    removeAllListeners();
                    System.out.println("LEFT");
                    moveButton.setEnabled(true);
                    if(Engine.checkMonster(h.getXPos(), h.getYPos())){
                        if(Engine.getMonster(h.getXPos(), h.getYPos()).getHP() > 0) startBattle(h, Engine.getMonster(h.getXPos(), h.getYPos()));
                    }
                }
            });;
        }
        if(h.getYPos() < 4){
            tiles[h.getYPos() + 1][h.getXPos()].setIcon(null);
            tiles[h.getYPos() + 1][h.getXPos()].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    tiles[h.getYPos()][h.getXPos()].setIcon(null);
                    h.setPos(h.getXPos(), h.getYPos() + 1);
                    drawSprite(h);
                    removeAllListeners();
                    System.out.println("DOWN");
                    moveButton.setEnabled(true);
                    if(Engine.checkMonster(h.getXPos(), h.getYPos())){
                        if(Engine.getMonster(h.getXPos(), h.getYPos()).getHP() > 0) startBattle(h, Engine.getMonster(h.getXPos(), h.getYPos()));
                    }
                }
            });;
        }
        if(h.getYPos() > 0){
            tiles[h.getYPos() - 1][h.getXPos()].setIcon(null);
            tiles[h.getYPos() - 1][h.getXPos()].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    tiles[h.getYPos()][h.getXPos()].setIcon(null);
                    h.setPos(h.getXPos(), h.getYPos() - 1);
                    drawSprite(h);
                    removeAllListeners();
                    System.out.println("UP");
                    moveButton.setEnabled(true);
                    if(Engine.checkMonster(h.getXPos(), h.getYPos())){
                        if(Engine.getMonster(h.getXPos(), h.getYPos()).getHP() > 0) startBattle(h, Engine.getMonster(h.getXPos(), h.getYPos()));
                    }
                }
            });;
        }
    }

    private void startBattle(Hero h, Monster m){
        ingameFrame.setVisible(false);

        JFrame battleFrame = new JFrame();
        JPanel battlePanel = new JPanel();
        battlePanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        battlePanel.setLayout(new GridLayout(0, 3));

        // this shit makes the world go around
        // when we atk/ex we test if after the hit the monsterHP < 1 or playerHP < 1 and
        // act accordingly
        JButton atkButton = new JButton("atk");
        JButton specialButton = new JButton("ex");
        JButton heroButton = new JButton();
        JButton monButton = new JButton();

        JLabel vsLabel = new JLabel("VS", SwingConstants.CENTER);

        // idk if i should modulate this
        switch(m.getType()){
            case 0:
                monButton.setIcon(slimeIcon);
                break;
            case 1:
                monButton.setIcon(goblinIcon);
                break;
            case 2:
                monButton.setIcon(skellyIcon);
                break;
            case 3:
                monButton.setIcon(orcIcon);
                break;
            case 4:
                monButton.setIcon(kingIcon);
                break;
            default:
                monButton.setIcon(deathIcon);
                break;
        }
        if(h instanceof Barbarian){
            heroButton.setIcon(barbIcon);
        }
        if(h instanceof Warrior){
            heroButton.setIcon(warIcon);
        }
        if(h instanceof Paladin){
            heroButton.setIcon(palIcon);
        }

        makeOpaque(monButton);
        makeOpaque(heroButton);
        battlePanel.add(heroButton);
        battlePanel.add(vsLabel);
        battlePanel.add(monButton);

        JButton elixirButton = new JButton("elx");
        battlePanel.add(atkButton);
        battlePanel.add(specialButton);
        battlePanel.add(elixirButton);

        for(int i =0; i < 3; i++){
            battlePanel.add(statLabels[i]);
        }

        JLabel monStatLabels[] = new JLabel[3];
        for(int i = 0; i < 3; i++){
            monStatLabels[i] = new JLabel();
            battlePanel.add(monStatLabels[i]);
        }
        updateStatLabels(m, monStatLabels);

        atkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                h.attack(h, m);
                updateStatLabels(m, monStatLabels);
                updateStatLabels(h, statLabels);
                if(h.getHP() < 1){
                    //gameover
                    battleFrame.setVisible(false);
                    setCurrentWindow(windowState.POSTGAME);
                    createWindow(currentWindow);
                }
                if(m.getHP() < 1){
                    //close screen
                    battleFrame.setVisible(false);
                    //why do i need to add labels again
                    for(int i =0; i < 3; i++){
                        ingamePanel.add(statLabels[i]);
                    }
                    ingamePanel.add(elixirLabel);
                    ingameFrame.setVisible(true);
                }
            }
        });

        battleFrame.add(battlePanel, BorderLayout.CENTER);
        battleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        battleFrame.setResizable(false);
        battleFrame.pack();
        battleFrame.setVisible(true);
        battleFrame.setLocationRelativeTo(null);
    }

    private void makeOpaque(JButton button){
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(null);
    }
    private void updateElixirLabel(int elixir){
        elixirLabel.setText("ELX: " + elixir);
    }

    public static void happening(Mob p1, Mob p2, int totalDmg, int totalDef){
        if(totalDmg > totalDef){
            JOptionPane.showMessageDialog(null, "O dano de " + p1.getClass().getSimpleName()  + " (" + totalDmg + ") foi maior que a defesa de " + p2.getClass().getSimpleName() + " (" + totalDef + "). Voce causou " + (totalDmg - totalDef) + " de dano.");
        }else if(totalDmg == totalDef){
            JOptionPane.showMessageDialog(null, "O dano de " + p1.getClass().getSimpleName()  + " (" + totalDmg + ") foi igual que a defesa de " + p2.getClass().getSimpleName() + " (" + totalDef + "). Nada acontece, feijoada.");
        }else{
            JOptionPane.showMessageDialog(null, "O dano de " + p1.getClass().getSimpleName()  + " (" + totalDmg + ") foi menor que a defesa de " + p2.getClass().getSimpleName() + " (" + totalDef + "). Voce tomou " + (totalDef - totalDmg) + " de dano.");
        }
    }
    // Metodos para incrementar e decrementar atributos
    public void incrementaAtributo(ActionEvent e){
        int res = hero.getATK() + hero.getDEF() + hero.getHP();
        //TODO: definir o numero maximo de atributos em algum lugar do codigo
        if(res < 20){
            if(e.getSource().equals(statInc[0])){
                hero.incATK();
            }
            if(e.getSource().equals(statInc[1])){
                hero.incDEF();
            }
            if(e.getSource().equals(statInc[2])){
                hero.incHP();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Maximo de 20 pontos alocados!");
        }
        updateStatLabels(hero, statLabels);
    }

    public void instantiateIcons(){
        slimeIcon = new ImageIcon("img/slime.png");
        goblinIcon = new ImageIcon("img/goblin.png");
        skellyIcon = new ImageIcon("img/skelly.png");
        orcIcon = new ImageIcon("img/orc.png");
        kingIcon = new ImageIcon("img/skellyking.png");
        trapIcon = new ImageIcon("img/trap.png");
        rngTrapIcon = new ImageIcon("img/rngtrap.png");
        elixirIcon = new ImageIcon("img/elixir.png");
    }

    // generic function for painting sprites on JButtons
    // wanted to make one even more generic but meh
    public static void drawSprite(Mob e){
        if(e instanceof Monster){
            switch(((Monster)e).getType()){
                case 0:
                    tiles[e.getYPos()][e.getXPos()].setIcon(slimeIcon);
                    break;
                case 1:
                    tiles[e.getYPos()][e.getXPos()].setIcon(goblinIcon);
                    break;
                case 2:
                    tiles[e.getYPos()][e.getXPos()].setIcon(skellyIcon);
                    break;
                case 3:
                    tiles[e.getYPos()][e.getXPos()].setIcon(orcIcon);
                    break;
                case 4:
                    tiles[e.getYPos()][e.getXPos()].setIcon(kingIcon);
                    break;
                default:
                    tiles[e.getYPos()][e.getXPos()].setIcon(deathIcon);
                    break;
            }
            System.out.println(((Monster)e).getType());
        }
        if(e instanceof Barbarian){
            tiles[e.getYPos()][e.getXPos()].setIcon(barbIcon);
        }
        if(e instanceof Warrior){
            tiles[e.getYPos()][e.getXPos()].setIcon(warIcon);
        }
        if(e instanceof Paladin){
            tiles[e.getYPos()][e.getXPos()].setIcon(palIcon);
        }
    }

    public static void drawSprite(Elixir e){
        tiles[e.getYPos()][e.getXPos()].setIcon(elixirIcon);
    }

    public static void drawSprite(Trap e){
        if(e.getType()) tiles[e.getYPos()][e.getXPos()].setIcon(trapIcon);
        else tiles[e.getYPos()][e.getXPos()].setIcon(rngTrapIcon);
    }

    public void decrementaAtributo(ActionEvent e){
        // no negative attributes!
        int res = hero.getATK() + hero.getDEF() + hero.getHP();
        if(res > 0){
            if((e.getSource().equals(statDec[0])) && (hero.getATK() > 0)){
                hero.decATK();
            }
            if((e.getSource().equals(statDec[1])) && (hero.getDEF() > 0)){
                hero.decDEF();
            }
            if((e.getSource().equals(statDec[2])) && (hero.getHP() > 0)){
                hero.decHP();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Aloca uns atributos ai!");
        }
        //update jlabels
        updateStatLabels(hero, statLabels);
    }

    // Get and set current window
    public void setCurrentWindow(windowState window){
        this.currentWindow = window;
    }
    public windowState getCurrentWindow(){
        return this.currentWindow;
    }
}
