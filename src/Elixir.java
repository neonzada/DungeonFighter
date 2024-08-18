public class Elixir{
    private int xpos, ypos;
    private boolean isActive;
    public Elixir(int xpos, int ypos){
        this.xpos = xpos;
        this.ypos = ypos;
        this.isActive = true;
    }

    public boolean getActive(){
        return this.isActive;
    }
    public void deactivateElixir(){
        this.isActive = false;
    }
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
