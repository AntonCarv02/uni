package cap12;

import java.io.*;
import java.util.*;

public class FileProcessor {
    // Resolva aqui este exercício.
    public static String scan(String file) {

        int minsize = 100, maxsize = 0, i = 0;
        float media = 0;
        try {
            File f =new File(file);
            if(!(f.exists())){
                throw new Exception("ERRO");

            }
            Scanner s = new Scanner(f);
            String read = "", min = "", max = "";

            if (!(s.hasNext())) {
                s.close();

                throw new Exception("ERRO");

            }

            do {

                read = s.nextLine();

                if (read.equals(null)) {
                    continue;
                }

                media += read.length();

                if (read.length() < minsize) {
                    min = read;
                    minsize = read.length();

                } else if (read.length() > maxsize) {
                    max = read;
                    maxsize = read.length();
                }

                i++;

            } while (s.hasNext());
            s.close();
            media /= i;
            String res = min + "|" + max + "|" + String.format("%.3f", media);

            return res;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}