package main;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.io.*;
import java.util.ArrayList;

/*
 * Input/output:
 * https://www.youtube.com/watch?v=ScUJx4aWRi0
 * 
 * Threading:
 * https://www.youtube.com/watch?v=r_MbozD32eo
 * 
 * Testing:
 * https://www.youtube.com/watch?v=vZm0lHciFsQ
 * 
 * Important, compile using this command from outside the src file: javac -d ./bin/ ./src/*.java
 */

public class CardGame {
    public static void main(String[] args){
        System.out.println("Hello Patryk, what do you think of my code?");
        
        //create variables
        //this variable is an integer so that we can use the Integer.valueOf() method.
        Integer playerCount = 0;
        BlockingQueue<Card> pack = new LinkedBlockingDeque<Card>();
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
        //initialse the deck objects
        generateDecks(playerCount);

        //initialise the player threads
        generatePlayerThreads(playerCount);

        //deal to the players, create the decks and deal to the decks
        dealToPlayers(pack);

        //deal to the decks
        dealToDecks(pack);

        //start() the player threads
        startPlayerThreads(Player.getPlayers());
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
    static void generatePack(String fileName, BlockingQueue<Card> pack, int playerCount){
        try{
            //create an object which can read the file     
            BufferedReader br = new BufferedReader(
                new FileReader(fileName)
            );
            String line = br.readLine();
            //while the next read line is not null, write it to pack.
            for (int i = 0; i < 8*playerCount; i++) {
                //add the next line to the pack as a new card object
                pack.add(new Card(Integer.valueOf(line)));
                //load the next line
                line = br.readLine();
            }
            //close the stream
            br.close();
        }  
        catch(IOException e){
            //If there are not enough cards,the program 
            //will empty to pack to garentee that the game does not start.
            pack.clear();
        }
    }

    /*
     * This method initialses all the player objects.
     * No thread is started at this point.
     * An additional parameter is passed to notify the constructor when the last player is being 
     * created as they need to be linked to Deck 1 as their discard deck
     * <p>
     * Because the players are stored in a static array in Player.class,
     * they can all be indapendantly accessed later to start() them.
     * 
     * @param int playerCount: the number of threads needing to be initialised.
     * @returns none
     */
    static void generatePlayerThreads(int playerCount){
        for (int i = 1; i <= playerCount; i++) {
            Boolean lastPlayer = false;
            if (i == playerCount) {
                lastPlayer = true;
            }
            new Player(i,lastPlayer);
        }
    }

    /*
     * Create the deck objects.
     * Assign them to players as draw/discard piles
     * 
     * @param none.
     * @return none.
     */
    static void generateDecks(int playerCount){
        for (int i = 1; i <= playerCount; i++){
            new AtomicReference<CardDeck>(new CardDeck(i));
        }
    }
    
    static void dealToPlayers(BlockingQueue<Card> pack){
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
    }

    static void dealToDecks(BlockingQueue<Card> pack){
        //deal to the decks
        for (int i = 0; i < 4; i++){
            //deal a single card to each deck, four times (hence two loops)
            for (CardDeck deck: CardDeck.getDecks()){
                try{
                    deck.getContents().add(pack.take());
                }catch(InterruptedException e){
                    return;
                }
            }
        }
    }

    /*
     * Start the player threads by getting the static list of all players.
     * 
     * @param none.
     * @return none.
     */
    static void startPlayerThreads(ArrayList<Player> players){
        for (Player player: players){
            Thread newThread = new Thread(player);
            newThread.start();
        }
    }
}