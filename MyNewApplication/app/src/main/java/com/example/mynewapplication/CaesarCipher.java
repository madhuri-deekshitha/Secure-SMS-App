package com.example.mynewapplication;
public class CaesarCipher {

    // Encrypts the message using Caesar Cipher
    public static String encrypt(String message, int shift) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);

            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) (((c - base + shift) % 26) + base);
            }

            encryptedMessage.append(c);
        }
        return encryptedMessage.toString();
    }

    // Decrypts the message using Caesar Cipher
    public static String decrypt(String message, int shift) {
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);

            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) (((c - base - shift + 26) % 26) + base);
            }

            decryptedMessage.append(c);
        }
        return decryptedMessage.toString();
    }
}
