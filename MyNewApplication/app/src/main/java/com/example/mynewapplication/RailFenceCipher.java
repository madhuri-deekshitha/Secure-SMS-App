package com.example.mynewapplication;

public class RailFenceCipher {

    // Encrypts the message using Rail Fence Cipher
    public static String encrypt(String message, int rails) {
        if (rails == 1) return message;

        char[][] rail = new char[rails][message.length()];
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < message.length(); j++) {
                rail[i][j] = '\n'; // Fill with newline character
            }
        }

        boolean dirDown = false;
        int row = 0, col = 0;

        // Fill the rail matrix
        for (int i = 0; i < message.length(); i++) {
            if (row == 0 || row == rails - 1) {
                dirDown = !dirDown;
            }

            rail[row][col++] = message.charAt(i);

            row = dirDown ? row + 1 : row - 1;
        }

        // Read the rail matrix to get the encrypted message
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < message.length(); j++) {
                if (rail[i][j] != '\n') {
                    encryptedMessage.append(rail[i][j]);
                }
            }
        }

        return encryptedMessage.toString();
    }

    // Decrypts the message using Rail Fence Cipher
    public static String decrypt(String message, int rails) {
        if (rails == 1) return message;

        char[][] rail = new char[rails][message.length()];
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < message.length(); j++) {
                rail[i][j] = '\n'; // Fill with newline character
            }
        }

        boolean dirDown = false;
        int row = 0, col = 0;

        // Mark the places with characters
        for (int i = 0; i < message.length(); i++) {
            if (row == 0 || row == rails - 1) {
                dirDown = !dirDown;
            }

            rail[row][col++] = '*';

            row = dirDown ? row + 1 : row - 1;
        }

        // Place the message in the matrix
        col = 0;
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < message.length(); j++) {
                if (rail[i][j] == '*' && col < message.length()) {
                    rail[i][j] = message.charAt(col++);
                }
            }
        }

        // Read the message in a zigzag pattern to get the decrypted message
        StringBuilder decryptedMessage = new StringBuilder();
        row = 0;
        col = 0;
        for (int i = 0; i < message.length(); i++) {
            if (row == 0 || row == rails - 1) {
                dirDown = !dirDown;
            }

            decryptedMessage.append(rail[row][col++]);
            row = dirDown ? row + 1 : row - 1;
        }

        return decryptedMessage.toString();
}
}