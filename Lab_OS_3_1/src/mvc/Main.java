package mvc;

/*
Написать утилиту, начинающую работу в заданном каталоге и
спускающуюся по дереву каталогов, записывая по пути размеры
всех встретившихся ей файлов. Закончив сканирование каталога,
программа должна распечатать гистограмму размеров файлов,
используя шаг гистограммы в качестве параметра
(например, при шаге 1024, файлы размером от 0
до 1023 байт попадают в одни интервал,
от 1024 до 2047 байт — в следующий интервал и т.д.).
 */
        import java.io.File;
        import java.io.IOException;
        import java.nio.file.*;
        import java.nio.file.attribute.PosixFilePermission;
        import java.util.*;

public class Main {
    static Map<Long, Integer> numbers = new TreeMap<>();
    static int step = 1024;
    //map.put(key, map.get(key) + 1);
    public static void main(String[] args) {
        //
        String s1 = "C:\\Users\\PC\\Desktop";
        System.out.println("analising of path "+s1);
        my_dir_info(s1);
int max = 0;
int x = 0;
String z = "*";
        for (Map.Entry<Long, Integer> entry : numbers.entrySet()) {//vivod na ekran
            if (max < entry.getValue()) {
                max = entry.getValue();
            }

        }
        String j = " ";
        for (Map.Entry<Long, Integer> entry : numbers.entrySet()) {//prohodim po vsem elementam
            x = entry.getValue()*50/max;
          j=String.format("Interval: %10d : %10d %10d ", entry.getKey()*step, (entry.getKey()+ 1)*step, entry.getValue());
                      System.out.println(j+ z.repeat(x));
                    }
    }
    public static void my_dir_info(String path1) {
        //
        Path dir = Paths.get(path1);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file: stream) {

                if ( Files.isRegularFile(file)) {
                 long r = Files.size(file);
                 long x = r/step;
                 if (numbers.containsKey(x)) {
                     numbers.put(x, numbers.get(x) + 1);
                    }
                 else {
                     numbers.put(x,1);
                 }
                }

               if ( Files.isDirectory(file))  my_dir_info(file.toAbsolutePath().toString());
            }
        } catch (IOException | DirectoryIteratorException x) {

            System.err.println(x);
        }
    }

}
