public class Barbarian extends Hero {
    public Barbarian(int ATK, int DEF, int HP, int xpos, int ypos, String name){
        super(ATK, DEF, HP, xpos, ypos, name);
    }
    // Special attacks (can only be used once per fight)
    public void rageAttack(Mob p1, Mob p2){
        ATK *= 1.5;
        super.attack(p1, p2);
        ATK /= 1.5;
    }
}
