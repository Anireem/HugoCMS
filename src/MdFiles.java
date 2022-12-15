import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MdFiles {

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
    private static void renameFile(File file, String regex, String replacement) {
        String newName = file.getName().replaceAll(regex, replacement);
        Path source = Paths.get(file.getAbsolutePath());
        try {
            Files.move(source, file.toPath().resolveSibling(newName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void renameFile(File file, String newName) {
        Path source = Paths.get(file.getAbsolutePath());
        try {
            Files.move(source, file.toPath().resolveSibling(newName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void convertMDFilesToBundles() {
        for (File file : files) {
            if (
                    !file.getName().endsWith(".md")
                    || file.getName().equals("index.md")
                    || file.getName().equals("_index.md")
            ) continue;

            List<File> imagesFromMd = getImagesFromMd(file);
            if (imagesFromMd.isEmpty()) continue;


            File folder = new File(file.getAbsolutePath().replaceAll(".md", ""));
            if (!folder.exists()) {
                folder.mkdirs();
            }

            List<File> imagesFromMd1 = getImagesFromMd(file);
            replaceImageRefererence(file);

            Path fileSource = Paths.get(file.getAbsolutePath());
            Path fileDestination = Paths.get(folder.getAbsolutePath() + "\\index.md");

            try {
                Files.move(fileSource, fileDestination);
                for (File image : imagesFromMd) {
                    Path imageSource = Paths.get(image.getCanonicalPath());
                    Path imageDestination = Paths.get(
                            folder.getAbsolutePath() + "\\" + image.getName()
                    );
                    Files.move(imageSource, imageDestination);
                }
            } catch (IOException e) {
                System.out.println("Can't copy file: " + ((NoSuchFileException) e).getFile());
            }

        }
    }

    private static void replaceImageRefererence(File file) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(file));
                FileWriter fileWriter = new FileWriter(file, false)
        ) {
            String line;
            StringBuilder newContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.contains("![")) {
                    String[] split = line.split("/");
                    line = split[split.length - 1];
                    String imageName = line.replace(")", "");
                    line = "![" + imageName + "](" + imageName + ")";
                }
                newContent.append(line).append("\n");
            }

            fileWriter.write(newContent.toString());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static List<File> getImagesFromMd(File file) {

        List<File> images = new ArrayList<>();

        if (file != null && !file.getName().endsWith("md")) {
            System.out.println("No .md file");
            return images;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("![")) {
                    String pathToImage = line.split("]")[1];
                    pathToImage = pathToImage.substring(1, pathToImage.length() -1);
                    File image = Paths.get(
                            file.getAbsolutePath() + "\\..\\" + pathToImage
                    ).toFile();

                    images.add(image);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return images;
    }
}
