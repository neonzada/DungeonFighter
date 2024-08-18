import java.util.Random;
public abstract class Mob {
    protected int ATK, DEF, HP, xpos, ypos;
    public Mob(int ATK, int DEF, int HP, int xpos, int ypos){
        this.ATK = ATK;
        this.DEF = DEF;
        this.HP = HP;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    // Basic actions
    public void attack(Mob p1, Mob p2){
        Random rand = new Random();
        int p1dmg = rand.nextInt(5);
        int p2def = rand.nextInt(5);
        int totalDmg = p1dmg + this.ATK;
        int totalDef = p2def + p2.getDEF();
        if(totalDmg >= totalDef){
            p2.takeDamage(totalDmg - totalDef);
            UI.happening(p1, p2, totalDmg, totalDef);
        }else{
            p1.takeDamage(totalDef - totalDmg);
            UI.happening(p1, p2, totalDmg, totalDef);
        }
    }

    public void takeDamage(int dmg){
        HP -= dmg;
    }
    public void setPos(int xpos, int ypos){
        this.xpos = xpos;
        this.ypos = ypos;
    }

    // Get pos
    public int getXPos(){
        return this.xpos;
    }
    public int getYPos(){
        return this.ypos;
    }

    public void setHP(int HP){
        this.HP = HP;
    }
    public int getHP(){
        return this.HP;
    }

    public int getDEF(){
        return this.DEF;
    }
    public int getATK(){
        return this.ATK;
    }

}
