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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame{
	private HitBoard player1HitBoard,player2HitBoard;
	private FriendlyBoard player1Board,player2Board;
	private JButton PlaceShips;
	private ImageIcon tempIcon;
	private Image image;
	private Player player1,player2,currentPlayer,enemyPlayer;
	private Ship currentShip;
	private	JButton rotateShip = new JButton("Rotate");
	private JLabel hit,miss;
	
	public GUI(Player player1,Player player2){
		this.player1 = player1;
		this.player2 = player2;
		currentPlayer= player1;
		enemyPlayer = player2;
		//making players boards
		player1Board = new FriendlyBoard();
		player1Board.setBounds(50,50,300,300);
		player2Board = new FriendlyBoard();
		player2Board.setBounds(50,50,300,300);
		//making hit boards
		player1HitBoard = new HitBoard();
		player1HitBoard.setBounds(650,50,300,300);
		player2HitBoard = new HitBoard();
		player2HitBoard.setBounds(650,50,300,300);
		
		player2HitBoard.setVisible(false);
		player2Board.setVisible(false);
		
		//stin arxi gia placement apo ta ploia
		PlaceShips = new JButton("Place");
		PlaceShips.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//stamataei tous mouselisteners gia ploia placement
				if(currentPlayer == player2) {
				player2Board.enabled = false;
				PlaceShips.setVisible(false);
				rotateShip.setVisible(false);
				sizeset();
				}
				player1Board.enabled = false;
				changeTurn();
				currentPlayer.setPlacedShips(true);
			}
		});
		ImageIcon hitimg = new ImageIcon(new ImageIcon("images\\hit.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		hit = new JLabel(hitimg);
		hit.setBounds(400,200,150,150);
		hit.setVisible(false);
		
		ImageIcon missimg = new ImageIcon(new ImageIcon("images\\miss.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		miss = new JLabel(missimg);
		miss.setBounds(400,200,150,150);
		miss.setVisible(false);
		
		PlaceShips.setBounds(10,0,100,40);
		
		
		rotateShip.setBounds(200,0,100,40);
		
		this.add(hit);
		this.add(miss);
		this.add(rotateShip);
		this.setLayout(null);
		this.add(player1Board);
		this.add(player1HitBoard);
		this.add(player2Board);
		this.add(player2HitBoard);
		this.add(PlaceShips);
		this.setTitle("Battleships");
		this.setVisible(true);
		this.setSize(450,420);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	
	}
	
	public void sizeset() {
		this.setSize(1000,600);
	}
	//enemy Board
	class HitBoard extends JPanel{
		private boolean shipHit = false;
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
								for(Ship ship: enemyPlayer.getShips()) {
									if(ship.isHit(i, j)) {
										buttons[i][j].setBackground(Color.red);
										shipHit = true;
										hit.setVisible(true);
									}
								}
								if(!shipHit)
									miss.setVisible(true);
								if(currentPlayer==player1)
									player2Board.repaint();
								else
									player1Board.repaint();
								if(!enemyPlayer.hasShips())
									System.out.println("Game is over!");
								//250 milisecond delay
								new Timer().schedule( 
								        new TimerTask() {
								            @Override
								            public void run() {
								            	if(shipHit) 
								            		hit.setVisible(false);
								            	else
								            		miss.setVisible(false);
								            	shipHit=false;
								            	changeTurn();
								            }
								        }, 
								        250
								);
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
	
	public class FriendlyBoard extends JPanel{
		private boolean enabled = true;
		@Override
		public void paint(Graphics g) {
			for(int i=0; i<10; i++) {
				for(int j=0; j<10; j++) {
					//conditions gia repaint
					if(currentPlayer==player1) {
					if(player2HitBoard.getButton(i,j).getBackground()== Color.blue) {
					g.setColor(Color.blue);
					}
					else if(player2HitBoard.getButton(i,j).getBackground()== Color.red){
						g.setColor(Color.red);
					}
					else {
						g.setColor(Color.gray);
					}
					g.fillRect(j*30, i*30, 30, 30);
					g.setColor(Color.black);
					g.drawRect(j*30, i*30, 30, 30);
					}
					else {
						if(player1HitBoard.getButton(i,j).getBackground()== Color.blue) {
							g.setColor(Color.blue);
							}
							else if(player1HitBoard.getButton(i,j).getBackground()== Color.red){
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
			}
			for(Ship ship: currentPlayer.getShips()) {
				if(ship.vertical==true)
			tempIcon = new ImageIcon("images\\" + ship.getName() + ".png");
				else
					tempIcon = new ImageIcon("images\\Rotated" + ship.getName() + ".png");
			image = tempIcon.getImage();
				if(ship.vertical==true)
			image = getScaledImage(image,30,ship.getLength()*30);
			else
				image = getScaledImage(image,ship.getLength()*30,30);
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawImage(image,ship.getY(),ship.getX(),this);
			}
			
			addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent e) {
					//+30 gia na kaluptei olo to block
					int Ypos=e.getY();
					int Xpos=e.getX();

					System.out.println("Y:"+e.getY());
					System.out.println("X:"+e.getX());
					
				//An o xristis kanei klik panw se kouti pou brisketai ploio tha epilegetai to ploio
														//30(megethos koutiou)* twn arithmo twn koutiwn pou brisketai to ploio
														//analoga me to megethos tou ploiou(length)
				
				rotateShip.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						
					if(currentShip.vertical==true) {	
						currentShip.vertical=false;
						if(Ypos>=currentShip.getY()*30 && Ypos<=currentShip.getY()+30*currentShip.getLength()) {
							currentShip.setYpos(Ypos-(30*(currentShip.getLength()-1)));
							currentShip.move(currentShip.getY()/30, currentShip.getX()/30);
						}
						repaint();
					}
					else if(currentShip.vertical==false) {
						currentShip.vertical=true;
						if(Xpos>=currentShip.getX()*30 && Xpos<=currentShip.getX()+30*currentShip.getLength()) {
							currentShip.setYpos(Ypos+(30*(currentShip.getLength()-1)));
							currentShip.move(currentShip.getY()/30, currentShip.getX()/30);
							
						}
						repaint();
						System.out.println(currentShip.getY());
						System.out.println(currentShip.getX());
					}
					
					
										
					}
				
				});
				}

				@Override
				public void mouseEntered(MouseEvent e) {}

				@Override
				public void mouseExited(MouseEvent e) {}

				@Override
				public void mousePressed(MouseEvent e) {
					if(enabled == true) {
					for(Ship ship: currentPlayer.getShips()) {
						if ((e.getX()>=ship.getY()) && (e.getX()<=ship.getY()+30) && e.getY()>=ship.getX() && e.getY()<=ship.getX() + (ship.getLength()*30)) {
							currentShip = ship;
						}
					}
				}
				}
				
	

				@Override
				public void mouseReleased(MouseEvent e) {
					//currentShip=null;
				}
				
			});
			addMouseMotionListener( new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if(enabled == true) {
					if(currentShip!=null) {
						if(e.getX()<300 && e.getY()<300 && e.getX()>=0 && e.getY()>=0) {
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
	private void changeTurn() {
		if(currentPlayer == player2) {
			player1HitBoard.setVisible(true);
			player1Board.setVisible(true);
			player2HitBoard.setVisible(false);
			player2Board.setVisible(false);
			currentPlayer = player1;
			enemyPlayer = player2;
			}
			else {
			player1HitBoard.setVisible(false);
			player1Board.setVisible(false);
			player2HitBoard.setVisible(true);
			player2Board.setVisible(true);
			currentPlayer = player2;
			enemyPlayer = player1;
			}
	}
	
}