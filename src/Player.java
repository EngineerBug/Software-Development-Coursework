import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;

/*
 * Input/output:
 * https://www.youtube.com/watch?v=ScUJx4aWRi0
 * 
 * Threading:
 * https://www.youtube.com/watch?v=r_MbozD32eo
 * 
 * Testing:
 * https://www.youtube.com/watch?v=vZm0lHciFsQ
 * 
 * Important, compile using this command from outside the src file: javac -d ./bin/ ./src/*.java
 */
public class Player implements Runnable{
    private int playerId;
    private PriorityBlockingQueue<Card> hand;
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static int winner = 0;

    /*
     * A getter method for the private attribute playerId.
     * @param none
     * @return the int player Id.
     */
    public int getPlayerId(){
        return playerId;
    }
    public static ArrayList<Player> getPlayers(){
        return players;
    }
    public synchronized Card discardCard(){
        try{
            //go through the hand, moving prefered cards to the back until one is ok to discard.
            while (hand.peek().getValue() == playerId){
                hand.add(hand.take());
            }
            return hand.take();
        }catch (InterruptedException e){
            //if the system is interupted, a dud card is added because no one will need it.
            return new Card(0);
        }
    }
    public synchronized void drawCard(Card card){
        hand.add(card);
    }

    /**
     * Constructor method that 
     *      - sets player's id number
     *      - initialises the player's hand
     *      - adds the player to a static list of players
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
        System.out.println("Player "+playerId+"'s initial hand: "+hand.toString());   
    }
}
