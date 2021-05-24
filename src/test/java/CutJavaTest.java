import java.io.*;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.apache.commons.io.FileUtils.*;

class CutJavaTest {


    @Test
    void JavaCutTest() throws IOException {

        File input1 = getFile("src\\test\\resources\\input\\inp1");
        File output1 = getFile("src\\test\\resources\\output\\outp1");
        File exp1 = getFile("src\\test\\resources\\expected\\exp1");

        JavaCut cut = new JavaCut(true, "4-");
        cut.cutter(input1, output1);
        assertFalse(contentEquals(output1, exp1));

        File input2 = getFile("src\\test\\resources\\input\\inp2");
        File output2 = getFile("src\\test\\resources\\output\\outp2");
        File exp2 = getFile("src\\test\\resources\\expected\\exp2");

        JavaCut cut2 = new JavaCut(true, "4-14");
        cut2.cutter(input2, output2);
        assertFalse(contentEquals(exp2, output2));

    }

    @Test
    void JavaCutLauncherTest() throws IOException {
        File input1 = getFile("src\\test\\resources\\input\\inp1");
        File input2 = getFile("src\\test\\resources\\input\\inp2");
        File output = getFile("src\\test\\resources\\output\\outp1");
        File exp1 = getFile("src\\test\\resources\\expected\\exp1");
        JavaCutLauncher.main(("-c -o " + output.getAbsolutePath() + " " + input1.getAbsolutePath() + " 4- ").split(" "));
        assertFalse(contentEquals(output, exp1));

        File output2 = getFile("src\\test\\resources\\output\\outp2");
        File exp2 = getFile("src\\test\\resources\\expected\\exp2");
        JavaCutLauncher.main(("-c -o " + output2 + " " + input2.getAbsolutePath() + " 4-14 ").split(" "));
        assertFalse(contentEquals(output2, exp2));

    }
}
