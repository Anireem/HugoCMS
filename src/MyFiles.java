import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MyFiles {

    public static List<File> files = new ArrayList<>();

    public static void addAllFilesFromFolder(File folder) {
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                files.add(file);
            } else {
                addAllFilesFromFolder(file);
            }
        }
    }



    public void renameFiles(File folder, String regex, String replacement) {
        addAllFilesFromFolder(folder);
        for (File file : files) {
            renameFile(file, "---", "-");
        }
    }
    private void renameFile(File file, String regex, String replacement) {
        String newName = file.getName().replaceAll(regex, replacement);
        Path source = Paths.get(file.getAbsolutePath());
        try {
            Files.move(source, file.toPath().resolveSibling(newName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
