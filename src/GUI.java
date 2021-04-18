import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame{
	private Board enemyBoard,yourBoard;
	private JButton[][] buttons;
	
	public GUI(){
		//making your board(den kanei tipota pros to parwn mono sto deutero to antipalo board akoune ta buttons... logika tha ginei kapws alliws to board tou paikti)
		yourBoard = new Board();
		yourBoard.setBounds(50,50,300,300);
		//making enemy board
		enemyBoard = new Board();
		enemyBoard.setBounds(650,50,300,300);
		
		this.setLayout(null);
		this.add(yourBoard);
		this.add(enemyBoard);
		this.setTitle("Battleships");
		this.setVisible(true);
		this.setSize(1000,600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	//enemy Board
	class Board extends JPanel{
		public Board() {
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
								System.out.println(i + " " +j);
							}}}}	
			});
			add(buttons[i][j]);
			}
		}
		}
	}
}
