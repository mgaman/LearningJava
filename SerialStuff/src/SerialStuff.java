
import com.fazecast.jSerialComm.*;
public class SerialStuff {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SerialPort [] allports = SerialPort.getCommPorts();
		System.out.println(String.format("Found %d ports", allports.length));
		if (allports.length > 0)
		{
			for (int i=0;i<allports.length;i++)
				System.out.println(allports[i].getSystemPortName());			
		}
	}
}
