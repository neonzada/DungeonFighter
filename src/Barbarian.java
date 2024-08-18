public class Barbarian extends Hero {
    public Barbarian(int ATK, int DEF, int HP, int xpos, int ypos, String name){
        super(ATK, DEF, HP, xpos, ypos, name);
    }

    public void specialAttack(Mob p1, Mob p2){
        int atkBefore = this.ATK;
        this.ATK *= 1.5;
        super.attack(p1, p2);
        this.ATK = atkBefore;
    }
}
