package pong.equipment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Bat
{
	public static final int height = 60, width = 20; //height and width of bat
	public static final int velocity = 10; //number of pixels moved by a bat in one key press
	public Point location; //location of primary vertex of bat
	
	private static Color batColor; //color of bat
	public Controller controls;
	
	public static class Controller
	{
		public char up;
		public char down;
		public boolean up_pressed;
		public boolean down_pressed;
		
		public Controller(Controller contr)
		{
			up_pressed = false;
			down_pressed = false;
			this.up = contr.up;
			this.down = contr.down;
		}
		
		public Controller(char up_key, char down_key)
		{
			up_pressed = false;
			down_pressed = false;
			up = up_key;
			down = down_key;
		}
	}
			
	public void draw(Graphics drawEngine) //method to draw the bat
	{
		drawEngine.setColor(batColor); //set the color of bat
		drawEngine.fillRect(location.x,  location.y, width, height); //draw the bat
	}
		
	public Bat(Point p, boolean theme, Controller controller)
	{
		location = new Point(p); //initialize co-ordinates of first vertex of bat
		batColor = theme ? Ball.lightColor : Ball.darkColor; //initialize the color of the bat as black or white
		controls = new Controller(controller);
	}
}