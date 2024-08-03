public class Warrior extends Hero {
    public Warrior(int ATK, int DEF, int HP, String name){
        super(ATK, DEF, HP, name);
    }
    // Special attacks (can only be used once per fight)
    public void defensivePosture(){
        this.HP *= 1.5;
    }
    public void endPosture(){
        this.HP /= 1.5;
    }
}
