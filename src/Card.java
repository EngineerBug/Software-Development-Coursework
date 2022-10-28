public class Card{
    private int value;

    public int getValue(){
        return this.value;
    }

    public void Card(int value) {
        this.value = value;
    }

    public String toString(){
        return "The value of this card is: "+this.value;
    }
}