package org.example.cmu_project.helpers;

import org.mindrot.jbcrypt.BCrypt;

import java.util.logging.Logger;

public class PasswordHashingHelper {

    private static final Logger logger = Logger.getLogger(FileHelper.class.getName());
    private static final int SALT_COMPLEXITY = 12;
    private static final FileHelper fileHelper = new FileHelper();

    public static void hash(String password, String userId) {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(SALT_COMPLEXITY));
        fileHelper.store(hash, userId, true);
    }

    public static boolean verifyHash(String password, String userId) {
        String hash = fileHelper.retrieve(userId, true);
        if (BCrypt.checkpw(password, hash)) {
            logger.info("Password correct!");
            return true;
        }
        else {
            logger.info("Password incorrect!");
            return false;
        }
    }

}
