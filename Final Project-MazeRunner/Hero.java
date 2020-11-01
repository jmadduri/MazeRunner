import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;

public class Hero
{
	private int x;
	private int y;
	private int width;
	private int height;
	private Color body;
	private Color outline;

	public Hero(int x, int y, int width, int height, Color body, Color outline)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.body=body;
		this.outline=outline;
	}
	public int getX()
	{
		return x;
	}

	public Color getBody()
	{
		return body;
	}

	public Color getOutline()
	{
		return outline;
	}

	public int getY()
	{
		return y;
	}

	public void move(int dir, ArrayList<Wall> walls)
	{
		boolean canMove = true;

		switch(dir)
		{
			//left
			case 37:
				for(Wall wall: walls)
				{
					Rectangle futureLoc = new Rectangle((y-1)*getHeight(),x*getWidth(), getWidth(), getHeight());
					if(futureLoc.intersects(wall.getRect()))
						canMove = false;
				}
				if(canMove)
					y--;
				break;

			//up
			case 38:
				for(Wall wall: walls)
				{
					Rectangle futureLoc = new Rectangle(y*getHeight(),(x-1)*getWidth(), getWidth(), getHeight());
					if(futureLoc.intersects(wall.getRect()))
						canMove = false;
				}
				if(canMove)
					x--;
				break;

			//right
			case 39:
				for(Wall wall: walls)
				{
					Rectangle futureLoc = new Rectangle((y+1)*getHeight(),x*getWidth(), getWidth(), getHeight());
					if(futureLoc.intersects(wall.getRect()))
						canMove = false;
				}
				if(canMove)
					y++;
				break;

			//down
			case 40:
				for(Wall wall: walls)
				{
					Rectangle futureLoc = new Rectangle(y*getHeight(),(x+1)*getWidth(), getWidth(), getHeight());
					if(futureLoc.intersects(wall.getRect()))
						canMove = false;
				}
				if(canMove)
					x++;
				break;
		}
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public Rectangle getRect()
	{
		return new Rectangle(getY()*getHeight(), getX()*getWidth(), getWidth(), getHeight());
	}
}
