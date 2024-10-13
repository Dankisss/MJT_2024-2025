import java.util.Arrays;

public class TextJustifier {

    public static String[] justifyText(String[] words, int maxWidth) {

        String[] lines = new String[100000];

        int currentSize = 0;
        int i = 0;
        while(i < words.length) {

            int last = i + 1;
            int curRowLength = words[i].length();

            while (last < words.length && curRowLength + words[last].length() + (last - i) <= maxWidth) {
                curRowLength += words[last].length();
                last++;
            }

            StringBuilder str = new StringBuilder();
            int wordsCount = last - i;

            if(last == words.length || wordsCount == 1) {
                for (int j = i; j < last - 1; j++) {
                    str.append(words[j]).append(" ");
                }

                str.append(words[last - 1]);

                while(str.toString().length() < maxWidth) {
                    str.append(" ");
                }
            }else {

                for(int j = i; j < last - 1; j++) {
                    double gaps = (double) (maxWidth - curRowLength) / (wordsCount - 1);
                    int spaces = (int) Math.ceil(gaps);

                    str.append(words[j]).append(" ".repeat(spaces));

                    curRowLength += spaces;
                    wordsCount = wordsCount == 1 ? 1 : wordsCount - 1;
                }

                str.append(words[last - 1]);
            }

            lines[currentSize++] = str.toString();

            i = last;
        }

        String[] res = new String[currentSize];

        System.arraycopy(lines, 0, res, 0, currentSize);

        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(justifyText(new String[]{"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer."}, 20)));
//        System.out.println(Arrays.toString(justifyText(new String[]{"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog."}, 11)));
//        System.out.println(Arrays.toString(justifyText(new String[]{"enough", "to", "explain", "to", "a", "computer."}, 20)));
        System.out.println(Arrays.toString(justifyText(new String[]{"This", "is", "an",  "example"}, 16)));
        System.out.println(Arrays.toString(justifyText(new String[]{"a", "computer."}, 20)));
        System.out.println(Arrays.toString(justifyText(new String[]{"ask","not","what","your","country","can","do","for","you","ask","what","you","can","do","for","your","country"}, 16)));
    }
}