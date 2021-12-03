package mvc;
/*
Написать собственную версию программы ls (для UNIX) и dir для Windows.
Эта версия должна принимать в качестве параметра одно или несколько
имен каталогов и для каждого каталога выдавать список всех файлов,
содержащихся в этом каталоге, по одной строке на файл.
Для каждого файла должна быть выведена информация о его имени,
размере, типе, владельце, правах доступа и времени доступа.
Каждое поле должно форматироваться в соответствии с его типом.
Укажите в списке только первый дисковый адрес (или ни одного).
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermission;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        //
        String [] s1 = {"C:\\Users\\PC\\Desktop", "C:"};
//"/", "./"
        massivStrok(s1);
    }
    public static void massivStrok(String[] args) {
        for (String s: args) {
            System.out.println("   Analising of path " + s);
            my_dir_info(s);//вызываем метод для каждой дирректории
        }
    }
    public static void my_dir_info(String path1) {
        //
        Path dir = Paths.get(path1);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            String h = "";
           final String TIMEFORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            for (Path file: stream) {
                if ( Files.isDirectory(file))  System.out.print("DIR  ");
                else if ( Files.isRegularFile(file))  System.out.print("FILE ");
                else System.out.print("     ");
                h=Files.getLastModifiedTime(file).toString();
               h = String.format("%30s", h);
                System.out.print(h);

                h= String.format("%10d b ", Files.size(file));
                System.out.print(h);


                System.out.println(file.getFileName());

            }
        } catch (IOException | DirectoryIteratorException x) {

            System.err.println(x);
        }
    }
}
