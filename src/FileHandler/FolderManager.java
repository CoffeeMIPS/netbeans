package FileHandler;

import java.io.File;

public class FolderManager {

    private String FolderName;

    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String folderName) {
        FolderName = folderName;
    }

    public FolderManager(String folderName) {
        super();
        FolderName = folderName;
    }

    public static String FolderCreator(String dir) {
        (new File(dir)).mkdir();
        return dir;
    }
}
