import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;

/*
 * Important, compile using this command from outside the src file: javac -d ./bin/ ./src/*.java
 * <p>
 */
public class Player implements Runnable{
    private int playerId;
    private PriorityBlockingQueue<Card> hand;
    //private PriorityBlockingQueue<Card> drawPile;
    //private PriorityBlockingQueue<Card> discardPile;
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static int winner = 0;

    /*
     * A getter method for the private attribute playerId.
     * @param none
     * @return the int player Id.
     */
    public int getPlayerId(){
        return this.playerId;
    }
    public Card discardCard(){
        try{
            return hand.take();
        }catch (InterruptedException e){
            e.printStackTrace();
            return new Card(0);
        }
    }
    public void drawCard(Card card){
        this.hand.add(card);
    }
    public static ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * Constructor method that sets player's id number and adds the player to a static list of players
     * @param playerId
     */
    public Player(int playerId){
        //assign the playerId
        this.playerId = playerId;
        //assign the player an empty hand
        this.hand = new PriorityBlockingQueue<Card>();
        //add the player to the static list of players
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
