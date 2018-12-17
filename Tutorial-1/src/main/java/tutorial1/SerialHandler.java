package tutorial1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.fazecast.jSerialComm.*;

public class SerialHandler {
	
	private SerialPort [] ports;
	private int portIndex;
	private String [] namesArray;
	private final BlockingQueue<String> queue;  // put received data on this queue
	private List _listeners = new ArrayList();  // list of listeners for data
	
	public SerialHandler()
	{
		ports = SerialPort.getCommPorts();
		portIndex = -1;
		queue =  new LinkedBlockingQueue<String>();
	}
	
	public String [] getSystemComPortNames()
	{		
		namesArray = new String[ports.length];
		for (int i = 0; i<ports.length;i++)
			namesArray[i] = ports[i].getSystemPortName();
		return namesArray;
	}
	
	public void portClose()
	{
		if (portIndex != -1)
			ports[portIndex].closePort();
		
	}
	public boolean portOpen(String name)
	{
		boolean rc = false;
		for (int i=0;i<namesArray.length;i++)
		{
			if (name.equals(namesArray[i]))
			{
				portIndex = i;
				break;
			}
		}
		if (portIndex == -1)
			System.out.println("No port match");
		else
		{
			System.out.println("Index " + portIndex);
			ports[portIndex].addDataListener(new SerialPortDataListener() {
				   public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
				   public void serialEvent(SerialPortEvent event)
				   {
				   if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
				         return;
				      if (ports[portIndex].bytesAvailable() > 0)
				      {
					      byte[] newData = new byte[ports[portIndex].bytesAvailable()];
					      int numRead = ports[portIndex].readBytes(newData, newData.length);
					      if (numRead > 0)
					      {
					    	  byte [] slice = Arrays.copyOfRange(newData, 0, numRead);
					    	//  System.out.print(new String(slice));
					    	  Iterator listeners = _listeners.iterator();
					          while( listeners.hasNext() ) {
					              ( (SerialListener) listeners.next() ).dataReceived(slice  );
					          }
					      }			    	  
				      }
				   }
				});
			rc = ports[portIndex].openPort();
		}
		return rc;
	}
	
	public synchronized void addListener( SerialListener l ) {
        _listeners.add( l );
    }
    
    public synchronized void removeListener( SerialListener l ) {
        _listeners.remove( l );
    }
    
}
