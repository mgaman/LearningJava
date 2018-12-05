
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
//			System.out.println(ports[i].getDescriptivePortName());
//			System.out.println(ports[i].getPortDescription());
			if (ports[i].getSystemPortName().equals(args[0]))
				foundPort = i;
		}
		SerialPort comport = ports[foundPort];
		comport.openPort();
		// data sent at 1Hz rate so set timeout just over 1 sec
	//	comport.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1100, 0);  // works
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
				    	  System.out.print(new String(newData));
				      }			    	  
			      }
			   }	
		});
		while (true) {}
//		System.out.println("Closing");
	//	ports[foundPort].closePort();
	}
}
