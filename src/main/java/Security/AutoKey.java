package Security;

public class AutoKey {
    public String analyse(String plainText, String cipherText) {
        // Students should complete this part
        cipherText = cipherText.toLowerCase().replaceAll("[^a-z]", "");
        plainText = plainText.toLowerCase().replaceAll("[^a-z]", "");
        StringBuilder autokey = new StringBuilder();
        int len = plainText.length();
        for (int i =0 ;i<len;i++){
            int p = plainText.charAt(i)-'a';
            int c = cipherText.charAt(i)-'a';
            int k = (c-p+26)%26;
            char key_char = (char)(k+'a');
            autokey.append(key_char);
        }
        String key = autokey.toString();
        // to delete part of plain
        for(int i=1;i<key.length();i++){
            if (plainText.startsWith(key.substring(i))){
                return key.substring(0,i); // return from first char to length = plain length
            }
        }
        return key;
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
        int key_len = autoKey.length();
        int plain_len= plain_txt.length();
      if (key_len>plain_len) {
    for (int i= key_len-1; i >= plain_len ;i--){
        autoKey.deleteCharAt(i);
    }
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
