import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Ball{
	//properties
  
	int intx = 390;
	int inty = 280;
	int intdefy = 5;
	int intdefx = 5;
	BufferedImage ballimg;
	
	//method
	public void nextLoc(){
		inty = inty + intdefy;
		intx = intx + intdefx;
	}
	public void drawIt(Graphics g){
		g.drawImage(ballimg, intx, inty, null);
		this.nextLoc();
		if (inty >= 585){
			System.out.println("pasesed 585");
			intdefy = -5;
		}
		else if (inty <= 0){
			System.out.println("pasesed 0");
			intdefy = 5;
		}
		
	}
	
	//constructor
	public Ball(){ 
		try{
			ballimg = ImageIO.read(new File("ball.png"));
		}catch(IOException e){
			System.out.println("ERROR");
		}
		
			
	}
	
}
