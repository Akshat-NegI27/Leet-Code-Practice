class Solution {
    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {

        Map<String, Integer> priority = new HashMap<>();
        priority.put("electronics", 0);
        priority.put("grocery", 1);
        priority.put("pharmacy", 2);
        priority.put("restaurant", 3);

        List<String[]> validCoupons = new ArrayList<>();

        for (int i = 0; i < code.length; i++) {
            if (!isActive[i])
                continue;
            if (!priority.containsKey(businessLine[i]))
                continue;
            if (code[i].isEmpty() || !code[i].matches("[A-Za-z0-9_]+"))
                continue;

            validCoupons.add(new String[] { businessLine[i], code[i] });
        }

        validCoupons.sort((a, b) -> {
            int p1 = priority.get(a[0]);
            int p2 = priority.get(b[0]);
            if (p1 != p2)
                return p1 - p2;
            return a[1].compareTo(b[1]);
        });

        List<String> result = new ArrayList<>();
        for (String[] coupon : validCoupons) {
            result.add(coupon[1]);
        }

        return result;
    }
}
