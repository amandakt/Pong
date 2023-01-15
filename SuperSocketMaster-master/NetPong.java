//Amanda Tong
//Program: Net Pong Game
//Version 1.0
//Created: 12/3/2021

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class NetPong implements KeyListener, ActionListener{
	//properties
	JFrame theframe = new JFrame("PONG!");
	PPanel thepanel = new PPanel();
	SuperSocketMaster ssm;
	String strNetText;
	JLabel ServerUserLabel = new JLabel("Left Player Username:");
	JLabel ClientUserLabel = new JLabel("Right Player Username:");
	JTextField txtServerUser = new JTextField();
	JTextField txtClientUser = new JTextField();
	
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == txtServerUser){ // gets data from textfield and writes the server (left) player username on the server panel
			txtServerUser.setEnabled(false);
			txtServerUser.setText(txtServerUser.getText());
			thepanel.strLeftUsername = txtServerUser.getText();
			System.out.println(thepanel.strLeftUsername);
			theframe.requestFocus();
			
		}
		if(evt.getSource() == ssm){
			strNetText = ssm.readText();
			if (strNetText.equals("down")){ //if the keyboard data from the client equals "down", the right paddle moves down 
				thepanel.intrightdefy = 5;
			}
			else if (strNetText.equals("up")){ //if the keyboard data from the client equals "up", the right paddle moves up
				thepanel.intrightdefy = -5;
			}
			else if (strNetText.equals("stop")){ // if the keyboard data from the client equals "stop", the right paddle stops moving
				thepanel.intrightdefy = 0;
			}
			else{
				txtClientUser.setText(strNetText); // gets data from the ssm and writes the client (right) player name on the server panel
				thepanel.strRightUsername = strNetText;
				System.out.println(thepanel.strRightUsername);
			}
		}		
	}
	public void keyReleased(KeyEvent evt){
		System.out.println("key released");
		if(evt.getKeyChar() == 'w'){
			thepanel.intleftdefy = 0;
		}
		else if(evt.getKeyChar() == 's'){
			thepanel.intleftdefy = 0;
		}	
	}
	public void keyPressed(KeyEvent evt){
		System.out.println("key pressed");
		
		if (evt.getKeyChar() == 'w'){
			thepanel.intleftdefy = -5;
		}
		else if (evt.getKeyChar() == 's'){
			thepanel.intleftdefy = 5;
		}
		if (evt.getKeyCode()== KeyEvent.VK_SPACE){
			System.out.println("space pressed");
			//if one player wins, reset the score points to 0 when space is pressed
			if (thepanel.intpleft == 0 && thepanel.intpright == 5 || thepanel.intpright == 5 || thepanel.intpleft == 5){
				thepanel.intpleft = 0;
				thepanel.intpright = 0;
			}
			thepanel.thetimer.start();
		}
	}
	public void keyTyped(KeyEvent evt){
		System.out.println("key typed");
	}
	
	//constructor
	public NetPong(){
		theframe.addKeyListener(this);
		thepanel.setLayout(null);
		
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.setContentPane(thepanel);
		theframe.pack();
		theframe.setVisible(true);
		theframe.setResizable(false);
		
		ServerUserLabel.setSize(150, 20);
		ServerUserLabel.setLocation(820, 180);
		thepanel.add(ServerUserLabel);
		
		txtServerUser.setSize(150, 30);
		txtServerUser.setLocation(820, 200);
		txtServerUser.addActionListener(this);
		thepanel.add(txtServerUser);
		
		ClientUserLabel.setSize(150, 20);
		ClientUserLabel.setLocation(820, 280);
		thepanel.add(ClientUserLabel);
		
		txtClientUser.setSize(150, 30);
		txtClientUser.setLocation(820, 300);
		thepanel.add(txtClientUser);
		txtClientUser.setEnabled(false);
		
		
		ssm = new SuperSocketMaster(6112, this);
		ssm.connect();
	}
	
	//main program   
	public static void main(String[] args){
		new NetPong();
	}

}
