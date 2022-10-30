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
    public final synchronized Card discardCard(){
        try{
            return hand.take();
        }catch (InterruptedException e){
            return new Card(0);
        }
    }
    public final synchronized void drawCard(Card card){
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
        //determine draw and discard decks
        CardDeck drawDeck = CardDeck.getDecks().get(playerId+1);
        CardDeck discardDeck;
        if (playerId == players.size()+1){
            discardDeck = CardDeck.getDecks().get(0);
        }else{
            discardDeck = CardDeck.getDecks().get(playerId+2);
        }

        //print opening hand
        while (winner == 0){
            //check if this thread has won by all cards in its hand being the same
            if (this.hand.stream().distinct().count() <= 1){
                winner = playerId;
            }else{
                //draw and discard a card.
                try{
                    discardDeck.getContents().add(discardCard());
                    drawCard(drawDeck.getContents().take());
                }catch(InterruptedException e){
                    return;
                }
            }
        }
        //exit procedure
    }
}
