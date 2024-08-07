public class Monster extends Mob{
    private int type;
    public Monster(int ATK, int DEF, int HP, int xpos, int ypos, int type){
        super(ATK, DEF, HP, xpos, ypos);
        this.type = type;
    }
    // Normal monster, random image and random HP (balanced)
    public int getType(){
        return this.type;
    }
}
