import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    private static ExecutorService pool;

    public static void main(String [] args) {
        if(args.length != 0) {
            System.out.println("Аргумент получен");
            System.out.println("Инициализация поиска в файловой системе");
            String inputText = "";
            for (String strArray: args
                    ) {
                inputText += strArray + " ";
            }
            inputText.trim();
            System.out.println("Текст для поиска: " + inputText);

            pool = Executors.newFixedThreadPool(2);

            // Пробегаем по логическим дискам
            for (File file : File.listRoots()) {
                System.out.println(file);
                pool.submit(new TaskFind(file.getPath(), inputText));
            }
            pool.shutdown();
        }
        else{
            System.out.println("Входящий аргумент отстуствует");
            System.out.println("Инициализация Http запроса к серверу");
            System.out.println("Получение списка книг");
            String response = HttpRequest.executePost("http://localhost:9000/routs/getBooks","");
            if(response != null){
                System.out.println(response);
            }
        }
    }


}
