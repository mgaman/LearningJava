package tutorial1;
import org.junit.Test;
import static org.junit.Assert.*;

public class DemoTest {
	@Test
	public void shouldReturnTrue(){
		Demo demo = new Demo();
		assertTrue(demo.getBool());
	}
}
