import java.io.*;

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
