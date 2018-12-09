package UnitTest;
//import Parser.GetLine;

public class ParserUnitTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] buffers = {">OK\r\nRI","NG\r\nO","K\r\n+CL","I","P......\r\n","$GPRMC,,,,","gfdfgfdgfd*54\r\n"};
		Parser.GetLine myParser = new Parser.GetLine();
		myParser.linemode = false;
		for (int i=0;i<buffers.length;i++)
		{
			byte [] temp = buffers[i].getBytes();
			myParser.getLine(temp);
			String finishedLine = myParser.getNext();
			while (finishedLine != null)
			{
				System.out.println(finishedLine);
				finishedLine = myParser.getNext();
			}
		}		
	}
}
