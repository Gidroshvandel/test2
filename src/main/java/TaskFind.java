
import java.io.IOException;
import java.nio.file.*;

public class TaskFind implements Runnable {

    String pathDir;
    String inputText;

    public TaskFind(String pathDir, String inputText) {
        this.pathDir = pathDir;
        this.inputText = inputText;
    }

    @Override
    public void run() {
            Path startingDir = Paths.get(pathDir);

            Finder finder = new Finder(inputText);
            try {
                Files.walkFileTree(startingDir, finder);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
