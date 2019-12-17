package ucll.project.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExcelReader implements Runnable {
    public void run(){
        try{
            File excelFile = new File("/test.xlsx");
            FileInputStream fis = new FileInputStream(excelFile);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
