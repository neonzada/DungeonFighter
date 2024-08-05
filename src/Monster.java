public class Monster extends Mob{
    private int type;
    public Monster(int ATK, int DEF, int HP, int type){
        super(ATK, DEF, HP);
        this.type = type;
    }
    // Normal monster, random image and random HP (balanced)
}
