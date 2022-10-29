import java.util.Scanner;

public class CardGame {
    public static void main(String[] args){
        System.out.println("Hello Patryk, stop snooping on my code!");
        
        //Take user input for player humber
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Enter number of players: ");
        String playerCount = myScanner.nextLine();

        //Take user input for deck file
        System.out.println("Enter input file for the cards: ");
        String cardFile = myScanner.nextLine();
        
    }
}