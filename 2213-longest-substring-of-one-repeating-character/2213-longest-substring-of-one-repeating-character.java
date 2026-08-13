class Solution {

    static class SegmentTreeNode {
        int lo, hi;
        char maxLetter;
        char prefixLetter;
        char suffixLetter;
        int maxLength;
        int prefixLength;
        int suffixLength;
        SegmentTreeNode left, right;

        SegmentTreeNode(int lo, int hi,
                        char maxLetter,
                        char prefixLetter,
                        char suffixLetter,
                        int maxLength,
                        int prefixLength,
                        int suffixLength,
                        SegmentTreeNode left,
                        SegmentTreeNode right) {
            this.lo = lo;
            this.hi = hi;
            this.maxLetter = maxLetter;
            this.prefixLetter = prefixLetter;
            this.suffixLetter = suffixLetter;
            this.maxLength = maxLength;
            this.prefixLength = prefixLength;
            this.suffixLength = suffixLength;
            this.left = left;
            this.right = right;
        }

        SegmentTreeNode(int lo, int hi, char c) {
            this(lo, hi, c, c, c, 1, 1, 1, null, null);
        }
    }

    static class SegmentTree {
        SegmentTreeNode root;

        SegmentTree(String s) {
            root = build(s, 0, s.length() - 1);
        }

        SegmentTreeNode build(String s, int lo, int hi) {
            if (lo == hi) {
                return new SegmentTreeNode(lo, hi, s.charAt(lo));
            }

            int mid = (lo + hi) / 2;

            SegmentTreeNode left = build(s, lo, mid);
            SegmentTreeNode right = build(s, mid + 1, hi);

            return merge(left, right);
        }

        void update(int index, char c) {
            update(root, index, c);
        }

        void update(SegmentTreeNode node, int index, char c) {
            if (node.lo == node.hi) {
                node.maxLetter = c;
                node.prefixLetter = c;
                node.suffixLetter = c;
                return;
            }

            int mid = (node.lo + node.hi) / 2;

            if (index <= mid) {
                update(node.left, index, c);
            } else {
                update(node.right, index, c);
            }

            mergeInto(node, node.left, node.right);
        }

        int getMaxLength() {
            return root.maxLength;
        }

        SegmentTreeNode merge(SegmentTreeNode left,
                              SegmentTreeNode right) {

            SegmentTreeNode node = new SegmentTreeNode(
                left.lo,
                right.hi,
                ' ',
                ' ',
                ' ',
                0,
                0,
                0,
                left,
                right
            );

            mergeInto(node, left, right);

            return node;
        }

        void mergeInto(SegmentTreeNode node,
                       SegmentTreeNode left,
                       SegmentTreeNode right) {

            // Maximum length
            char maxLetter;
            int maxLength;

            if (left.maxLength > right.maxLength) {
                maxLetter = left.maxLetter;
                maxLength = left.maxLength;
            } else {
                maxLetter = right.maxLetter;
                maxLength = right.maxLength;
            }

            // Combine suffix of left + prefix of right
            if (left.suffixLetter == right.prefixLetter &&
                left.suffixLength + right.prefixLength > maxLength) {

                maxLetter = left.suffixLetter;
                maxLength = left.suffixLength + right.prefixLength;
            }

            node.maxLetter = maxLetter;
            node.maxLength = maxLength;

            // Prefix
            node.prefixLetter = left.prefixLetter;
            node.prefixLength = left.prefixLength;

            if (left.lo + left.prefixLength == right.lo &&
                left.prefixLetter == right.prefixLetter) {

                node.prefixLength += right.prefixLength;
            }

            // Suffix
            node.suffixLetter = right.suffixLetter;
            node.suffixLength = right.suffixLength;

            if (right.hi - right.suffixLength == left.hi &&
                right.suffixLetter == left.suffixLetter) {

                node.suffixLength += left.suffixLength;
            }
        }
    }

    public int[] longestRepeating(String s,
                                  String queryCharacters,
                                  int[] queryIndices) {

        int[] ans = new int[queryIndices.length];

        SegmentTree tree = new SegmentTree(s);

        for (int i = 0; i < queryIndices.length; i++) {
            tree.update(
                queryIndices[i],
                queryCharacters.charAt(i)
            );

            ans[i] = tree.getMaxLength();
        }

        return ans;
    }
}