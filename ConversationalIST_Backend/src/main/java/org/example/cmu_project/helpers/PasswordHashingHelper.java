package org.example.cmu_project.helpers;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashingHelper {

    private static final int SALT_COMPLEXITY = 12;
    private static final FileHelper fileHelper = new FileHelper();

    public static void hash(String password, String userId) {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(SALT_COMPLEXITY));
        fileHelper.store(hash, userId, true);
    }

}
