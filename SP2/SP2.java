import com.fazecast.jSerialComm.*;
public class SP2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Starting..");
		SerialPort [] allPorts = SerialPort.getCommPorts();
		if (allPorts.length > 0)
		{
			for (int i=0;i<allPorts.length;i++)
				System.out.println(allPorts[i].getSystemPortName());
		}
		else
			System.out.println("No ports found");
	}

}
