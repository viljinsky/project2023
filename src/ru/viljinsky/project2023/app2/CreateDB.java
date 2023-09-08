package ru.viljinsky.project2023.app2;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author viljinsky
 */
public class CreateDB implements Runnable {
    
    File file;
    static final String SOURCE = "/ru/viljinsky/project2023/app2/table.sql";

    public CreateDB(File file) {
        if(file.exists()){
            throw new RuntimeException(file+" already exists");
        }
        this.file = file;
    }
    

    String[] tables() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        try (
                InputStream stream = getClass().getResourceAsStream(SOURCE); InputStreamReader reader = new InputStreamReader(stream, "utf-8");) {

            int count;
            char[] buf = new char[800];
            while ((count = reader.read(buf)) > 0) {
                stringBuilder.append(buf, 0, count);
            }

        }
        return stringBuilder.toString().replaceAll("--.*","").split(";");
    }


    @Override
    public void run() {


        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:"+file.getAbsolutePath());
            for (String s : tables()) {
                if (!s.trim().isEmpty()) {
                    con.prepareStatement(s.trim()).execute();
                }
            }

            con.close();
            System.out.println(file.getName()+ " has been created");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public static void main(String[] args) {
        File file = new File("tmp2.db");
        if (file.exists()){
            file.delete();
        }
        new CreateDB(file).run();
    }

}
