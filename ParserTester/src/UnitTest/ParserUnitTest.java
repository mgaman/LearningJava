package UnitTest;

public class ParserUnitTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] buffers = {"OK\r\nRI","NG\r\nO","K\r\n+CL","I","P......\r\n","$GPRMC,,,,","gfdfgfdgfd*54\r\n"};
		Parser.Parser myParser = new Parser.Parser();
		for (int i=0;i<buffers.length;i++)
		{
			byte [] temp = buffers[i].getBytes();
			if (myParser.parseLine(temp))
				System.out.print(myParser.finalString);
		}
	}
}
