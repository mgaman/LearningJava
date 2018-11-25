
import com.fazecast.jSerialComm.*;

public class Serial1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SerialPort [] ports = SerialPort.getCommPorts();
		int foundPort = -1;
		for (int i=0;i<ports.length;i++)
		{
			System.out.println(ports[i].getSystemPortName());
//			System.out.println(ports[i].getDescriptivePortName());
//			System.out.println(ports[i].getPortDescription());
			if (ports[i].getSystemPortName().equals(args[0]))
				foundPort = i;
		}
		SerialPort comport = ports[foundPort];
		comport.openPort();
		comport.addDataListener(new SerialPortDataListener() {
			@Override
			   public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
			   @Override
			   public void serialEvent(SerialPortEvent event)
			   {
			      if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
			         return;
			      byte[] newData = new byte[comport.bytesAvailable()];
			      int numRead = comport.readBytes(newData, newData.length);
			      System.out.print(new String(newData));
			    //  System.out.println("Read " + numRead + " bytes.");
			   }	
		});
		while (true) {}
//		System.out.println("Closing");
	//	ports[foundPort].closePort();
	}
}
