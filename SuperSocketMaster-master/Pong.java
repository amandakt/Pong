import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.util.*;

public class Pong implements KeyListener, ActionListener{
//variables
	JFrame theFrame;
	JPanel thePanel;
	SuperSocketMaster ssm;
	JTextField ipaddress;
	JLabel ipaddresslabel;
	JTextField Username;
	JLabel Usernamelabel;
	JTextArea textrecieved;
	JScrollPane theScroll;
	JLabel textrecievedlabel;
	JFrame theframe1 = new JFrame("Pong");
	APanel thepanel = new APanel();
	int intnextpos1 = 0;
	int intnextpos2 = 0;
	Random random = new Random();   
//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == Username){
			if(ssm != null){
				thepanel.Player1 = Username.getText();
				Username.setEnabled(false); 
				ssm.sendText(Username.getText());  
				textrecieved.setEnabled(false);      
			}
		}else if(evt.getSource() == ssm){
			textrecieved.append(ssm.readText() + "\n");
			String What = ssm.readText();	 
			if (What.equals("Down")){
				intnextpos2 = thepanel.intY2 + 5;
				if (intnextpos2 < 505){
					thepanel.intY2Def = 5;
				}else if (intnextpos2 >= 505){
					thepanel.intY2Def = 0;
				}
			}else if(What.equals("Up")){
				intnextpos2 = thepanel.intY2 - 5;
				if (intnextpos2 > -5){
					thepanel.intY2Def = -5;
				}else if (intnextpos2 <= -5){
					thepanel.intY2Def = 0;
				}
			}else if(What.equals("Stop")){
				thepanel.intY2Def = 0;
			}else{
				thepanel.Player2 = What;
			}
		}
	}
	public void keyReleased(KeyEvent evt){
		if(evt.getKeyChar() == 'w'){
			thepanel.intY1Def = 0;
		}else if(evt.getKeyChar() == 's'){
			thepanel.intY1Def = 0;
		}		
	}
//Moving paddles
	public void keyPressed(KeyEvent evt){
		if(evt.getKeyChar() == 'w'){
			intnextpos1 = thepanel.intY1 - 5;
			if (intnextpos1 > -5){
				thepanel.intY1Def = -5;
			}else if (intnextpos1 <= -5){
				thepanel.intY1Def = 0;
			}
		}else if(evt.getKeyChar() == 's'){
			intnextpos1 = thepanel.intY1 + 5;
			if (intnextpos1 < 505){
				thepanel.intY1Def = 5;
			}else if (intnextpos1 >= 505){
				thepanel.intY1Def = 0;
			}
		}else if(evt.getKeyCode() == 32){
			int posneg = random.nextInt(2);
			if (posneg == 0){
				thepanel.intBallXDef = 5;
			}else if (posneg == 1){
				thepanel.intBallXDef = -5;
			}
			thepanel.intBallYDef = random.nextInt(11)-5;
		}
	}
	public void keyTyped(KeyEvent evt){
	}
//Constructor
	public Pong(){
		ssm = new SuperSocketMaster(6112, this);
		ssm.connect();
		theframe1.addKeyListener(this);
		theframe1.requestFocus();
		theframe1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe1.setContentPane(thepanel);
		theframe1.setResizable(false);	
		theframe1.pack();
		theframe1.setVisible(true);
		theFrame = new JFrame("Testing");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thePanel = new JPanel();
		thePanel.setLayout(null);
		thePanel.setPreferredSize(new Dimension(300, 450));
		theFrame.setContentPane(thePanel);
		theFrame.setResizable(false);
		theFrame.pack();
		ipaddresslabel = new JLabel("ip");
		ipaddresslabel.setHorizontalAlignment(SwingConstants.CENTER);
		ipaddresslabel.setSize(300, 25);
		ipaddresslabel.setLocation(0, 0);
		thePanel.add(ipaddresslabel);
		ipaddress = new JTextField("localhost");
		ipaddress = new JTextField("localhost");
		ipaddress.setSize(300,25);
		ipaddress.setLocation(0, 25);
		thePanel.add(ipaddress);
		Usernamelabel = new JLabel("Username");
		Usernamelabel.setHorizontalAlignment(SwingConstants.CENTER);
		Usernamelabel.setSize(300, 25);
		Usernamelabel.setLocation(0, 50);
		thePanel.add(Usernamelabel);
		Username = new JTextField();
		Username.setSize(300,25);
		Username.setLocation(0, 75);
		Username.addActionListener(this);
		thePanel.add(Username);
		textrecievedlabel = new JLabel("Text Recieved From Network");
		textrecievedlabel.setHorizontalAlignment(SwingConstants.CENTER);
		textrecievedlabel.setSize(300, 25);
		textrecievedlabel.setLocation(0, 100);
		thePanel.add(textrecievedlabel);
		textrecieved = new JTextArea();
		theScroll = new JScrollPane(textrecieved);
		theScroll.setSize(300,325);
		theScroll.setLocation(0, 125);
		textrecieved.setEnabled(false);
		thePanel.add(theScroll);  
		theFrame.setVisible(true);
	}
//main program
	public static void main(String[] args){
		Pong nt = new Pong(); 
	}
}
