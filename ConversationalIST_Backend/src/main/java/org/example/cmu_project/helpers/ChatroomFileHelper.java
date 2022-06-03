package org.example.cmu_project.helpers;

public class ChatroomFileHelper extends FileHelper {

    private static final String CHATROOM_FILE_BEGIN = "app-data-files/chatrooms/";

    @Override
    public void writeToFile(String... args){
        String data = args[0];
        String username = args[1];
        String timestamp = args[2];
        String fileName = args[3];
        String finalString =    data + "," +
                                username + "," +
                                timestamp + "\n";

        super.write(finalString, CHATROOM_FILE_BEGIN + fileName);
    }
}
