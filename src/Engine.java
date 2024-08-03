public class Engine extends Thread{
    private String chosenClass;
    private int mobNumber;
    //TODO: implement multithreading when the game begins
    public Engine(String chosenClass){
        this.chosenClass = chosenClass;
        this.mobNumber = 4;
    }
}
