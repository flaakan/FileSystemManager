package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


/**
 * This class is responsible for the logs of the whole program.
 */
public class Logger {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String directory = "log/";

    FileWriter fileWriter = null;
    BufferedWriter bufferedWriter = null;
    PrintWriter printWriter = null;

    /**
     * Writes a successful log to the log file with the time of the operation.
     * Puts together a log message with text and diffInMillis.
     * Calls the function writeLog() to write to a txt file.
     * @param text this is the text from to build the log text from.
     * @param diffInMillis this is the time it took to execute an operation.
     */
    public void logSuccess(String text, long diffInMillis){
        String formatDateTime = getFormattedCurrentTimestamp();
        String millis = "Operation took " + diffInMillis  +"ms";
        String logText =  formatDateTime+ " " + text + " " + millis;
        writeLog(logText);
    };

    /**
     * Called by other log functions to write a log entry to a txt file
     * with the today's date as title.
     * @param logText this is the text to log.
     */
    private void writeLog(String logText){

        try{
            fileWriter = new FileWriter (directory+"log_" + LocalDate.now()+".txt",true);

            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(logText);

            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get current local time
     * @return LocalTime this returns the current local time
     */
    public LocalTime getCurrentTime(){
        LocalTime now = LocalTime.now();
        return now;
    }

    /**
     * Gets current date and time in the format
     * "yyyy-MM-dd HH:mm:ss"
     * @return String This is the current date and time with custom format.
     */
    public String getFormattedCurrentTimestamp(){
        LocalDateTime now = LocalDateTime.now();
        String formatDateTime = now.format(formatter);
        return formatDateTime;
    }

    /**
     * Writes an error log to the log file.
     * Puts together a log message with an error message with timestamp.
     * Calls the function writeLog() to write to a txt file.
     * @param error text to create a log text
     */
    public void logError(String error){
        String formatDateTime = getFormattedCurrentTimestamp();
        String logText = formatDateTime + " an error occurred: " + error;

        writeLog(logText);
    }

    /**
     * Calculates the operation time from start to finish.
     * @param startTime start time of the operation
     * @param endTime end time of the operation
     * @return long The time it took to execute an operation
     */
    public long calculateLoadTime(LocalTime startTime, LocalTime endTime ){
        long time = ChronoUnit.MILLIS.between(startTime,endTime);
        return time;
    }

}
