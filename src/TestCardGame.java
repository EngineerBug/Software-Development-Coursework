import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TestCardGame {
    @BeforeClass
    public static void classSetup(){
        try {
            //create temporary files
            File emptyTempFile = File.createTempFile("empty", ".txt");
            File fourTempFile = File.createTempFile("four", ".txt");
            File invalidTempFile = File.createTempFile("invalid", ".txt");

            //write to temp files
            BufferedWriter fourWriter = new BufferedWriter(new FileWriter("four.txt"));
            BufferedWriter invalidWriter = new BufferedWriter(new FileWriter("invlid.txt"));

            for(int i = 0; i < 32; i++){
                fourWriter.write(i+"\n");
            }
            invalidWriter.write("zsdfgj;dskjfghuis");

            //close writers
            fourWriter.close();
            invalidWriter.close();

            emptyTempFile.deleteOnExit();
            fourTempFile.deleteOnExit();
            invalidTempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @After
    public void methodSetDown(){   
        Player.getPlayers().clear();
        CardDeck.getDecks().clear();
    }

    /*
     * testMainEmptyPack()
     * Does cardGame refuse an empty pack?
     * 
     * 
     */
    @Test
    public void testMainEmptyPack(){
        BlockingQueue<Card> pack = new LinkedBlockingDeque<Card>();
        assertFalse(CardGame.generatePack("empty.txt", pack, 2));
    }
    /*
     * testMainFakePack()
     * Does cardGame refuse a non-existant pack?
     */
    @Test
    public void testMainFakePack(){
        BlockingQueue<Card> pack = new LinkedBlockingDeque<Card>();
        assertFalse(CardGame.generatePack("nonexistant.txt", pack, 0));
    }
    /*
     * testMainSmallPack()
     * Does cardGame refuse a pack with too few numbers?
     */
    @Test
    public void testMainSmallPack(){
        BlockingQueue<Card> pack = new LinkedBlockingDeque<Card>();
        assertFalse(CardGame.generatePack("four.txt", pack, 6));
    }
    /*
     * testMainLargePack()
     * Does cardGame only take the require numbers from a list with too many numbers?
     * 
     * 
     */
    @Test
    public void testMainLargePack(){
        BlockingQueue<Card> pack = new LinkedBlockingDeque<Card>();
        assertTrue(CardGame.generatePack("four.txt", pack, 2));
        assertEquals(16, pack.size());
    }
    /*
     * testMainInvalidLinesPack()
     * Does cardGame refuse a pack with numbers on the same line?
     */
    @Test
    public void testMainInvalidLinesPack(){
        BlockingQueue<Card> pack = new LinkedBlockingDeque<Card>();
        assertFalse(CardGame.generatePack("invalid.txt", pack, 2));
    }
    /*
     * testMainZeroPlayers()
     * Does cardGame refuse to play with zero players.
     */
    @Test
    public void testMainZeroPlayers(){
        BlockingQueue<Card> pack = new LinkedBlockingDeque<Card>();
        assertFalse(CardGame.generatePack("four.txt", pack, 0));
    }
    /*
     * testMainNegativePlayers()
     * Does cardGame refuse to play with negative players.
     */
    @Test
    public void testMainNegativePlayers(){
        BlockingQueue<Card> pack = new LinkedBlockingDeque<Card>();
        assertFalse(CardGame.generatePack("four.txt", pack, -4));
    }
    /*
     * testGetPlayers()
     * Does the method ask for an input and read it correctly? 
     */
    @Test
    public void testGetPlayers(){
        Scanner scanner = new Scanner("2");
        Integer expected = 2;
        assertEquals(expected, CardGame.getPlayers(scanner));
    }
    /*
     * testGetPack()
     * Does the method ask for an input and read it correctly? 
     */
    @Test
    public void testGetPack(){
        Scanner scanner = new Scanner("file_name.txt");
        assertEquals("file_name.txt", CardGame.getPack(scanner));
    }
    /*
     * testGeneratePlayerThreads()
     * Does the method create the given number of player threads and save them? 
     */
    @Test
    public void testGeneratePlayerThreads(){
        CardGame.generatePlayerThreads(2);
        assertEquals(2, Player.getPlayers().size());
    }
    /*
     * testGenerateDecks()
     */
    @Test
    public void testGenerateDecks(){
        CardGame.generateDecks(2);
        assertEquals(2, CardDeck.getDecks().size());
    }
    /*
     * testDealToPLayers()
     */
    @Test
    public void testDealToPLayers(){
        Player player1 = new Player(1,false);
        Player player2 = new Player(2,true);
        BlockingQueue<Card> pack = new LinkedBlockingDeque<Card>();
        for (int i = 0; i < 4; i++){
            pack.add(new Card(i));
            pack.add(new Card(i));
        }
        CardGame.dealToPlayers(pack);
        for (int i = 0; i < 4; i++){
            assertEquals(i, player1.getHand().poll().getValue());
            assertEquals(i, player2.getHand().poll().getValue());
        }
        assertTrue(player1.getHand().isEmpty());
        assertTrue(player2.getHand().isEmpty());
    }
    /*
     * testDealToDecks()
     */
    @Test
    public void testDealToDecks(){
        CardDeck deck1 = new CardDeck(1);
        CardDeck deck2 = new CardDeck(2);
        BlockingQueue<Card> pack = new LinkedBlockingDeque<Card>();
        for (int i = 1; i < 5; i++){
            pack.add(new Card(i));
            pack.add(new Card(i));
        }
        CardGame.dealToDecks(pack);
        for (int i = 1; i < 5; i++){
            assertEquals(i, deck1.getContents().poll().getValue());
            assertEquals(i, deck2.getContents().poll().getValue());
        }
        assertTrue(deck1.getContents().isEmpty());
        assertTrue(deck2.getContents().isEmpty());
    }
}
