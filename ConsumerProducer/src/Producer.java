import java.io.*;
import java.util.concurrent.*;
 
public class Producer implements Runnable{
  private final BlockingQueue<String> queue;
 
  Producer(BlockingQueue<String> q) { queue = q; }
 
  public void run() {
    String thisLine;
    System.out.println("Start PrepareProduction");
    try {
       FileInputStream fin =  new FileInputStream("/home/susan/Documents/kafir.ps");
       BufferedReader input = new BufferedReader
           (new InputStreamReader(fin));
       while ((thisLine = input.readLine()) != null) {
           queue.put(thisLine);
       }
       fin.close();
       input.close();
       // special marker for the consumer threads
       // to mark the EOF
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
    }
    catch (Exception e) {
       e.printStackTrace();
    }
  }
}