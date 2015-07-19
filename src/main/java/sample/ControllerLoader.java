package sample;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Олег on 19.07.2015.
 * нить контролирующая загрузку всех файлов, допускает одновременно запускать 20 потоков закачки
 */
class ControllerLoader implements Runnable{
    ArrayDeque<Thread> threads;

    public ControllerLoader(ArrayDeque<Thread> threads) {
        this.threads = threads;
    }

    /*контролирует жизненый цикл нитей скачивания
     *Как только нить отрабатывает, её место занимает следующая в очереди
     */
    public void downloader(){
        List<Thread> threadsList=creatList();
        while(!threads.isEmpty()&&!threadsList.isEmpty()){
            for (int i=0;i<threadsList.size();i++){
                if (!threadsList.get(i).isAlive()){
                    if(threads.peekFirst()!=null){
                        threadsList.set(i,threads.pollFirst());
                        threadsList.get(i).start();
                    }else{
                        threads.remove(i);
                    }
                }
            }
        }

    }

    //формирует начальный список нитей и запускает их
    private List<Thread> creatList(){
        List<Thread> threadsList=new ArrayList<>();
        for (int i = 0; i <20&&threads.peekFirst()!=null ; i++) {
            threads.add(threads.pollFirst());
            threadsList.get(i).start();
        }
        return threadsList;
    }

    @Override
    public void run() {
        downloader();
    }
}
