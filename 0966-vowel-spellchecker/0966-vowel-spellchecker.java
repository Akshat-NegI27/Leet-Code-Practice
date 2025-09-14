import java.util.*;

class Solution {
    public String[] spellchecker(String[] wordlist, String[] queries) {
        Set<String> words = new HashSet<>(Arrays.asList(wordlist));
        Map<String, String> caseInsensitive = new HashMap<>();
        Map<String, String> vowelInsensitive = new HashMap<>();

        for (String word : wordlist) {
            caseInsensitive.putIfAbsent(word.toLowerCase(), word);
            vowelInsensitive.putIfAbsent(devowel(word.toLowerCase()), word);
        }

        String[] res = new String[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String q = queries[i];
            if (words.contains(q)) {
                res[i] = q;
            } else if (caseInsensitive.containsKey(q.toLowerCase())) {
                res[i] = caseInsensitive.get(q.toLowerCase());
            } else if (vowelInsensitive.containsKey(devowel(q.toLowerCase()))) {
                res[i] = vowelInsensitive.get(devowel(q.toLowerCase()));
            } else {
                res[i] = "";
            }
        }
        return res;
    }

    private String devowel(String word) {
        return word.replaceAll("[aeiou]", "#");
    }
}
