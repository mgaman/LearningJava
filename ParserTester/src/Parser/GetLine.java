package Parser;

import java.util.LinkedList;
import java.util.Queue;

public class GetLine {
	public boolean linemode;
	Queue<String> q;
	// Constructor
	String tempString;	
	public GetLine ()
	{
		linemode = true;
		tempString = "";
		q = new LinkedList<>(); 
	}
	public void getLine(byte [] rawData)
	{
		for (int i=0;i<rawData.length;i++)
		{
			if (linemode)
			{
				tempString += (char)rawData[i];
				if (rawData[i] == 10)
				{
					q.add(tempString);
					tempString = "";
				}				
			}
			else
			{
				linemode = true; // linemode only for 1 char at a time
				tempString = "";
				tempString += (char)rawData[i];
				q.add(tempString);
				tempString = "";
			}
		}
	}
	public String getNext()
	{
		return q.poll();
	}
}
