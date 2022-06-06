package org.example.cmu_project.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
            FileWriter myWriter = new FileWriter(fileId + FILE_FORMAT, true);
            myWriter.write(data);
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
            while (myReader.hasNextLine()) {
                data.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            logger.warning(e.getMessage());
        }
        return data;
    }

    protected void store(String data, String fileId) {
        this.store(data, fileId, false);
    }

    protected void store(String data, String fileId, boolean isSensitive) {
        if(!isSensitive) {
            parseFromClient(data);
        }
        try {
            File myObj =  new File(fileId + FILE_FORMAT);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            logger.warning(e.getMessage());
        }
        write(data, fileId);
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
}
