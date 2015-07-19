package sample;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayDeque;


/**
 * Created by Олег on 17.07.2015.
 *
 * Класс обрабатывает заданую страницу сайта EX.ua выбирает все файлы на ней для дальнейшего многопоточного скачивания
 */
public class Loader {
    private String url;
    private String folderSafe;
    private int size;
    private VBox vBox;

    private ArrayDeque<Thread> queue=new ArrayDeque<>();
    public Loader(String url, String folderSafe, VBox vBox) {
        this.url = url;
        this.folderSafe = folderSafe;
        this.vBox=vBox;
    }

    public Loader(String url) {
        this.url = url;
        readPage();
    }

    public String getFolderSafe() {
        return folderSafe;
    }

    public void setFolderSafe(String folderSafe) {
        this.folderSafe = folderSafe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
//метод считывает страничку и выбирает нужные строки для формирования ссылок для скачивания
    public void readPage() {
        try {
            URI uri = new URI(url);
            URL url = uri.toURL();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
            while (br.ready()) {
                String line = br.readLine();
                if (line.contains("<a href='/get/") && line.contains("title=") && line.contains("span class=small")) {
                    size(br);
                    parseLine(line);
                }
            }
        br.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new ControllerLoader(queue)).start();
    }

    //метод разберает строку, выделяя ссылку для скачивания и имя файла
    private void parseLine(String line) throws IOException {
        String temp = line.substring(line.indexOf("get/"), line.indexOf("' title"));
        String fileUrl = "http://www.ex.ua/" + temp;
        String fileName = "/" + line.substring(line.indexOf(temp + "' title='")).substring(temp.length() + 9);
        fileName = fileName.substring(0, fileName.indexOf("'"));
        System.out.println(fileName);
        load(fileUrl, fileName);
    }

    //метод находит размер файла
    private void size(BufferedReader br) throws IOException {
        while (br.ready()) {
            String line = br.readLine();
            if (line.contains("<td align=right width=230 class=small>")) {
                String result = line.substring(line.indexOf("<b>") + 3, line.indexOf("</b>")).replaceAll("\\,", "");
                System.out.println(result);
                size = Integer.parseInt(result);
                break;

            }
        }
    }

    //метод добавляет индикатор скачивания файла и создаёт нить для его скачивания
    private void load(String url, String fileName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/load.fxml"));
        ProgressBar progressBar=null;
        for (Node node:root.getChildrenUnmodifiable()) {
            if(node instanceof ProgressBar)progressBar=(ProgressBar)node;
            if (node instanceof Label) {
                ((Label) node).setText(fileName);
            }
        }
        vBox.getChildren().add(root);
        vBox.setPrefHeight(vBox.getPrefHeight() + 40);
        queue.offer(new Thread(new LoadThread(url, fileName, folderSafe, size, progressBar)));
    }


    public synchronized static void sleep(){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

}

