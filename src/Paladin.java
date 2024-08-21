public class Paladin extends Hero {
    private int totalHP;
    public Paladin(int ATK, int DEF, int HP, int xpos, int ypos, String name){
        super(ATK, DEF, HP, xpos, ypos, name);
        this.totalHP = HP;
    }
    // Special attacks (can only be used once per fight)
    // idk if it need a this keyword, ask around
    public void specialAttack(Mob p1, Mob p2){
        int hpBefore = this.HP;
        if(this.HP < this.totalHP/2){
            this.HP += this.totalHP/2;
        }else if(this.HP > this.totalHP){
            this.HP = hpBefore;
        }else{
            this.HP = this.totalHP;
        }
        super.attack(p1, p2);
    }
}
