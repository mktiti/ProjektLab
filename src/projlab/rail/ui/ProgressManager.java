package projlab.rail.ui;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 5/7/2017.
 */
public class ProgressManager {

    /**
     * Loads the last played map
     * @return ID of the map
     */
    public static int getProgress() {

        URL url = MenuWindow.class.getResource("/progress.txt");
        try {
            Path path = Paths.get(url.toURI());
            if (!Files.exists(path)) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile()))) {
                    bw.write("0");
                    bw.flush();
                }
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(MenuWindow.class.getResourceAsStream("/progress.txt")))) {
            try {
                return Integer.parseInt(br.readLine());
            }catch (NumberFormatException nfe){
                nfe.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Cannot read progress, possibly not exists");
        }

        return 0;
    }

    /**
     * Saves the game progress
     * @param mapid Map's ID
     */
    public static void saveProgress(int mapid){
        URL url = MenuWindow.class.getResource("/progress.txt");
        try {
            Path path = Paths.get(url.toURI());
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile()))) {
                System.out.println("Writing to file");
                bw.write(Integer.toString(mapid));
                bw.close();
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }
}
