//Amanda Tong
//Program: Net Pong Game - Client
//Version 1.0
//Created: 12/3/2021

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class NetPongClient implements ActionListener, KeyListener{
	//propeterties
	JFrame theframe = new JFrame("Basic Cliet Net APP");
	JPanel thepanel = new JPanel();
	JLabel IPLabel = new JLabel("Server IP");
	JTextField txtIP = new JTextField("127.0.0.1");
	JTextField txtUser = new JTextField();
	JLabel UserLabel = new JLabel("Enter a username:");
	SuperSocketMaster ssm = new SuperSocketMaster(txtIP.getText(), 6112, this);
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == txtIP){ //gets IP address 
			System.out.println("connected");
			txtIP.setEnabled(false);
		}
		if(evt.getSource() == txtUser){ //gets client username from the textfield and sends to ssm
			System.out.println("username entered");
			ssm.sendText(txtUser.getText());
			txtUser.setEnabled(false);
		}
	}
	public void keyReleased(KeyEvent evt){ //if key is released, send "stop" to ssm
		ssm.sendText("stop");
	}
	public void keyPressed(KeyEvent evt){ 
		System.out.println("key pressed");
		if(evt.getKeyCode() == KeyEvent.VK_UP){ //if key is pressed up, send "up" to ssm
			ssm.sendText("up");
		}
		else if(evt.getKeyCode() == KeyEvent.VK_DOWN){ //if key is pressed down, send "down" to ssm
			ssm.sendText("down");
		}
	}
	public void keyTyped(KeyEvent evt){
		System.out.println("key typed");
	}
			
	//constructor
	public NetPongClient(){
		theframe.addKeyListener(this);
		thepanel.setLayout(null);
		IPLabel.setSize(200, 50);
		IPLabel.setLocation(100, 30);
		thepanel.add(IPLabel);
		
		txtIP.setSize(200, 50);
		txtIP.setLocation(100, 70);
		txtIP.addActionListener(this);
		thepanel.add(txtIP);
		
		UserLabel.setSize(200, 30);
		UserLabel.setLocation(100, 140);
		thepanel.add(UserLabel);
		
		txtUser.setSize(200, 50);
		txtUser.setLocation(100, 170);
		txtUser.addActionListener(this);
		thepanel.add(txtUser);
		
		thepanel.setPreferredSize(new Dimension(400,400));
		theframe.setContentPane(thepanel);
		
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.pack();
		theframe.setVisible(true);
		theframe.setResizable(false);
		
		ssm.connect();
	}
	
	//main method
	public static void main(String[] args){
		new NetPongClient();
	}

}
