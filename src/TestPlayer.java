import static org.junit.Assert.assertTrue;
import org.junit.Test;

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
    @Test
    public void testWinningAtStart(){
        Player player = new Player(1,true);
        for (int i = 1; i <= 4; i++){
            player.getHand().add(new Card(1));
        }
        Thread newThread = new Thread(player);
        newThread.run();
        assert player.getWinner()==1;
    }
    /*
     * testWinningAtStart2()
     * Do players declare victory if they start the game with a winning hand?
     * 
     * Create a Player with playerId = 1 with a hand 2222.
     * If the static variable winner is changed to 1, the test passes.
     */
    @Test
    public void testWinningAtStart2(){
        Player player = new Player(1,true);
        for (int i = 1; i <= 4; i++){
            player.getHand().add(new Card(2));
        }
        Thread newThread = new Thread(player);
        newThread.run();
        assert player.getWinner()==1;
    }
    /*
     * testWinningAtStart3()
     * Do players declare victory if they start the game with a winning hand?
     * 
     * Create a Player with playerId = 2 with a hand 2222.
     * If the static variable winner is changed to 2, the test passes.
     */
    @Test
    public void testWinningAtStart3(){
        Player player = new Player(2,true);
        for (int i = 1; i <= 4; i++){
            player.getHand().add(new Card(2));
        }
        Thread newThread = new Thread(player);
        newThread.run();
        assert player.getWinner()==2;
    }
    /*
     * testWinningAtStart4()
     * Do players declare victory if they start the game with a winning hand?
     * 
     * Create a Player with playerId = 2 with a hand 3333.
     * If the static variable winner is changed to 2, the test passes.
     */
    @Test
    public void testWinningAtStart4(){
        Player player = new Player(2,true);
        for (int i = 1; i <= 4; i++){
            player.getHand().add(new Card(3));
        }
        player.run();
        assert player.getWinner()==2;
    }
    /*
     * TestWinningAfterStart()
     * Do players declare victory if they draw a card and get a winning hand?
     * 
     * Create a Player with playerId = 1 with a hand 2111 and a cardDeck with a single (1) card.
     * If the player declares victory with a hand 1111, the test passes.
     */
    @Test
    public void TestWinningAfterStart(){
        CardDeck deck = new CardDeck(1);
        deck.getContents().add(new Card(1));
        Player player = new Player(1,true);
        player.getHand().add(new Card(2));
        for (int i = 1; i <= 3; i++){
            player.getHand().add(new Card(1));
        }
        player.run();
        assert player.getWinner()==1;
        for (int i = 0; i<4 ; i++){
            assert player.getHand().poll().getValue() == 1;
        }
        assertTrue(player.getHand().isEmpty());
    }
    /*
     * TestWinningAfterStart2()
     * Do players declare victory if they draw a card and get a winning hand?
     * 
     * Create a Player with playerId = 2 with a hand 3222 and a cardDeck with a single (2) card.
     * If the player declares victory with a hand 2222, the test passes.
     */
    @Test
    public void TestWinningAfterStart2(){
        new CardDeck(1);
        CardDeck deck2 = new CardDeck(2);
        deck2.getContents().add(new Card(2));
        Player player = new Player(2,true);
        player.getHand().add(new Card(3));
        for (int i = 1; i <= 3; i++){
            player.getHand().add(new Card(2));
        }
        player.run();
        assert player.getWinner()==2;
        for (int i = 0; i<4 ; i++){
            assert player.getHand().poll().getValue() == 2;
        }
        assertTrue(player.getHand().isEmpty());
    }
    /*
     * TestLosingAtStart()
     * Do players concede defeat if another starts with a winning hand?
     * 
     * Create a Player with playerId = 1 and hand 1234, an empty cardDeck, and set winner = 2.
     * If the player does not try to take a card, the test passes.
     */
    @Test
    public void TestLosingAtStart(){
        new CardDeck(1);
        new CardDeck(2);
        Player player = new Player(1,true);
        for (int i = 1; i <= 4; i++){
            player.getHand().add(new Card(i));
        }
        player.setWinner(2);
        player.run();
        for (int i = 1; i<=4 ; i++){
            assert player.getHand().poll().getValue() == i;
        }
        assertTrue(player.getHand().isEmpty());
    }
    /*
     * TestLosingAtStart2()
     * Do players concede defeat if another starts with a winning hand?
     * 
     * Create a Player with playerId = 2 and hand 1234, an empty cardDeck, and set winner = 3.
     * If the player does not try to take a card, the test passes.
     */
    @Test
    public void TestLosingAtStart2(){
        new CardDeck(1);
        new CardDeck(2);
        Player player = new Player(2,true);
        for (int i = 1; i <= 4; i++){
            player.getHand().add(new Card(i));
        }
        player.setWinner(3);
        player.run();
        for (int i = 1; i<=4 ; i++){
            assert player.getHand().poll().getValue() == i;
        }
        assertTrue(player.getHand().isEmpty());
    }
    /*
     * TestLosingAfterStart()
     * Do players concede defear if another obtains a winning hand?
     * 
     * Create a Player with playerId = 1, a hand of 1234, a cardDeck with 5, and after one second set winner = 2.
     * If the player does draw from the deck, and then the thread stops running, the test passes.
     */
    @Test
    public void TestLosingAfterStart(){
        new CardDeck(1);
        CardDeck deck2 = new CardDeck(2);
        deck2.getContents().add(new Card(5));
        Player player = new Player(1,true);
        for (int i = 1; i <= 4; i++){
            player.getHand().add(new Card(i));
        }
        Thread thread = new Thread(player);
        thread.run();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.setWinner(2);
        assertTrue(!thread.isAlive());
    }
    /*
     * TestLosingAfterStart2()
     * Do players concede defear if another obtains a winning hand?
     * 
     * Create a Player with playerId = 2, a hand of 1234, a cardDeck with 5, and after one second set winner = 3.
     * If the player does draw from the deck, and then the thread stops running, the test passes.
     */
}
