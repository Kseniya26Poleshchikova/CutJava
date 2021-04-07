


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

    public JavaCut(boolean textToCut, String range) {
        this.textToCut = textToCut;
        rangeParse(range);
    }

    public void rangeParse(String range)  {
        if (range.startsWith("-")) {
            this.end = Integer.parseInt(range) * -1;
        } else {
            if (range.length() >= 3) {
                String[] ranges = range.split("-");
                this.begin = Integer.parseInt(ranges[0]);
                this.end = Integer.parseInt(ranges[1]);
                if (begin > end) throw new IllegalArgumentException();
            } else {
                this.begin = Integer.parseInt(range.substring(0, range.length() - 1));
            }
        }
        if (end == 0 || begin == 0) throw new IllegalArgumentException();
    }

    public void cutter(String input, String output) throws IOException {
        ArrayList<String> listOfString = new ArrayList<>();
        ArrayList<String> cutListOfString = new ArrayList<>();
        if (input.isEmpty()) {
            Scanner in = new Scanner(System.in);
            System.out.println("Ok");
            listOfString.add(in.nextLine());
        }
        else {
            try(FileInputStream in = new FileInputStream(input)) {
                try(InputStreamReader reader = new InputStreamReader(in)) {
                    try(BufferedReader bReader = new BufferedReader(reader)) {
                        String stroke = bReader.readLine();
                        while (stroke != null) {
                            listOfString.add(stroke);
                            stroke = bReader.readLine();
                        }
                    }
                }
            }

        }
        if (textToCut) cutListOfString.addAll(charCut(listOfString));
        else cutListOfString.addAll(wordCut(listOfString));
        if (output.isEmpty()) {
            cutListOfString.forEach(System.out::println);
        } else {
            try(FileOutputStream out = new FileOutputStream(output)) {
                try(OutputStreamWriter writer = new OutputStreamWriter(out)) {
                    try(BufferedWriter bWriter = new BufferedWriter(writer)) {
                        for (String stroke: cutListOfString) {
                            bWriter.write(stroke);
                        }
                    }
                }
            }
        }
    }


    public ArrayList<String> wordCut(ArrayList<String> listOfString) {
        ArrayList<String> cutListOfString = new ArrayList<String>();
        for (String string: listOfString) {
            var words = Arrays.stream(string.replaceAll("(\\s)+", " ").split(" ")).collect(Collectors.toList());
            String cString;
            cString = (end == -1) ?
                    String.join(" ", words.subList(begin, words.size() - 1)) : String.join(" ", words.subList(begin - 1, end));
            cutListOfString.add(cString);
        }
        return cutListOfString;
    }
    public ArrayList<String> charCut(ArrayList<String> listOfString) {
        ArrayList<String> cutListOfString = new ArrayList<>();
        for (String string: listOfString) {
            String cString;
            cString = (end == -1) ? string.substring(begin - 1) : string.substring(begin - 1, end);
            cutListOfString.add(cString);
        }
        return cutListOfString;
    }
}