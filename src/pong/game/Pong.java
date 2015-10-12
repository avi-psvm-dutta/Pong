package pong.game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import pong.equipment.*;
import pong.equipment.Bat.Controller;

public class Pong extends Console implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	
	private static boolean direction; // true --> <-- false
	
	private static final int maxScore = 7; //player has to reach this score to win
	
	protected static Controller player1_controller = new Controller((char)KeyEvent.VK_Q, (char)KeyEvent.VK_A);
	protected static Controller player2_controller = new Controller((char)KeyEvent.VK_UP, (char)KeyEvent.VK_DOWN);
	
	private static boolean collide() //function to detect collision with ceiling and floor
	{	
		//initialize a rectangle that the ball inscribes
		Rectangle ball = new Rectangle(Ball.location.x, Ball.location.y, Ball.radius, Ball.radius);
		
		return ball.intersects(upperBound) || ball.intersects(lowerBound);
	}
	
	private static boolean hit(Bat player) //function to detect collision with bat
	{
		//initialize a rectangle that the ball inscribes
		Rectangle ball = new Rectangle(Ball.location.x, Ball.location.y, Ball.radius, Ball.radius);
		//initialize a rectangle overlapping with the bat
		Rectangle bat = new Rectangle(new Rectangle(player.location.x, player.location.y, Bat.width, Bat.height));
		
		return bat.intersects(ball);
	}
	
	private static byte winRalley() //returns 0 if player 1 wins, 1 if player 2 wins, 2 otherwise
	{
		//initialize a rectangle that the ball inscribes
		Rectangle ball = new Rectangle(Ball.location.x, Ball.location.y, Ball.radius, Ball.radius);
		
		//initialize a thin rectangle behind player1's bat
		Rectangle leftBound = new Rectangle(0, 0, 1, height);
		//initialize a thin rectangle behind player2's bat
		Rectangle rightBound = new Rectangle(width - 1, 0, 1, height);
		
		return ball.intersects(leftBound) ? (byte)1 : ball.intersects(rightBound) ? (byte)0 : (byte)2;
	}
	
	private void reset()
	{
		score[0] = score[1] = 0;
	}
	
	public Pong() //constructor to set the direction of the ball to left to right, and initialize key listeners
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		direction = true;
		
		delay.start();
	}
	
	public static void main(String[] args)
	{
		Pong game = new Pong(); //initialize the game
		
		do
		{
			game.reset();
			
			int startSpeed = speed;
			
			while(score[0] < maxScore && score[1] < maxScore) //game loop, breaks when either player reaches the maximum score
			{
				new Ball(theme); //initialize ball (done in the beginning, and whenever a rally is won)
				int speed = startSpeed; //save the lowest speed
				game.delay.setDelay(speed); //set / reset speed to minimum speed
				direction = Ball.velocityX > 0; //set the direction accordingly
				
				while(true) //rally loop
				{				
					if(!direction && hit(player1) || direction && hit(player2)) //detect collision with either bat
					{
						Ball.velocityX *= -1; //reverse direction of ball
						direction = !direction; //reverse direction of ball
						if(speed > 1)
							game.delay.setDelay(speed --); //increase speed of ball
					}
					
					if(Ball.location.y <= upperBound.height + 10 || Ball.location.y <= lowerBound.y) //detect collision with floor or ceiling
						if(collide())
						{
							Ball.velocityY *= -1; //reverse y direction of ball
							Ball.location.y += Ball.velocityY; //shift ball by 1 unit, so that it doesn't get stuck inside the floor or ceiling
						}
					
					try
					{
						byte whoWon = winRalley();
						score[whoWon] ++; //winRalley is 0 if player1 wins, 1 if player2 wins
						
						break; //exit rally loop
					}
					catch(ArrayIndexOutOfBoundsException noWinner){} //winRalley was 2 means no one won, yet
				}
			}	
			
		}
		while(JOptionPane.showConfirmDialog(game, "Play again?", "Player " + (score[0] == maxScore ? "1" : "2") + " wins!", JOptionPane.YES_NO_OPTION) == 0);
		
		System.exit(0);
	}
	
	private final Set<Character> pressed = new HashSet<Character>(); //Set of currently pressed keys

    @Override
    public synchronized void keyPressed(KeyEvent e)
    {
        pressed.add((char)e.getKeyCode()); //get the KeyCode of a key currently pressed and at it to the set
        
        for(char key: pressed) //traverse the set
        {
        	if(key == player1.controls.up) //player1 moves up
        	{
        		if(player1.location.y > upperBound.height) //bat is not against the ceiling
        			player1.location.y -= Bat.velocity; //move the bat
        	}
        	else if(key == player1.controls.down) //player1 moves down
        	{
        		if(player1.location.y < height - lowerBound.height - 60) //bat is not against the floor
        			player1.location.y += Bat.velocity; //move the bat
        	}
        	else if(key == player2.controls.up) //player2 moves up
        	{
        		if(player2.location.y > upperBound.height) //bat is not against the ceiling
        			player2.location.y -= Bat.velocity; //move the bat
        	}
        	else if(key == player2.controls.down) //player2 moves down
        	{
        		if(player2.location.y < height - lowerBound.height - 60) //bat is not against the floor
        			player2.location.y += Bat.velocity; //move the bat
        	}
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e)
    {
        pressed.remove((char)e.getKeyCode()); //get the KeyCode and remove from the set 
    }
    

    @Override
    public synchronized void keyTyped(KeyEvent e)
    {
    	keyPressed(e);
    }
    
	protected Timer delay = new Timer(Ball.speed, this); //Timer object delays before changing the ball's position - delay duration is decreased to increase the ball speed

	@Override
	public synchronized void actionPerformed(ActionEvent arg0) 
	{
		Ball.move();
		repaint();
	}
}