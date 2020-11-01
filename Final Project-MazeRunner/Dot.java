import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.util.ArrayList;

public class Dot
{
	private int x;
	private int y;
	private int width;
	private int height;
	private Color body;

	public Dot(int x, int y, int width, int height, Color body)
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

	public Ellipse2D.Double getEllipse()
	{
		return new Ellipse2D.Double(getY()*getHeight()+height/4, getX()*getWidth()+width/4, getWidth()/2, getHeight()/2);
	}
}
