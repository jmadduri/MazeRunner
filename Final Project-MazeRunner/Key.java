import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;

public class Key
{
	private int x;
	private int y;
	private int width;
	private int height;
	private Color body;

	public Key(int x, int y, int width, int height, Color body)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.body=body;
	}
	public int getX()
	{
		return x;
	}

	public Color getBody()
	{
		return body;
	}

	public int getY()
	{
		return y;
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
