package main;
import java.util.concurrent.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.io.BufferedWriter;

public class CardDeck {
    //private attributes
    private BlockingQueue<Card> contents;
    private int deckId;
    
    private static ArrayList<CardDeck> decks = new ArrayList<CardDeck>();

    //public setter/getter methods
    public int getId(){
        return deckId;
    }
    public BlockingQueue<Card> getContents(){
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
        //add the deck to the static arrayList
        decks.add(this);
    }

    //toString() method
    public String toString(){
        return "This deck is labeled "+deckId+" contains "+contents.toString()+".";
    }

    public void writeContents(){
        try {
            BufferedWriter writer = new BufferedWriter(
                new FileWriter("deck"+deckId+"_output.txt")
            );
            writer.write("Deck"+deckId+" contents: "+getContents().toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
