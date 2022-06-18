package org.example.cmu_project.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatroomFileHelper extends FileHelper {

    public static final String CHATROOM_FILE_BEGIN = "app-data-files/chatrooms/";
    public static final String CHATROOM_FILE_INFO = "app-data-files/chatrooms_info";

    @Override
    public void writeToFile(String... args){
        String data = args[0];
        String username = args[1];
        String timestamp = args[2];
        String type = args[3];
        String fileName = args[4];
        String position = args[5];
        String finalString =    data + "," +
                                username + "," +
                                timestamp + ","+
                                type + "," +
                                position;

        super.write(finalString, CHATROOM_FILE_BEGIN + fileName);
    }

    public List<String> readFile(String fileName){
        List<String> data;

        data = super.read(CHATROOM_FILE_BEGIN + fileName);

        return data;
    }

    public List<String> readInfoFile() {

        List<String> data;

        data = super.read(CHATROOM_FILE_INFO);

        return data;
    }


    @Override
    public void store(String data, String fileId) {
        super.store(data,fileId);
    }

    public String getChatType(String chat_name) {

        String chat_type = "";

        try {
            File myObj = new File(CHATROOM_FILE_INFO + FILE_FORMAT);
            Scanner myReader = new Scanner(myObj);
            String newLine;

            while (myReader.hasNextLine()) {
                newLine = myReader.nextLine();

                String[] splited_line = newLine.split(",");
                if (splited_line[0].equals(chat_name)) {
                    chat_type = splited_line[2];
                }


            }
            myReader.close();

        } catch (FileNotFoundException e) {
            logger.warning(e.getMessage());
        }
        return chat_type;


    }

    public List<String> getChatLocation(String chat_name) {

        List<String> ret = new ArrayList<>();

        try {
            File myObj = new File(CHATROOM_FILE_INFO + FILE_FORMAT);
            Scanner myReader = new Scanner(myObj);
            String newLine;

            while (myReader.hasNextLine()) {
                newLine = myReader.nextLine();

                String[] splited_line = newLine.split(",");
                if (splited_line[0].equals(chat_name)) {
                     ret.add(splited_line[3]);
                     ret.add(splited_line[4]);
                }


            }
            myReader.close();

        } catch (FileNotFoundException e) {
            logger.warning(e.getMessage());
        }

        return ret;

    }
}
