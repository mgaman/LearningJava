package Parser;

public class Parser {
	// what the caller cares about
	public String finalString;
	// Constructor
	String tempString;
	public Parser ()
	{
		tempString = "";
	}
	// method parse byte buffer for line ending in cr/lf
	// assumes not more than 1 line in the buffer
	// return true if a line present, else false
	public boolean parseLine(byte [] rawData)
	{
		boolean rc = false;
		for (int i=0;i<rawData.length;i++)
		{
			tempString += (char)rawData[i];
			if (rawData[i] == 10)
			{
				//System.out.print(finalString);
				//finalString = "";
				rc = true;
				finalString = tempString;
				tempString = "";
			}
		}
		return rc;
	}
}
