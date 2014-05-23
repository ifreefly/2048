import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;


public class Game extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int rows=4,columns=4;
	private JPanel gridPanel=new JPanel();
	private JLabel[][] label=new JLabel[rows][columns];
	private int[][] map=new int[rows][columns];
	private JToolBar toolBar=new JToolBar();
	private int[] randomArray=new int[]{2,2,2,2,2,2,2,2,2,4};
	private boolean merge=false,isSetNumber=false;
	private int free=16;
	private int max=0;
	public Game(){
		setLayout(new BorderLayout());
		Action restart=new AbstractAction("restart"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				initGame();
			}
			
		};
		toolBar.add(restart);
		add(toolBar,BorderLayout.NORTH);
		add(gridPanel);
		placeLabel();
		initGame();
		setVisible(true);
		setSize(400,400);
		setResizable(false);
		//pack();
		addKeyListener(new KL());
		requestFocus(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void initGame(){
		merge=false;
		isSetNumber=false;
		free=16;
		max=0;
		initMap();
		setRandomNumber();
		setRandomNumber();
	}
	private void placeLabel(){
		gridPanel.setLayout(new GridLayout(4,4,2,2));
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				label[i][j]=new JLabel();
				//label[i][j].setBorder(BorderFactory.createLineBorder(Color.CYAN));
				label[i][j].setOpaque(true);
				label[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				//label[i][j].setBackground(Color.decode("#999999"));
				setLabelBackground(i,j,map[i][j]);
				label[i][j].setFont(new Font(label[i][j].getFont().getName(), Font.PLAIN, 32));
				gridPanel.add(label[i][j]);
			}
		}
	}
	
	private void initMap(){
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				label[i][j].setText("");
				setLabelBackground(i,j,0);
				map[i][j]=0;
			}
		}
	}
	
	private void setRandomNumber(){
		int pos=(int) (Math.random()*(randomArray.length)+0);
		int x=(int) (Math.random()*rows+0);
		int y=(int) (Math.random()*rows+0);
		while(0!=map[x][y] && free>0){
			x=(int) (Math.random()*rows+0);
			y=(int) (Math.random()*rows+0);
		}
		map[x][y]=randomArray[pos];
		label[x][y].setText(String.valueOf(map[x][y]));
		setLabelBackground(x,y,map[x][y]);
		free--;
		//System.out.println(free);
		
	}
	
	private void setLabelBackground(int row,int column,int value){
		switch(value){
			case 0:{label[row][column].setBackground(Color.decode("#999999"));};break;
			case 2:{label[row][column].setBackground(Color.decode("#FFFFCC"));};break;
			case 4:{label[row][column].setBackground(Color.decode("#FFFF99"));};break;
			case 8:{label[row][column].setBackground(Color.decode("#FFFF66"));};break;
			case 16:{label[row][column].setBackground(Color.decode("#FFCC66"));};break;
			case 32:{label[row][column].setBackground(Color.decode("#FFCC33"));};break;
			case 64:{label[row][column].setBackground(Color.decode("#FFCC00"));};break;
			case 128:{label[row][column].setBackground(Color.decode("#FF9966"));};break;
			case 256:{label[row][column].setBackground(Color.decode("#FF9933"));};break;
			case 512:{label[row][column].setBackground(Color.decode("#FF9900"));};break;
			case 1024:{label[row][column].setBackground(Color.decode("#FF3366"));};break;
			case 2048:{label[row][column].setBackground(Color.decode("#FF3333"));};break;
			case 4096:{label[row][column].setBackground(Color.decode("#FF3300"));};break;
			case 8192:{label[row][column].setBackground(Color.decode("#FF0000"));};break;
		}
	}
	
	class KL extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			switch (e.getKeyCode()){
				case KeyEvent.VK_UP:{
					for(int j=0;j<columns;j++){
						for(int i=0;i<rows;i++){
							for(int row=i+1;row<rows;row++){
								if(0==map[row][j]){
									continue;
								}else if(0!=map[row][j]){
									if(map[i][j]==0){
										merge=true;
									}else if(map[i][j]==map[row][j]){
										free++;
										merge=true;
									}else{
										merge=false;
										break;
									}
								}
								if(merge){
									mergeNumber(i,j,row,j);
								}
								updateMax(map[i][j]);
							}
						}
					}
					//System.out.println("Up");
				};break;
				case KeyEvent.VK_DOWN:{
					for(int j=0;j<columns;j++){
						for(int i=rows-1;i>=0;i--){
							for(int row=i-1;row>=0;row--){
								if(0==map[row][j]){
									continue;
								}else if(0!=map[row][j]){
									if(map[i][j]==0){
										merge=true;
									}else if(map[i][j]==map[row][j]){
										free++;
										merge=true;
									}else{
										merge=false;
										break;
									}
								}
								if(merge){
									mergeNumber(i,j,row,j);
								}
								updateMax(map[i][j]);
							}
						}
					}
					//System.out.println("Down");
				};break;
				case KeyEvent.VK_LEFT:{
					for(int i=0;i<rows;i++){
						for(int j=0;j<columns;j++){
							for(int column=j+1;column<columns;column++){
								if(0==map[i][column]){
									continue;
								}else if(0!=map[i][column]){
									if(map[i][j]==0){
										merge=true;
									}else if(map[i][j]==map[i][column]){
										free++;
										merge=true;
									}else{
										merge=false;
										break;
									}
								}
								if(merge){
									mergeNumber(i,j,i,column);
								}
								updateMax(map[i][j]);
							}
						}
					}
					//System.out.println("Left");
				};break;
				case KeyEvent.VK_RIGHT:{
					for(int i=0;i<rows;i++){
						for(int j=columns-1;j>=0;j--){
							for(int column=j-1;column>=0;column--){
								if(0==map[i][column]){
									continue;
								}else if(0!=map[i][column]){
									if(map[i][j]==0){
										merge=true;
									}else if(map[i][j]==map[i][column]){
										free++;
										merge=true;
									}else{
										merge=false;
										break;
									}
								}
								if(merge){
									mergeNumber(i,j,i,column);
								}
								updateMax(map[i][j]);
							}
						}
					}
					//System.out.println("Right");
					};break; 
				}
				if(isSetNumber){
					setRandomNumber();
					isSetNumber=false;
				}
			result();
		}
	}
	
	private void mergeNumber(int i,int j,int row,int column){
		map[i][j]=map[i][j]+map[row][column];
		map[row][column]=0;
		label[i][j].setText(String.valueOf(map[i][j]));
		label[row][column].setText("");
		setLabelBackground(i,j,map[i][j]);
		setLabelBackground(row,column,map[row][column]);
		isSetNumber=true;
	}
	
	private void updateMax(int number){
		if(number>max){
			max=number;
		}
	}
	
	private boolean canMerge(){
		//boolean bMerge=false;
		for(int j=0;j<columns;j++){//up
			for(int i=0;i<rows;i++){
				for(int row=i+1;row<rows;row++){
					if(0==map[row][j]){
						continue;
					}else if(0!=map[row][j]){
						if(map[i][j]==0){
							return true;
						}else if(map[i][j]==map[row][j]){
							return true;
						}else{
							break;
						}
					}
				}
			}
		}
		//System.out.println("canMerge left");
		
		for(int i=0;i<rows;i++){//left
			for(int j=0;j<columns;j++){
				for(int column=j+1;column<columns;column++){
					if(0==map[i][column]){
						continue;
					}else if(0!=map[i][column]){
						if(map[i][j]==0){
							return true;
						}else if(map[i][j]==map[i][column]){
							return true;
						}else{
							break;
						}
					}
				}
			}
		}
		//System.out.println("canMerge rigtht");
		return false;
	}
	
	private void result(){
		if(2048==max){
			JOptionPane.showMessageDialog(null, "YOU WIN!", "Result", JOptionPane.INFORMATION_MESSAGE,null);
		}else if(!canMerge() && free<=0){
			JOptionPane.showMessageDialog(null, "FAILED!", "Result", JOptionPane.INFORMATION_MESSAGE,null);
			initGame();
		}
	}
	
	public static void main(String args[]){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {   
		      public void run() {   
		        new Game();   
		      }   
		    }); 
	}

}
