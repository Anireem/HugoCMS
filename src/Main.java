import java.io.File;

public class Main {
    public static void main(String[] args) {
//        MyFiles myFiles = new MyFiles();
        File folder = new File("c:/projects/hugo/Skorpofeo_1/Skorpofeo/content");

//        myFiles.createHeadersIfNotExist(folder);
        HugoHeaders hugoHeaders = new HugoHeaders();
        hugoHeaders.setProperty(folder, "draft", "false");
    }
}