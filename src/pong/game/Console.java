package pong.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import pong.equipment.*;
import pong.equipment.Bat.Controller;

public class Console extends Ball
{	
	private static final long serialVersionUID = 1L;
	
	private static JFrame window; //the main console window
	protected static final boolean theme = false; //true is light, false is dark
	protected static final int height = 600, width = 800; //the height and width of the console
	protected static final Rectangle lowerBound = new Rectangle(0, height - 10, width, 10); //the floor
	protected static final Rectangle upperBound = new Rectangle(0, 0, width, 10); //the ceiling
	private static Color bgColor; //background color
	
	protected static Bat player1; //player1's bat
	protected static Bat player2; //player2's bat
	protected static Controller player1_controller = new Controller((char)KeyEvent.VK_Q, (char)KeyEvent.VK_A);
	protected static Controller player2_controller = new Controller((char)KeyEvent.VK_UP, (char)KeyEvent.VK_DOWN);
	
	protected static int[] score = new int[2]; //score[i-1] is player(i)'s score
	protected static int[] totalScore = new int[2]; //score[i-1] is player(i)'s total score
	
	public Console() //constructor
	{
		super(theme); //initialize a ball
		
		bgColor = !theme ? lightColor : darkColor; //set background color to black or white
		
		player1 = new Bat(new Point(0, 100), theme, player1_controller); //initialize player1's bat, with contrasting colors
		player2 = new Bat(new Point(width - Bat.width, height - Bat.height - 100), theme, player2_controller); //initialize player1's bat, with contrasting colors
		
		window = new JFrame(); //initialize main console window
		window.setLocation(new Point(0, 0)); //put window to the top left corner
		window.setVisible(true); //make window visible
		window.setTitle("Pong"); //set the title to Pong
		window.setResizable(false); //disable the user from resizing the window
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //the program exits when the user clicks on the 'X' of window
		
		setBackground(bgColor); //set the JPanel's background color
		setLayout(new FlowLayout()); //set the JPanel's layout to default FlowLayout
		
		window.add(this); //add the JPanel to window
		window.setSize(width, height  + 25); //set the size of window
	}
	
	@Override
	public void paintComponent(Graphics drawEngine)
	{
		super.paintComponent(drawEngine);
		render((Graphics2D)(drawEngine));
	}
	
	private void render(Graphics2D drawEngine)
	{
		drawEngine.setColor(!theme ? darkColor : lightColor);
		
		drawEngine.setStroke(new BasicStroke(2f));
		drawEngine.drawOval(width / 2 - 75, height / 2 - 75, 150, 150);
		drawEngine.setFont(new Font("Arial", 1, 50));
		drawEngine.drawString(String.valueOf(score[0]), width / 2 - 60, 70);
		drawEngine.drawString(String.valueOf(score[1]), width / 2 + 30, 70);
		
		drawEngine.drawRect(width / 2, 0, 1, height - 10); //draw thin rectangle for the half-line
		
		Ball.draw(drawEngine); //draw the ball
		player1.draw(drawEngine); //draw player1's bat
		player2.draw(drawEngine); //draw player2's bat
		
		drawEngine.fillRect(lowerBound.x, lowerBound.y, lowerBound.width, lowerBound.height); //draw floor
		drawEngine.fillRect(upperBound.x, upperBound.y, upperBound.width, upperBound.height); //draw ceiling
	}
}