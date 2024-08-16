public abstract class Mob {
    protected int ATK, DEF, HP, xpos, ypos;
    public Mob(int ATK, int DEF, int HP, int xpos, int ypos){
        this.ATK = ATK;
        this.DEF = DEF;
        this.HP = HP;
        this.xpos = xpos;
        this.ypos = ypos;
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
}
