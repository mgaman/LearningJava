import com.fazecast.jSerialComm.*;
import java.io.*;  // for InputStream

public class SP2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Starting..");
		SerialPort [] allPorts = SerialPort.getCommPorts();
		int index = -1;
		if (allPorts.length > 0)
		{
			for (int i=0;i<allPorts.length;i++)
			{
				System.out.println("Index " + i + "," + allPorts[i].getSystemPortName());
				if (allPorts[i].getSystemPortName().equals(args[0]))
					index = i;
			}
			
			SerialPort comPort = SerialPort.getCommPorts()[index];
			comPort.openPort();
			// data sent at 1Hz rate so set timeout just over 1 sec
			comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1100, 0);  // works
//			comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1100, 0);  // works
//			comPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING,1100,0);  // doesnt work, get timeout
			
			InputStream in = comPort.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			try
			{
			   for (int j = 0; j < 1000000; j++)
			   {
			      System.out.println(reader.readLine());
			      System.out.println(j);
			   }
			   reader.close();
			   in.close();
			} catch (Exception e) { e.printStackTrace(); }
			comPort.closePort();
		}
		else
			System.out.println("No ports found");
		System.out.println("Done...");
	}

}
