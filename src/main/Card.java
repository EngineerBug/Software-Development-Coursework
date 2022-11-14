package main;
public class Card implements Comparable<Card>{
    //private attributes
    private int value;

    //public setter/getter methods
    public int getValue(){
        return this.value;
    }

    //constructor
    public Card(int value){
        this.value = value;
    }

    //public toString method
    public String toString(){
        return String.valueOf(this.value);
    }

    @Override
    /*
     * The purpose of this method is to allow
     * Card objects into queues.
     * <p>
     * When executing CardGame.main() without this method, 
     * I get the error "class Card cannot be cast to class java.lang.Comparable"
     * This error is thrown by the line "pack.add(nextCard);" in the generatePack method.
     * 
     * @param Card card the card that the object is being compared to.
     * @Retuen a 1 or 0 depending on weather the cards have the same value.
     */
    public int compareTo(Card card){
        if (this.value == card.value){
            return 1;
        } else {
            return 0;
        }
    }
}