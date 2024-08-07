import java.util.Random;

public class Engine extends Thread{
    // Constant mob attributes, fine-tune these for balancing
    private final int base_monATK = 4;
    private final int base_monDEF = 3;
    private final int base_monHP = 3;

    private final int base_bossATK = 12;
    private final int base_bossDEF = 10;
    private final int base_bossHP = 10;

    private Monster[] monArr;
    private Boss bossy;
    private Trap[] trapArr;
    
    private Hero hero;

    //random positions and attr buffs
    private int monATK, monDEF, monHP, bossATK, bossDEF, bossHP;

    private String chosenClass;
    private int mobNumber, trapNumber;
    private int[] statArray;
    private int[] mobIndex, mobXPos, mobYPos, trapXPos, trapYPos; //only used for the sprites lol

    private boolean debugMode;
    private boolean retryGame;

    public Engine(String chosenClass, int[] statArray, boolean debugMode, boolean retryGame){
        this.chosenClass = chosenClass;
        this.mobNumber = 5;
        this.trapNumber = 3;
        this.statArray = statArray;
        this.mobIndex = new int[]{
            0, //SLIME
            1, //GOBLIN
            2, //SKELLY
            3, //ORC
            4, //SKELLYKING
            5 //DEATH
        };
        this.debugMode = debugMode;
        this.retryGame = retryGame;
    }
    
    //TODO: implement multithreading when the game begins
    @Override
    public void run(){
        if(!retryGame) getSeed(mobNumber);
        spawnPlayer();
        spawnMobs(mobNumber);
        spawnTraps(trapNumber);
        spawnBoss();
        //game loop here
    }

    // because the game needs to repeat every attribute and monster position,
    // let's store those values to reuse them in case of a "retry" signal
    public synchronized void getSeed(int mobNumber){
        Random rand = new Random();
        monATK = rand.nextInt(5);
        monDEF = rand.nextInt(5);
        monHP = rand.nextInt(5);
        bossATK = rand.nextInt(8);
        bossDEF = rand.nextInt(8);
        bossHP = rand.nextInt(8);

        //maybe pos could be class attributes lol
        mobXPos = new int[mobNumber];
        mobYPos = new int[mobNumber];

        trapXPos = new int[trapNumber];
        trapYPos = new int[trapNumber];

        //TODO: implement monster checking collision (no 2 monsters at the same tile)
        // i think this is going to be costly since we have to iterate through the
        // xpos array and ypos array to make sure there isn't any same coordinates
        // there's probably a better way to do this, research more
        for(int i = 0; i < mobNumber; i++){
            mobXPos[i] = 1 + rand.nextInt(9);
            mobYPos[i] = rand.nextInt(4);
            System.out.println("mobXPos[" + i + "]: " + mobXPos[i]);
            System.out.println("mobYPos[" + i + "]: " + mobYPos[i]);
        }
        for(int i = 0; i < trapNumber; i++){
            trapXPos[i] = 1 + rand.nextInt(9);
            trapYPos[i] = rand.nextInt(4);
            System.out.println("trapXPos[" + i + "]: " + trapXPos[i]);
            System.out.println("trapYPos[" + i + "]: " + trapYPos[i]);
        }
    }

    public void spawnPlayer(){
        switch(chosenClass){
            case "Paladin":
                hero = new Paladin(statArray[0], statArray[1], statArray[2], 0, 2, chosenClass);
                break;
            case "Warrior":
                hero = new Warrior(statArray[0], statArray[1], statArray[2], 0, 2, chosenClass);
                break;
            default:
                hero = new Barbarian(statArray[0], statArray[1], statArray[2], 0, 2, chosenClass);
                break;
        }
        UI.drawSprite(hero);
    }

    public void spawnMobs(int mobNum){
        monArr = new Monster[mobNum];
        for(int i = 0; i < mobNum; i++){
            Random rand = new Random();
            monArr[i]  = new Monster(
                base_monATK + monATK,
                base_monDEF + monDEF,
                base_monHP + monHP,
                mobXPos[i],
                mobYPos[i],
                rand.nextInt(3) //type
            );
            if(debugMode) UI.drawSprite(monArr[i]);
        }
    }

    // spawns boss at the middle of the last collumn
    public void spawnBoss(){
        Random rand = new Random();
        bossy = new Boss(
            base_bossATK + bossATK,
            base_bossDEF + bossDEF,
            base_bossHP + bossHP,
            9,
            2,
            3 + rand.nextInt(2)
        );
        System.out.println(bossy.getXPos());
        UI.drawSprite(bossy);
    }

    public void spawnTraps(int trapNumber){
        trapArr = new Trap[trapNumber];
        for(int i = 0; i < trapNumber; i++){
            Random rand = new Random();
            trapArr[i] = new Trap(trapXPos[i], trapYPos[i], rand.nextBoolean());
        }
    }
}
