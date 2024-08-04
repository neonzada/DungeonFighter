public class Engine extends Thread{
    private String chosenClass;
    private int mobNumber;
    public enum mobIndex{
        SLIME,
        GOBLIN,
        SKELLY,
        ORC,
        SKELLYKING,
        DEATH
    }
    //TODO: implement multithreading when the game begins
    public Engine(String chosenClass){
        this.chosenClass = chosenClass;
        this.mobNumber = 4;
    }
    public void spawnMobs(int mobNum){
        for(int i = 0; i < mobNum; i++){

        }
    }
}
