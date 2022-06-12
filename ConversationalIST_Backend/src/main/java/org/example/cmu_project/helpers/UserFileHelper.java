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
        super.store(data,USER_FILES_PATH + fileId);
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

}
