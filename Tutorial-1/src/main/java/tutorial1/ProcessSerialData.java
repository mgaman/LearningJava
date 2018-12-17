package tutorial1;

public class ProcessSerialData extends SerialWindow implements SerialListener {
	 public void dataReceived( byte [] raw )
	 {
	//	 System.out.println(raw.length);
		System.out.print(new String(raw)); 
		// textArea.append(new String(raw));
	 }
}
