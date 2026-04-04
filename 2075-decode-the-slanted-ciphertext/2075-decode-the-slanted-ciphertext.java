class Solution {
    public String decodeCiphertext(String encodedText, int rows) {
        int n = encodedText.length();
        int cols = n / rows;

        char[][] matrix = new char[rows][cols];
        int idx = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = encodedText.charAt(idx++);
            }
        }

        StringBuilder ans = new StringBuilder();

        for (int col = 0; col < cols; col++) {
            int i = 0;
            int j = col;

            while (i < rows && j < cols) {
                ans.append(matrix[i][j]);
                i++;
                j++;
            }
        }

        int end = ans.length() - 1;
        while (end >= 0 && ans.charAt(end) == ' ') {
            end--;
        }

        return ans.substring(0, end + 1);
    }
}