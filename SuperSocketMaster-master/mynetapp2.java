import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class mynetapp2 implements ActionListener{
	//creating a client program to recieve basic test to a server
	//propeterties
	JFrame theframe = new JFrame("Basic Cliet Net APP");
	JPanel thepanel = new JPanel();
	JLabel thelabel = new JLabel("Enter text here");
	SuperSocketMaster ssm = new SuperSocketMaster(7777, this);
	
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == ssm){
			thelabel.setText(ssm.readText());
			System.out.println("server opened");
		
		}
	}
			
	//constructor
	public mynetapp2(){
		thepanel.add(thelabel);
		thepanel.setPreferredSize(new Dimension(300,300));
		theframe.setContentPane(thepanel);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.pack();
		theframe.setVisible(true);
		ssm.connect();
	}
	
	//main method
	public static void main(String[] args){
		new mynetapp2();
	}

}
