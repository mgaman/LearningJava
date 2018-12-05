import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import com.fazecast.jSerialComm.*;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class SerialDisplayMain extends JFrame {

	private JPanel contentPane;
	private SerialPort [] ports;
	private JButton portButton;
	private int portIndex = -1;  // -1 indicates nothing chosen
	private JComboBox portsComboBox;
	private JTextArea textArea;
	private JTextField textField;
	private JCheckBox crCheckBox;
	private JCheckBox lfCheckBox;
	private JButton ascButton;
	private JButton numButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SerialDisplayMain frame = new SerialDisplayMain();
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
	public SerialDisplayMain() {
		
		initComponents();
		createEvents();
	}

	private void createEvents() {
		// TODO Auto-generated method stub
		portButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// get the index of the selected port
				// close existing port if already open
				String s ; 
				if (portIndex >= 0)
				{
					if (ports[portIndex].closePort())
					{
						s = String.format("Port %s closed",ports[portIndex].getSystemPortName());
						JOptionPane.showMessageDialog(null, s);
						portIndex = -1;
					}
					else
					{
						s = String.format("Port %s not closed",ports[portIndex].getSystemPortName());
						JOptionPane.showMessageDialog(null, s);
					}
				}					
				portIndex = portsComboBox.getSelectedIndex();
				if (ports[portIndex].openPort())
				{
			//		s = String.format("Port %s opened",ports[portIndex].getSystemPortName());
			//		JOptionPane.showMessageDialog(null, s);
					ports[portIndex].addDataListener(new SerialPortDataListener() {
						   @Override
						   public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
						   @Override
						   public void serialEvent(SerialPortEvent event)
						   {
						   if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
						         return;
						      if (ports[portIndex].bytesAvailable() > 0)
						      {
							      byte[] newData = new byte[ports[portIndex].bytesAvailable()];
							      int numRead = ports[portIndex].readBytes(newData, newData.length);
						    //	  System.out.println("Read " + numRead + " bytes.");
							      if (numRead > 0)
							      {
							    //	  System.out.print(new String(newData));
							    	  textArea.append(new String(newData));
							      }			    	  
						      }
						   }
						});
				}
				else
				{
					s = String.format("Port %s not opened",ports[portIndex].getSystemPortName());
					JOptionPane.showMessageDialog(null, s);
					portIndex = -1;
				}
			}
		});		

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				// close serial port (if open)
				if (portIndex >= 0)
					if (ports[portIndex].closePort());
			}
		});
		
		ascButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// output the text in the text field
				if (portIndex >= 0)  // port is open
				{
					String txt = textField.getText();
					if (crCheckBox.isSelected())
						txt += "\r";
					if (lfCheckBox.isSelected())
						txt += "\n";
					byte [] msg = txt.getBytes();
					ports[portIndex].writeBytes(msg, msg.length);					
				}
			}
		});

		numButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// convert text field to array of int, if OK send as array of byte 
				if (portIndex >= 0)  // port is open
				{
				try {
					byte [] msg = new byte [1];
					msg[0] = (byte)Integer.parseUnsignedInt(textField.getText());
					ports[portIndex].writeBytes(msg, msg.length);
				}
				catch (NumberFormatException  ex)
				{
					
				}
				}
			}
		});

	}

	private void initComponents() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		portsComboBox = new JComboBox();
		
		portButton = new JButton("Open");
		
		JScrollPane scrollPane = new JScrollPane();
		
		textField = new JTextField();
		textField.setColumns(10);
		
		crCheckBox = new JCheckBox("CR");
		
		lfCheckBox = new JCheckBox("LF");
		
		ascButton = new JButton("ASCII");
		
		numButton = new JButton("Number");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(portsComboBox, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(portButton)
					.addContainerGap(283, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(crCheckBox)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lfCheckBox)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(ascButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(numButton)
							.addGap(180)))
					.addContainerGap())
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(portsComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(portButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(crCheckBox)
						.addComponent(lfCheckBox)
						.addComponent(ascButton)
						.addComponent(numButton)))
		);
		
		textArea = new JTextArea();
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.YELLOW);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(textArea);
		
		// populate combobox with known serial ports
		ports = SerialPort.getCommPorts();
		if (ports.length > 0)
		{
			for (int i=0;i<ports.length;i++)
			{
				System.out.println(ports[i].getSystemPortName());
				portsComboBox.addItem(ports[i].getSystemPortName());
			}
			portsComboBox.setSelectedIndex(0);
		}
		else
			JOptionPane.showMessageDialog(null, "No serial ports found");
		
		contentPane.setLayout(gl_contentPane);		
	}
}
