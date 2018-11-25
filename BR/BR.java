import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BR {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SerialPort [] ports = SerialPort.getCommPorts();
		int foundPort = -1;
		for (int i=0;i<ports.length;i++)
		{
			System.out.println(ports[i].getSystemPortName());
	//		System.out.println(ports[i].getDescriptivePortName());
	//		System.out.println(ports[i].getPortDescription());
			if (ports[i].getSystemPortName().equals(args[0]))
				foundPort = i;
		}
		System.out.println("Using port " + foundPort);
		ports[foundPort].openPort();
		InputStreamReader isr = new InputStreamReader(ports[foundPort].getInputStream());
		BufferedReader br = new BufferedReader(isr);
		String line;
    	while (true)
	    {
		   try
		   {
			   line = br.readLine();
			   if (!line.isEmpty())
				   System.out.println(line);
		   }
		   catch (Exception e){
			   
		   }
	    }
	}
}
