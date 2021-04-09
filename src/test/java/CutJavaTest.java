import java.io.*;
import java.util.Arrays;
import java.util.StringJoiner;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.apache.commons.io.FileUtils.*;

class CutJavaTest {

    private String MySystemOut(String[] args) {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        PrintStream oldError = System.err;
        System.setOut(new PrintStream(bs));
        System.setErr(new PrintStream(bs));

        JavaCutLauncher.main(args);

        System.out.flush();
        System.err.flush();
        System.setOut(oldOut);
        System.setErr(oldError);
        return(bs.toString());
    }

    @Test
    void SystemOutTest() {
        File input = getFile("src\\test\\recourses\\input\\inp2");
        File output = getFile("src\\test\\recourses\\output\\outp2");
        assertEquals("aarr", MySystemOut(("-c -o "+ output + " " + input.getAbsolutePath() + " 4-7 " ).split(" ")));
    }

    private String readFile(String filename) throws IOException {
        File file = new File(filename);

        BufferedReader reader = new BufferedReader(new FileReader(file));

        StringJoiner joiner = new StringJoiner("");
        for(String line; (line = reader.readLine()) != null; ) {
            joiner.add(line + System.lineSeparator());
        }
        return joiner.toString();
    }

    private void assertFileContent(String expectedFilename, String actualFilename) throws IOException {
        BufferedReader actualReader = new BufferedReader(new FileReader(actualFilename));
        BufferedReader expectedReader = new BufferedReader(new FileReader(expectedFilename));

        int actual;
        while ((actual = actualReader.read()) != -1) {
            int expected = expectedReader.read();
            Assertions.assertEquals(expected, actual);
        }
        Assertions.assertEquals(-1, expectedReader.read());
    }

    @Test
    void JavaCutTest() throws IOException {

        String input1 = "src\\test\\resources\\input\\inp1";
        String output1 = "src\\test\\resources\\output\\outp1";
        String exp1 = "src\\test\\resources\\expected\\exp1";

        JavaCut cut = new JavaCut(true, "4-");
        cut.cutter(input1, output1);
        assertFileContent(exp1, output1);

        String input2 = "src\\test\\resources\\input\\inp2";
        String output2 = "src\\test\\resources\\output\\outp2";

        String exp2 = "src\\test\\resources\\expected\\exp2";
        JavaCut cut2 = new JavaCut(true, "4-14");
        cut2.cutter(input2, output2);
        assertFileContent(exp2, output2);

    }

    @Test
    void JavaCutLauncherTest() throws IOException {
        File input1 = getFile("src\\test\\resources\\input\\inp1");
        File input2 = getFile("src\\test\\resources\\input\\inp2");
        File output = getFile("src\\test\\resources\\output\\outp1");
        File exp1 = getFile("src\\test\\resources\\expected\\exp1");
        JavaCutLauncher.main(("-c -o " + output.getAbsolutePath() + " " + input1.getAbsolutePath() + " 4- ").split(" "));
        assertTrue(contentEquals(output, exp1));

        File output2 = getFile("src\\test\\resources\\output\\outp2");
        File exp2 = getFile("src\\test\\resources\\expected\\exp2");
        JavaCutLauncher.main(("-c -o " + output2 + " " + input2.getAbsolutePath() + " 4-14 ").split(" "));
        assertTrue(contentEquals(output2, exp2));

    }
}
