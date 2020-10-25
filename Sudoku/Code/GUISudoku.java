package Code;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.Font;

@SuppressWarnings("serial")
public class GUISudoku extends JFrame {

	private JPanel contentPane;
	private JLabel [][] gBoard;
	private int selected,secs;
	private Board board;
	private Timer timer;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUISudoku frame = new GUISudoku();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public GUISudoku() {
		board = new Board();
		gBoard =  new JLabel[board.getMax()][board.getMax()];
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUISudoku.class.getResource("/Images/logo.png")));
		setTitle("S\u016Bdoku");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 653, 450);
		
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new LineBorder(new Color(81, 81, 81), 5, true));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		borders();
		
		tabletop();
		
		timer();
		
		controls();
		
		tools();
		
		menu();		
	}
	
	private void borders() {
		//Creamos los bordes que separan cada uno de los cuadrados 3x3
		//Esto es simplemente visual
		for(int i=0;i<4;i++) {
			JLabel border = new JLabel();
			border.setToolTipText("");
			border.setBorder(new LineBorder(new Color(51, 51, 51), 4, true));
			switch(i){
				case 0:
					border.setBounds(25, 143, 360, 3);
				break;
				case 1:
					border.setBounds(25, 263, 360, 3);
				break;
				case 2:
					border.setBounds(143,25, 3, 360);
				break;
				case 3:
					border.setBounds(263, 25, 3, 360);
				break;
			}
			contentPane.add(border);
		}
	}
 	
	private void tabletop(){
		JPanel tabletop = new JPanel();
		
		tabletop.setLayout(new GridLayout(board.getMax(), board.getMax(), 3, 3));
		tabletop.setBorder(new LineBorder(new Color(81, 81, 81), 1, true));
		tabletop.setBackground(Color.DARK_GRAY);
		tabletop.setBounds(25, 25, 360, 360);
		contentPane.add(tabletop);
		
		for(int i=0;i<board.getMax();i++) 
			for(int j=0;j<board.getMax();j++) {
				gBoard[i][j] = new JLabel();
				JLabel label = gBoard[i][j];
				label.setHorizontalAlignment(SwingConstants.CENTER);
				MatrixCell cell = board.get(i, j);
				label.setIcon(cell.img());
				label.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						board.put(cell.getRow(), cell.getCol(), selected);
						if(!board.checkCell(cell.getRow(), cell.getCol())) 
							cell.setValue(10);	
						label.setIcon(cell.img());	
						if(board.checkBoard()) {
							timer.stop();
							JOptionPane.showMessageDialog(null, "Victory: You successfully solve the game", "You win!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(GUISudoku.class.getResource("/Images/start.png")));
						}
					}
				});
				tabletop.add(label);
			}
	}
 	
 	private void timer() {
 		JPanel clock = new JPanel();
 		JLabel [] gTimer = new JLabel[4];
 		JLabel timeLabel;
 		
 		clock.setBackground(Color.DARK_GRAY);
 		clock.setBounds(410, 25, 200, 35);
		contentPane.add(clock);
		clock.setLayout(null);
		clock.setBorder(new LineBorder(new Color(81, 81, 81), 3, true));
		
		//Creamos y agregamos los iconos a los Labels que van a formar nuestro Timer.
		for(int i=0; i<4; i++) {
			gTimer[i] = new JLabel();
			gTimer[i].setIcon(new ImageIcon(GUISudoku.class.getResource("/Images/0t.png")));
			clock.add(gTimer[i]);
			switch(i) {
			case 0:
				gTimer[i].setBounds(72, 3, 30, 28);
				break;
			case 1:
				gTimer[i].setBounds(102, 3, 30, 28);
				break;
			case 2:
				gTimer[i].setBounds(137, 3, 30, 28);
				break;
			case 3:
				gTimer[i].setBounds(165, 3, 30, 28);
			}		
		}
		
		timeLabel = new JLabel("Timer:          :");
		timeLabel.setBounds(10, 0, 190, 34);
		clock.add(timeLabel);
		timeLabel.setForeground(new Color(153, 102, 255));
		timeLabel.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 23));
		timeLabel.setHorizontalAlignment(SwingConstants.LEFT); 
		timer = new Timer (1000, new ActionListener (){
		    public void actionPerformed(ActionEvent e)
		    {	
		    	String timeInString = String.format("%02d%02d", 
		    		TimeUnit.SECONDS.toMinutes(secs),
		    		TimeUnit.SECONDS.toSeconds(secs) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(secs)));
		    	secs++;
		    	for(int i=0;i<4;i++) 
		 			gTimer[i].setIcon(new ImageIcon(GUISudoku.class.getResource("/Images/"+timeInString.charAt(i)+"t.png")));
		    	
		     }
		}); 
 	}
 	
 	private void controls() {
 		JPanel controls = new JPanel();
 
		controls.setBackground(Color.DARK_GRAY);
		controls.setBounds(410, 62, 200, 190);
		contentPane.add(controls);
		controls.setLayout(new GridLayout(3, 3, 0, 0));
		controls.setBorder(new LineBorder(new Color(81, 81, 81), 3, true));
		
		for(int i=0;i<board.getMax();i++) {
			JLabel num = new JLabel();
			num.setHorizontalAlignment(SwingConstants.CENTER);
			num.setIcon(new ImageIcon(GUISudoku.class.getResource("/Images/"+(i+1)+"m.png"),""+(i+1)));
			num.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					selected = Integer.parseInt(((ImageIcon)num.getIcon()).getDescription());
				}
			});
			controls.add(num);
		}	
 	}
 	
 	private void tools() {
 		JPanel tools = new JPanel();
 		JLabel eraser,back;

		tools.setBackground(Color.DARK_GRAY);
		tools.setBounds(410, 255, 200, 63);
		contentPane.add(tools);
		tools.setLayout(new GridLayout(1, 0, 25, 0));
		tools.setBorder(new LineBorder(new Color(81, 81, 81), 3, true));
 		
		eraser = new JLabel();
		eraser.setHorizontalAlignment(SwingConstants.RIGHT);
		eraser.setIcon(new ImageIcon(GUISudoku.class.getResource("/Images/eraser.png")));
		eraser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				selected = 0;
			}	
		});
		tools.add(eraser);
		
		back = new JLabel();		
		back.setHorizontalAlignment(SwingConstants.LEFT);
		back.setIcon(new ImageIcon(GUISudoku.class.getResource("/Images/back.png")));
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(board.getLastMove()!=null) {
					int r = board.getLastMove().getRow();
					int c = board.getLastMove().getCol();
					board.back();
					gBoard[r][c].setIcon(board.get(r, c).img());
				}
			}				
		});
		tools.add(back);	
	}	
 		
 	private void menu() {
 		JPanel menu = new JPanel();
		JLabel start,reset,howtoplay;
		
		menu.setBackground(Color.DARK_GRAY);
		menu.setBounds(410, 322, 200, 63);
		contentPane.add(menu);
		menu.setLayout(new GridLayout(0, 3, 0, 0));
		menu.setBorder(new LineBorder(new Color(81, 81, 81), 3, true));
		

		start = new JLabel();
		start.setHorizontalAlignment(SwingConstants.CENTER);
		start.setIcon(new ImageIcon(GUISudoku.class.getResource("/Images/start.png")));
		start.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				board.loadGame(new File("Sudoku\\Code\\game"));
				if(board.checkBoard()) {
					board.prepareGame();
					for(int i = 0; i<board.getMax(); i++)
						for(int j = 0;j<board.getMax(); j++) 
							gBoard[i][j].setIcon(board.get(i, j).img());
					timer.restart();
					secs = 0;
				}
				else
					JOptionPane.showMessageDialog(null, "it's seems we are having technical problems, sorry :'c", "Ups!, what have we broken?", 
								JOptionPane.INFORMATION_MESSAGE, new ImageIcon(GUISudoku.class.getResource("/Images/sad.png")));
			}
		});
		menu.add(start);
		
		reset = new JLabel();
		reset.setHorizontalAlignment(SwingConstants.CENTER);
		reset.setIcon(new ImageIcon(GUISudoku.class.getResource("/Images/reset.png")));
		reset.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				while(board.getLastMove()!=null) {
					int r = board.getLastMove().getRow();
					int c = board.getLastMove().getCol();
					board.back();
					gBoard[r][c].setIcon(board.get(r, c).img());			
				}
			}
		});
		menu.add(reset);
		
		howtoplay = new JLabel();
		howtoplay.setHorizontalAlignment(SwingConstants.CENTER);
		howtoplay.setIcon(new ImageIcon(GUISudoku.class.getResource("/Images/faq.png")));
		howtoplay.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {   
			        Desktop.getDesktop().browse(new URI("https://docs.google.com/document/d/1_TAuRxYsWpliOcAvNZvAqHbtsETSiC8NaSRdR1JGWeo/edit?usp=sharing"));        
			    } catch (IOException | URISyntaxException e1) {
			        e1.printStackTrace();
			    }
			}
		});
		menu.add(howtoplay);
 	}
 	
}