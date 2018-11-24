
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class REexample {

	public static void main(String[] args) {
		// print out arguments
		for (int j=0;j<args.length;j++)
			System.out.println(args[j]);
		String line1 = "$GPGGA,,,,,*22\r\n$GPRMC,081836,A,3751.65,S,14507.36,E,000.0,360.0,130998,011.3,E*62\r\n$GPGGA,,,,,*22\r\n$GLRMC,,,,,*22\r\n";
//		String [] matchPattern = {"\\$GPRMC(.*)\\*..\r\n","\\$G.RMC(.*)\\*..\r\n","\\$GPSAV"};
		String [] matchPattern = {"\\$G.RMC(.*)\\*..\r\n", "\\$GPSAV,,,*33"};
		for (int i=0;i<matchPattern.length;i++)
		{
			Pattern pattern1 = Pattern.compile(matchPattern[i]);
			Matcher matcher = pattern1.matcher(line1);
			int foundCount = 0;
			System.out.println("Pattern " + matchPattern[i]);
			while (matcher.find()) {
				foundCount++;
		        System.out.print("Start index: " + matcher.start());
		        System.out.print(" End index: " + matcher.end() + " ");
		        System.out.println(matcher.group());
		    }
			System.out.println(String.format("Found %d matches", foundCount));
		}
	}
}
