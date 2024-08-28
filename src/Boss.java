public class Boss extends Monster {
  private boolean regenReady;
	public Boss(int ATK, int DEF, int HP, int xpos, int ypos, int type){
		super(ATK, DEF, HP, xpos, ypos, type);
    regenReady = false;
	}

  @Override
  public void takeDamage(int dmg){
    if(regenReady){
      HP++;
      UI.bossRegenAlert();
    }
    HP -= dmg;
    regenReady = !regenReady;
  }
}