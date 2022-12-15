import java.io.File;

public class Main {
    public static void main(String[] args) {
//        MyFiles myFiles = new MyFiles();
        File folder = new File("c:/projects/Archive/Notes/Work_Wiki_GSkorpofeo");
        MdFiles.addAllFilesFromFolder(folder);
        MdFiles.convertMDFilesToBundles();
//        myFiles.createHeadersIfNotExist(folder);
//        HugoHeaders hugoHeaders = new HugoHeaders();
//        hugoHeaders.setProperty(folder, "draft", "false");
    }
}