package org.example.cmu_project.helpers;

import java.util.List;

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
}
