package tutorial1;
import org.junit.Test;

public class SerialHandlerTest {
	@Test
	public void getComPorts()
	{
		SerialHandler sh = new SerialHandler();
		String [] portNames = sh.getSystemComPortNames();
		System.out.println(portNames);
	}	
}
