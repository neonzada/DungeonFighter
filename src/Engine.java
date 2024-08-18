import java.util.Random;

public class Engine{
    // Constant mob attributes, fine-tune these for balancing
    private final int base_monATK = 5;
    private final int base_monDEF = 5;
    private final int base_monHP = 4;

    private final int base_bossATK = 7;
    private final int base_bossDEF = 7;
    private final int base_bossHP = 6;

    private static Monster[] monArr;
    private static Boss bossy;
    private Trap[] trapArr;
    private Elixir[] elxArr;
    
    private Hero hero;

    //random positions and attr buffs
    private int monATK, monDEF, monHP, bossATK, bossDEF, bossHP;

    private static int mobNumber, trapNumber, elixirNumber;
    private static int[] mobXPos, mobYPos, trapXPos, trapYPos, elixirXPos, elixirYPos; //only used for the sprites lol
    private int[] usedXPos, usedYPos;

    private boolean debugMode;
    private boolean retryGame;
    private boolean finalState;

    public Engine(Hero hero, boolean debugMode, boolean retryGame){
        this.hero = hero;
        // por algum motivo a soma desses numeros nao pode ser maior que 9, wtf
        mobNumber = 4;
        trapNumber = 3;
        elixirNumber = 2;
        this.debugMode = debugMode;
        this.retryGame = retryGame;
    }
    
    public void startGame(){
        if(!retryGame) getSeed(mobNumber);
        spawnPlayer();
        spawnMobs(mobNumber);
        spawnTraps(trapNumber);
        spawnElixir(elixirNumber);
        spawnBoss();
    }

    // because the game needs to repeat every attribute and monster position,
    // let's store those values to reuse them in case of a "retry" signal
    public void getSeed(int mobNumber){
        Random rand = new Random();
        monATK = rand.nextInt(2);
        monDEF = rand.nextInt(2);
        monHP = rand.nextInt(2);
        bossATK = rand.nextInt(4);
        bossDEF = rand.nextInt(4);
        bossHP = rand.nextInt(4);

        //maybe pos could be class attributes lol refactor plsss
        mobXPos = new int[mobNumber];
        mobYPos = new int[mobNumber];

        trapXPos = new int[trapNumber];
        trapYPos = new int[trapNumber];

        elixirXPos = new int[elixirNumber];
        elixirYPos = new int[elixirNumber];

        usedXPos = new int[mobNumber + trapNumber + elixirNumber]; //new int[mobNumber + trapNumber + elixirNumber] para armazenar as posicoes usadas pelos monstros, traps e elixir
        usedYPos = new int[mobNumber + trapNumber + elixirNumber]; //dai na proxima checagem (trap) so adicionar mobNumber + i em cada iteracao

        for(int i = 0; i < mobNumber; i++){
            // roll the dice
            mobXPos[i] = 1 + rand.nextInt(8);
            mobYPos[i] = rand.nextInt(4);
            System.out.println("mobXPos[" + i + "]: " + mobXPos[i]);
            System.out.println("mobYPos[" + i + "]: " + mobYPos[i]);
            // check if values exist, if it exists then reroll (redo the loop)
            if((check(usedXPos, mobXPos[i])) && (check(usedYPos, mobYPos[i]))){
                i--;
            }else{
                // store existing values on an auxiliary array
                usedXPos[i] = mobXPos[i];
                usedYPos[i] = mobYPos[i];
            }
        }
        for(int i = 0; i < trapNumber; i++){
            trapXPos[i] = 1 + rand.nextInt(8);
            trapYPos[i] = rand.nextInt(4);
            System.out.println("trapXPos[" + i + "]: " + trapXPos[i]);
            System.out.println("trapYPos[" + i + "]: " + trapYPos[i]);
            if((check(usedXPos, trapXPos[i])) && (check(usedYPos, trapYPos[i]))){
                i--;
            }else{
                usedXPos[mobNumber + i] = trapXPos[i];
                usedYPos[mobNumber + i] = trapYPos[i];
            }
        }
        for(int i = 0; i < elixirNumber; i++){
            elixirYPos[i] = rand.nextInt(4);
            elixirXPos[i] = 1 + rand.nextInt(8);
            System.out.println("elixirXPos[" + i + "]: " + elixirXPos[i]);
            System.out.println("elixirYPos[" + i + "]: " + elixirYPos[i]);
            if((check(usedXPos, elixirXPos[i])) && (check(usedYPos, elixirYPos[i]))){
                i--;
            }else{
                usedXPos[mobNumber + trapNumber + i] = elixirXPos[i];
                usedYPos[mobNumber + trapNumber + i] = elixirYPos[i];
            }
        }
    }

    // check if the specified value is on the array
    private static boolean check(int[] arr, int value)
    {
        boolean isThere = false;
        for (int element : arr) {
            if (element == value) {
                isThere = true;
                break;
            }
        }
        if(isThere) System.out.println("rerolling");
        return isThere;
    }

    public void spawnPlayer(){
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
        UI.drawSprite(bossy);
    }

    public void spawnTraps(int trapNumber){
        trapArr = new Trap[trapNumber];
        for(int i = 0; i < trapNumber; i++){
            Random rand = new Random();
            trapArr[i] = new Trap(trapXPos[i], trapYPos[i], rand.nextBoolean());
            if(debugMode) UI.drawSprite(trapArr[i]);
        }
    }
    public void spawnElixir(int elixirNumber){
        elxArr = new Elixir[elixirNumber];
        for(int i = 0; i < elixirNumber; i++){
            elxArr[i] = new Elixir(elixirXPos[i], elixirYPos[i]);
            if(debugMode) UI.drawSprite(elxArr[i]);
        }
    }

    public boolean getFinalState(){
        return this.finalState;
    }

    public Hero getHero(){
        return this.hero;
    }

    public static boolean checkMonster(int x, int y){
        boolean found = false;
        if(bossy.getXPos() == x && bossy.getYPos() == y) found = true;
        for(int i = 0; i < mobNumber; i++){
            if(mobXPos[i] == x && mobYPos[i] == y) found = true;
        }
        return found;
    }

    public static Monster getMonster(int x, int y){
        int i;
        if(bossy.getXPos() == x && bossy.getYPos() == y) return bossy;
        for(i = 0; i < mobNumber; i++){
            if(mobXPos[i] == x && mobYPos[i] == y) break;
        }
        return monArr[i];
    }

    public static boolean checkTrap(int x, int y){
        boolean found = false;
        for(int i = 0; i < trapNumber; i++){
            if(trapXPos[i] == x && trapYPos[i] == y) found = true;
        }
        return found;
    }

    public static boolean checkElixir(int x, int y){
        boolean found = false;
        for(int i = 0; i < elixirNumber; i++){
            if(elixirXPos[i] == x && elixirYPos[i] == y) found = true;
        }
        return found;
    }
}
