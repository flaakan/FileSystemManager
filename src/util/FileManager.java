package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.Scanner;


/**
 * This class is responsible for managing all files and actions.
 */
public class FileManager {
    Logger logger = new Logger();
    ClassLoader classLoader = getClass().getClassLoader();
    File directory = new File(classLoader.getResource("./resources").getFile());
    File[] files = directory.listFiles();

    final String delimiter = "[\\s.,;'\"?-]+";

    /**
     * Asks the user for file name of a txt file to check. Prints the name, size and path for the file.
     * If the file is not a txt file or it does not exist, asks the user for a txt file again.
     * @throws FileNotFoundException
     */
    public void getInfoFromFile() throws FileNotFoundException {
        String text="";
        DecimalFormat df = new DecimalFormat("0.00");
        Scanner scanner = new Scanner(System.in);
        while(true){
            try{
                listAllFiles("txt");
                System.out.println("Enter the name of the file to check:");
                String fileName = scanner.nextLine();
               LocalTime startTime = logger.getCurrentTime();
                File file = findFile(fileName);
                if(file != null){
                    String size = "";
                    if(file.length()> 1024*1024){
                        float sizeInMb = file.length()/(1024*1024);
                        size = df.format(sizeInMb) + "mb";
                    }
                    else{
                        float sizeInKb =  file.length()/ 1024;
                        size = df.format(sizeInKb) + "kb";
                    }
                    text = "Check completed! " + "file name: " +file.getName() + ", size: " + size + ", path: " + file.getAbsolutePath()+" .";
                    System.out.println(text);
                    LocalTime endTime =logger.getCurrentTime();

                    long diffInMillis = logger.calculateLoadTime(startTime,endTime);
                    logger.logSuccess(text, diffInMillis);
                    break;
                }else{
                    throw new FileNotFoundException();
                }
            }catch (FileNotFoundException ex){
                String errorText = "File not found, try again!";
                logger.logError(errorText);
                System.out.println(errorText);
                continue;
            }
        }


    }

    /**
     * Finds a file by the fileName entered, in the set directory.
     *
     * @param fileName
     * @return File the file the user asked for.
     * @throws FileNotFoundException
     */
    public File findFile(String fileName) throws FileNotFoundException {
        for (File file: files) {
            if (file.getName().equalsIgnoreCase(fileName)){
                return file;
            }
            else{
                throw new FileNotFoundException();
            }
        }
        return null;

    }


    /**
     * Lists all files in the set directory.
     * Also called by listAllFilesByExtension() to list all the files with a specific extension.
     * If extension is null, lists all files, otherwise lists all files with the specific extension.
     * @param extension This is the extension of the files to be listed.
     */
    public void listAllFiles(String extension){
        LocalTime startTime =  logger.getCurrentTime();
        if(extension != null){
            System.out.println("All ."+ extension + " files in this project:");
        }
        else{
            System.out.println("All files in this project:");
        }

        for(File f : files){
            if(extension != null){
                String fileExtension = f.getName().substring(f.getName().lastIndexOf(".")+1);
                if(fileExtension.equalsIgnoreCase(extension)){
                    System.out.println(f.getName());
                }
            }
            else{
                System.out.println(f.getName());
            }

        }
        LocalTime endTime = logger.getCurrentTime();
        long loadTimeInMillis = logger.calculateLoadTime(startTime, endTime);
        String text = "Listing all files";
       logger.logSuccess(text,loadTimeInMillis);
    }


    /**
     * Asks the user for an extension to list all the files in a set directory.
     * Calls listAllFiles to list all the files with a specific extension.
     */
    public void listAllFilesByExtension(){

        String extension = null;
        Scanner scanner  = new Scanner(System.in);
        System.out.println("Which type of files are you looking for? enter extension without . :");
        extension =  scanner.nextLine();
        listAllFiles(extension);
    }


    /**
     * Asks the user for a file to read from and a word to search for.
     * Searches for the word the user asked for and counts the occurrences of the chosen word.
     * Handles wrong inputs by asking the user for file name and word.
     */
    public void countWordFrequency() {
        int wordCount = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Scanner fileScanner = null;
            String fileName = "";
            try {
                listAllFiles("txt");
                System.out.println("Enter file name to read from, include the extension:");
                fileName = scanner.nextLine();
                System.out.println(fileName + " is the file name");
                System.out.println("What word are you searching for?");
                String wordToSearch = scanner.nextLine();
                LocalTime startTime = logger.getCurrentTime();
                File file =  findFile(fileName);
                fileScanner = new Scanner(file);
                fileScanner.useDelimiter(delimiter);
                String word = "";
                while (fileScanner.hasNext()) {
                    word = fileScanner.next();
                    if (word.equalsIgnoreCase(wordToSearch)) {
                        wordCount++;
                    }
                }
                String text = wordToSearch + " was found " + wordCount + " times.";
                fileScanner.close();
                LocalTime endTime = logger.getCurrentTime();
                long loadTimeInMillis = logger.calculateLoadTime(startTime,endTime);
                logger.logSuccess(text,loadTimeInMillis);
                System.out.println(text);
                break;
            } catch (FileNotFoundException e) {
                String errorText = "File "+ fileName + " not found, try again!";
                logger.logError(errorText);
                System.out.println("File not found, try again!");
                continue;

            }
        }

    }
}
