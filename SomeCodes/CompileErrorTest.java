import java.util.*;

/**
 * CompileErrorTest类用于检查输入的字符串是否符合Java语法规则。
 * 它将字符串分解为单词，并检查每个单词是否是正整数、浮点数、运算符、变量或保留字。
 * 如果所有单词都符合这些类别之一，那么输入的字符串就被认为是有效的。
 * 否则，它将返回一个编译错误。
 */
public class CompileErrorTest {
    public static final String ERROR_MESSAGE = "Compile Error";
    public static final String r = "Reversed";
    public static final String i = "Integer";
    public static final String f = "Float";
    public static final String o = "Operator";
    public static final String v = "Variable";
    static ArrayList<String> reversed = new ArrayList<String>(Arrays.asList(
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue",
            "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "if", "goto",
            "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private",
            "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "void", "volatile", "while"));
    static ArrayList<String> operator = new ArrayList<String>(Arrays.asList(
            "+", "-", "*", "/", "=","%", "++", "--", "==", "!=", ">", "<", ">=", "<=", "&&", "||", "!", "&", "|", "^",
            "~", "<<", ">>", ">>>", "+=", "-=", "*=", "/=", "%=", "&=", "|=", "^=", "<<=", ">>=", ">>>="));

    public static boolean compile(String s) {
        /*s = s.trim(); // 去除尾部空格
        if (s.length() == 0 || s.charAt(s.length() - 1) != ';') {
            return false; // 如果字符串为空或最后一个字符不是分号，返回false
        }
        s = s.substring(0, s.length() - 1); // 去除尾部的分号
        */
        String[] temp = s.split(" ");
        for (String str : temp) {
            if (!(isPositiveInteger(str) || isFloat(str) || isOperator(str) || isVariable(str) || isReversed(str))) {
                return false;
            }
        }
        return true;
    }

    public static String[] result(String s) {
        String[] temp = s.split(" ");
        String[] result = new String[temp.length];
        for (int j = 0; j < temp.length; j++) {
            if (isPositiveInteger(temp[j])) {
                result[j] = i;
            } else if (isFloat(temp[j])) {
                result[j] = f;
            } else if (isOperator(temp[j])) {
                result[j] = o;
            } else if (isVariable(temp[j])) {
                result[j] = v;
            } else if (isReversed(temp[j])) {
                result[j] = r;
            }
        }
        return result;
    }

    public static boolean isPositiveInteger(String s) {
        return s.matches("\\d+");
    }

    public static boolean isReversed(String s) {
        return reversed.contains(s);
    }

    public static boolean isOperator(String s) {
        return operator.contains(s);
    }

    public static boolean isVariable(String s) {
        return s.matches("[a-zA-Z_$][a-zA-Z_$0-9]*") && !isReversed(s) && !isOperator(s) && !isPositiveInteger(s)
                && !isFloat(s);
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> input = new ArrayList<>();
        while (sc.hasNextLine()) {
            String[] line = sc.nextLine().split(";|\\n");
            for (String s : line) {
                input.add(s);
            }
        }
        int count = 0;
        for (String s : input) {
            if (compile(s)) {
                count++;
            }
        }
        if (count == input.size()) {
            for (String s : input) {
                System.out.println(Arrays.toString(result(s)));
            }
        } else {
            System.out.println(ERROR_MESSAGE);
        }
        sc.close();
        // System.out.println(Arrays.deepToString(input.toArray()));
    }

}
