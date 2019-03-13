import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Compiles and runs the GUI for the Chess board.
 * Code from https://www.journaldev.com/937/compile-run-java-program-another-java-program
 * Date accessed: March 13, 2019
 * Adjusted to work for the chess project.
 */
public class RunGUI {
    public static void main(String[] args) {
        String separator = System.getProperty("file.separator");

        if (args.length == 0) {
            try {
                runProcess("javac GUIMain.java");
                runProcess("javac pieces" + separator + "*.java");
                runProcess("javac board" + separator + "*.java");
                runProcess("javac gamestate" + separator + "*/.java");
                runProcess("java GUIMain");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (args.length == 1) {
            if (args[0].equals("-nc")) {
                try {
                    runProcess("java GUIMain");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Invalid arguments.");
        }

    }

    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(pro.getInputStream());
        printLines(pro.getErrorStream());
        pro.waitFor();
    }

    private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
        }
    }

    private static void printLines(InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
}
