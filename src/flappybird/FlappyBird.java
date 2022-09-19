package flappybird;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, MouseListener, KeyListener{
	
	public static FlappyBird flappybird;
	public Random rand;
	public Rectangle bird;
	public boolean gameover, started;
	public final int width = 800, height = 800;
	public int ticks, yMotion, xMotion, score;
	public Renderer renderer;
	public ArrayList<Rectangle> columns;
	
	public FlappyBird() {
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);
		rand = new Random();
		
		renderer = new Renderer();
		
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(width, height);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setVisible(true);
		jframe.setTitle("Flappy Bird");
		jframe.setResizable(false);
		
		
		columns = new ArrayList<Rectangle>();
		bird = new Rectangle(width/2 - 10, height/2 -10, 20, 20);
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		
		timer.start();
	}
	
	public void jump() {
		if(gameover) {
			bird = new Rectangle(width/2 - 10, height/2 -10, 20, 20);
			
			columns.clear();
			yMotion = 0;
			score = 0;
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			gameover = false;
		}
		if(!started) {
			started = true;
		}else if(!gameover){
			if(yMotion > 0) {
				yMotion = 0;
			}
			yMotion -= 10;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		ticks++;
		
		int speed = 10;
		if(started) {
			for(int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				column.x -= speed;
			}
			
			if(ticks%2==0 && yMotion < 15) {
				yMotion += 2;
			}
			
			for(int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				
				if(column.x + column.width < 0) {
					columns .remove(column);
					if(column.y == 0) {
						addColumn(false);
					}
				}
			}
			
			bird.y += yMotion;
			
			for(Rectangle column : columns) {
				
				if(column.y == 0 && bird.x + bird.width/2 > column.x + column.width/2-10 &&  bird.x + bird.width / 2 < column.x + column.width / 2 + 10) {
					score++;
				}
				if(column.intersects(bird)) { 
					gameover = true;
					
					if(bird.x<=column.x) {
						bird.x = column.x - bird.width;
					}else {
						if(column.y!=0) {
							bird.y = column.y;
						}else if(bird.y < column.height) {
							bird.y = column.height;
						}
					}
					
					
				}
			}
			
			if(bird.y > height - 120 || bird.y <0) {
				gameover = true;
			}
			
			if(bird.y + yMotion >= height - 120) {
				bird.y = height - 120 - bird.height;
			}
			
			if(gameover) {
				bird.y = height - 120 - bird.height;
			}
			
			
		}
		
		
		renderer.repaint();
	}
	
	public void addColumn(boolean start) {
		int space = 300;
		int WIDTH = 100;
		int HEIGHT = 50 + rand.nextInt(300);
		if(start) {
			columns.add(new Rectangle(width + WIDTH + columns.size()*300, height - HEIGHT - 120, WIDTH, HEIGHT));
			columns.add(new Rectangle(width + WIDTH + (columns.size() - 1)*300, 0, WIDTH, height - HEIGHT - space));
		}else {
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, height - HEIGHT - 120, WIDTH, HEIGHT));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, WIDTH, height - HEIGHT - space));
		}
		
		
	}
	
	public void paintColumn(Graphics g, Rectangle column) {
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}

	public static void main(String[] args) {
		flappybird = new FlappyBird();
	}

	public void repaint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.cyan);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.orange);
		g.fillRect(0, height-120, width, 120);
		
		g.setColor(Color.green);
		g.fillRect(0, height-120, width, 20);
		
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		for(Rectangle column : columns) {
			paintColumn(g, column);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));
		
		if(!started) {
			g.drawString("Click to start!", 100, height/2 - 50);
		}
		
		if(gameover) {
			g.drawString("Game Over!", 100, height/2 - 50);
		}
		
		if(!gameover && started) {
			g.drawString(String.valueOf(score), width/2 - 25, 100);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			jump();
		}
	}
	
}
