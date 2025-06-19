package com.example.mynewapplication;
public class PlayfairCipher {

    private static char[][] generateMatrix(String key) {
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        boolean[] used = new boolean[26];
        StringBuilder seq = new StringBuilder();
        for (char c : key.toCharArray()) {
            if (!used[c - 'A']) {
                used[c - 'A'] = true;
                seq.append(c);
            }
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !used[c - 'A']) {
                seq.append(c);
                used[c - 'A'] = true;
            }
        }
        char[][] m = new char[5][5];
        for (int i = 0; i < 25; i++) {
            m[i / 5][i % 5] = seq.charAt(i);
        }
        return m;
    }

    private static String[] prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); ) {
            char a = text.charAt(i);
            char b = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';
            if (a == b) {
                sb.append(a).append('X');
                i++;
            } else {
                sb.append(a).append(b);
                i += 2;
            }
        }
        if (sb.length() % 2 == 1) sb.append('X');
        int n = sb.length() / 2;
        String[] pairs = new String[n];
        for (int i = 0; i < n; i++) {
            pairs[i] = sb.substring(2 * i, 2 * i + 2);
        }
        return pairs;
    }

    private static String[] splitCipherText(String text) {
        String[] pairs = new String[text.length() / 2];
        for (int i = 0; i < text.length(); i += 2) {
            pairs[i / 2] = text.substring(i, i + 2);
        }
        return pairs;
    }

    private static int[] findPos(char[][] m, char c) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (m[i][j] == c) return new int[]{i, j};
        return null;
    }

    public static String encrypt(String text, String key) {
        char[][] m = generateMatrix(key);
        StringBuilder out = new StringBuilder();
        for (String p : prepareText(text)) {
            int[] A = findPos(m, p.charAt(0));
            int[] B = findPos(m, p.charAt(1));
            if (A[0] == B[0]) {
                out.append(m[A[0]][(A[1] + 1) % 5]);
                out.append(m[B[0]][(B[1] + 1) % 5]);
            } else if (A[1] == B[1]) {
                out.append(m[(A[0] + 1) % 5][A[1]]);
                out.append(m[(B[0] + 1) % 5][B[1]]);
            } else {
                out.append(m[A[0]][B[1]]).append(m[B[0]][A[1]]);
            }
        }
        return out.toString();
    }

    public static String decrypt(String text, String key) {
        char[][] m = generateMatrix(key);
        StringBuilder out = new StringBuilder();
        for (String p : splitCipherText(text)) {
            int[] A = findPos(m, p.charAt(0));
            int[] B = findPos(m, p.charAt(1));
            if (A[0] == B[0]) {
                out.append(m[A[0]][(A[1] + 4) % 5]);
                out.append(m[B[0]][(B[1] + 4) % 5]);
            } else if (A[1] == B[1]) {
                out.append(m[(A[0] + 4) % 5][A[1]]);
                out.append(m[(B[0] + 4) % 5][B[1]]);
            } else {
                out.append(m[A[0]][B[1]]);
                out.append(m[B[0]][A[1]]);
            }
        }
        return out.toString();
    }
}
