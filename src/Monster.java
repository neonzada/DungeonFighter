public class Monster extends Mob{
	private int type;
	public Monster(int ATK, int DEF, int HP, int xpos, int ypos, int type){
		super(ATK, DEF, HP, xpos, ypos);
		this.type = type;
	}

	public int getType(){
		return this.type;
	}
}
