package org.example.cmu_project.helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserFileHelper extends FileHelper {

    private static final String USER_FILES_PATH = "app-data-files/users/";
    public static final String USER_FILE_INFO = "app-data-files/users_info";

    @Override
    public List<String> parseToClient(List<String> fileId) {
        return null;
    }

    @Override
    public void parseFromClient(String data) {

    }

    @Override
    public void store(String data, String fileId) {
        super.store(data,USER_FILES_PATH + fileId);
    }

    public void store_info(String data) {
        super.store(data,USER_FILE_INFO);
    }


    public List<String> getChats(String fileId) {

        List<String> userData = super.read(USER_FILES_PATH + fileId);
        List<String> userChats = new ArrayList<>();

        for (String line: userData) {
            String[] splitedLine = line.split(",");
            String chat_name = splitedLine[0];
            userChats.add(chat_name);
        }

        return userChats;
    }

    public boolean userExists(String user) {

        try {
            File myObj = new File(USER_FILE_INFO + FILE_FORMAT);
            Scanner myReader = new Scanner(myObj);
            String newLine;

            while (myReader.hasNextLine()) {
                newLine = myReader.nextLine();

                String[] splited_line = newLine.split(",");
                if (splited_line[0].equals(user)) {
                    return true;
                }

            }
            myReader.close();

        } catch (FileNotFoundException e) {
            logger.warning(e.getMessage());
        }
        return false;

    }

    public boolean checkPassword(String user, String password_hashed) {

        try {
            File myObj = new File(USER_FILE_INFO + FILE_FORMAT);
            Scanner myReader = new Scanner(myObj);
            String newLine;

            while (myReader.hasNextLine()) {
                newLine = myReader.nextLine();

                String[] splited_line = newLine.split(",");
                if (splited_line[0].equals(user)) {
                    return splited_line[1].equals(password_hashed);
                }

            }
            myReader.close();

        } catch (FileNotFoundException e) {
            logger.warning(e.getMessage());
        }
        return false;

    }

    public void leaveChat(String user, String chat_name) throws IOException {

        File inputFile = new File(USER_FILES_PATH + user + FILE_FORMAT);
        File tempFile = new File(USER_FILES_PATH + "myTempFile.csv");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = chat_name+",";
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();

        inputFile.delete();

        tempFile.renameTo(inputFile);

    }

    public List<String> allUsers() {

        List<String> users = new ArrayList<>();

        try {
            File myObj = new File(USER_FILE_INFO + FILE_FORMAT);
            Scanner myReader = new Scanner(myObj);
            String newLine;

            while (myReader.hasNextLine()) {
                newLine = myReader.nextLine();
                String[] splited_line = newLine.split(",");
                String user = splited_line[0];
                users.add(user);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            logger.warning(e.getMessage());
        }

        return users;

    }

    public boolean userInChat(String user,String chat_name) {

        List<String> user_chats = this.getChats(user);

        return user_chats.contains(chat_name);

    }


}
