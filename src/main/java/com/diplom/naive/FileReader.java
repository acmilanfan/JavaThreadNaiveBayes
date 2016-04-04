package com.diplom.naive;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class FileReader {

    public String readAttrs(String fileName) {
        String line = null;
        try {
            line = new String(readAllBytes(get(fileName)));
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода");
            e.printStackTrace();
        }

        return line;
    }

    public void read(String fileName, Learning learning) {
        Path path = FileSystems.getDefault().getPath("", fileName);
        List<String> lines = new ArrayList<String>();

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода");
            e.printStackTrace();
        }

        for (String line : lines) {
            learning.addExample(line.substring(0, line.lastIndexOf(":") - 1), line.substring(line.lastIndexOf(":") + 1, line.length()));
        }
    }
}
