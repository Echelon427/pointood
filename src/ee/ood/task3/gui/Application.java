/**
 * 
 */
package ee.ood.task3.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ee.ood.task3.businesslogic.planets.SpaceShip;
import ee.ood.task3.businesslogic.planets_fascade.PSController;

/**
 * @author Jaroslav Judin
 * Mar 19, 2010
 */
@SuppressWarnings("serial")
public class Application extends JFrame {
	
	private JPanel drawPanel;
	private JPanel buttonPanel;
	private PSController controller;
	private ArrayList<Point> po_dict;
	//private ArrayList<Point> no_dict;
	private final int planet_width = 3;
	private final int zoom = 6;
	private final int auto_speed = 100;
	private boolean auto_on = false;
	private final int cx = 400;
	private final int cy = 350;
	
	public Application(String name) {
		super(name);
		
		controller = new PSController();
		po_dict = new ArrayList<Point>();
		//no_dict = new ArrayList<Point>();
		
		drawPanel = new JPanel();
		buttonPanel = new JPanel();
		
		this.setVisible(true);
		this.createWidgets();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.make_solar_system();
	}
	
	public void tick() {
		this.controller.tick();
		this.move_planets();
	}
	
	public void tick100() {
		this.controller.multi_tick(100);
		this.move_planets();
	}
	
	public void auto() {
		this.auto_on = !this.auto_on;
		this.autoTick();
	}
	
	public void autoTick() {
		Timer t = new Timer();
		if(auto_on){
			this.tick();
			TimerTask tt= new TimerTask() {
				public void run() {
					autoTick();	
				}
			};
			t.schedule(tt, auto_speed);
		}
	}
		
	private void make_solar_system() {
		this.controller.make_solar_system();
		this.delete_planets();
		this.draw_planets();
	}
	
	public void paint(Graphics g) {       		
        //super.paint(g);
		for(int i=0; po_dict.size()>0 && i<1; i++) {
			drawPanel.paint(g);
	        drawLines(drawPanel.getGraphics());
		}
	}
	
	private void drawLines(Graphics g) {		
		g.setColor(Color.yellow);
		g.fillOval(cx, cy, planet_width, planet_width);
		g.setColor(Color.white);
		for(int i=0; i<po_dict.size(); i++)
			g.fillOval(po_dict.get(i).x, po_dict.get(i).y, planet_width, planet_width);
		//erase trace
		g.setColor(Color.black);
		//for(int i=0; i<no_dict.size(); i++)
			//g.fillOval(no_dict.get(i).x, no_dict.get(i).y, planet_width, planet_width);
	}

	private void createWidgets() {
		drawPanel.setBackground(Color.black);
		buttonPanel.setBackground(Color.gray);
		buttonPanel.setLayout((new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)));
		this.setLayout(new BorderLayout());
		this.setSize(cx*2, cy*2);
		
		JButton bTick = new JButton("Tick");
		JButton bAuto = new JButton("Auto");
		JButton b100 = new JButton("Tick100");
		JButton bDefault = new JButton("Solar System");
		JButton bLaunch = new JButton("Launch");
		
		bTick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		bAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				auto();
			}
		});
		b100.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick100();
			}
		});
		bDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				make_solar_system();
			}
		});
		bLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launch();
			}
		});
		buttonPanel.add( bTick );
		buttonPanel.add( b100 );
		buttonPanel.add( bAuto );
		buttonPanel.add( bDefault );
		buttonPanel.add( bLaunch );
		
		this.getContentPane().add(BorderLayout.CENTER,drawPanel);
		this.getContentPane().add(BorderLayout.EAST,buttonPanel);
	}
	
	public Point conv_coord(double x, double y) {
		int x0 = (int) (this.cx + x * this.zoom);
		int y0 = (int) (this.cy + y * this.zoom);
		return new Point(x0,y0);
	}
	
	public void draw_planets() {
		for(int id=0; id<this.controller.system().len(); id++)
			draw_planet(id, controller.system().get(id).x(), controller.system().get(id).y());
		super.paint(getGraphics());
	}
	
	public void draw_planet(int planet_id, double x, double y) {
		Point coord = this.conv_coord(x, y); 
		this.po_dict.add(planet_id, coord);
	}
	
	public void move_planets() {
		//no_dict.clear();
		//no_dict.addAll(po_dict);
		for(int i=0; i < controller.system().len(); i++)
			move_planet(i, controller.system().get(i).x(), controller.system().get(i).y());
		paint(getGraphics());
	}
	
	public void move_planet(int planet_id, double x, double y) {
		Point coord = this.conv_coord(x, y); 
		this.po_dict.set(planet_id, coord);
	}
	
	public void delete_planets() {
		po_dict.clear();
		repaint();
	}
	
	public void launch() {
		SpaceShip ship = controller.launch(5, -0.5, -0.5);
		int id = this.controller.system().len() - 1;
		draw_planet(id, ship.x(), ship.y());
		paint(getGraphics());
	}
	
	/*---------------------------------------
	 * --------------MAIN--------------------
	 * -------------------------------------*/
	public static void main(String[] args) {
		new Application("Planet System");	
	}
}