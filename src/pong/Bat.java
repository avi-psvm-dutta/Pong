package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Bat
{
	public static final int height = 60, width = 20; //height and width of bat
	public static final int velocity = 10; //number of pixels moved by a bat in one key press
	public Point location; //location of primary vertex of bat
	private static Color batColor; //color of bat
		
	public void draw(Graphics drawEngine) //method to draw the bat
	{
		drawEngine.setColor(batColor); //set the color of bat
		drawEngine.fillRect(location.x,  location.y, width, height); //draw the bat
	}
		
	public Bat(Point p, boolean theme)
	{
		location = new Point(p); //initialize co-ordinates of first vertex of bat
		batColor = !theme ? new Color(255, 255, 255) : new Color(0, 0, 0); //initialize the color of the bat as black or white
	}
}