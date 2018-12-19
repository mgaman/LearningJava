package tutorial1;

public class CollectSerialData extends SerialWindow implements SerialListener {
	private static final long serialVersionUID = 1L;
	private String s = "";
	public void dataReceived( byte [] raw )
	 {
		 s = new String(raw);
		System.out.print(s); 	
		addition = s;
	 }	
}
