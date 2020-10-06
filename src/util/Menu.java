package util;

import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * This class is the main menu when the program starts.
 * It shows all the options you get to choose from.
 */
public class Menu {


    /**
     * This method is the whole program. It will continue to run until
     * the user decides to exit (case 0).
     * Handles wrong input by asking the user to enter a valid option.
     */
    public void startMenu(){
    FileManager fileManager = new FileManager();
    Scanner scanner = null;
    Logger logger = new Logger();

    int action = -1;
        while (true){
        try {
            scanner = new Scanner(System.in);
            System.out.println("What do you want to do?");
            System.out.println("1. Count frequency of a word from a file");
            System.out.println("2. List all files");
            System.out.println("3. List all files with specific extension");
            System.out.println("4. Check information of a file");
            System.out.println("0. Exit");
            action = scanner.nextInt();
            switch (action) {
                case 1:
                    try {
                        scanner.nextLine();
                        fileManager.countWordFrequency();
                    } catch (Exception e) {
                        break;
                    }
                    break;

                case 2:
                    fileManager.listAllFiles(null);
                    break;

                case 3:
                    fileManager.listAllFilesByExtension();
                    break;

                case 4:
                    try{
                        scanner.nextLine();
                        fileManager.getInfoFromFile();
                    }
                    catch (Exception e){
                     break;
                    }
                    break;
                case 0:
                    String logText = "Exiting Program.";
                    logger.logSuccess(logText, 0);
                    System.out.println(logText);
                    scanner.close();
                    System.exit(0);
                default:
                    String errorText = "Selected number is out of bounds";
                    logger.logError(errorText);
                    System.out.println("Enter a number between 0-4 please");
                    break;
            }
        }


        catch (InputMismatchException ex) {
            String errorText = "Entered wrong type, expected number";
            logger.logError(errorText);
            System.out.println("Please enter a number!");
            continue;
        }
    }
}

}
