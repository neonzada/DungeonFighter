import java.util.ArrayList;

public abstract class Hero extends Mob{
    protected int xpos, ypos;
    protected ArrayList<Elixir> storedElixir;
    protected String name;
    public Hero(int ATK, int DEF, int HP, int xpos, int ypos, String name){
        super(ATK, DEF, HP, xpos, ypos);
        this.name = name;
        storedElixir = new ArrayList<Elixir>();
    }

    public abstract void specialAttack(Mob p1, Mob p2);

    public void displayInfo(){
        System.out.println("name: " + name + ", HP: " + HP + ", ATK: " + ATK + ", DEF: " + DEF);
    }

    public int getStoredElixir(){
        return storedElixir.size();
    }

    public void storeElixir(Elixir e){
        storedElixir.add(e);
    }

    public void consumeElixir(){
        if(!storedElixir.isEmpty()){
            incHP(3);
            storedElixir.removeLast();
        }
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
    public void incHP(int a){
        this.HP += a;
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
