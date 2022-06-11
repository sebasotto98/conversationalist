package org.example.cmu_project.helpers;

import java.util.ArrayList;
import java.util.List;

public class UserFileHelper extends FileHelper {

    private static final String USER_FILES_PATH = "app-data-files/users/";

    @Override
    public List<String> parseToClient(List<String> fileId) {
        return null;
    }

    @Override
    public void parseFromClient(String data) {

    }

    @Override
    public void store(String data, String fileId) {
        super.store(data,USER_FILES_PATH+fileId);
    }


    public List<String> getChats(String fileId) {
        List<String> user_data = super.read(USER_FILES_PATH + fileId);
        List<String> user_chats = new ArrayList<>();

        for (String line: user_data) {
            String[] splited_line = line.split(",");
            String chat_name = splited_line[0];
            user_chats.add(chat_name);
        }

        return  user_chats;



    }
}
