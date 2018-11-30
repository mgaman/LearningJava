import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ImageArray {

	protected Shell shlImagearray;
	protected int index = 0;
//	protected String basepath = "C:\\Users\\david\\Downloads\\images\\";
	protected String basepath = "Images/";  // relative to project base
	/* Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ImageArray window = new ImageArray();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlImagearray.open();
		shlImagearray.layout();
		while (!shlImagearray.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlImagearray = new Shell();
		shlImagearray.setSize(142, 164);
		shlImagearray.setText("ImageArray");
		
		Button btnNewButton = new Button(shlImagearray, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String path = String.format(basepath + "signal-%d-bar.png", index);
				btnNewButton.setImage(SWTResourceManager.getImage(path));
				index++;
				index = index % 6;
			}
		});
		String path = String.format(basepath + "signal-%d-bar.png", index);
		btnNewButton.setImage(SWTResourceManager.getImage(path));
		btnNewButton.setBounds(10, 33, 86, 66);
	}
}
