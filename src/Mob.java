public abstract class Mob {
    protected int ATK, DEF, HP, xpos, ypos;
    public Mob(int ATK, int DEF, int HP){
        this.ATK = ATK;
        this.DEF = DEF;
        this.HP = HP;
    }
    public void takeDamage(int dmg){
        HP -= dmg;
    }
    public void move(){
        // TODO: Move function
    }
}
