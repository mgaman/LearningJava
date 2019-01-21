package application;

public class AppendLinesToParent extends SampleController implements AppendLines {

	@Override
	public void addLine(String s) {
		// TODO Auto-generated method stub
		inQ.add(s);
	}
}
