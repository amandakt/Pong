import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PPanel extends JPanel implements ActionListener{
	//properties
	Timer thetimer = new Timer(1000/60, this);
	int intcolor = 0;
	
	int intlefty = 250;
	int intleftx = 0;
	int intleftdefy = 0;
	int leftscore = 0;
	
	int intrighty = 250;
	int intrightx = 790;
	int intrightdefy = 0;
	int rightscore = 0;
	
	int intballx = 400;
	int intballdefx = 0;
	int intbally = 300;
	int intballdefy = 0;
	
	int intpleft;
	int intpright;
	
	Color backgroundcolor = Color.BLACK;
	Color netbackgroundcolor = Color.WHITE;
	BufferedImage winimg;
	
	Ball ball = new Ball();
	
	String strRightUsername;
	String strLeftUsername;
	boolean blnRightWinner;
	boolean blnLeftWinner;
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetimer){
			this.repaint();
		}
	}
		
	public void paintComponent(Graphics g){
		g.setColor(netbackgroundcolor);
		g.fillRect(800, 0, 200, 600);
		g.setColor(backgroundcolor);
		g.fillRect(0, 0, 800, 600);
		
	
		//left paddle
		g.setColor(Color.WHITE);
		g.fillRect(intleftx, intlefty, 10, 50);
		intlefty = intlefty + intleftdefy;
		
		
		//right paddle
		g.setColor(Color.WHITE);
		g.fillRect(intrightx, intrighty, 10, 50);
		intrighty = intrighty + intrightdefy;
		
		//ball
		ball.drawIt(g);
		
		//line in middle
		for (int intCount = 0; intCount <30; intCount++){
			g.setColor(Color.WHITE);
			g.fillRect(400, intcolor+(20*intCount), 5, 5);
		}
		
		//if ball hits the paddle it bounces off
		if(intrighty+50 > ball.inty && intrighty < ball.inty+20 && ball.intx >= 770){
			System.out.println("touching paddle");
			ball.intdefx = -3;
		}
		if(intlefty+50 > ball.inty && intlefty < ball.inty+20 && ball.intx <= 10){
			System.out.println("touching paddle");
			ball.intdefx = 3;
		}
		
		//if the ball reaches the right opponents end of the screen, the left player score increases, the background color of the game changes, and the ball is reserved
		if (ball.intx > 780){
			thetimer.stop();
			backgroundcolor = Color.BLACK;
			ball = new Ball();
			intpleft++;
			
			if (intpleft == 1){
				backgroundcolor = Color.BLUE;
			}
			else if (intpleft == 2){
				backgroundcolor = Color.PINK;
			}
			else if (intpleft == 3){
				backgroundcolor = Color.ORANGE;
			}
			else if (intpleft == 4){
				backgroundcolor = Color.GREEN;
			}
			//if the left player wins 5 to 0, include easter egg
			else if (intpleft == 5 && intpright == 0){
				g.setColor(backgroundcolor);
				g.fillRect(0, 0, 800, 600);
				g.setColor(Color.WHITE);
				g.drawString("Haha " + strRightUsername+ " is a noob.", 320, 280);
				g.drawString("Press space to restart.", 330, 320);
				intleftx = 0;
				intlefty = 250;
				intrightx = 790;
				intrighty = 250;
				try{
					PrintWriter txtWinners = new PrintWriter(new FileWriter("winners.txt", true)); //prints winner to text file
					txtWinners.println(strLeftUsername);
					txtWinners.close();
				}catch(IOException e){
					System.out.println("Error reading from keyboard or wriing to file");
				}
			}
			//if left player wins, game is rest
			else if (intpleft == 5){
				g.setColor(Color.WHITE);
				g.drawImage(winimg,120, 270, null);
				intleftx = 0;
				intlefty = 250;
				intrightx = 790;
				intrighty = 250;
				try{
					PrintWriter txtWinners = new PrintWriter(new FileWriter("winners.txt", true)); //prints winner to text file
					txtWinners.println(strLeftUsername);
					txtWinners.close();
				}catch(IOException e){
					System.out.println("Error reading from keyboard or wriing to file");
				}
			}
			
		}
		//if the ball reaches the left opponents end of the screen, the right player score increases, the background color of the game changes, and the ball is reserved
		else if (ball.intx < -30){
			thetimer.stop();
			backgroundcolor = Color.BLACK;
			ball = new Ball();
			intpright++;
			
			if (intpright == 1){
				backgroundcolor = Color.BLUE;
			}
			else if (intpright == 2){
				backgroundcolor = Color.PINK;
			}
			else if (intpright == 3){
				backgroundcolor = Color.ORANGE;
			}
			else if (intpright == 4){
				backgroundcolor = Color.GREEN;
			}
			//if right player wins 5 to 0, include easter egg
			else if (intpleft == 0 && intpright == 5){
				g.setColor(backgroundcolor);
				g.fillRect(0, 0, 800, 600);
				g.setColor(Color.WHITE);
				g.drawString("Haha " + strLeftUsername+ " is a noob.", 320, 280);
				g.drawString("Press space to restart.", 330, 320);
				intleftx = 0;
				intlefty = 250;
				intrightx = 790;
				intrighty = 250;
				try{
					PrintWriter txtWinners = new PrintWriter(new FileWriter("winners.txt", true)); //prints winner to text file
					txtWinners.println(strRightUsername);
					txtWinners.close();
				}catch(IOException e){
					System.out.println("Error reading from keyboard or wriing to file");
				}
			}
			//if right player wins, game is reset
			else if (intpright == 5){
				g.setColor(Color.WHITE);
				g.drawImage(winimg, 470 , 270, null);
				intleftx = 0;
				intlefty = 250;
				intrightx = 790;
				intrighty = 250;
				try{
					PrintWriter txtWinners = new PrintWriter(new FileWriter("winners.txt", true)); //prints winner to text file
					txtWinners.println(strRightUsername);
					txtWinners.close();
				}catch(IOException e){
					System.out.println("Error reading from keyboard or wriing to file");
				}
			}		
		}
		
		//player score
		g.setColor(Color.WHITE);
		g.drawString("Score: "+intpright, 500, 20);
		g.drawString("Score:"+intpleft, 250, 20);
	}
	
	//constructor
	public PPanel(){
		super();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(1000, 600));
		
		//include win image when left or right player wins
		try{
			winimg = ImageIO.read(new File("win.png"));
		}catch(IOException e){
			System.out.println("ERROR");
		}
		
		
	}
	
}
