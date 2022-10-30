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
        //print opening hand
        while (winner == 0){
            //check if this thread has won by all cards in its hand being the same
            if (this.hand.stream().distinct().count() <= 1){
                winner = playerId;
                //print to file
            }else{
                //check if \nother thread has won
                if (winner == 0){
                    //draw
                    //discard
                }else{
                    //interrupt
                }
            }
            //Thread.yield();
        }
        //exit
        //print ending status
    }
    /*
     * An atomic action action designed to draw and discard 
     * a card from a players hand such that the hand size is always 4.
     */
    public Card discardCard(){
        try{
            return hand.take();
        }catch (InterruptedException e){
            return new Card(0);
        }
    }
    public void drawCard(Card card){
        hand.add(card);
    }
}
