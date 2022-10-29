import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;
import java.io.*;

public class CardGame {
    public static void main(String[] args){
        System.out.println("Hello Patryk, what do you think if my code?");
        
        //create variables to store
        Integer playerCount = 0;
        String cardFile = "";
        final PriorityBlockingQueue<Card> pack = new PriorityBlockingQueue<Card>();
        Scanner scanner = new Scanner(System.in);

        //to prevent invalid inputs, a while loop checks.
        Boolean value = false;
        while (!value){
            //Take user input for player number and pack file
            playerCount = getPlayers(scanner);
            cardFile = getPack(scanner);
            generatePack(cardFile);

            //check if the 
            if (playerCount > 0 && pack.size() != 8*playerCount) {
                System.out.println(playerCount.toString()+" players are playing with the pack "+cardFile);
                value = true;
            } else {
                System.out.println("Please enter that again.");
            }
        }
        //initialize the player threads

        //deal to the players

        //deal to the decks

        //start() the player threads
    }

    static Integer getPlayers(Scanner scanPlayers){

        //Take user input for player number
        System.out.println("Enter number of players: ");
        String x = scanPlayers.nextLine();
        Integer output = Integer.valueOf(x);

        return output;
    }
    static String getPack(Scanner scanPack){

        //Take user input for pack file
        System.out.println("Enter pack file: ");
        String output = scanPack.nextLine(); //throws java.until.NoSuchElementException: No line found

        return output;
    }
    static PriorityBlockingQueue<Card> generatePack(String fileName){
        PriorityBlockingQueue<Card> pack = new PriorityBlockingQueue<Card>();
        try{
            //locate the file and store it
            File file = new File(fileName);  
            FileReader fileReader = new FileReader(file);
            //create an object which can read the file     
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while((line = br.readLine())!=null){
                Card nextCard = new Card(Integer.valueOf(line));
                pack.add(nextCard);
            }  
            fileReader.close();    //closes the stream and release the resources    
        }  
        catch(IOException e){  
            pack.clear();
        }

        return pack;
    }
}