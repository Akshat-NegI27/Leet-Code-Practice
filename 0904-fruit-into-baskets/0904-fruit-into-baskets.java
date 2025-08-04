class Solution {
    public int totalFruit(int[] fruits) {
        int ans = 0;
        int l = 0;
        int type1 = -1;
        int type2 = -1;
        int count1 = 0;
        int count2 = 0;

        for (int r = 0; r < fruits.length; ++r) {
            int currentFruit = fruits[r];
            if (currentFruit == type1 || type1 == -1) {
                type1 = currentFruit;
                count1++;
            } else if (currentFruit == type2 || type2 == -1) {
                type2 = currentFruit;
                count2++;
            } else {
                ans = Math.max(ans, count1 + count2);
                
                int lastFruitType = fruits[r - 1];
                int newL = r - 1;
                while (newL >= l && fruits[newL] == lastFruitType) {
                    newL--;
                }
                l = newL + 1;
                
                if (lastFruitType == type1) {
                    type2 = currentFruit;
                    count2 = 1;
                    count1 = r - l;
                } else {
                    type1 = currentFruit;
                    count1 = 1;
                    count2 = r - l;
                }
            }
        }
        ans = Math.max(ans, count1 + count2);
        return ans;
    }
}