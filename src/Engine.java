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
    
    private Hero hero;

    //random positions and attr buffs
    private int heroYPos, monATK, monDEF, monHP, bossATK, bossDEF, bossHP;

    private String chosenClass;
    private int mobNumber;
    private int[] statArray;
    private int[] mobIndex, mobXPos, mobYPos; //only used for the sprites lol

    private boolean debugMode;
    private boolean retryGame;

    public Engine(String chosenClass, int[] statArray, boolean debugMode, boolean retryGame){
        this.chosenClass = chosenClass;
        this.mobNumber = 6;
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
        spawnBoss();
    }

    // because the game needs to repeat every attribute and monster position,
    // let's store those values to reuse them in case of a "retry" signal
    public void getSeed(int mobNumber){
        Random rand = new Random();
        heroYPos = rand.nextInt(6);
        monATK = rand.nextInt(5);
        monDEF = rand.nextInt(5);
        monHP = rand.nextInt(5);
        bossATK = rand.nextInt(8);
        bossDEF = rand.nextInt(8);
        bossHP = rand.nextInt(8);

        mobXPos = new int[mobNumber];
        mobYPos = new int[mobNumber];
        //TODO: implement monster checking collision (no 2 monsters at the same tile)
        for(int i = 0; i < mobNumber; i++){
            mobXPos[i] = 1 + rand.nextInt(9);
            mobYPos[i] = rand.nextInt(6);
            System.out.println("mobXPos[" + i + "]: " + mobXPos[i]);
            System.out.println("mobYPos[" + i + "]: " + mobYPos[i]);
        }
    }

    public void spawnPlayer(){
        // starting pos, x is constant so the player spawns at the first collumn
        Random yrand = new Random();
        hero = new Hero(statArray[0], statArray[1], statArray[2], chosenClass);
        hero.setPos(0, yrand.nextInt(6));
    }

    public void spawnMobs(int mobNum){
        monArr = new Monster[mobNum];
        for(int i = 0; i < mobNum; i++){
            Random rand = new Random();
            monArr[i]  = new Monster(
                base_monATK + monATK,
                base_monDEF + monDEF,
                base_monHP + monHP,
                rand.nextInt(6) //type
            );
            monArr[i].setPos(mobXPos[i], mobYPos[i]);
        }
    }

    public void spawnBoss(){
        Random rand = new Random();
        bossy = new Boss(
            base_bossATK + rand.nextInt(8),
            base_bossDEF + rand.nextInt(8),
            base_bossHP + rand.nextInt(8),
            rand.nextInt(6)
        );
    }
}
