package tutorial1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;

import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JLabel;

public class SerialWindow extends JFrame/* implements SerialListener*/ {

	private JPanel contentPane;
	private SerialHandler sh = new SerialHandler();
	private JButton btnPort;
	private JComboBox comboBox;
	protected JTextArea textArea;
	protected JLabel lblNewLabel;
	protected static String addition = "";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SerialWindow frame = new SerialWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SerialWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				sh.portClose();
			}
		});
		initComponents();
		createEvents();
		
	}

	
	private void createEvents() {
		// TODO Auto-generated method stub
		btnPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (sh.portOpen(comboBox.getSelectedItem().toString()))
				{
					btnPort.setEnabled(false);
					SerialListener sl = new CollectSerialData();
					sh.setListener(sl);
					 SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
						 @Override
						 protected Boolean doInBackground() throws Exception {
						  // Simulate doing something useful.
							 int x = 100;
						  while (x < 1000) {
						   Thread.sleep(1000);

						   // The type we pass to publish() is determined
						   // by the second template parameter.
						   	if (addition != null)
						   	{
							   publish(addition);
							   addition = null;
						   	}
						  }

						  // Here we can return some object of whatever type
						  // we specified for the first template parameter.
						  // (in this case we're auto-boxing 'true').
						  return true;
						 }

						 // Can safely update the GUI from this method.
						 protected void done() {
						 }

						 @Override
						 // Can safely update the GUI from this method.
						 protected void process(List<String> chunks) {
						  // Here we receive the values that we publish().
						  // They may come grouped in chunks.
						  String mostRecentValue = chunks.get(chunks.size()-1);
						  int size = chunks.size();
						  textArea.append(mostRecentValue);
						  lblNewLabel.setText("l " + size);
						 }

						};

						worker.execute();				
				}
				else
				{
					System.out.println("Failed to open serial port");
				}
			}
		});		
	}

	private void initComponents() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(10, 11, 66, 20);
		contentPane.add(comboBox);
		
		btnPort = new JButton("Open");
		btnPort.setBounds(84, 10, 66, 23);
		contentPane.add(btnPort);
		
		textArea = new JTextArea();
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.GREEN);
		textArea.setBounds(20, 42, 432, 209);
		contentPane.add(textArea);
		
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(178, 14, 46, 14);
		contentPane.add(lblNewLabel);
		
		String [] portNames = sh.getSystemComPortNames();
		for (int i=0;i<portNames.length;i++)
			comboBox.addItem(portNames[i]);
	}
}
