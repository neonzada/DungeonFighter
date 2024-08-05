public class Hero extends Mob{
    protected int bagSize, xpos, ypos;
    protected String name;
    public Hero(int ATK, int DEF, int HP, String name){
        super(ATK, DEF, HP);
        this.name = name;
        this.bagSize = 5;
    }

    // Basic actions
    public void attack(){
        // attack the enemy
    }

    public void displayInfo(){
        System.out.println("name: " + name + ", HP: " + HP + ", ATK: " + ATK + ", DEF: " + DEF);
    }

    // Getters
    public int getATK(){
        return this.ATK;
    }
    public int getDEF(){
        return this.DEF;
    }
    public int getHP(){
        return this.HP;
    }
    public String getName(){
        return this.name;
    }

    // Setters
    public void setATK(int ATK){
        this.ATK = ATK;
    }
    public void setDEF(int DEF){
        this.DEF = DEF;
    }
    public void setHP(int HP){
        this.HP = HP;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPos(int x, int y){
        this.xpos = x;
        this.ypos = y;
    }
}
