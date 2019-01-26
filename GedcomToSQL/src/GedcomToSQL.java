import java.io.*;
import java.sql.*;

public class GedcomToSQL {

	static Connection c = null;
	static Statement stmt = null;
	ResultSet rs = null;
	static int gedcomlevel = 0;
	static enum eTypes {UNSET,HEAD,SUBM,INDI,FAM,TRAILER,UNKNOWN};
	static eTypes type = eTypes.UNSET;
	static int lineNumber = 0;
	
	static void processLine(String s)
	{
		type = eTypes.UNKNOWN;
		// split line at first blank to get level and rest
		// if level 0 next part(s) is top level type
		String st = s.trim();
		int br = st.indexOf(" ");
		if (br > 0)   // last line is CTRL^Z
		{
			int level = Integer.parseInt(st.substring(0, br));
			if (level == 0) // determine kind of toplevel
			{
				if (st.substring(br+1).equals("HEAD"))
				{
					type = eTypes.HEAD;
				}
				else if (st.substring(br+1).equals("TRLR"))
				{
					type = eTypes.TRAILER;
				}
				else {  // others have 2 parts
					String [] parts = st.split(" ");
					if (parts.length == 3) {
						if (parts[2].equals("INDI"))
							type = eTypes.INDI;
						else if (parts[2].equals("SUBM"))
							type = eTypes.SUBM;
						else if (parts[2].equals("FAM"))
							type = eTypes.FAM;					
					}
				}
				if (type.equals(eTypes.UNKNOWN))
				{
					System.out.println("Unknown at line " + lineNumber);
				}
			}						
		}
	}

	public static void main(String[] args) {
		// setup database
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:src/Data/family.db");
			System.out.println("Opened database successfully");
			c.setAutoCommit(true);
			stmt = c.createStatement();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		try {
			FileInputStream fstream = new FileInputStream("src/Data/transfer.ged");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
			//	System.out.println(strLine);
				processLine(strLine);
				lineNumber++;
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
