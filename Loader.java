package ua.artcode.ds.Week4.Loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private String folderSafe="/temp";
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
                    System.out.println(fileUrl);
                    load(fileUrl);
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
    private void load(String url){
        
    }
}
