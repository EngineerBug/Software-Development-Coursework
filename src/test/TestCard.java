package test;
import org.junit.Test;

//import main.Card;

public class TestCard {
    /*
     * testCompareEqual()
     * Do cards with equal values return 1 when compared?
     * 
     * Create two card objects with different values.
     * If compareTo() returns 1 in both directions, the test passes.
     */
    @Test
    public void testCompareEqual(){
        Card card1 = new Card(2);
        Card card2 = new Card(2);
        assert(card1.compareTo(card2) == 1);
        assert(card2.compareTo(card1) == 1);
    }

    /*
     * testCompareUnequal()
     * Do cards with unequal values return 0 when compared?
     * 
     * Create two card objects with different values.
     * If compareTo() returns 0 in both directions, the test passes.
     */
    @Test
    public void testCompareUnequal(){
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        assert(card1.compareTo(card2) == 0);
        assert(card2.compareTo(card1) == 0);
    }
}
