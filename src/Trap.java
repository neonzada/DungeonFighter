public class Trap{
    private int xpos, ypos;
    private boolean type;
    public Trap(int xpos, int ypos, boolean type){
        this.xpos = xpos;
        this.ypos = ypos;
        this.type = type;
    }
    // Get type for different sprites
    public boolean getType(){
        return this.type;
    }

    // Setters and getters
    // i wish i could extend Mob but i have no HP to give this trap
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
