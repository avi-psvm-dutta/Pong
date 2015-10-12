package pong.equipment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import java.util.Random;

import javax.swing.JPanel;

public class Ball extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	protected static final int radius = 15; //radius of the ball
	protected static int velocityX, velocityY; //number of pixels moved by the ball in one go
	protected static int speed = 5; //delay factor between movements of the ball
	protected static Point location; //radius co-ordinates
	protected static final Color darkColor = new Color(130, 0, 0), lightColor = new Color(255, 255, 255);	
	private static Color ballColor; //color of ball
	
	protected static void draw(Graphics drawEngine) //method to draw the ball
	{
		drawEngine.setColor(ballColor);//set the color of ball
		drawEngine.fillOval(location.x, location.y, radius, radius); //draw the ball
	}
	
	public Ball(boolean theme) //constructor to initialize the ball and set the color according to a theme
	{
		ballColor = theme ? lightColor : darkColor; //set the color as white or black
		
		Random randomGenerator = new Random(); //random generator
		
		location = new Point(randomGenerator.nextInt(500) + 2*Bat.width, randomGenerator.nextInt(550) + 10); //initialize the position of the center of the ball with random but fair co-ordinates 
		
		boolean direction = randomGenerator.nextBoolean(); //randomly generate the direction
		
		velocityX = direction ? 1 : -1; //set the direction of the ball according to the random generator
		velocityY = 1; //ball always moves upwards
	}
	
	protected static void move()
	{
		location.x += velocityX; //move ball with time
		location.y += velocityY; //move ball with time
	}
}