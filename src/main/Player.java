package main;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.io.*;

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
        return discardCard;
    }
    public synchronized void drawCard(Card card){
        hand.add(card);
    }
    public synchronized Card discardAndDraw(Card card){
        Card discardCard = discardCard();
        discardDeck.getContents().add(discardCard);
        drawCard(card);
        return discardCard;
    }
    public String toString(){
        return ("This is player "+this.playerId+" who has a hand of "+this.hand);
    }
    
    @Override
    public void run(){
        try {
            BufferedWriter writer = new BufferedWriter(
                new FileWriter("player"+playerId+"_output.txt")
            );
            writer.write("player "+playerId +" initial hand: "+ hand.toString()+"\n");
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
                    for (CardDeck deck : CardDeck.getDecks()){
                        deck.writeContents();
                    }
                }else{
                    //draw and discard a card.
                    try{
                        //draw a new card and tell the overseer about it.
                        Card drawCard = drawDeck.getContents().poll();
                        Card discardCard = discardAndDraw(drawCard);
                        writer.write("Player "+playerId+" discards a "+discardCard+" to deck "+discardDeck.getId()+"\n");
                        writer.write("Player "+playerId+" draws a "+drawCard.getValue()+" from deck "+drawDeck.getId()+"\n");
                        //drawCard(drawDeck.getContents().poll());

                        //discard a card
                        //discardDeck.getContents().add(discardCard());
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        //if there is an interupt, the thread will stop (closing the write)
                        // With the new while loop I dont think this is necessary anymore but Ive left it in just in case
                        System.out.println("InterruptedException "+playerId);
                        writer.close();
                        return;
                    }catch (NullPointerException e){
                        //if discardDeck or drawDeck were not initialized, the thread will stop (closing the write)
                        System.out.println("NullPointerException "+playerId+discardDeck.getId());
                        writer.close();
                        return;
                    }
                    writer.write("Player "+playerId+" current hand is: "+hand.toString()+"\n");
                }
            }
            if (winner == playerId){
                writer.write("Player "+playerId+" wins\n");
            }else{
                writer.write("player "+winner+" has informed player "+playerId+" that player "+winner+" has won\n");
                
            }
            writer.write("Player "+playerId+" exits\n");
            writer.write("Player "+playerId+" final hand: "+hand.toString()+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
