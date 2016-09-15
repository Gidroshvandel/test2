import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private static Logger log = Logger.getLogger(Application.class.getName());

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
            inputText = inputText.trim();

//            Scanner emailRead = new Scanner(System.in);
//            String inputText = emailRead.nextLine().trim();
            System.out.println("Текст для поиска: " + inputText);

            try {
                pool = Executors.newFixedThreadPool(2);
            // Пробегаем по логическим дискам
                for (File file : File.listRoots()) {
                    System.out.println(file);
                    pool.submit(new TaskFind(file.getPath(), inputText));
                }
            }catch (Exception e){
                log.log(Level.SEVERE, "Exception: ", e);
            }finally {
                pool.shutdown();
            }
        }
        else{
            System.out.println("Входящий аргумент отстуствует");
            System.out.println("Выберите тип запроса");
            System.out.println("Введите 1. Для просмотра наличия книг в данный момент у человека ");
            System.out.println("Введите 2. Чтобы просматривать, кто из людей брал определенную книгу ");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();

            switch (s) {
                case "1":
                    getBook();
                    break;
                case "2":
                    getUser();
                    break;
                    default:
                        System.out.println("Введите 1 или 2");
                        break;
            }
        }
    }

    private static void getBook(){
        System.out.println("Введите название Книги");
        Scanner bookRead = new Scanner(System.in);
        String bookName = bookRead.nextLine().trim();
        System.out.println("Введите имя Автора");
        Scanner bookAuthor = new Scanner(System.in);
        String authorName = bookAuthor.nextLine().trim();

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("bookName",bookName));
        urlParameters.add(new BasicNameValuePair("author",authorName));

        System.out.println("Инициализация Http запроса к серверу");
        System.out.println("Получение списка книг");

        String response = HttpRequest.sendPost("http://localhost:9000/routes/getBooks", urlParameters);
        if(response != null){
            System.out.println(response);
        }
    }

    private static void getUser(){
        System.out.println("Введите почту пользователя");
        Scanner emailRead = new Scanner(System.in);
        String userEmail = emailRead.nextLine().trim();

        System.out.println("Инициализация Http запроса к серверу");
        System.out.println("Получение списка пользователей");

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("userEmail", userEmail));

        String response = HttpRequest.sendPost("http://localhost:9000/routes/getUsers", urlParameters);
        if(response != null){
            System.out.println(response);
        }
    }


}
