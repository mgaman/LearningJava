import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
 
public class CPMain {
  public static void main(String[] args) throws Exception {
        
    BlockingQueue<String> q =
        new LinkedBlockingQueue<String>();

    Thread p1 = new Thread(new Producer(q,args[0]));
    Thread c1 = new Thread(new Consumer(q));
    p1.setPriority(c1.getPriority()+1);    
    p1.start();
    c1.start();
    p1.join();
    c1.join();
    System.out.println("Done.");
    while (true) {}
  }
}