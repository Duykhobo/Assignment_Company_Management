package repo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public abstract class FileHandler<T> {

    /**
     *
     * @param Tlist
     * @param url
     * @return true|false
     */
    public boolean load(ArrayList<T> Tlist, String url) {
        Tlist.clear();
        File f = new File(url);

        try ( BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line = reader.readLine();

            while (line != null) {
                T newT = handleLine(line);
                Tlist.add(newT);
                line = reader.readLine();
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    /**
     * C?n d?nh nghia hàm x? lý dòng
     *
     * @param line
     * @return
     */
    public abstract T handleLine(String line);

    /**
     * Hàm chuy?n object thành string d? luu
     *
     * @param TList
     * @param url
     * @return true|false
     */
    public boolean save(ArrayList<T> TList, String url) {
        File f = new File(url);

        try ( OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f))) {
            for (T item : TList) {
                writer.write(item.toString());
                writer.write("\n");
            }
            writer.flush();
            return true;
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            return false;
        }
    }
}
