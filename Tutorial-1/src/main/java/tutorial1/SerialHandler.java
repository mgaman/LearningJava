package tutorial1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import com.fazecast.jSerialComm.*;

import SerialDisplayMain.eState;

public class SerialHandler implements ActionListener{
	
	private SerialPort [] ports;
	private int portIndex;
	private String [] namesArray;
	
	public SerialHandler()
	{
		ports = SerialPort.getCommPorts();
		portIndex = -1;
	}
	
	public String [] getSystemComPortNames()
	{		
		namesArray = new String[ports.length];
		for (int i = 0; i<ports.length;i++)
			namesArray[i] = ports[i].getSystemPortName();
		return namesArray;
	}
	
	public void portOpen(String name)
	{
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
				   @Override
				   public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
				   @Override
				   public void serialEvent(SerialPortEvent event)
				   {
				   if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
				         return;
				      if (ports[portIndex].bytesAvailable() > 0)
				      {
					      byte[] newData = new byte[ports[portIndex].bytesAvailable()];
					      int numRead = ports[portIndex].readBytes(newData, newData.length);
				    //	  System.out.println("Read " + numRead + " bytes.");
					      if (numRead > 0)
					      {
					    	  byte [] slice = Arrays.copyOfRange(newData, 0, numRead);
					      }			    	  
				      }
				   }
				});
		}
	}
	
	// this action event passes all received data to whoever needs it
	public void actionPerformed(ActionEvent e) { 
	    //code that reacts to the action... 
	}
}
