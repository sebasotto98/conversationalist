package org.example.cmu_project.helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class FileHelper {

    protected static final Logger logger = Logger.getLogger(FileHelper.class.getName());

    protected String FILE_FORMAT = ".csv";

    public List<String> parseToClient(List<String> fileId) {
        return null;
    }

    public void parseFromClient(String data) {

    }

    public void writeToFile(String... args){

    }

    protected void write(String data, String fileId) {
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileId + FILE_FORMAT, true));
            myWriter.write(data);
            myWriter.newLine();
            myWriter.close();
            logger.info("Successfully wrote to the file.");
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    protected List<String> read(String fileId) {
        List<String> data = new ArrayList<>();
        try {
            File myObj = new File(fileId + FILE_FORMAT);
            Scanner myReader = new Scanner(myObj);
            String stringToBeAdded = "";
            String newLine;
            boolean imageMessage = false;
            while (myReader.hasNextLine()) {
                newLine = myReader.nextLine();
                if(!newLine.contains(",")) {
                    imageMessage = true;
                    stringToBeAdded = stringToBeAdded.concat(newLine);
                } else if(imageMessage) {
                    stringToBeAdded = stringToBeAdded.concat(newLine);
                    data.add(stringToBeAdded);
                    stringToBeAdded = "";
                    imageMessage = false;
                } else {
                    data.add(newLine);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            logger.warning(e.getMessage());
        }
        return data;
    }

    public void store(String data, String fileId) {
        this.store(data, fileId, false);
    }

    protected void store(String data, String fileId, boolean isSensitive) {
        System.out.println(data);
        if(!isSensitive) {
            parseFromClient(data);
        }
        try {
            File myObj =  new File(fileId + FILE_FORMAT);
            if (myObj.createNewFile()) {
                logger.info("File created: " + myObj.getName());
            } else {
                logger.info("File already exists.");
                write(data, fileId);
            }
        } catch (IOException e) {
            logger.info("An error occurred.");
            logger.warning(e.getMessage());
        }
    }

    protected List<String> retrieve(String fileId) {
        return retrieve(fileId, false);
    }

    protected List<String> retrieve(String fileId, boolean isSensitive) {
        List<String> data = read(fileId);
        if (!isSensitive) {
            return parseToClient(data);
        } else {
            return data;
        }
    }

    public String getCurGuestNumber() {

        String cur_number = "";
        String next_cur_number = "";

        try {
            File myObj = new File("app-data-files/guest_number_register" + FILE_FORMAT);
            Scanner myReader = new Scanner(myObj);
            String first_line = myReader.nextLine();

            String[] line_splited  = first_line.split(",");
            cur_number = line_splited[0];

            myReader.close();

            myObj.delete();

            //update the value
            int cur_int_number = Integer.parseInt(cur_number);
            cur_int_number = cur_int_number + 1;
            next_cur_number = String.valueOf(cur_int_number);

            BufferedWriter myWriter = new BufferedWriter(new FileWriter("app-data-files/guest_number_register" + FILE_FORMAT, true));
            myWriter.write(next_cur_number+",");
            myWriter.close();

        } catch (FileNotFoundException e) {
            logger.warning(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cur_number;

    }
}