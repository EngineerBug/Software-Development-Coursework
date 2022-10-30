import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;
import java.io.*;

/*
 * Input/output:
 * https://www.youtube.com/watch?v=ScUJx4aWRi0
 * 
 * Threading:
 * https://www.youtube.com/watch?v=r_MbozD32eo
 * 
 * Testing:
 * https://www.youtube.com/watch?v=vZm0lHciFsQ
 */

public class CardGame {
    public static void main(String[] args){
        System.out.println("Hello Patryk, what do you think of my code?");
        
        //create variables
        //this valueable is an integer so that we can use the Integer.valueOf() method.
        Integer playerCount = 0;
        final PriorityBlockingQueue<Card> pack = new PriorityBlockingQueue<Card>();
        Scanner scanner = new Scanner(System.in);

        //get inputs from user
        //to prevent invalid inputs, a while loop checks
        Boolean value = false;
        while (!value){
            //Take user input for player number and pack file
            playerCount = getPlayers(scanner);
            generatePack(getPack(scanner), pack, playerCount);

            //check if the inputs are valid
            if (playerCount > 0 && pack.size() == 8*playerCount){
                System.out.println(playerCount.toString()+" players are playing with "+pack.size()+" cards.");
                value = true;
            }else{
                System.out.println("Required card count: "+8*playerCount+", you put: "+pack.size());
            }
        }
        //initialise the player threads
        initialisePlayerThreads(playerCount);

        //deal to the players

        //deal to the decks

        //start() the player threads
    }
    /*
     * This method gets an input from the user and returns it as an Integer.
     * 
     * @param Scanner scanPlayers: the current scanner object.
     * @returns none
     */
    static Integer getPlayers(Scanner scanPlayers){

        //Take user input for player number
        System.out.println("Enter number of players: ");
        String x = scanPlayers.nextLine();
        Integer output = Integer.valueOf(x);

        return output;
    }
    /*
     * This method gets an input from the user and returns it as a string.
     * 
     * @param Scanner scanPack: the current scanner object.
     * @returns none
     */
    static String getPack(Scanner scanPack){

        //Take user input for pack file
        System.out.println("Enter pack file: ");
        String output = scanPack.nextLine(); //throws java.until.NoSuchElementException: No line found

        return output;
    }
    /*
     * This method extracts all the numbers from the input file.
     * The numbers are then converted to cards and stored in the pack queue.
     * 
     * @param String filename: the name of the file with numbers stored in it.
     * @returns none
     */
    static void generatePack(String fileName, PriorityBlockingQueue<Card> pack, int playerCount){
        try{
            //create an object which can read the file     
            BufferedReader br = new BufferedReader(
                new FileReader(fileName)
            );
            String line = br.readLine();
            //while the next read line is not null, write it to pack.
            for (int i = 0; i < 8*playerCount; i++) {
                Card nextCard = new Card(Integer.valueOf(line));
                pack.add(nextCard);
                //load the next line
                line = br.readLine();
            }
            //close the stream
            br.close();
        }  
        catch(IOException e){
            /*
             * if there are not enough cards,
             * the program will empty to pack 
             * so that the game does not start.
             */
            pack.clear();
        }
    }
    /*
     * This method initialses all the player threads.
     * No player thread is started at this point.
     * <p>
     * Because the players are stored in a static array in Player.class,
     * they can all be indapendantly accessed later to start() them.
     * 
     * @param int playerCount: the number of threads needing to be initialised.
     * @returns none
     */
    static void initialisePlayerThreads(int playerCount){
        for (int i = 1; i <= playerCount; i++) {
            Player newPlayer = new Player(i);
            Thread newThread = new Thread(newPlayer);
        }
    }
    
    static void dealCards(PriorityBlockingQueue<Card> pack){
        //deal to the players
        for (int i = 0; i < 4; i++){
            //deal a single card to each player, four times (hence two loops)
            for (Player player: Player.getPlayers()){
                try{
                    player.drawCard(pack.take());
                }catch(InterruptedException e){
                    return;
                }
            }
        }
        //create the decks

        //deal to the decks
    }
    /*
     * Deal to the decks
     * 
     * @param none.
     * @return none.
     */

    /*
     * Start the player threads
     * 
     * @param none.
     * @return none.
     */
}