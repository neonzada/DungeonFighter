public class Barbarian extends Hero {
    public Barbarian(int ATK, int DEF, int HP, String name){
        super(ATK, DEF, HP, name);
    }
    // Special attacks (can only be used once per fight)
    public void rageAttack(){
        ATK *= 1.5;
        super.attack();
        ATK /= 1.5;
    }
}
