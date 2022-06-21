package com.example.cmu_project.helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CryptographyHelper {

    private static final String KEYSTORE_DIR = "keystores/";
    private static final String INPUT_CHARACTER_VALIDATION = "^[a-zA-Z0-9]*$";
    private static final String INPUT_LENGTH_VALIDATION = "^(.{4,20})";
    private static final Logger logger = Logger.getLogger(CryptographyHelper.class.getName());

    public static PublicKey readPublic(String publicKeyPath) throws GeneralSecurityException, IOException {
        logger.info("Reading public key from file " + publicKeyPath + " ...");
        FileInputStream pubFis = new FileInputStream(publicKeyPath);
        byte[] pubEncoded = new byte[pubFis.available()];
        pubFis.read(pubEncoded);
        pubFis.close();

        X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
        KeyFactory keyFacPub = KeyFactory.getInstance("RSA");
        PublicKey pub = keyFacPub.generatePublic(pubSpec);

        return pub;
    }

    public static PrivateKey readPrivate(String privateKeyPath) throws GeneralSecurityException, IOException {
        logger.info("Reading private key from file " + privateKeyPath + " ...");
        FileInputStream privFis = new FileInputStream(privateKeyPath);
        byte[] privEncoded = new byte[privFis.available()];
        privFis.read(privEncoded);
        privFis.close();

        PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privEncoded);
        KeyFactory keyFacPriv = KeyFactory.getInstance("RSA");
        PrivateKey priv = keyFacPriv.generatePrivate(privSpec);

        return priv;
    }

    public static String hash_string(String stringToHash) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(stringToHash.getBytes());
        String stringHash = new String(messageDigest.digest());

        return stringHash;

    }

    private static PrivateKey getPrivateKey(String username, Scanner sc) {
        PrivateKey pk = null;

        KeyStore ks;
        try {
            System.out.println("Please input alias for the keyStore entry.");
            String alias = sc.nextLine();
            while (!isRegularInput(alias, false)) {
                System.out.println("Please enter a valid alias.");
                alias = sc.nextLine();
            }
            System.out.println("Please input password for the keyStore.");
            String passwordString = sc.nextLine();
            while (!isRegularInput(passwordString, true)) {
                System.out.println("Please enter a valid password.");
                passwordString = sc.nextLine();
            }
            String filePath = KEYSTORE_DIR + username + "_KeystoreFile.jks";
            FileInputStream fis = new FileInputStream(filePath);

            ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(fis, passwordString.toCharArray());
            fis.close();

            KeyStore.PasswordProtection password = new KeyStore.PasswordProtection(passwordString.toCharArray());
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias, password);
            pk = pkEntry.getPrivateKey();

        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            logger.warning(e.getMessage());
        }
        return pk;
    }

    private static void savePrivateKey(PrivateKey privateKey, String username, Scanner sc, String alias, String passwordString) {
        KeyStore ks;
        try {
            String filePath = KEYSTORE_DIR + username + "_KeystoreFile.jks";
            FileInputStream fis = new FileInputStream(filePath);
            ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(fis, passwordString.toCharArray());
            fis.close();

            KeyStore.PrivateKeyEntry pkEntry = new KeyStore.PrivateKeyEntry(privateKey, ks.getCertificateChain(alias));
            KeyStore.PasswordProtection password = new KeyStore.PasswordProtection(passwordString.toCharArray());

            ks.setEntry(alias, pkEntry, password);
            FileOutputStream fos = new FileOutputStream(filePath);
            ks.store(fos, passwordString.toCharArray());
            fos.close();
        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException e) {
            logger.warning(e.getMessage());
        }
    }

    private static boolean isRegularInput(String input, boolean pass) {
        Matcher m;
        if (!pass) {
            Pattern p1 = Pattern.compile(INPUT_CHARACTER_VALIDATION);
            m = p1.matcher(input);
            if (!m.matches()) {
                return false;
            }
        }
        Pattern p2 = Pattern.compile(INPUT_LENGTH_VALIDATION);
        m = p2.matcher(input);
        return m.matches();
    }
}
