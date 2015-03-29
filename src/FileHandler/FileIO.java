package FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileIO {

    private String fileName;
    private String OwnerName;
    private String PrjName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getPrjName() {
        return PrjName;
    }

    public void setPrjName(String prjName) {
        PrjName = prjName;
    }

    public FileIO(String fileName, String ownerName, String prjName) {
        super();
        this.fileName = fileName;
        OwnerName = ownerName;
        PrjName = prjName;
    }

    public static void FCopy(String Source, String Destination) {
        File src = new File(Source);
        File dest = new File(Destination);
        try {
            FileUtils.copyFileToDirectory(src, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String Fread(String Source) {
        File src = new File(Source);
        String file;
        try {
            file = FileUtils.readFileToString(src);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
    
        public static List<String> FiletoStringArray(String Source) {
        File src = new File(Source);
        List<String> lines;
        try {
            lines = FileUtils.readLines(src);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return lines;
    }

    public static void TextTOFile(String text, String Destination) {
        File dest = new File(Destination);
        try {
            FileUtils.writeStringToFile(dest, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delFile(String Source) {
        File dest = new File(Source);
        try {
            FileUtils.deleteDirectory(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
