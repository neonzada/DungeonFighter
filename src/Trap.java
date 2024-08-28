import java.util.Random;
public class Trap{
	private int xpos, ypos;
	private boolean type, isActive;
	private Random rand;
	public Trap(int xpos, int ypos, boolean type){
		this.xpos = xpos;
		this.ypos = ypos;
		this.type = type;
		this.isActive = true;
		rand = new Random();
	}
	// Get type for different sprites
	public boolean getType(){
		return this.type;
	}

	public void deactivateTrap(){
		this.isActive = false;
	}
	
	public boolean getActive(){
		return this.isActive;
	}

	public int dealDamage(Hero h){
		int dmg = 0;
		if(type){
			h.decHP();
			dmg = 1;
		}else{
			dmg = rand.nextInt(2) + 1;
			h.setHP(h.getHP() - dmg);
		}
		return dmg;
	}
	// Setters and getters
	public void setPos(int xpos, int ypos){
		this.xpos = xpos;
		this.ypos = ypos;
	}

	public int getXPos(){
		return this.xpos;
	}
	public int getYPos(){
		return this.ypos;
	}
}
