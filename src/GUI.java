import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame{
	private HitBoard enemyBoard;
	private FriendlyBoard yourBoard;
	private JButton PlaceShips;
	private ImageIcon tempIcon;
	private Image image;
	private boolean enabled = true;
	private Ship[] ships;
	private Ship currentShip;
	
	public GUI(Ship[] ships){
		this.ships = ships;
		//making your board(den kanei tipota pros to parwn mono sto deutero to antipalo board akoune ta buttons... logika tha ginei kapws alliws to board tou paikti)
		yourBoard = new FriendlyBoard();
		yourBoard.setBounds(50,50,300,300);
		//making enemy board
		enemyBoard = new HitBoard();
		enemyBoard.setBounds(650,50,300,300);
		
		//stin arxi gia placement apo ta ploia
		PlaceShips = new JButton("Place");
		PlaceShips.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//stamataei tous mouselisteners gia ploia placement
				enabled=false;
				PlaceShips.setVisible(false);
			}
		});
		PlaceShips.setBounds(450,500,100,40);
		
		
		this.setLayout(null);
		this.add(yourBoard);
		this.add(enemyBoard);
		this.add(PlaceShips);
		this.setTitle("Battleships");
		this.setVisible(true);
		this.setSize(1000,600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	//enemy Board
	class HitBoard extends JPanel{
		private JButton[][] buttons;
		public HitBoard() {
		setLayout(new GridLayout(10,10));
		buttons = new JButton[10][10];
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
			buttons[i][j] = new JButton();
			buttons[i][j].setBackground(Color.blue);
			buttons[i][j].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//finds coordinates
					for(int i=0; i<10; i++) {
						for(int j=0; j<10; j++) {
							if(buttons[i][j]==e.getSource()) {
								//tipota endiaferon prws to parwn
								buttons[i][j].setEnabled(false);
								buttons[i][j].setBackground(Color.gray);
								for(Ship ship: ships) {
									if(ship.isHit(i, j)) {
										buttons[i][j].setBackground(Color.red);
									}
								}
								yourBoard.repaint();
							}}}}	
			});
			add(buttons[i][j]);
			}
		}
		}
		public JButton getButton(int i,int j) {
			return buttons[i][j];
		}
		
	}
	
	class FriendlyBoard extends JPanel{
		@Override
		public void paint(Graphics g) {
			for(int i=0; i<10; i++) {
				for(int j=0; j<10; j++) {
					//conditions gia repaint
					if(enemyBoard.getButton(i,j).getBackground()== Color.blue) {
					g.setColor(Color.blue);
					}
					else if(enemyBoard.getButton(i,j).getBackground()== Color.red){
						g.setColor(Color.red);
					}
					else {
						g.setColor(Color.gray);
					}
					g.fillRect(j*30, i*30, 30, 30);
					g.setColor(Color.black);
					g.drawRect(j*30, i*30, 30, 30);
				}
			}
			for(Ship ship: ships) {
			tempIcon = new ImageIcon("images\\" + ship.getName() + ".png");
			image = tempIcon.getImage();
			image = getScaledImage(image,30,ship.getLength()*30);
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawImage(image,ship.getY(),ship.getX(),this);
			}
			
			addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {}

				@Override
				public void mouseExited(MouseEvent e) {}

				@Override
				public void mousePressed(MouseEvent e) {
					if(enabled == true) {
					for(Ship ship: ships) {
						if ((e.getX()>=ship.getY()) && (e.getX()<=ship.getY()+30)) {
							currentShip = ship;
						}
					}
				}
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					currentShip=null;
				}
				
			});
			addMouseMotionListener( new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if(enabled == true) {
					if(currentShip!=null) {
						if(e.getX()<=300 && e.getY()<=300 && e.getX()>=0 && e.getY()>=0) {
						currentShip.move(e.getY()/30, e.getX()/30);
						repaint();
						}
					}
				}
				}

				@Override
				public void mouseMoved(MouseEvent e) {}
			});
		}
		
		//kanei resize to image gia na xwraei sta cell tou board(1 cell = 30x30)
				private Image getScaledImage(Image srcImg, int w, int h){
				    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				    Graphics2D g2 = resizedImg.createGraphics();

				    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				    g2.drawImage(srcImg, 0, 0, w, h, null);
				    g2.dispose();

				    return resizedImg;
				}
	}
}