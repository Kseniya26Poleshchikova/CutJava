import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;

public class JavaCutLauncher {
    @Option(name = "-c", usage = "indents given in chars", forbids = {"-w"})
    private boolean charCut = false;

    @Option(name = "-w", usage = "indents given in words", forbids = {"-c"})
    private boolean wordCut = false;

    @Option(name = "-o", usage = "output file name", metaVar = "OutputFile")
    private File outputFile;

    @Argument(usage = "Input file name", metaVar = "InputFile")
    private File inputFile;

    @Argument(usage = "range of cut: N-K, from N to K", metaVar = "range", required = true, index = 1)
    private String range;

    public static void main(String[] args) {
        new JavaCutLauncher().parse(args);
    }

    private void parse(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            if ((charCut || wordCut) && range != "-" && range.matches("([0-9]*)?-([0-9]*)?")) {
            } else {
                throw new CmdLineException("cannot do cut");
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar cut.jar [-c|-w] [-o outputFile] [inputFile] range");
            parser.printUsage(System.err);
            return;
        }
        JavaCut cut = new JavaCut(charCut, range);
        try {
            cut.cutter(inputFile, outputFile);
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
