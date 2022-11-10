import java.util.concurrent.*;
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
    private BlockingQueue<Card> hand;
    private static ArrayList<Player> players = new ArrayList<Player>();
    private CardDeck drawDeck;
    private CardDeck discardDeck;
    private int winner;

    /*
     * Constructor method that 
     *      - sets player's id number
     *      - initialises the player's hand
     *      - adds the player to a static list of players
     * @param playerId Boolean lastPlayer to be able to link the last player to deck1 as their 
     * discard deck
     */
    public Player(int playerId,Boolean lastPlayer){
        //assign the playerId
        this.playerId = playerId;

        //assign the player an empty hand
        this.hand = new LinkedBlockingDeque<Card>();

        //assign the player's draw and discard decks
        //these decks must be created before creating the players
        for (CardDeck deck: CardDeck.getDecks()){
            if (deck.getId()==playerId){
                this.drawDeck = deck;
            }
            if ((deck.getId()-1) == playerId){
                this.discardDeck = deck;
            } else if (deck.getId()==1 && lastPlayer == true){
                this.discardDeck = deck;
            }
        }
        //set winner as no one
        winner = 0;

        
        //add the player to the static list of players
        players.add(this);
    }

    /*
     * A getter method for the private attribute playerId.
     * @param none
     * @return the int player Id.
     */
    public int getPlayerId(){
        return playerId;
    }
    //this method is used in testing
    public int getDrawId(){
        return drawDeck.getId();
    }
    //this method is used in testing
    public int getDiscardId(){
        return discardDeck.getId();
    }
    //this method is used in testing
    public BlockingQueue<Card> getHand(){
        return hand;
    }
    public static ArrayList<Player> getPlayers(){
        return players;
    }

    public synchronized Card discardCard(){
        //go through the hand, moving prefered cards to the back until one is ok to discard.
        while (hand.peek().getValue() == playerId){
            hand.add(hand.poll());
        }
        Card discardCard = hand.poll();
        System.out.println("Player "+playerId+" discards a "+discardCard+" to deck "+discardDeck.getId());
        return discardCard;
    }
    public synchronized void drawCard(Card card){
        hand.add(card);
        System.out.println("Player "+playerId+" draws a "+card.getValue()+" from deck "+drawDeck.getId());
    }
    public String toString(){
        return ("This is player "+this.playerId+" who has a hand of "+this.hand);
    }
    
    @Override
    public void run(){
        System.out.println("player "+playerId +" initial hand: "+ hand.toString());
        //print opening hand
        while (winner == 0){
            //System.out.println("Player "+this.playerId+"'s hand is: "+this.hand.toString());
            //check if this thread has won by all cards in its hand being the same
            Boolean hasWon = true;
            for (Card card : hand) {
                if (card.getValue() != hand.peek().getValue()) {
                    hasWon = false;
                }
            }
            if (hasWon == true){
                for (Player player : players){
                    player.winner = this.playerId;
                }
            }else{
                //draw and discard a card.
                try{
                    //draw a new card and tell the overseer about it.
                    drawCard(drawDeck.getContents().poll());

                    //discard a card
                    discardDeck.getContents().add(discardCard());
                    Thread.sleep(10);
                }catch (InterruptedException e){
                    //if there is an interupt, the thread will stop
                    // With the new while loop I dont think this is necessary anymore but Ive left it in just in case
                    System.out.println("InterruptedException "+playerId);
                    return;
                }catch (NullPointerException e){
                    //if discardDeck or drawDeck were not initialized, the thread will stop
                    System.out.println("NullPointerException "+playerId+discardDeck.getId());
                    return;
                }
                System.out.println("Player "+playerId+" current hand is: "+hand.toString());
            }
        }
        if (winner == playerId){
            System.out.println("Player "+playerId+" wins");
        }else{
            System.out.println("player "+winner+" has informed player "+playerId+" that player "+winner+" has won");
        }
        System.out.println("Player "+playerId+" exits");
        System.out.println("Player "+playerId+" final hand: "+hand.toString());
    }
}
