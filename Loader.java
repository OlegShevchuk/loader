package ua.artcode.ds.Week4.Loader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Олег on 17.05.2015.
 */
public class Loader
{
    private  String url;
    private String fileName;
    private String folderSafe="D:\\RUSH\\Project\\JavaRushHomeWork\\temp";
    public Loader(String url){
        this.url=url;
        readPage();
    }

    public Loader(String url, String fileName)
    {
        this.url = url;
        this.fileName = fileName;
    }

    public String getFolderSafe()
    {
        return folderSafe;
    }

    public void setFolderSafe(String folderSafe)
    {
        this.folderSafe = folderSafe;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    private void readPage(){
        try
        {
            URI uri=new URI(url);
            URL url=uri.toURL();
            BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
            while (br.ready()){
                String line=br.readLine();
                if (line.contains("mp3")&&line.contains("title")&&line.contains("get")) {
                    String fileUrl="http://www.ex.ua/"+line.substring(line.indexOf("get/"), line.indexOf("' title"));
                    String fileName="/"+line.substring(line.indexOf("title='"),line.indexOf("mp3")).substring(11)+"mp3";
                    System.out.print(fileUrl);
                    System.out.println(fileName);
                    load(fileUrl,fileName);
                }
                //if (line.contains("mp3")) System.out.println(line);
            }
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void load(String url, String fileName){
        url=url.replace("get","load");
        System.out.println(url);
        URL url1 = null;
        try
        {
            url1 = new URL(url);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        try
                (


            FileOutputStream writer=new FileOutputStream(folderSafe+fileName)){
            InputStream is=url1.openStream();
            byte[] buff = new byte[1000000];
            int count = 0;
            while((count = is.read(buff)) != -1){
                writer.write(buff,0,count);
                writer.flush();
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
