import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Finder extends SimpleFileVisitor<Path>  {

    String inputText;

    public Finder(String inputText) {
        this.inputText = inputText;
    }
    // Compares the glob pattern against
    // the file or directory name.
    void find(Path file) {
        Path name = file.getFileName();
        if (name != null) {
            read(inputText, file.toFile());
        }
    }

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    // Invoke the pattern matching
    // method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        find(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
//            System.err.println(exc);
        return CONTINUE;
    }

    private void read(String findTxt, File pathToFileWithTxt){
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
        } catch (Exception e ) {
//            System.out.println( e.toString() );
        }
    }
}
