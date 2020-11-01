import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Maze extends JPanel implements KeyListener,Runnable
{
	private JFrame frame;
	private Hero hero;
	private Monster monster1;
	private Monster monster2;
	private File sound1;
	private File sound2;
	private File sound3;
	private File sound4;
	private File sound5;
	private File sound6;
	private Key key1;
	private Key key2;
	private Key key3;
	private Key key4;
	private Key keyWin;
	private int keyint1 = 0;
	private int keyint2 = 0;
	private int keyint3 = 0;
	private int keyint4 = 0;
	private int keyint5 = 0;
	private int keyint6 = 0;
	private Thread thread;
	private boolean gameOn = true;
	//private boolean right = false;
	private int dim = 15;
	private ArrayList<Wall> walls;
	private ArrayList<Dot> dots;
	private ArrayList<Dot> dotsTemp;
	//private boolean up = false;
	private int dir = 0;
	private int count = 0;
	private int temp = 0;

	public Maze()
	{
		frame=new JFrame("Maze");
		frame.add(this);
		dotsTemp = new ArrayList<Dot>();
		createMaze("Maze1.txt");
		//instantiate hero

		hero = new Hero(1,13,dim,dim,Color.MAGENTA,Color.YELLOW);
		monster1 = new Monster(28,50,dim,dim,Color.WHITE,Color.RED);
		monster2 = new Monster(10,20,dim,dim,Color.WHITE,Color.RED);
		key1 = new Key(13,57,dim,dim,Color.BLUE);
		key2 = new Key(9,10,dim,dim,Color.RED);
		key3 = new Key(20,24,dim,dim,Color.BLUE);
		key4 = new Key(22,66,dim,dim,Color.RED);
		keyWin = new Key(29,36,dim,dim,Color.BLUE);
		sound1 = new File("pacman_beginning.wav");
		sound2 = new File("pacman_death.wav");
		sound3 = new File("pacman_chomp.wav");
		sound4 = new File("pacman_eatfruit.wav");
		sound5 = new File("pacman_intermission.wav");
		sound6 = new File("pacman_eatghost.wav");
		frame.addKeyListener(this);
		frame.setSize(1300,750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		thread=new Thread(this);
		thread.start();
	}

	public void createMaze(String fileName)
	{
		walls = new ArrayList<Wall>();
		dots = new ArrayList<Dot>();

		File name = new File(fileName);
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			int row = 0;
			String text;
			while( (text=input.readLine())!= null)
			{
				for(int col = 0; col<text.length(); col++)
				{
					if(text.charAt(col) == 'x')
					{
						walls.add(new Wall(row,col,dim,dim));
					}

					else if(text.charAt(col) == 'o')
					{
						dots.add(new Dot(row,col,dim,dim,Color.YELLOW));
					}
				}
				row++;
			}

			for(int i=0;i<dotsTemp.size();i++)
			{
				for(int j=dots.size()-1;j>=0;j--)
				{
					if(dots.get(j).getX()==dotsTemp.get(i).getX() && dots.get(j).getY()==dotsTemp.get(i).getY())
					{
						dots.remove(j);
						j=0;
					}
				}
			}
		}
		catch (IOException io)
		{
			System.err.println("File does not exist");
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0,0,frame.getWidth(),frame.getHeight());
		g2.setColor(Color.BLUE);

		for(Wall wall: walls)
		{
			g2.fill(wall.getRect());
		}

		for(Dot dot: dots)
		{
			g2.setColor(dot.getBody());
			g2.fill(dot.getEllipse());
		}

		g2.setColor(hero.getBody());
		g2.fill(hero.getRect());
		g2.setColor(hero.getOutline());
		g2.setStroke(new BasicStroke(2));
		g2.draw(hero.getRect());

		g2.setColor(monster1.getBody());
		g2.fill(monster1.getRect());
		g2.setColor(monster1.getOutline());
		g2.setStroke(new BasicStroke(2));
		g2.draw(monster1.getRect());

		g2.setColor(monster2.getBody());
		g2.fill(monster2.getRect());
		g2.setColor(monster2.getOutline());
		g2.setStroke(new BasicStroke(2));
		g2.draw(monster2.getRect());

		g2.setColor(key1.getBody());
		g2.fill(key1.getRect());

		g2.setColor(key2.getBody());
		g2.fill(key2.getRect());

		g2.setColor(key3.getBody());
		g2.fill(key3.getRect());

		g2.setColor(key4.getBody());
		g2.fill(key4.getRect());

		g2.setColor(keyWin.getBody());
		g2.fill(keyWin.getRect());

		g2.setColor(Color.RED);
		Font myFont = new Font ("Courier New", 1, 100);

		if((hero.getRect().intersects(key1.getRect())))
		{
			key1 = new Key(0,0,dim,dim,Color.BLUE);
		}

		if((hero.getRect().intersects(key2.getRect())))
		{
			key2 = new Key(0,0,dim,dim,Color.BLUE);
		}

		if((hero.getRect().intersects(key3.getRect())))
		{
			key3 = new Key(0,0,dim,dim,Color.BLUE);
		}

		if((hero.getRect().intersects(key4.getRect())))
		{
			key4 = new Key(0,0,dim,dim,Color.BLUE);
		}

		if(hero.getRect().intersects(monster1.getRect()))
		{
			myFont = new Font ("Courier New", 1, 100);
			g2.setFont(myFont);
			g2.drawString("Game Over!",300,200);
			myFont = new Font ("Courier New", 1, 65);
			g2.setFont(myFont);
			g2.drawString("You got "+temp+" out of 598 dots!",20,300);
		}

		if(hero.getRect().intersects(monster2.getRect()))
		{
			myFont = new Font ("Courier New", 1, 100);
			g2.setFont(myFont);
			g2.drawString("Game Over!",300,200);
			myFont = new Font ("Courier New", 1, 65);
			g2.setFont(myFont);
			g2.drawString("You got "+temp+" out of 598 dots!",20,300);
		}

		if((hero.getRect().intersects(keyWin.getRect())) && count==4 && ((dotsTemp.size()) == 598))//598 is total # of dots
		{
			g2.setFont(myFont);
			g2.drawString("You Win!",300,300);
		}
	}

	public void run()
	{
		System.out.println("Welcome, to win you must collect all of the dots, collect all of the keys, and stay away from the monsters. Good Luck!");
		System.out.println("The keys are red cubes, and the dots are yellow circles all around the maze!");
		PlaySound(sound1);

		while(true)
		{
			if(gameOn)
			{
				monster1.move((int)(Math.random()*4)+37, walls);
				if(dir == 39)
					monster1.move(37, walls);

				else if(dir == 37)
					monster1.move(39, walls);

				else
					monster1.move(dir, walls);

				monster2.move((int)(Math.random()*4)+37, walls);
				if(dir == 38)
					monster2.move(40, walls);

				else if(dir == 40)
					monster2.move(38, walls);

				else
					monster2.move(dir, walls);

				if(hero.getRect().intersects(monster1.getRect()))
				{
					gameOn = false;
					PlaySound(sound2);
				}

				if(hero.getRect().intersects(monster2.getRect()))
				{
					gameOn = false;
					PlaySound(sound2);
				}
			}
			try
			{
				thread.sleep(30);

			}catch(InterruptedException e)
			{
			}
			repaint();
		}
	}

	public static void PlaySound(File Sound)
	{
		try
		{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();

			Thread.sleep(clip.getMicrosecondLength()/100000);
		}
		catch(Exception e)
		{
		}
	}

	public void keyPressed(KeyEvent e)
	{
		if(gameOn)
		{
			dir = e.getKeyCode();
			hero.move(dir,walls);

			for(int i = 0; i<dots.size(); i++)
			{
				if(dots.get(i).getEllipse().intersects(hero.getRect()))
				{
					dotsTemp.add(dots.remove(i));
					temp++;

					if(temp%20==0)
						PlaySound(sound3);
				}
			}

			if(hero.getRect().intersects(monster1.getRect()))
			{
				gameOn = false;
				PlaySound(sound2);
			}

			if(hero.getRect().intersects(monster2.getRect()))
			{
				gameOn = false;
				PlaySound(sound2);
			}

			if((hero.getRect().intersects(keyWin.getRect())) && count==4 && ((dotsTemp.size()) == 598))//598 is total # of dots
			{
				gameOn = false;
				PlaySound(sound5);
			}

			else if((hero.getRect().intersects(key1.getRect())) && keyint1!=1)
			{
				count++;
				keyint1++;
				PlaySound(sound4);
			}

			else if((hero.getRect().intersects(key2.getRect())) && keyint2!=1)
			{
				count++;
				keyint2++;
				PlaySound(sound4);
			}

			else if((hero.getRect().intersects(key3.getRect())) && keyint3!=1)
			{
				count++;
				keyint3++;
				PlaySound(sound4);
			}

			else if((hero.getRect().intersects(key4.getRect())) && keyint4!=1)
			{
				count++;
				keyint4++;
				PlaySound(sound4);
			}

			if((count == 2)&&(keyint5!=1))
			{
				createMaze("Maze2.txt");
				monster1 = new Monster(28,50,dim,dim,Color.WHITE,Color.RED);
				monster2 = new Monster(10,20,dim,dim,Color.WHITE,Color.RED);
				key1 = new Key(13,57,dim,dim,Color.RED);
				key3 = new Key(20,24,dim,dim,Color.RED);
				keyint5++;
				PlaySound(sound6);
			}

			if((count == 4)&&(keyint6!=1))
			{
				createMaze("Maze3.txt");
				monster1 = new Monster(28,50,dim,dim,Color.WHITE,Color.RED);
				monster2 = new Monster(10,20,dim,dim,Color.WHITE,Color.RED);
				keyWin = new Key(29,36,dim,dim,Color.BLACK);
				keyint6++;
				PlaySound(sound6);
			}
		}
	}

	public void keyReleased(KeyEvent e)
	{
		dir = 0;
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public static void main(String[] args)
	{
		Maze app=new Maze();
	}
}