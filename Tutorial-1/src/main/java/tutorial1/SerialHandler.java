package tutorial1;
import com.fazecast.jSerialComm.*;

public class SerialHandler {
	
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
			System.out.println("Index " + portIndex);
	}
}
