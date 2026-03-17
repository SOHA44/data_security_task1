package Security;
import java.util.*;

public class ColumnarCipher {

    public List<Integer> analyse(String plainText, String cipherText) {
        // TODO: Analyze the plainText and cipherText to determine the key(s)
        plainText = plainText.toLowerCase();
        cipherText = cipherText.toLowerCase();

        int pLen = plainText.length();

        //loop to find true key_length
        for (int key_len = 2; key_len <= pLen; key_len++) {

            int rows = (int) Math.ceil((double) pLen / key_len);

            char[][] grid = new char[rows][key_len];

            int index = 0;

            // fill grid row-wise
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < key_len; j++) {
                    if (index < pLen)
                        grid[i][j] = plainText.charAt(index++);
                    else
                        grid[i][j] = 'x';
                }
            }

            // extract columns
            String[] pColumns = new String[key_len];
            for (int j = 0; j < key_len; j++) {
                StringBuilder pCol = new StringBuilder();
                for (int i = 0; i < rows; i++) {
                    pCol.append(grid[i][j]);
                }
                pColumns[j] = pCol.toString();
            }

            // split cipherText
            String[] cipherParts = new String[key_len];
            int pos = 0;

            for (int i = 0; i < key_len; i++) {
                if (pos + rows <= cipherText.length())
                    cipherParts[i] = cipherText.substring(pos, pos + rows);
                else
                    break;

                pos += rows;
            }

            boolean[] used = new boolean[key_len];
            int[] order = new int[key_len];
            boolean valid = true;

            for (int i = 0; i < key_len; i++) {

                boolean found = false;

                for (int j = 0; j < key_len; j++) {

                    if (!used[j] && cipherParts[i].equals(pColumns[j])) {

                        order[j] = i + 1;
                        used[j] = true;
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                List<Integer> key = new ArrayList<>();
                for (int i = 0; i < key_len; i++) {
                    key.add(order[i]);
                }
                return key;
            }
        }

        return new ArrayList<>();
    }

    public String decrypt(String cipherText, List<Integer> key) {
        int cipherSize = cipherText.length();
        int rows = (int) Math.ceil((double) cipherSize / key.size());
        char[][] grid = new char[rows][key.size()];
        int count = 0;

        Map<Integer, Integer> keyMap = new HashMap<>();
        for (int i = 0; i < key.size(); i++) {
            keyMap.put(key.get(i) - 1, i);
        }

        int remainingCols = cipherSize % key.size();
        for (int i = 0; i < key.size(); i++) {
            for (int j = 0; j < rows; j++) {
                if (remainingCols != 0 && j == rows - 1 && keyMap.get(i) >= remainingCols) continue;
                grid[j][keyMap.get(i)] = cipherText.charAt(count++);
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.size(); j++) {
                result.append(grid[i][j]);
            }
        }
        return result.toString().toUpperCase().trim();
    }

    public String encrypt(String plainText, List<Integer> key) {
        int ptSize = plainText.length();
        int rows = (int) Math.ceil((double) ptSize / key.size());
        char[][] grid = new char[rows][key.size()];
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.size(); j++) {
                if (count >= ptSize) {
                    grid[i][j] = 'x';
                } else {
                    grid[i][j] = plainText.charAt(count++);
                }
            }
        }

        Map<Integer, Integer> keyMap = new HashMap<>();
        for (int i = 0; i < key.size(); i++) {
            keyMap.put(key.get(i) - 1, i);
        }

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < key.size(); i++) {
            for (int j = 0; j < rows; j++) {
                cipherText.append(Character.toUpperCase(grid[j][keyMap.get(i)]));
            }
        }
        return cipherText.toString();
    }
}
