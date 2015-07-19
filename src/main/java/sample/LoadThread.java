package sample;

import javafx.scene.control.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Олег on 19.07.2015.
 * обеспечивает скачивание файла
 */
class LoadThread implements Runnable {
    private ProgressBar progressBar;
    private String url;
    private String fileName;
    private int size;
    private String folderSafe;

    public LoadThread(String url, String fileName, String folderSafe, int size, ProgressBar progressBar) {
        this.url = url;
        this.fileName = fileName;
        this.folderSafe = folderSafe;
        this.size = size;
        this.progressBar = progressBar;
    }

    public LoadThread(String url, String fileName, int size) {
        this.url = url;
        this.size=size;
        this.fileName = fileName;
    }

    public LoadThread(String url, String fileName, int size, ProgressBar progressBar) {
        this.url = url;
        this.fileName = fileName;
        this.size = size;
        this.progressBar = progressBar;
    }

    /*непосредственно момент скачивания файла
    *wait- индткатор увиличения задерки, который позволяет буферу наполнится, если скорость скачивания низкая
    *
     */
    @Override
    public void run() {
        Loader.sleep();
        folder(folderSafe);
        int count = 0;
        try (InputStream is = new URL(url).openStream();
             FileOutputStream writer= new FileOutputStream(folderSafe + fileName))
        {
            byte[] buff = new byte[409600];
            int i = 0;
            int wait=0;
            while (i<size) {
                count = is.read(buff);
                if (count<1){
                    sleep(wait++);
                    if (wait==1000) {
                        System.out.println("ВИСИМ: "+ fileName);
                        wait=0;
                    }
                    continue;
                }
                wait=0;
                writer.write(buff, 0, count);
                writer.flush();
                i += count;
                count=0;
                progressBar.setProgress(i/(double)size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sleep(int wait){
        try {
            Thread.sleep(100 * wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //проверяет существует ли директория для скачивания, если нет, создаёт
    private void folder(String folder){
        File file=new File(folderSafe);
        if(!file.exists()) file.mkdirs();
    }
}


