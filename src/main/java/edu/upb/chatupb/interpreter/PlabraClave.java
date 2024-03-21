package edu.upb.chatupb.interpreter;

public class PlabraClave implements Expression {

    private String palabraClave;

    public PlabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    @Override
    public boolean evalua(String sentence) throws Exception {
        String[] words = sentence.split("\\s+");
        for (String word : words) {
            //TODO: remove special characters
            if (compareIgnoreCase(word, palabraClave)) {
                return true;
            }
        }
        return false;
    }

    public boolean compareIgnoreCase(String str1, String str2) {
        System.out.println(str1);
        System.out.println(str2);
        char[] charArray1 = str1.toCharArray();
        char[] charArray2 = str2.toCharArray();

        if (charArray1.length != charArray2.length) {
            return false;
        }

        for (int i = 0; i < charArray1.length; i++) {
            if (Character.toLowerCase(charArray1[i]) != Character.toLowerCase(charArray2[i])) {
                return false;
            }
        }
        return true;
    }
}
