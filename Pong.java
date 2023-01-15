//Amanda Tong
//Program: Pong Game
//Version 1.0
//Created: 11/18/2021

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class Pong implements KeyListener{
	//properties
	
	JFrame theframe = new JFrame("PONG!");
	PPanel thepanel = new PPanel();
	
	//methods
	public void keyReleased(KeyEvent evt){
		System.out.println("key released");
		if(evt.getKeyChar() == 'w'){
			thepanel.intleftdefy = 0;
		}
		else if(evt.getKeyChar() == 's'){
			thepanel.intleftdefy = 0;
		}
		if(evt.getKeyCode() == KeyEvent.VK_UP){
			thepanel.intrightdefy = 0;
		}
		if(evt.getKeyCode() == KeyEvent.VK_DOWN){
			thepanel.intrightdefy = 0;
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
		if(evt.getKeyCode() == KeyEvent.VK_UP){
			thepanel.intrightdefy = -5;
		}
		else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
			thepanel.intrightdefy = 5;
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
	public Pong(){
		theframe.addKeyListener(this);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.setContentPane(thepanel);
		theframe.pack();
		theframe.setVisible(true);
		theframe.setResizable(false);
	}
	
	//main program   
	public static void main(String[] args){
		new Pong();
	}

}
