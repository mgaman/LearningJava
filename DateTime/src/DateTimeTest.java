import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class DateTimeTest {
	private static LocalDateTime modemTime;
	public static void main(String[] args) {
		String dt = "+CCLK:18/12/11,15:14:22+08";
		String [] LR = dt.split(",");
		String [] date = LR[0].split(":");
		String [] ymd = date[1].split("/");
		String [] time = LR[1].split("[+-]");
		String [] hms = time[0].split(":");
	//	System.out.println(ymd[0] + "," + ymd[1] + "," +ymd[2]);
	//	System.out.println(hms[0] + "," + hms[1] + "," +hms[2]);
	//	System.out.println("GMT+" + (Integer.parseInt(time[1]))/4);
		modemTime = LocalDateTime.of(Integer.parseInt(
				ymd[0])+2000, Integer.parseInt(ymd[1]), Integer.parseInt(ymd[2]), 
				Integer.parseInt(hms[0]), Integer.parseInt(hms[1]),Integer.parseInt(hms[2]));
		System.out.println("Epoch: " + modemTime);
		modemTime = modemTime.plusSeconds(3601);
		System.out.println("Epoch: " + modemTime);
		String [] p1 = dt.split(":", 2);
		String newdt = "20"+p1[1] ;
		System.out.println(newdt);
		//                                              2018/12/11,15:14:22+08
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd,HH:mm:ss");
		try {
			System.out.println(format.parse(newdt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
