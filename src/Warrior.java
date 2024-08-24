public class Warrior extends Hero {
	public Warrior(int ATK, int DEF, int HP, int xpos, int ypos, String name){
		super(ATK, DEF, HP, xpos, ypos, name);
	}
	// Special attacks (can only be used once per fight)
	public void specialAttack(Mob p1, Mob p2){
		int defBefore = this.DEF;
		this.DEF *= 1.5;
		super.attack(p1, p2);
		this.DEF = defBefore;
	}
}
