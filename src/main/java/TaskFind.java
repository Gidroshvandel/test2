import java.io.*;
import java.util.Scanner;

public class TaskFind implements Runnable {

    String pathDir;
    String inputText;

    public TaskFind(String pathDir, String inputText) {
        this.pathDir = pathDir;
        this.inputText = inputText;
    }

    public void run() {
        list(pathDir,inputText);
    }


    static void read(String findTxt, File pathToFileWithTxt){
        try
        {
            Scanner scanner = new Scanner(pathToFileWithTxt);
            //now read the file line by line...
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.indexOf(findTxt) != -1) {
                    System.out.println("!!! "+findTxt + " detected in " + pathToFileWithTxt );
                    break;
                }
            }
        } catch ( Exception e ) {
            System.out.println( e.toString() );
        }
    }

    static void list ( String pathDir, String inputText) {

        File file = new File(pathDir);
        String[] dirList = file.list();

        // Если папка пуста то не заходим
        if ( file.list() != null ) {
            for( int i = 0; i < dirList.length; i++ )
            {
                File file1 = new File( pathDir + File.separator + dirList[i] );

                if(file1.isFile()) {
                    read( inputText, file1);
                }
                else {
                    list(pathDir + File.separator + dirList[i], inputText);
                }
            }
        }
    }
}