package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import java.util.Random;

import javax.swing.JPanel;

public class Ball extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static final int radius = 15; //radius of the ball
	public static int velocityX, velocityY; //number of pixels moved by the ball in one go
	public static int speed = 5; //delay factor between movements of the ball
	public static Point location; //radius co-ordinates
	private static Color ballColor; //color of ball
	
	public static void draw(Graphics drawEngine) //method to draw the ball
	{
		drawEngine.setColor(ballColor);//set the color of ball
		drawEngine.fillOval(location.x, location.y, radius, radius); //draw the ball
	}
	
	public Ball(boolean theme) //constructor to initialize the ball and set the color according to a theme
	{
		ballColor = !theme ? new Color(255, 255, 255) : new Color(0, 0, 0); //set the color as white or black
		
		Random randomGenerator = new Random(); //random generator
		
		location = new Point(randomGenerator.nextInt(500) + 2*Bat.width, randomGenerator.nextInt(550) + 10); //initialize the position of the center of the ball with random but fair co-ordinates 
		
		boolean direction = randomGenerator.nextBoolean(); //randomly generate the direction
		
		velocityX = direction ? 1 : -1; //set the direction of the ball according to the random generator
		velocityY = 1; //ball always moves upwards
	}
}