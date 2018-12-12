/*
 * Copied from tutorialspoint.com
 * Added the bit about priorities
 */
class RunnableDemo implements Runnable {
	private Thread t;
	private String threadName;
	private int priority = 5;
	// constructor
	RunnableDemo(String name)
	{
		threadName = name;
		System.out.println("Creating " + threadName);
	}
	
	RunnableDemo(String name, int p)
	{
		threadName = name;
		System.out.println("Creating " + threadName);
		priority = p;
	}
	// must at least implement method run
	public void run()
	{
		System.out.println("Running " + threadName);
		try {
			for (int i=0; i<10;i++)
			{
				System.out.println("Thread " + threadName + "," + i);
				Thread.sleep(1);				
			}
		}
		catch (InterruptedException e)
		{
			System.out.println("Thread " + threadName + " interrupted");
		}
		System.out.println("Thread " + threadName + " exiting");
	}
	
	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this,threadName);
			t.setPriority(priority);
			t.start();
		}
	}
}
public class TestThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RunnableDemo R1 = new RunnableDemo("Thread-1",5);
		R1.start();
		RunnableDemo R2 = new RunnableDemo("Thread-2",5);
		R2.start();
		RunnableDemo R3 = new RunnableDemo("Thread-3",10);
		R3.start();
	}

}
