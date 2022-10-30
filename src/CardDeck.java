import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;

public class CardDeck {
    //private attributes
    private PriorityBlockingQueue<Card> contents;
    private int deckId;
    
    private static ArrayList<CardDeck> decks = new ArrayList<CardDeck>();

    //public setter/getter methods
    public int getId(){
        return deckId;
    }
    public PriorityBlockingQueue<Card> getContents(){
        return contents;
    }

    public static ArrayList<CardDeck> getDecks(){
        return decks;
    }

    //constructor
    public CardDeck(int deckId){
        //assign an Id
        this.deckId = deckId;
        //initialise the contents
        contents = new PriorityBlockingQueue<Card>();
    }

    //toString() method
    public String toString(){
        return "This deck is labeled "+deckId+" contains "+contents.toString()+".";
    }
}
