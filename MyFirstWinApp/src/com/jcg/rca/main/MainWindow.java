package com.jcg.rca.main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainWindow {

	protected Shell shlMyFirstWin;
	private Text UsernameText;
	private Text PasswordText;
	private int counter = 0;
	
	private String userName = null;
	private String password = null;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
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
		shlMyFirstWin.open();
		shlMyFirstWin.layout();
		while (!shlMyFirstWin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlMyFirstWin = new Shell();
		shlMyFirstWin.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		shlMyFirstWin.setImage(SWTResourceManager.getImage("C:\\Users\\david\\Downloads\\images\\java.png"));
		shlMyFirstWin.setSize(304, 408);
		shlMyFirstWin.setText("My First App");
		
		CLabel lblNewLabel = new CLabel(shlMyFirstWin, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage("C:\\Users\\david\\Downloads\\images\\java.png"));
		lblNewLabel.setBounds(66, 10, 86, 105);
		lblNewLabel.setText("");
		
		Label lblUserName = new Label(shlMyFirstWin, SWT.NONE);
		lblUserName.setBounds(27, 131, 55, 15);
		lblUserName.setText("Username");
		
		Label lblPassword = new Label(shlMyFirstWin, SWT.NONE);
		lblPassword.setBounds(27, 158, 55, 15);
		lblPassword.setText("Password");
		
		UsernameText = new Text(shlMyFirstWin, SWT.BORDER);
		UsernameText.setBounds(88, 128, 98, 21);
		
		PasswordText = new Text(shlMyFirstWin, SWT.BORDER);
		PasswordText.setBounds(88, 155, 98, 21);
		
		Button btnLogin = new Button(shlMyFirstWin, SWT.NONE);
		btnLogin.setBounds(98, 182, 75, 25);
		btnLogin.setText("Login");
		
		StyledText styledText = new StyledText(shlMyFirstWin, SWT.V_SCROLL | SWT.BORDER | SWT.WRAP );
		styledText.setBounds(46, 213, 178, 95);
		formToolkit.adapt(styledText);
		formToolkit.paintBordersFor(styledText);

		btnLogin.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
 
                userName = UsernameText.getText();
                password = PasswordText.getText();
                styledText.append(String.format("Line %d\n", counter++));
                styledText.setTopIndex(styledText.getLineCount() - 1);  // forces cursor to the end
                if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
                    String errorMsg = null;
                    MessageBox messageBox = new MessageBox(shlMyFirstWin, SWT.OK | SWT.ICON_ERROR);
 
                    messageBox.setText("Alert");
                    if (userName == null || userName.isEmpty()) {
                        errorMsg = "Please enter username";
                    } else if (password == null || password.isEmpty()) {
                        errorMsg = "Please enter password";
                    }
                    if (errorMsg != null) {
                      messageBox.setMessage(errorMsg);
                      messageBox.open();
                    }
                } else {
                    MessageBox messageBox = new MessageBox(shlMyFirstWin, SWT.OK | SWT.ICON_WORKING);
                    messageBox.setText("Info");
                    messageBox.setMessage("Valid");
              //      messageBox.open();
                }
            }
        });

	}
}
