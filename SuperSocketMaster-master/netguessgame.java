//Amanda Tong
//Program: Guess The Number Game
//Version 1.0
//Created: 12/3/2021

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.lang.Math;

public class netguessgame implements ActionListener{
	//properties
	JFrame Frame = new JFrame("Guessing Game");
	JPanel Panel = new JPanel();
	JLabel Label = new JLabel("Guess The Number!");
	JTextArea txtArea = new JTextArea("Welcome to Guess The Number! \nEnter a username before connecting...\n");
	JScrollPane txtScrolled = new JScrollPane(txtArea);
	JLabel Label2 = new JLabel("Username");
	JTextField txtUser = new JTextField();
	JLabel Label3 = new JLabel("Guess a number");
	JTextField txtGuess = new JTextField();
	JButton StartButton = new JButton("Start");
	JButton ClientButton = new JButton("Client");
	JButton ServerButton = new JButton("Server");
	JLabel Label4 = new JLabel("IP");
	JTextField txtIP = new JTextField("127.0.0.1");
	SuperSocketMaster ssm;
	int intHigherNum;
	int intLowerNum;
	int intHiddenNum;
	int intServerGuess;
	int intClientGuess;
	int intClientDifference;
	int intClientAbsVal;
	int intServerAbsVal;
	int intServerDifference;
	String strClientWinner;
	String strWinner;
	int intFinalVal;
	int intClosestVal = 10000000;;
	boolean blnGameRun;
	
