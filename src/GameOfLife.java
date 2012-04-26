import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

/**
 * A graphical component which plays  GameOfLife
 * with teh rule of the following rules
 *1 a live cell will be alive in the next generation if only it has
 *2 or 3 alive neighbours
 *
 * @author  Seid Muhie
 */

public class GameOfLife   extends JComponent
    implements MouseListener, ActionListener
{
    /*---------- Private constants ----------*/
    private static final int NUM_ROW =59;
    private static final int NUM_COLM =59;
    /**
     *special constants representing the current state of a cell
     */
    private static final boolean CELL_DEAD = false;
    private static final boolean CELL_ALIVE = true;
   /*---------- Private instance variables ----------*/

    /** 2-dimensional NUM_RO x nUM_COLM array storing the current state of the
     *grids
     *and number of died or alive neighbour grides
    */
   private byte[][] grid = new byte[NUM_ROW][NUM_COLM];
   private boolean[][] Nbgrid = new boolean[NUM_ROW][NUM_COLM];
       /** A Timer object configured to call this class's actionPerformed() method
        once .1 second. */

       private Timer timer = new Timer(10, this);

    /*---------- Public constructors ----------*/

    /**
     * Constructs a new GameOfLife graphical component. 
     */
    public GameOfLife ()
    {
        setForeground(Color.cyan);
        setBackground(Color.black);
        setPreferredSize(new Dimension(600,600));

        /* Tells the windowing system to call this object's MouseListener
           methods (mouseClicked(), etc - below) when the appropriate event
           occurs, so that the object can react accordingly: 
           */
        addMouseListener(this);
    }
    /*---------- Public MouseListener methods ----------*/

    /**
     * Called by the windowing system whenever the mouse is clicked (pressed
     * and released) inside the rectangle drawn
     *
     * @param  e  object containing info about the event, eg, where the mouse
     * was clicked, which button was pressed, etc.
     */
    public void mouseClicked(MouseEvent e)
    {
    	int x =(int)e.getX();
        int y =(int) e.getY();
        int h,w;
        /**
         *the mouse may be clicked at position 13,17
         *but since the rectangle is 10 by 10 it should start always from
         *tens digit i.e 0,10,20,30...
         *so we have to change the 13 and 17 to the nearest lower tens digit
         *that is both have to start from 10,10
         *i do this by the method of modulo
         *any number % give as always ones digit and we have to subtract this 
         *remiander to have the nearest lower tens digit
         *13%10=3
         *17%10=7
         *13-3=10,17-7=10
         *it paints the rectangele at 10,10
         */ 
        h=x%10;
        w=y%10;
        x=x-h;
        y=y-w;
        /**
         *since the painting is 10 by 10 we have to devide it by 10
         *so if the mouse is clicked at this position
         *then a dead cell will be alive and  but an alive cell becomes dead
         *and we have to check if the click is not out of the rectangle 
         */
         if(x/10<NUM_ROW&&y/10<NUM_COLM){
         	if(Nbgrid[x/10][y/10]==CELL_DEAD){
         		Nbgrid[x/10][y/10]=CELL_ALIVE;
         		}else{
         			if(Nbgrid[x/10][y/10]==CELL_ALIVE) 	
         			Nbgrid[x/10][y/10]=CELL_DEAD; 
         		}
        /* Tell the windowing system to redraw this TicTacToe component: */
         repaint();
      }
    }

    /**
     * Called by the windowing system when the mouse moves within the bounds of
     * the gameOfLife rectangle.  Ignored.
     *
     * @param  e  contains event info such as where the mouse was clicked.
     */
    public void mouseEntered(MouseEvent e)
    {
        /* NOP (do nothing) */
    }

    /**
     * Called by the windowing system when the mouse leaves the bounds of the
     * GameOfLife Ignored.
     *
     * @param  e  contains event info such as where the mouse was clicked.
     */
    public void mouseExited(MouseEvent e)
    {
        /* NOP */
    }

    /**
     * Called by the windowing system when the mouse is pressed within the
     * GameOfLife.  Ignored.
     *
     * @param  e  contains event info such as where the mouse was clicked.
     */
    public void mousePressed(MouseEvent e)
    {
        /* NOP */
    }

    /**
     * Called by the windowing system when the mouse is released within the
     * GameOfLife. Ignored.
     *
     * @param  e  contains event info such as where the mouse was clicked.
     */
    public void mouseReleased(MouseEvent e)
    {
        /* NOP */
    }

    /*---------- Public ActionListener methods ----------*/

    /**
     * Called by the windowing system whenever an ActionEvent occurs for which
     * this object has registered itself as an ActionListener.  Specifically, a
     * GameOfLife registers itself as an ActionListener of a Timer object, which
     * just triggers events once every second.  So this method will be called
     * by the timer (when the timer is active) once every second.
     *
     * @param  e  object containing info about the event - in this case
     * ignored.
     */
  	  public void actionPerformed(ActionEvent e)
  	  {
    	nextgen();
       	repaint();
      
    
  }
  /**
   *initialy all the grides are dead
   */
    protected void initlife1()
    {
        System.out.println("Call to initGame()");
        for (int r = 0;  r <NUM_ROW; ++r) {
            for (int c = 0;  c <NUM_ROW; ++c) {
                Nbgrid[r][c] = CELL_DEAD;
                }
        }
    }
    /** initalizing  all the grides to have value 0
     *all gride will be initialy dead because all thier neighbours are dead
     *(=0) in fututre it will be aliev if it has more than 3 neighbours alive
     */
     protected void initlife12()
    {
        System.out.println("Call to initGame()");
        for (int r = 0;  r <NUM_ROW; ++r) {
            for (int c = 0;  c <NUM_ROW; ++c) {
                grid[r][c] = 0;
                }
        }
    }
    /**
     *Panel is the simplest container class. A panel provides space in 
     *which an application can attach any other component,
     * including other panels
     *here the panel returned with packed Buttons(i.e start,stop,clear,autmatic
     *and random
     *and next
     */
    public JPanel control(){
	JPanel panel=new JPanel();
	JButton start=new JButton("Start ");
     start.setBackground(Color.green);
     JButton next=new JButton("Next");
	next.setBackground(Color.yellow);   
	JButton automatic=new JButton("automatic");
	automatic.setBackground(Color.cyan);
	JButton random=new JButton("random");
	random.setBackground(Color.magenta);
    JButton clear=  new JButton("Clear");
    clear.setBackground(Color.pink);
    JButton stop=  new JButton("Stop");
    stop.setBackground(Color.red);
     panel.add(start);
     panel.add(next);
     panel.add(automatic);
     panel.add(random);
     panel.add(clear);
     panel.add(stop);
      /** 
      *rondomly fill the grides 
      */
      random.addActionListener(
	  new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					random();
				}
			}
		);
     /** 
      *rondomly fill the grides with predefined shapes
      */
      automatic.addActionListener(
	  new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					randomly();
				}
			}
		);
      /**
       * shows what will be the next generation
       *with repeated click you can see the next generation
       *till the cell died or be in aconstant state
       */  
     next.addActionListener(
	  new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nextgen();
				}
			}
		);
		/**
		 *immaidetly stops the runnig state if it has started to show you
		 *with a given pause here 1 for every next generration
		 */
	 stop.addActionListener(
	 	new ActionListener() {
	 		public void actionPerformed(ActionEvent e) {
	 			timer.stop();
			}
		}
		);
		/**
		 *when a button clear clicked it change the state of all grides to 
		 *dead
		 */
		 clear.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					timer.stop();
					clear();
					
				}
			}
		);
		/**starts to show you the next state without a click on
		 *next button
		 */
         start.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					timer.start();
				}
			}
		);
     return panel;   
	}  
	/** 
	 *clears all the grides  dead
	 */
	   public void clear(){
	initlife1();
	repaint();
	}
 public void random(){
 	for(int r=NUM_ROW/2;r>NUM_ROW/6;r--){
 		for(int c=NUM_COLM/2;c>NUM_COLM/6;--c){
 			int choice = (int)(Math.random() * (NUM_ROW/2));
 	        Nbgrid[c][choice]=CELL_ALIVE;
 	       Nbgrid[choice][c]=CELL_DEAD; 
 	    }
 	 }
 	repaint(); 
 	}
 	/**
 *counting the number of gridges who have a cell alive
 *it counts all of its 8 neigbours which are alive
 *if there are exactly 3 alive neighbours it will alive in the next generation
 *a cell needs 2 alive neighbours to be alive in the next generation
 */
    public void nextgen(){ 
      for( int i=0;i<NUM_ROW;++i)
      for(int  j=0;j<NUM_COLM;++j)
      grid[i][j]=0;
      for(int r=0;r<NUM_ROW;++r){
      	for(int c=0;c<NUM_COLM;++c){
      		/**
      		 *if the current cell is alive then it implies that all of its 
      		 *neighbour has this cell as an alive. so every cell record that
      		 *it has atleast one alive cell and prayes for other 2
      		 *neigbours to be alive .in case it exeeds 3 it will again
      		 * die. a cell currently alive needs only 2 cells to be alive
      		 */
      		if(Nbgrid[r][c]==CELL_ALIVE){
      			/**
			    *if the gride is  right most first cell
			    *it will not be checked for  neghbours above  it
			    *it has only 3 neighbours 
			    */
			    if(r==0&&c==0){
			    	grid[r][c+1]+=1;
			        grid[r+1][c+1]+=1;
			        grid[r+1][c]+=1;
			       }
			       /**
			        *checks the right most bottom gride
			        *it has als only 3 neighbours
			        */
			    else if(r==NUM_ROW-1&&c==NUM_COLM-1){
			       	grid[r-1][c-1]+=1;
			 	    grid[r-1][c]+=1;
				    grid[r][c-1]+=1;
				    /**
				     *checks the right most grides
				     *it has als only 3 neighbours
				     */ 
				}
				else if(r==0&&c==NUM_COLM-1){
					grid[r][c-1]+=1;
			 	    grid[r+1][c-1]+=1;
				    grid[r+1][c]+=1;
				    /**
				     *checks for the left most bottom gride
				     *it has also only 3 neighbours
				     */
			   }
			   else if(c==0&&r==NUM_ROW-1){
			 	    grid[r-1][c]+=1;
			 	    grid[r-1][c+1]+=1;
				    grid[r][c+1]+=1;
				    /**
				     *checks the last row
				     *eache cell have 5 neighbours
				     */
			   }
			   else if(r==NUM_ROW-1&&c<NUM_COLM-1&&c>0){
			   		grid[r][c-1]+=1;
				    grid[r][c+1]+=1;
				    grid[r-1][c+1]+=1;
				    grid[r-1][c-1]+=1;
				    grid[r-1][c]+=1;
				    /**
				     *checks the last column
				     *it also have 5 neighbours
				     */
			  }
			  else if(c==NUM_COLM-1&&r<NUM_ROW-1){
			     grid[r-1][c]+=1;
				 grid[r-1][c-1]+=1;
				 grid[r][c-1]+=1;
				 grid[r+1][c-1]+=1;
				 grid[r+1][c]+=1;
				 /**
				  *it checks the first row
				  *again it has 8 neighbours
				  */
			 }
			 else if(r==0&&c<NUM_COLM-1&&c>0){//r<NUM_ROW-1){
			   	grid[r][c-1]+=1;
				grid[r+1][c-1]+=1;
				grid[r][c+1]+=1;
				grid[r+1][c+1]+=1;
				grid[r+1][c]+=1;
				/**
				 *checks for the first row
				 *it has 5 neighbours
				 */				    
            }  
			 else if(c==0&&r<NUM_ROW-1&&r>0){//r<NUM_ROW-1){
				 grid[r-1][c+1]+=1;
				 grid[r-1][c]+=1;
				 grid[r][c+1]+=1;
				 grid[r+1][c+1]+=1;
				 grid[r+1][c]+=1;
				 /** it check all the interior cells
				  *all the interior cell have exactly 8 neighebours
				  */
		    }else if(r<NUM_ROW-1&&c<NUM_COLM-1){
				 grid[r-1][c+1]+=1;
				 grid[r-1][c]+=1;
				 grid[r][c+1]+=1;
				 grid[r+1][c+1]+=1;
				 grid[r-1][c-1]+=1;
				 grid[r+1][c-1]+=1;
				 grid[r][c-1]+=1;
				 grid[r+1][c]+=1;
			}
	     }
	   }
     }
	for(int r=0;r<NUM_ROW;++r){
		for(int c=0;c<NUM_COLM;++c){
			if(grid[r][c]==2&&Nbgrid[r][c]==CELL_ALIVE){}
			else if(grid[r][c]==3){
				Nbgrid[r][c]=CELL_ALIVE;
				}
				else {
					Nbgrid[r][c]=CELL_DEAD;
				}
			}
		}
		repaint();
	}
	/**randomly paint the cell acoording to the random choice
	 *from 6 given choices
	 *each choices are done in the way to give different shapes
	 */
	 public void randomly(){
	 	int choice = (int)(Math.random() * (6));
	    switch(choice){
	    	case 1:
		    for(int r=0;r<NUM_ROW;r++)
		    for(int c=0;c<NUM_COLM;c++)
		    Nbgrid[3][2]=Nbgrid[4][2]=Nbgrid[5][2]=Nbgrid[5][3]=Nbgrid[5][4]=CELL_ALIVE;
		    Nbgrid[4][4]=Nbgrid[3][4]=Nbgrid[3][3]=Nbgrid[3][5]=Nbgrid[3][6]=CELL_ALIVE;
		    Nbgrid[3][7]=Nbgrid[2][7]=Nbgrid[1][7]=CELL_ALIVE;
		    break;
		    case 2:
		    for(int i=NUM_ROW-1;i>0;--i)
		    Nbgrid[0][i]=CELL_ALIVE;
		    for (int i=0;i<NUM_ROW;++i)
		    Nbgrid[i][NUM_ROW-1]=CELL_ALIVE;
	        break;
	        case 3:
	        Nbgrid[10][10]=Nbgrid[10][11]=Nbgrid[10][12]=Nbgrid[11][11]=Nbgrid[12][11]=CELL_ALIVE;
		    Nbgrid[13][11]=Nbgrid[14][11]=Nbgrid[15][11]=Nbgrid[15][10]=Nbgrid[15][9]=CELL_ALIVE;
		    Nbgrid[15][13]=Nbgrid[14][14]=Nbgrid[13][15]=CELL_ALIVE;
	        Nbgrid[12][16]=Nbgrid[11][17]=Nbgrid[10][18]=CELL_ALIVE; 
		    break;
		    case 4:
		    for (int i=0;i<NUM_ROW;++i)
		    Nbgrid[i][NUM_ROW-1]=CELL_ALIVE;
		    for(int i=0;i<NUM_ROW;++i)
		    Nbgrid[i][i]=CELL_ALIVE;	
		    break;
		    /**here the whole grid will be alive
		     *then the consquence in the next generation is only 4 cells
		     *will alive and die for all in the next generation
		     */
		   case 5:
		   for(int r=0;r<NUM_ROW;++r){
		   	  for(int c=0;c<NUM_COLM;++c){
		   		 Nbgrid[r][c]=CELL_ALIVE;
		      }
          }
          break;
		  default:{}
       }
		repaint();
   }
					
    public void paint(Graphics g)
    {
        System.out.println("CALL TO PAINT()");
          
        /* Fill in the background: */
        g.setColor(getBackground());
        /**
         *drawing arectangele of size each cell 10 by  10
         */
        for(int r=0;r<NUM_ROW;r++){
        	for(int c=0;c<NUM_COLM;++c){
        		g.drawRect(10*r,10*c,10,10);
            }
      }
      /**it will paint the given rectangel if it is cellalive
       */
      for(int r=0;r<NUM_ROW;++r){
      	 for(int c=0;c<NUM_COLM; c++){
      	 	if(Nbgrid[r][c]==CELL_ALIVE)
   		     g.fillRect(10*r,10*c,10,10);
   	     }
     }
  }
    
 
    public static void main(String[] args) {
    	
       JFrame frame = new JFrame("Game of trulife");
       GameOfLife trulife=new GameOfLife();     
       JPanel control=new JPanel();
        control=trulife.control();
        frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.SOUTH, control);
		frame.getContentPane().add(BorderLayout.NORTH, trulife);
		frame.pack();
		frame.setVisible(true);
}
}