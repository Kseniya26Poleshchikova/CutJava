


import java.io.*;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

//Вариант 8 -- cut
//Выделение из каждой строки текстового файла некоторой подстроки:
//file задаёт имя входного файла. Если параметр отсутствует, следует считывать входные данные с консольного ввода;
//Флаг -o ofile задаёт имя выходного файла (в данном случае ofile). Если параметр отсутствует,
// следует выводить результат на консольный вывод.
//Флаг -с означает, что все числовые параметры задают отступы в символах (буквах) входного файла.
//Флаг -w означает, что все числовые параметры задают отступы в словах
// (т.е. последовательностях символов без пробелов) входного файла.
//Параметр range задаёт выходной диапазон и имеет один из следующих видов (здесь N и К -- целые числа):
//-K диапазон от начала строки до K
// N- диапазон от N до конца строки
//N-K диапазон от N до K
//Command line: cut [-c|-w] [-o ofile] [file] range
//Программа построчно обрабатывает входные данные и для каждой строки выдаёт часть этой строки согласно заданному диапазону.
// Если какого-то из указанных файлов не существует или неправильно указаны параметры -c и -w (должен быть указан ровно один из них), следует выдать ошибку.
// Если в строке не хватает символов или слов, это ошибкой не является, в этом случае следует выдать ту часть входных данных, которая попадает в диапазон.


public class JavaCut {

    private boolean textToCut;
    private int begin = 0;
    private int end = -1;
    static final String StopLine = "cEnd";


    public JavaCut(boolean textToCut, String range) {
        this.textToCut = textToCut;
        rangeParse(range);
    }

    public void rangeParse(String range)  {
        String[] rangeArgs = range.split("-");
        if (range.matches("[0-9]+-[0-9]+")) {
            this.begin = Integer.parseInt(rangeArgs[0]);
            this.end = Integer.parseInt(rangeArgs[1]);
            if (begin > end) throw new IllegalArgumentException();
        } else {
            if (range.startsWith("-")) {
                this.end = Integer.parseInt(rangeArgs[1]);
            } else {
                this.begin = Integer.parseInt(rangeArgs[0]);
            }
        }
    }

    public void cutter(File input, File output) throws IOException {
        ArrayList<String> listOfString = new ArrayList<>();
        ArrayList<String> cutListOfString = new ArrayList<>();
        if (input == null) {
            Scanner in = new Scanner(System.in);
            System.out.println("Ok");
            String stroke = in.nextLine();
            while (in.hasNext() && !stroke.equals(StopLine)) {
                listOfString.add(stroke);
                stroke = in.nextLine();
                if(stroke.equals("cEnd")) break;
            }
        } else {
            try (BufferedReader bufReader =
                         new BufferedReader(new FileReader(input))) {
                String stroke = bufReader.readLine();
                while (stroke != null) {
                    listOfString.add(stroke);
                    stroke = bufReader.readLine();
                }
            }
        }
        if (textToCut) cutListOfString.addAll(charCut(listOfString));
        else cutListOfString.addAll(wordCut(listOfString));

        if (input == null) {
            for (int i = 0; i < cutListOfString.size(); i++) {
                System.out.print(cutListOfString.get(i));
                if (i != cutListOfString.size() - 1) System.out.println();
            }
            } else {
            try (BufferedWriter bufW = new BufferedWriter(new FileWriter(output))) {
                for (int i = 0; i < cutListOfString.size(); i++) {
                    bufW.write(cutListOfString.get(i));
                    if (i != cutListOfString.size() - 1) bufW.newLine();
                }
            }
        }
    }


    public ArrayList<String> wordCut(ArrayList<String> listOfString) {
        ArrayList<String> cutListOfString = new ArrayList<>();
        for (String string: listOfString) {
            var words = Arrays.stream(string.replaceAll("(\\s)+", " ").split(" ")).collect(Collectors.toList());
            String cString;
            cString = (end == -1) ?
                    String.join(" ", words.subList(begin, words.size() - 1)) : String.join(" ", words.subList(begin, end));
            cutListOfString.add(cString);
        }
        return cutListOfString;
    }

    public ArrayList<String> charCut(ArrayList<String> listOfString) {
        ArrayList<String> cutListOfString = new ArrayList<>();
        for (String string: listOfString) {
            String cString;
            cString = (end != -1) ? string.substring(begin, Math.min(string.length() - 1, end)) : string.substring(begin);
            cutListOfString.add(cString);
        }
        return cutListOfString;
    }
}