public class Hero extends Mob{
    protected int bagSize, xpos, ypos;
    protected String name;
    public Hero(int ATK, int DEF, int HP, int xpos, int ypos, String name){
        super(ATK, DEF, HP, xpos, ypos);
        this.name = name;
        this.bagSize = 5;
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

    // increment
    public void incATK(){
        this.ATK++;
    }
    public void incDEF(){
        this.DEF++;
    }
    public void incHP(){
        this.HP++;
    }

    // decrement
    public void decATK(){
        this.ATK--;
    }
    public void decDEF(){
        this.DEF--;
    }
    public void decHP(){
        this.HP--;
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
}
