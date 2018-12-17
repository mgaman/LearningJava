package tutorial1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class SerialWindow extends JFrame {

	private JPanel contentPane;
	private SerialHandler sh = new SerialHandler();
	private JButton btnPort;
	private JComboBox comboBox;
	private JScrollPane scrollPane;
	protected JTextArea textArea;
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
				if (!sh.portOpen(comboBox.getSelectedItem().toString()))
					System.out.println("Failed to open serial port");
				else
				{
					btnPort.setEnabled(false);
					SerialListener sl = new ProcessSerialData();
					sh.addListener(sl);
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
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(586, 258, -574, -215);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setBounds(23, 52, 558, 199);
		contentPane.add(textArea);
		
		btnPort = new JButton("Open");
		btnPort.setBounds(84, 10, 66, 23);
		contentPane.add(btnPort);
		
		contentPane.add(textArea);
		//scrollPane.add(textArea);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(textArea);
		
		String [] portNames = sh.getSystemComPortNames();
		for (int i=0;i<portNames.length;i++)
			comboBox.addItem(portNames[i]);
	}

}
