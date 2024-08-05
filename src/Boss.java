public class Boss extends Mob {
    private int type;
    public Boss(int ATK, int DEF, int HP, int type){
        super(ATK, DEF, HP);
        this.type = type;
    }
    // TODO: Charge attack maybe?
    // Different attributes, regen maybe?
}
