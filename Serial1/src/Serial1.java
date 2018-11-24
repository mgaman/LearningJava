
import com.fazecast.jSerialComm.*;

public class Serial1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Looking for port "+args[0]);
		SerialPort [] ports = SerialPort.getCommPorts();
		int foundPort = -1;
		for (int i=0;i<ports.length;i++)
		{
			System.out.println(ports[i].getSystemPortName());
			//System.out.println(ports[i].getDescriptivePortName());
			//System.out.println(ports[i].getPortDescription());
			if (ports[i].getSystemPortName().equals(args[0]))
				foundPort = i;
		}
		System.out.println(String.format("Found at index %d",foundPort));
		SerialPort comPort = SerialPort.getCommPorts()[foundPort];
//		SerialPort comPort = SerialPort.getCommPorts()[0];
		comPort.openPort();
		try {
		   while (true)
		   {
		      while (comPort.bytesAvailable() <= 0)
		         Thread.sleep(20);

		      byte[] readBuffer = new byte[comPort.bytesAvailable()];
		      int numRead = comPort.readBytes(readBuffer, readBuffer.length);
		      System.out.println(new String(readBuffer));
//		      System.out.println("Read " + numRead + " bytes.");
		   }
		} catch (Exception e) { e.printStackTrace(); }
		System.out.println("Closing");
		comPort.closePort();
	}
}
