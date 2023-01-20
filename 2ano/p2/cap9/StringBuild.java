package cap9;

public class StringBuild {
    public static StringBuilder foo(String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            if (s2.indexOf(c) >= 0) {
                sb.append(c);
            }
        }
        return sb;
    }
}


