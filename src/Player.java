import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;
public class Player implements Runnable{
    private int playerId;
    private PriorityBlockingQueue<Card> hand = new PriorityBlockingQueue<Card>();
    private static ArrayList <Player> players = new ArrayList<Player>();
    private static int winner = 0;

    public void setHand(Card newCard){
        this.hand.add(newCard);
    }
    public int getPlayerId(){
        return this.playerId;
    }
    /**
     * Constructor method that sets player's id number and adds the player to a static list of players
     * @param playerId
     */
    public Player(int playerId){
        this.playerId = playerId;
        players.add(this);
    }
    public String toString(){
        return ("This is player "+this.playerId+" who has a hand of "+this.hand);
    }
    @Override
    public void run(){
        while (!Thread.interrupted()){

            Thread.yield();
        }
        //exit
    }
}
