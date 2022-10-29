import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;
import java.io.*;

public class CardGame {
    public static void main(String[] args){
        System.out.println("Hello Patryk, what do you think of my code?");
        
        //create variables to store
        Integer playerCount = 0;
        final PriorityBlockingQueue<Card> pack = new PriorityBlockingQueue<Card>();
        Scanner scanner = new Scanner(System.in);

        //get inputs from user
        //to prevent invalid inputs, a while loop checks
        Boolean value = false;
        while (!value){
            //Take user input for player number and pack file
            playerCount = getPlayers(scanner);
            generatePack(getPack(scanner), pack);

            //check if the inputs are valid
            if (playerCount > 0 && pack.size() == 8*playerCount) {
                System.out.println(playerCount.toString()+" players are playing with "+pack.size()+" cards.");
                value = true;
            } else {
                System.out.println("Required card count: "+8*playerCount+", you put: "+pack.size());
            }
        }
        //initialize the player threads
        initializePlayerThreads(playerCount);

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
    /*
     * This class extracts all the numbers from the input file.
     * They numbers are then converted to cards and stored in the pack queue.
     * 
     * @param String filename: the name of the file with numbers stored in it.
     * @returns none.
     */
    static void generatePack(String fileName, 
                                                PriorityBlockingQueue<Card> pack){
        try{
            //create an object which can read the file     
            BufferedReader br = new BufferedReader(
                new FileReader(fileName)
            );
            String line = br.readLine();
            //while the next read line is not null, write it to pack.
            while(line!=null){
                Card nextCard = new Card(Integer.valueOf(line));
                pack.add(nextCard);
                //load the next line
                line = br.readLine();
            }
            //close the stream
            br.close();
        }  
        catch(IOException e){  
            pack.clear();
        }
    }
    static void initializePlayerThreads(int playerCount){
        for (int i = 1; i <= playerCount; i++) {
            Player newPlayer = new Player(i);
            Thread newThread = new Thread(newPlayer);
        }
    }
}