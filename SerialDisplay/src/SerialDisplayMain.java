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
import java.util.Arrays;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;



public class SerialDisplayMain extends JFrame {

	private JPanel contentPane;
	private SerialPort [] ports;
	private JButton portButton;
	private int portIndex = -1;  // -1 indicates nothing chosen
	private JComboBox<String> portsComboBox;
	private JTextArea textArea;
	private JTextField txtOut;
	private JCheckBox crCheckBox;
	private JCheckBox lfCheckBox;
	private JButton ascButton;
	private JButton numButton;
	enum eState {IDLE,DIALLING,RINGING,SMSIN,SMSOUT,ANSWERING,HANGINGUP};
	eState pState = eState.IDLE;
	private JLabel lblState;
	private GetLine getline = new GetLine();
	private JButton btnGreen;
	private JButton btnRed;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblSubscriber;
	private JLabel lblSMS;
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
		
		// CNMI=m,n    m 1,2,3 n 2 so sms appears right away
		String [] initModem = {"ATE0","AT+CMGF=1","AT+CNMI=1,2"};
		String nextResponse = "";
		initComponents();
		createEvents();
		pState = eState.IDLE;
		lblState.setText(pState.toString());
		
		getline.linemode = true;
/*
    	while (true)

		{
			nextResponse = getline.getNext();
			if (nextResponse != null)
			{
				if (nextResponse.startsWith("RING"))
				{
					pState = eState.RINGING;
					lblState.setText(pState.toString());
				}
				else if (nextResponse.startsWith("+CLIP:"))
				{
					
				}
				else if (nextResponse.startsWith("+CMT:"))
				{
					pState = eState.SMSIN;
					lblState.setText(pState.toString());
				}
			}
		}	
		*/
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
//							    	  textArea.append(new String(newData));
							    	  byte [] slice = Arrays.copyOfRange(newData, 0, numRead);
							    	  textArea.append(new String(slice));
							    	  
							    	  getline.addRaw(slice);
							    	  String next = getline.getNext();
							    	  boolean nextLineSMS = false;
							    	  while (next != null)
							    	  {
							    		  if (next.startsWith("OK"))
							    		  {
							    			 switch (pState)
							    			 {
							    			 	case DIALLING:
							    			 	case ANSWERING:
							    			 		break;
							    			 	default:
							    			 		pState = eState.IDLE;
									    			lblState.setText(pState.toString());
									    			lblSubscriber.setText("");
									    			break;
							    			 }
							    		  }
							    		  else if (next.startsWith("RING"))
							    		  {
							    			  pState = eState.RINGING;
							    			  lblState.setText(pState.toString());
							    		  }
							    		  else if (next.startsWith("+CLIP:"))
							    		  {
							    			  String [] parts = next.split("\"");
							    			  lblSubscriber.setText(parts[1]);
							    		  }
							    		  else if (next.startsWith("+CMT:"))
							    		  {
							    			  String [] parts = next.split("\"");
							    			  lblSubscriber.setText(parts[1]);
							    			  nextLineSMS = true;
							    		  }
							    		  else if (next.startsWith("NO CARRIER"))
							    		  {
							    			  pState = eState.IDLE;
									    	  lblState.setText(pState.toString());
									          lblSubscriber.setText("");
							    		  }
							    		  else
							    		  {
							    			  if (nextLineSMS)
							    			  {
							    				  lblSMS.setText("SMS> " + next);
							    				  nextLineSMS = false;
							    			  }
							    		  }
							    		  next = getline.getNext();
							    	  }
							    	
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
					String txt = txtOut.getText();
					if (crCheckBox.isSelected())
						txt += "\r";
					if (lfCheckBox.isSelected())
						txt += "\n";
			//		byte [] msg = txt.getBytes();
			//		ports[portIndex].writeBytes(msg, msg.length);	
					writeString(txt);
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
					msg[0] = (byte)Integer.parseUnsignedInt(txtOut.getText());
					ports[portIndex].writeBytes(msg, msg.length);
				}
				catch (NumberFormatException  ex)
				{
					
				}
				}
			}
		});

		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (pState)
				{
					case RINGING:
						pState = eState.ANSWERING;
						lblState.setText(pState.toString());
						writeString("ATA\r");
						break;
					case IDLE:
						pState = eState.DIALLING;
						lblState.setText(pState.toString());
						writeString("ATD" + txtOut.getText() + ";\r");
						break;
					default:
						break;
				}
			}
		});
		
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pState = eState.HANGINGUP;
				lblState.setText(pState.toString());
				writeString("ATH\r");
			}
		});

	}

	private void initComponents() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		portsComboBox = new JComboBox<>();
		
		portButton = new JButton("Open");
		
		JScrollPane scrollPane = new JScrollPane();
		
		txtOut = new JTextField();
		txtOut.setColumns(10);
		
		crCheckBox = new JCheckBox("CR");
		
		lfCheckBox = new JCheckBox("LF");
		
		ascButton = new JButton("ASCII");
		
		numButton = new JButton("Number");
		
		lblState = new JLabel("");
		
		btnGreen = new JButton("Green");
		
		btnRed = new JButton("Red");
		
		lblNewLabel = new JLabel("State:");
		
		lblNewLabel_1 = new JLabel("From:");
		
		lblSubscriber = new JLabel("");
		
		lblSMS = new JLabel("SMS:");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(portsComboBox, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(portButton)
					.addContainerGap(283, Short.MAX_VALUE))
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(txtOut, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(crCheckBox)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lfCheckBox)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(ascButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(numButton)
					.addGap(18)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblState)
					.addGap(120))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnGreen)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnRed)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSMS, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblSubscriber, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))
					.addGap(55))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(portsComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(portButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtOut, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(crCheckBox)
						.addComponent(lfCheckBox)
						.addComponent(ascButton)
						.addComponent(numButton)
						.addComponent(lblNewLabel)
						.addComponent(lblState))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnGreen)
						.addComponent(btnRed)
						.addComponent(lblNewLabel_1)
						.addComponent(lblSubscriber))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSMS)
					.addContainerGap(27, Short.MAX_VALUE))
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
	
	private void writeString(String s)
	{
		byte [] r = s.getBytes();
		ports[portIndex].writeBytes(r, r.length);
	}
}
