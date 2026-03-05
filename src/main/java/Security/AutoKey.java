package Security;

public class AutoKey {
    public String analyse(String plainText, String cipherText) {
        // Students should complete this part

        return null;
    }

    public String decrypt(String cipherText, String key) {
        // Students should complete this part
        cipherText = cipherText.toLowerCase().replaceAll("[^a-z]", "");
        key = key.toLowerCase().replaceAll("[^a-z]", "");
        StringBuilder plain_txt = new StringBuilder();
        int len = cipherText.length();
        StringBuilder autoKey = new StringBuilder(key);
        for (int i=0 ;i<len;i++){
        int c = cipherText.charAt(i)-'a'; // to get num of char in ascii in cipher
        int k= autoKey.charAt(i)-'a';// to get num of char in ascii in key
        int p = (c-k + 26) %26; // p = (c-k +26 )%26 to get num of char in plaintxt
        char plain  =(char)(p+'a'); // to convert int to char
         autoKey.append(plain); // key = key + (plain)
         plain_txt.append(plain);
        }

        return plain_txt.toString();
    }

    public String encrypt(String plainText, String key) {
        plainText = plainText.toLowerCase();
        key = key.toLowerCase();
        int len = plainText.length();

        // Extend key using the plaintext
        StringBuilder autoKey = new StringBuilder(key);
        if (autoKey.length() < len) {
            int diffLen = len - autoKey.length();
            for (int i = 0; i < diffLen; i++) {
                autoKey.append(plainText.charAt(i));
            }
        }

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int p = plainText.charAt(i) - 'a';
            int k = autoKey.charAt(i) - 'a';
            cipherText.append((char) (((p + k) % 26) + 'a'));
        }
        return cipherText.toString();
    }
}
