package com.example.cmu_project.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptographyHelper {

    public static String hashString(String stringToHash) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(stringToHash.getBytes());
        String stringHash = new String(messageDigest.digest());

        return stringHash;

    }

}
