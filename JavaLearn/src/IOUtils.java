/* import java.io.*;

public class IOUtils {
    private static BufferedReader br;
    private static BufferedWriter bw;

    static {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public static int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    public static String readLine() throws IOException {
        return br.readLine();
    }

    public static void write(String str) throws IOException {
        bw.write(str);
    }

    public static void writeln(String str) throws IOException {
        bw.write(str);
        bw.newLine();
    }

    public static void flush() throws IOException {
        bw.flush();
    }

    public static void close() throws IOException {
        br.close();
        bw.close();
    }
}
*/
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

public class IOUtils {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PrintWriter pw = new PrintWriter(System.out, true);

    public static int readInt() throws IOException {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            throw new IOException("Error reading integer from input", e);
        }
    }

    public static String readLine() throws IOException {
        try {
            return scanner.nextLine();
        } catch (Exception e) {
            throw new IOException("Error reading line from input", e);
        }
    }

    public static void write(String str) throws IOException {
        try {
            pw.print(str);
        } catch (Exception e) {
            throw new IOException("Error writing string to output", e);
        }
    }

    public static void writeln(String str) throws IOException {
        try {
            pw.println(str);
        } catch (Exception e) {
            throw new IOException("Error writing string to output", e);
        }
    }

    public static void close() throws IOException {
        try {
            scanner.close();
            pw.close();
        } catch (Exception e) {
            throw new IOException("Error closing input/output", e);
        }
    }
}