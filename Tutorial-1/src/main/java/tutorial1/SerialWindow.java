package tutorial1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SerialWindow extends JFrame {

	private JPanel contentPane;
	private SerialHandler sh = new SerialHandler();
	private JButton btnPort;
	private JComboBox comboBox;
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
		initComponents();
		createEvents();

	}

	private void createEvents() {
		// TODO Auto-generated method stub
		btnPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sh.portOpen(comboBox.getSelectedItem().toString());
			}
		});		
	}

	private void initComponents() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		comboBox = new JComboBox();
		contentPane.add(comboBox);
		
		btnPort = new JButton("Port");
		contentPane.add(btnPort);
		String [] portNames = sh.getSystemComPortNames();
		for (int i=0;i<portNames.length;i++)
			comboBox.addItem(portNames[i]);
	}

}
