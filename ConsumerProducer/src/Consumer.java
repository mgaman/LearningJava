
import java.util.concurrent.BlockingQueue;
 
public class Consumer implements Runnable {
  private final BlockingQueue<String> queue;
 
  Consumer(BlockingQueue<String> q) { queue = q; }
 
  public void run() {
    try {
       System.out.println
          ("Start " + Thread.currentThread().getName());
       
       String value = queue.take();
       while (true) {
//         System.out.println(Thread.currentThread().getName()+": " + value );
//         System.out.print(Thread.currentThread().getName()+": " + value );
         System.out.print(value);
         /*
              do something with value
         */     
         value = queue.take();
       }
    }
    catch (Exception e) {
       System.out.println
           (Thread.currentThread().getName() + " " + e.getMessage());
    }
  }
}