	//chat 
	JLabel chatLabel = new JLabel("Online Chat");
	JTextArea txtChat = new JTextArea();
	JTextField txtWrite = new JTextField();
	JLabel writeLabel = new JLabel("Write a Message");
	JScrollPane txtChatScrolled = new JScrollPane(txtChat);
	String txtArray[] = new String [3];
	String guessArray[] = new String [3];
	String strEnabled;
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if (evt.getSource() == txtUser){
			System.out.println("username entered");
			ClientButton.setEnabled(true);
			ServerButton.setEnabled(true);
			txtUser.setEnabled(false);
		}
		if (evt.getSource() == txtIP){
			System.out.println("IP entered");
		}
		if (evt.getSource() == ClientButton){ //if client button is pressed, user will be connected as a client
			System.out.println("client button pressed");
			ClientButton.setEnabled(false);
			ServerButton.setEnabled(false);
			StartButton.setEnabled(false);
			txtGuess.setEnabled(true);
			txtIP.setEnabled(false);
			ssm = new SuperSocketMaster(txtIP.getText(), 2188, this);
			boolean Connected = ssm.connect();
			
			if(Connected){
				txtArea.append("My Address: "+ssm.getMyAddress()+"\n");
				txtArea.append("My Hostname: "+ssm.getMyHostname()+"\n");
				txtArea.append("user "+txtUser.getText()+ " is connected\n");
				ssm.sendText("game,"+ "user " + txtUser.getText()+ " is connected");
			}		
		}
		if (evt.getSource() == ServerButton){ //if server button is pressed, user will be connected as a server
			System.out.println("Server button pressed");
			ClientButton.setEnabled(false);
			ServerButton.setEnabled(false);
			StartButton.setEnabled(true);
			txtGuess.setEnabled(true);
			txtIP.setEnabled(false);
			ssm = new SuperSocketMaster(2188, this);
			boolean Connected = ssm.connect();
			
			if(Connected){
				txtArea.append("My Address: "+ssm.getMyAddress()+"\n");
				txtArea.append("My Hostname: "+ssm.getMyHostname()+"\n");
				txtArea.append("user "+txtUser.getText()+ " is connected\n");
				ssm.sendText("game,"+ "user " + txtUser.getText()+ " is connected");
			}
		}
		if (evt.getSource() == txtGuess){ //if guess is entered, message with the user's guess will be printed
			System.out.println("guess entered");
			txtArea.append(txtUser.getText()+ " guessed " +txtGuess.getText()+"\n");
			ssm.sendText("guess,"+txtUser.getText()+ "," + txtGuess.getText());
			intServerGuess = Integer.parseInt(txtGuess.getText());
			txtGuess.setEnabled(false);
			
		}
		if (evt.getSource() == StartButton && blnGameRun == false){ 
			//if start button is pressed and the game isn't running, a start message is printed 
			//a random hidden number between a random range is generated
			
			intLowerNum = (int)(Math.random()*200+1);
			intHigherNum = (int)(Math.random()*600+400);
			intHiddenNum = (int)(Math.random()*intHigherNum)+intLowerNum;
			System.out.println("start button pressed");
			System.out.println("random number: " + intHiddenNum);
			
			txtArea.append("START! Guess number betweeen " + intLowerNum + " and " + intHigherNum+ "\n");
			ssm.sendText("game,"+"START! Guess number betweeen " + intLowerNum + " and " + intHigherNum);
			blnGameRun = true;
			
			txtGuess.setEnabled(true);
			ssm.sendText("enabled");
				
		}
		else if (evt.getSource() == StartButton && blnGameRun == true){
			//if start button is pressed again when the game is already running, winner is printed
			//server guess is compared with the best client guess in the game, whoever is closest is the winner
			
			System.out.println("start button pressed");
			System.out.println("random number: " + intHiddenNum);
			
			int intServerDifference = intHiddenNum - intServerGuess; 
			intServerAbsVal = Math.abs(intServerDifference);
			
			if (intServerAbsVal <= intClosestVal){
				strWinner = txtUser.getText();
				intFinalVal = intServerAbsVal;
				System.out.println();
			}
			else{
				strWinner = strClientWinner;
				intFinalVal = intClientAbsVal;
			}
			txtArea.append("Winner: " + strWinner + ". Guess was off by " + intFinalVal + " from the hidden number: " + intHiddenNum + "\n");
			ssm.sendText("game,Winner: " + strWinner + ". Guess was off by " + intFinalVal + " from the hidden number: " + intHiddenNum);
			blnGameRun = false;
				
		}
		if(evt.getSource() == txtWrite){ //if user wirtes a message, massage is printed to the chat area with the username
			System.out.println("message written");
			txtChat.append(txtUser.getText() + " : " + txtWrite.getText()+ "\n");
			ssm.sendText("chat," + txtUser.getText() + " : " + txtWrite.getText());
		}
		if(evt.getSource() == ssm){ //if text is sent to ssm, the text is read and sent to all user's screens
			System.out.println(ssm.readText());
			strEnabled = ssm.readText();
			
			for (int intCount = 0; intCount <=3; intCount++){
				txtArray = ssm.readText().split(",");	
				guessArray = ssm.readText().split(",");
			}
			
			if(txtArray[0].equals("game")){ //if string text starts with 'game', print guess message to all user's text area
				txtArea.append(txtArray[1] + "\n");
			}
			else if (txtArray[0].equals("chat")){ //if string text starts with 'chat', print guess message to all user's chat area
				txtChat.append(txtArray[1] + "\n");
			}
			else if(guessArray[0].equals("guess")){ //if string text starts with 'guess', print guess message to all user's text area
				txtArea.append(guessArray[1]+ " guessed " + guessArray[2]+ "\n");
				
				//calculates how close client guess is to the hidden number. Compares the guesses to see which is closest
				int intClientDifference = intHiddenNum - Integer.parseInt(guessArray[2]);
				intClientAbsVal = Math.abs(intClientDifference);
				
				if (intClientAbsVal <= intClosestVal){
					strClientWinner = guessArray[1];
					intClosestVal = intClientAbsVal;
					System.out.println(intClosestVal);
				}
			}
			else if (strEnabled.equals("enabled")){
				txtGuess.setEnabled(true);
			}	
		}	
	}
	
	//constructor
	public netguessgame(){
		Panel.setLayout(null);
		
		Label.setSize(200, 10);
		Label.setLocation(180, 10);
		Panel.add(Label);
		
		txtScrolled.setSize(400,100);
		txtScrolled.setLocation(30, 30);
		Panel.add(txtScrolled); 
    
		
		Label2.setSize(200, 10);
		Label2.setLocation(200, 140);
		Panel.add(Label2);
		
		txtUser.setSize(200, 30);
		txtUser.setLocation(130, 160);
		txtUser.addActionListener(this);
		Panel.add(txtUser);
		
		Label3.setSize(200, 10);
		Label3.setLocation(180, 210);
		Panel.add(Label3);
		
		txtGuess.setSize(200, 30);
		txtGuess.setLocation(130, 230);
		txtGuess.addActionListener(this);
		Panel.add(txtGuess);
		
		StartButton.setSize(200, 50);
		StartButton.setLocation(130, 280);
		StartButton.addActionListener(this);
		Panel.add(StartButton);
		StartButton.setEnabled(false);
		
		ServerButton.setSize(200, 30);
		ServerButton.setLocation(130, 360);
		ServerButton.addActionListener(this);
		Panel.add(ServerButton);
		ServerButton.setEnabled(false);
		
		ClientButton.setSize(200, 30);
		ClientButton.setLocation(130, 400);
		ClientButton.addActionListener(this);
		Panel.add(ClientButton);
		ClientButton.setEnabled(false);
		
		Label4.setSize(200, 10);
		Label4.setLocation(220, 450);
		Panel.add(Label4);
		
		txtIP.setSize(200, 30);
		txtIP.setLocation(130, 470);
		Panel.add(txtIP);
		
		//chat
		chatLabel.setSize(200, 10);
		chatLabel.setLocation(470, 10);
		Panel.add(chatLabel);
		
		txtChatScrolled.setSize(250,410);
		txtChatScrolled.setLocation(470, 30);
		Panel.add(txtChatScrolled);
		
		writeLabel.setSize(200, 10);
		writeLabel.setLocation(470, 450);
		Panel.add(writeLabel); 
		
		txtWrite.setSize(250,30);
		txtWrite.setLocation(470, 470);
		txtWrite.addActionListener(this);
		Panel.add(txtWrite); 
		
		Panel.setPreferredSize(new Dimension(800,550));
		Frame.setContentPane(Panel);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.pack();
		Frame.setVisible(true);
		Frame.setResizable(false);
	}
	
	//main method
	public static void main(String[] args){
		new netguessgame();
	}
		

}

