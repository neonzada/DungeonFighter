public class Paladin extends Hero {
    private int totalHP;
    public Paladin(int ATK, int DEF, int HP, int xpos, int ypos, String name){
        super(ATK, DEF, HP, xpos, ypos, name);
        this.totalHP = HP;
    }
    // Special attacks (can only be used once per fight)
    // idk if it need a this keyword, ask around
    public void hpRegen(){
        if(HP < this.totalHP/2){
            HP += this.totalHP/2;
        }else{
            HP = this.totalHP;
        }
    }
}
