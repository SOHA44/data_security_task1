package Security;

public class RepeatingKey {
    public String analyse(String plainText, String cipherText) {

        // Convert both to lowercase for consistent processing
        plainText = plainText.toLowerCase();
        cipherText = cipherText.toLowerCase();


        StringBuilder keyStream = new StringBuilder();

        // Loop through each character to calculate the corresponding key character
        for (int i = 0; i < plainText.length(); i++) {

            // Convert characters to numbers (0-25) for calculation
            int p = plainText.charAt(i) - 'a';
            int c = cipherText.charAt(i) - 'a';

            // Calculate key character using Vigenère formula: K = (C - P + 26) % 26
            int k = (c - p + 26) % 26;

            keyStream.append((char)(k + 'a'));
        }

        String fullKey = keyStream.toString();

        // Detect the original key by finding the repeating pattern in the keystream
        for (int i = 1; i <= fullKey.length(); i++) {

            // Take the first i characters as a candidate key
            String sub = fullKey.substring(0, i);
            StringBuilder repeated = new StringBuilder();

            // Repeat the candidate key to match the length of the full keystream
            while (repeated.length() < fullKey.length()) {
                repeated.append(sub);
            }

            // Check if the repeated candidate matches the full keystream
            if (repeated.substring(0, fullKey.length()).equals(fullKey)) {
                return sub; // Return the original key
            }
        }

        // If no repeating pattern is found, return the entire keystream as the key
        return fullKey;
    }

    public String decrypt(String cipherText, String key) {

        // Convert both to uppercase for consistent calculation
        cipherText = cipherText.toUpperCase();
        key = key.toUpperCase();

        StringBuilder plainText = new StringBuilder();

        // Loop through each character in the cipher text
        for (int i = 0; i < cipherText.length(); i++) {

            // Convert the current cipher character and key character to numbers (0-25)
            int c = cipherText.charAt(i) - 'A';
            int k = key.charAt(i % key.length()) - 'A'; // repeat the key as needed

            // Apply Vigenère decryption formula: P = (C - K + 26) % 26
            int p = (c - k + 26) % 26;

            // Convert the number back to a character and append to the decrypted text
            plainText.append((char)(p + 'A'));
        }

        // Return the final decrypted string
        return plainText.toString();
    }

    public String encrypt(String plainText, String key) {
        plainText = plainText.toLowerCase();
        key = key.toLowerCase();
        int plainLen = plainText.length();

        // Repeat key to match plaintext length
        StringBuilder extendedKey = new StringBuilder(key);
        while (extendedKey.length() < plainLen) {
            extendedKey.append(extendedKey.charAt(extendedKey.length() - key.length()));
        }

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < plainLen; i++) {
            int p = plainText.charAt(i) - 'a';
            int k = extendedKey.charAt(i) - 'a';
            cipherText.append((char) (((p + k) % 26) + 'a'));
        }

        return cipherText.toString();
    }
}
