public class Elixir{
    private int xpos, ypos;
    public Elixir(int xpos, int ypos){
        this.xpos = xpos;
        this.ypos = ypos;
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
