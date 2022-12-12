import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HugoHeaders {

    public void addDefaultHeadersIfNotExist(File folder) {
        MyFiles.addAllFilesFromFolder(folder);
        for (File file : MyFiles.files) {
            if (!headerExist(file) && file.getName().endsWith(".md")) {
                addDefaultHeader(file);
            }
        }
    }

    public void setProperty(File folder, String property, String newValue) {
        MyFiles.addAllFilesFromFolder(folder);

        for (File file : MyFiles.files) {

            try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
                String firstLine = reader.readLine();

                if (firstLine.equals("---")) {
                    String line;
                    boolean isHeader = true;
                    StringBuilder newContent = new StringBuilder();

                    newContent.append(firstLine);
                    while ((line = reader.readLine()) != null) {
                        if (line.equals("---")) isHeader = false;
                        if (isHeader) {
                            if (line.contains(property + ":")) line = property + ": " + newValue;
                        }
                        newContent.append(line).append("\n");
                    }

                    FileWriter fileWriter = new FileWriter(file, false);
                    fileWriter.write(newContent.toString());
                    fileWriter.close();
                } else {
                    return;
                }
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException");
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
    }

    private boolean headerExist(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String firstString = reader.readLine();
            if (firstString.equals("---")) {
                return true;
            } else {
                return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return true;
    }

    private void addDefaultHeader(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder newContent = new StringBuilder();
            newContent
                    .append("---")
                    .append("\n")
                    .append("title: \"" + file.getName().replaceAll("-", " ").replaceAll(".md", "") + "\"")
                    .append("\n")
                    .append("date: 2022-12-08T00:18:54+03:00") // TODO: 12/8/2022 make current date
                    .append("\n")
                    .append("draft: true")
                    .append("\n")
                    .append("---")
                    .append("\n")
                    .append("\n");
            while ((line = bufferedReader.readLine()) != null)
                newContent.append(line).append("\n");

            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(newContent.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}
