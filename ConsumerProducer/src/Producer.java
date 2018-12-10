import java.io.*;
import java.util.concurrent.*;
import com.fazecast.jSerialComm.*;
 
public class Producer implements Runnable{
  private final BlockingQueue<String> queue;
  private String portName;
  Producer(BlockingQueue<String> q, String port)
  {
	  queue = q;
	  portName = port;
  }
 
  public void run() {
    String thisLine;
    System.out.println("Start PrepareProduction");
	System.out.println("Looking for port "+portName);
	SerialPort [] ports = SerialPort.getCommPorts();
	int foundPort = -1;
	for (int i=0;i<ports.length;i++)
	{
		System.out.println(ports[i].getSystemPortName());
//		System.out.println(ports[i].getDescriptivePortName());
//		System.out.println(ports[i].getPortDescription());
		if (ports[i].getSystemPortName().equals(portName))
			foundPort = i;
	}
	SerialPort comport = ports[foundPort];
	comport.openPort();
//	comport.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 1100, 0);
	comport.addDataListener(new SerialPortDataListener() {
		@Override
		   public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
		   @Override
		   public void serialEvent(SerialPortEvent event)
		   {
		      if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
		         return;
		      if (comport.bytesAvailable() > 0)
		      {
			      byte[] newData = new byte[comport.bytesAvailable()];
			      int numRead = comport.readBytes(newData, newData.length);
		    //	  System.out.println("Read " + numRead + " bytes.");
			      if (numRead > 0)
			      {
			    	 // System.out.print(new String(newData));
			    	  try {
						queue.put(new String(newData));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			      }			    	  
		      }
		   }	
	});
	while (true) {}
  }
}