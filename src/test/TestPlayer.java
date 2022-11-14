package test;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import main.Card;
import main.CardDeck;
import main.Player;

public class TestPlayer {
    /*
     * testConstructorDraw()
     * Do players choose the correct draw pile?
     * 
     * Create a Player with playerId = 1 and a cardDeck with deckId = 1.
     * If the Player is assigned the cardDeck as their draw pile, the test passes.
     */
    @Test
    public void testConstructorDraw(){
        CardDeck deck = new CardDeck(1);
        Player player = new Player(1,true);

        assert player.getDrawId() == deck.getId();
    }

    /*
     * testConstructorDiscard()
     * Do players choose the correct discard pile?
     * 
     * Create a Player with playerId = 5 and a cardDeck with deckId = 6.
     * If the Player is assigned the cardDeck as their discard pile, the test passes.
     */
    @Test
    public void testConstructorDiscard(){
        CardDeck deck = new CardDeck(1);
        Player player = new Player(1,true);

        assert player.getDiscardId() == deck.getId();
    }

    /*
     * testDiscard()
     * Do players discard the first card in their hand if it isn't the preferred card?
     * 
     * Create a Player with playerId = 1 with a hand 2345.
     * If the hand after executing the discardCard() function is 345, the test passes.
     */
    @Test
    public void testDiscard(){
        Player player = new Player(1,true);
        for (int i = 2; i <= 5; i++){
            player.getHand().add(new Card(i));
        }
        player.discardCard();

        assert player.getHand().poll().getValue() == 3;
        assert player.getHand().poll().getValue() == 4;
        assert player.getHand().poll().getValue() == 5;
        assertTrue(player.getHand().isEmpty());
    }

    /*
     * testDiscard2()
     * Repeat testDiscard() with playerId = 2. 
     * 
     * Create a Player with playerId = 2 with a hand 1234.
     * If the hand after executing the discardCard() function is 234, the test passes.
     */
    @Test
    public void testDiscard2(){
        Player player = new Player(2,true);
        for (int i = 1; i <= 4; i++){
            player.getHand().add(new Card(i));
        }
        player.discardCard();

        assert player.getHand().poll().getValue() == 2;
        assert player.getHand().poll().getValue() == 3;
        assert player.getHand().poll().getValue() == 4;
        assertTrue(player.getHand().isEmpty());
    }

    /*
     * testDiscardCycle()
     * Do players discard the second card in their hand if the first is the preferred card?
     * 
     * Create a Player with playerId = 1 with a hand 1234.
     * If the hand after executing the discardCard() function is 341 the test passes.
     */
    @Test
    public void testDiscardCycle(){
        Player player = new Player(1,true);
        for (int i = 1; i <= 4; i++){
            player.getHand().add(new Card(i));
        }
        player.discardCard();

        assert player.getHand().poll().getValue() == 3;
        assert player.getHand().poll().getValue() == 4;
        assert player.getHand().poll().getValue() == 1;
        assertTrue(player.getHand().isEmpty());
    }

    /*
     * testDiscardCycle2()
     * Repeat testDiscardCycle() with playerId = 2. 
     * 
     * Create a Player with playerId = 2 with a hand 2345.
     * If the hand after executing the discardCard() function is 452 the test passes.
     */
    @Test
    public void testDiscardCycle2(){
        Player player = new Player(2,true);
        for (int i = 2; i <= 5; i++){
            player.getHand().add(new Card(i));
        }
        player.discardCard();

        assert player.getHand().poll().getValue() == 4;
        assert player.getHand().poll().getValue() == 5;
        assert player.getHand().poll().getValue() == 2;
        assertTrue(player.getHand().isEmpty());
    }

    /*
     * testDiscardFullCycle()
     * Do players discard the last card in their hand if the first three are the preferred card?
     * 
     * Create a Player with playerId = 1 with a hand 1112.
     * If the hand after executing the discardCard() function is 111 the test passes.
     */
    @Test
    public void testDiscardFullCycle(){
        Player player = new Player(1,true);
        for (int i = 1; i <= 3; i++){
            player.getHand().add(new Card(1));
        }
        player.getHand().add(new Card(2));
        player.discardCard();

        assert player.getHand().poll().getValue() == 1;
        assert player.getHand().poll().getValue() == 1;
        assert player.getHand().poll().getValue() == 1;
        assertTrue(player.getHand().isEmpty());
    }

    /*
     * testDiscardFullCycle2()
     * Do players discard the last card in their hand if the first three are the preferred card?
     * 
     * Create a Player with playerId = 2 with a hand 2221.
     * If the hand after executing the discardCard() function is 222 the test passes.
     */
    @Test
    public void testDiscardFullCycle2(){
        Player player = new Player(2,true);
        for (int i = 1; i <= 3; i++){
            player.getHand().add(new Card(2));
        }
        player.getHand().add(new Card(1));
        player.discardCard();

        assert player.getHand().poll().getValue() == 2;
        assert player.getHand().poll().getValue() == 2;
        assert player.getHand().poll().getValue() == 2;
        assertTrue(player.getHand().isEmpty());
    }

    /*
     * testWinningAtStart()
     * Do players declare victory if they start the game with a winning hand?
     * 
     * Create a Player with playerId = 1 with a hand 1111.
     * If the static variable winner is changed to 1, the test passes.
     */

    /*
     * TestWinningAfterStart()
     * Do players declare victory if they draw a card and get a winning hand?
     * 
     * Create a Player with playerId = 1 with a hand 2111 and a cardDeck with a single (1) card.
     * If the player declares victory with a hand 1111, the test passes.
     */

    /*
     * TestLosingAtStart()
     * Do players concede defeat if another starts with a winning hand?
     * 
     * Create a Player with playerId = 1 and hand 2222, an empty cardDeck, and set winner = 2.
     * If the player does not try to take a card, the test passes.
     */

    /*
     * TestLosingAfterStart()
     * Do players concede defear if another obtains a winning hand?
     * 
     * Create a Player with playerId = 1, a hand of 2222, a cardDeck with 3333, and after one second set winner = 2.
     * If the player does not try to draw from an empty deck, the test passes.
     */
}
