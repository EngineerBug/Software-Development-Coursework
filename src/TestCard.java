import org.junit.Test;

public class TestCard {
    @Test
    public void testCompare(){
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        Card card3 = new Card(2);
        assert(card1.compareTo(card2) == 0);
        assert(card1.compareTo(card3) == 1); 
    }
}
