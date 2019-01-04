package OSproject;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.geom.Rectangle2D;


public class RWsProblem {
	
	public static int data1[] = {10,22,31,47,75,62,88,91,78,18};
	public static int data2[] = {10,22,31,47,75,62,88,91,78,18};
	
	public static Object canReadMutex = new Object();
	public static Object canWriteMutex = new Object();
	
	public static Object rcMutex = new Object();
	public static Object wcMutex = new Object();
	public static Object copyMutex = new Object();
	public static Object moveMutex = new Object();
	
	public static Object buttomWait = new Object();
	
	public static boolean canRead = true;
	public static boolean canWrite = true;
	public static int readerCount = 0;
	public static Random ran = new Random();
	public static int[] pos = {50,100,150,200,250,300,350,400,450,500};
	
	public static boolean startCopy = false;
	
	
	
	
	public static  Object wall= new Object();
    public static void main(String[] argv) {
    	RWsProblem p = new RWsProblem();
        SwingUtilities.invokeLater( () -> new RWsProblem().startup(p) );
    }

    public void startup(RWsProblem p) {
        JFrame frame = new JFrame("Test");                                          
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        Harbor h = new Harbor(p);
        frame.setContentPane(h);                         
        frame.setSize(600, 600);                                        
        frame.setVisible(true);   //set window visible
        
        
        /*
        waitButtom wb = new waitButtom(h.balls);
        Container cp= frame.getContentPane();
        cp.setLayout(null);
        JButton b1=new JButton("Pause");    
        b1.setBounds(20,20,100,40);
        b1.addActionListener(wb);
        cp.add(b1);
        
        
        wakeButtom wb2 = new wakeButtom();
        cp.setLayout(null);
        JButton b2=new JButton("Start");    
        b2.setBounds(120,20,100,40);
        b2.addActionListener(wb2);
        cp.add(b2);
        */
    }
    
    
    public class waitButtom implements ActionListener{ 
    	  public List<BallWithOwnThread> balls;
    	  public waitButtom(List<BallWithOwnThread> balls) {
    		  this.balls = balls;
    	  }
    	  
    	 public void actionPerformed(ActionEvent e) {
    		  int count = 0;
    		  for(BallWithOwnThread b : balls) {
    		  }
    	 }
     }
    
    
    /*
	  synchronized(buttomWait) { 
	  try { 
		  System.out.println(balls.size());
		  buttomWait.wait(); 
       
	  } 
	  catch(InterruptedException ie) { 
	         ie.printStackTrace(); 
	   }
	  }
    */
    public class wakeButtom implements ActionListener{ 
  	  public wakeButtom() {
  	  }
  	 public void actionPerformed(ActionEvent e) {
  			  synchronized(buttomWait) {  
  				  buttomWait.notifyAll();  			 
  		  }
  	 }
   }
    
 
    public static class Harbor extends JComponent {
        public List<BallWithOwnThread> balls = new ArrayList<>();
        public DrawRoom r1 = new DrawRoom(this);
        public DrawData d1 = new DrawData(this);
        
        public Harbor(RWsProblem p) {
            balls.add(new BallWithOwnThread(Color.RED, 175, 590, 12, 101, this ,balls,p) );
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);                                    // handle opaqueness
            Graphics2D g2 = (Graphics2D)g;
            balls.forEach( ball -> ball.paint(g2) );
            r1.paint(g2);
            d1.paint(g2);;
        }
    } // end static class Harbor
 
    /*
    g2.draw(new Rectangle2D.Double(x, y,
            rectwidth,
            rectheight));
    */
    
    /**
     * Few of us would argue that each Ball should have its own thread,
     * especially if there are going to be a large number of balls, but
     * this is a proof-of-concept that the approach can work.
     */
    public static class DrawData implements Runnable {
    	private JComponent parent;
    	public static int y = 0;
    	public static int sizeOffset = -5;
    	public DrawData(JComponent parent) {
    		this.parent = parent;
    	}
    	public void paint(Graphics2D g) {
	    		if(startCopy) {
	    			try {
	                    Thread.sleep(10);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                    return;
	                }
	    			if(y < 155) {
	        		  y += 1;
	    			}else {
	    				 synchronized(moveMutex) {
	     						moveMutex.notifyAll(); 
	            	   }  
	    			}
	    		  g.setPaint(Color.BLUE);
	  	   		  g.drawString(""+data1[0],35,235+y);
	  	   		  g.drawString(""+data1[1],85,235+y);
	  	   		  g.drawString(""+data1[2],135,235+y);
	  	   		  g.drawString(""+data1[3],185,235+y);
	  	   		  g.drawString(""+data1[4],235,235+y);
	  	   		  g.drawString(""+data1[5],285,235+y);
	  	   		  g.drawString(""+data1[6],335,235+y);
	  	   		  g.drawString(""+data1[7],385,235+y);
	  	   		  g.drawString(""+data1[8],435,235+y);
	  	   		  g.drawString(""+data1[9],485,235+y);
	  	   		  
	  		   	  Rectangle2D array1_Element1 = new Rectangle2D.Double(25, 200+y, 50 ,50+sizeOffset);
	  	  		  g.setPaint(Color.BLACK);
	  	  		  g.draw(array1_Element1);	 
	  	  		  
	  	  		  Rectangle2D array1_Element2 = new Rectangle2D.Double(75, 200+y, 50 ,50+sizeOffset);
	  	  		  g.setPaint(Color.BLACK);
	  	  		  g.draw(array1_Element2);
	  	  		  
	  	  		  Rectangle2D array1_Element3 = new Rectangle2D.Double(125, 200+y, 50 ,50+sizeOffset);
	  	  		  g.setPaint(Color.BLACK);
	  	  		  g.draw(array1_Element3);
	  	  		  
	  	  		  
	  	  		  
	  	  		  Rectangle2D array1_Element4 = new Rectangle2D.Double(175, 200+y, 50 ,50+sizeOffset);
	  	  		  g.setPaint(Color.BLACK);
	  	  		  g.draw(array1_Element4);
	  	  		  ;
	  	  		  
	  	  		  Rectangle2D array1_Element5 = new Rectangle2D.Double(225, 200+y, 50 ,50+sizeOffset);
	  	  		  g.setPaint(Color.BLACK);
	  	  		  g.draw(array1_Element5);
	  	  		  
	  	  		  
	  	  		  
	  	  		  Rectangle2D array1_Element6 = new Rectangle2D.Double(275, 200+y, 50 ,50+sizeOffset);
	  	  		  g.setPaint(Color.BLACK);
	  	  		  g.draw(array1_Element6);
	  	  		  
	  	  		  
	  	  		  
	  	  		  Rectangle2D array1_Element7 = new Rectangle2D.Double(325, 200+y, 50 ,50+sizeOffset);
	  	  		  g.setPaint(Color.BLACK);
	  	  		  g.draw(array1_Element7);
	  	  		  
	  	  		  
	  	  		  
	  	  		  Rectangle2D array1_Element8 = new Rectangle2D.Double(375, 200+y, 50 ,50+sizeOffset);
	  	  		  g.setPaint(Color.BLACK);
	  	  		  g.draw(array1_Element8);
	  	  		  
	  	  		  
	  	  		  Rectangle2D array1_Element9 = new Rectangle2D.Double(425, 200+y, 50 ,50+sizeOffset);
	  	  		  g.setPaint(Color.BLACK);
	  	  		  g.draw(array1_Element9);
	  	  		  
	  	  		  
	  	  		  
	  	  		  Rectangle2D array1_Element10 = new Rectangle2D.Double(475, 200+y, 50 ,50+sizeOffset);
	  	  		  g.setPaint(Color.BLACK);
	  	  		  g.draw(array1_Element10);
	  	   		    
	  	  		  parent.repaint();	    			
	    		}else {
	    			 y = 0;
	    			 parent.repaint();	   
	    		}
    		
   
   	
    	}
    	public void run() {
    	}
    }
    
    
    
    
    public static class DrawRoom implements Runnable {
    	private JComponent parent;
    	public DrawRoom(JComponent parent) {
    		this.parent = parent;
    	}
    	
    	public void paint(Graphics2D g) {
    		
    		
    		  
    		  Rectangle2D topRectM = new Rectangle2D.Double(25, 250,500,5);
    	      g.setPaint(Color.BLACK);
    	      g.fill(topRectM);
    		  g.draw(topRectM);
    		  
    		  g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
              g.setColor(Color.BLUE);

    		  
    		  Rectangle2D topRectR = new Rectangle2D.Double(25, 150 ,5,100);
    	      g.setPaint(Color.BLACK);
    	      g.fill(topRectR);
    		  g.draw(topRectR);

    		  
    		  Rectangle2D topRectL = new Rectangle2D.Double(520, 150,5,100);
    	      g.setPaint(Color.BLACK);
    	      g.fill(topRectL);
    		  g.draw(topRectL);
    		  
    		 
    		  Rectangle2D topRectML = new Rectangle2D.Double(25, 150, 100 ,5);
    	      g.setPaint(Color.BLACK);
    	      g.fill(topRectML);
    		  g.draw(topRectML);
    		  
    		  
    		  Rectangle2D topRectMM = new Rectangle2D.Double(225, 150, 100 ,5);
    	      g.setPaint(Color.BLACK);
    	      g.fill(topRectMM);
    		  g.draw(topRectMM);
    		  
    		  
    		  Rectangle2D topRectMR = new Rectangle2D.Double(425, 150, 100 ,5);
    	      g.setPaint(Color.BLACK);
    	      g.fill(topRectMR);
    		  g.draw(topRectMR);
    		 
    		  
    		  if(!canWrite) {
    		    Rectangle2D topEnter = new Rectangle2D.Double(125, 150, 100 ,5);
    		    g.setPaint(Color.BLACK);
    		    g.draw(topEnter);
    		  }
    		  
    		  
    		 
    		  g.setPaint(Color.BLUE);
    		  g.drawString(""+data1[0],35,235);
    		  g.drawString(""+data1[1],85,235);
    		  g.drawString(""+data1[2],135,235);
    		  g.drawString(""+data1[3],185,235);
    		  g.drawString(""+data1[4],235,235);
    		  g.drawString(""+data1[5],285,235);
    		  g.drawString(""+data1[6],335,235);
    		  g.drawString(""+data1[7],385,235);
    		  g.drawString(""+data1[8],435,235);
    		  g.drawString(""+data1[9],485,235);
    		  
    		  
    		  
    		 
    		  Rectangle2D array1_Element1 = new Rectangle2D.Double(25, 200, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array1_Element1);
    		  
    		
    		  Rectangle2D array1_Element2 = new Rectangle2D.Double(75, 200, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array1_Element2);
    		  
    		  Rectangle2D array1_Element3 = new Rectangle2D.Double(125, 200, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array1_Element3);
    		  
    		  
    		  
    		  Rectangle2D array1_Element4 = new Rectangle2D.Double(175, 200, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array1_Element4);
    		  ;
    		  
    		  Rectangle2D array1_Element5 = new Rectangle2D.Double(225, 200, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array1_Element5);
    		  
    		  
    		  
    		  Rectangle2D array1_Element6 = new Rectangle2D.Double(275, 200, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array1_Element6);
    		  
    		  
    		  
    		  Rectangle2D array1_Element7 = new Rectangle2D.Double(325, 200, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array1_Element7);
    		  
    		  
    		  
    		  Rectangle2D array1_Element8 = new Rectangle2D.Double(375, 200, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array1_Element8);
    		  
    		  
    		  Rectangle2D array1_Element9 = new Rectangle2D.Double(425, 200, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array1_Element9);
    		  
    		  
    		  
    		  Rectangle2D array1_Element10 = new Rectangle2D.Double(475, 200, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array1_Element10);
    		  


    		  
    		  
    		  
    		  
    		 
    		  Rectangle2D bottomRectM = new Rectangle2D.Double(25, 350,500,5);
    	      g.setPaint(Color.BLACK);
    	      g.fill(bottomRectM);
    		  g.draw(bottomRectM);
    		  
    		  Rectangle2D bottomRectR = new Rectangle2D.Double(25, 350 ,5,100);
    	      g.setPaint(Color.BLACK);
    	      g.fill(bottomRectR);
    		  g.draw(bottomRectR);
    		  
    		  Rectangle2D bottomRectL = new Rectangle2D.Double(520, 350,5,100);
    	      g.setPaint(Color.BLACK);
    	      g.fill(bottomRectL);
    		  g.draw(bottomRectL);
    		  
    		  
    		  Rectangle2D bottomRectML = new Rectangle2D.Double(25, 450, 100 ,5);
    	      g.setPaint(Color.BLACK);
    	      g.fill(bottomRectML);
    		  g.draw(bottomRectML);
    		  
    		  Rectangle2D bottomRectMM = new Rectangle2D.Double(225, 450, 100 ,5);
    	      g.setPaint(Color.BLACK);
    	      g.fill(bottomRectMM);
    		  g.draw(bottomRectMM);
    		  
    		  Rectangle2D bottomRectMR = new Rectangle2D.Double(425, 450, 100 ,5);
    	      g.setPaint(Color.BLACK);
    	      g.fill(bottomRectMR);
    		  g.draw(bottomRectMR);
    		  
    		  
    		  if(!canRead) {
    		    Rectangle2D bottomEnter = new Rectangle2D.Double(125, 450, 100 ,5);
    		    g.setPaint(Color.BLACK);
    		    g.draw(bottomEnter);
    		  }
    		  
    		  
    		  g.setPaint(Color.RED);
    		  g.drawString(""+data2[0],35,390);
    		  g.drawString(""+data2[1],85,390);
    		  g.drawString(""+data2[2],135,390);
    		  g.drawString(""+data2[3],185,390);
    		  g.drawString(""+data2[4],235,390);
    		  g.drawString(""+data2[5],285,390);
    		  g.drawString(""+data2[6],335,390);
    		  g.drawString(""+data2[7],385,390);
    		  g.drawString(""+data2[8],435,390);
    		  g.drawString(""+data2[9],485,390);
    		  
    		  

    		  
    		  
    		
    		  Rectangle2D array2_Element1 = new Rectangle2D.Double(25, 350, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array2_Element1);
    		  Rectangle2D array2_Element2 = new Rectangle2D.Double(75, 350, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array2_Element2);
    		  Rectangle2D array2_Element3 = new Rectangle2D.Double(125, 350, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array2_Element3);
    		  Rectangle2D array2_Element4 = new Rectangle2D.Double(175, 350, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array2_Element4);
    		  Rectangle2D array2_Element5 = new Rectangle2D.Double(225, 350, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array2_Element5);
    		  Rectangle2D array2_Element6 = new Rectangle2D.Double(275, 350, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array2_Element6);
    		  Rectangle2D array2_Element7 = new Rectangle2D.Double(325, 350, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array2_Element7);
    		  Rectangle2D array2_Element8 = new Rectangle2D.Double(375, 350, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array2_Element8);
    		  Rectangle2D array2_Element9 = new Rectangle2D.Double(425, 350, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array2_Element9);
    		  Rectangle2D array2_Element10 = new Rectangle2D.Double(475, 350, 50 ,50);
    		  g.setPaint(Color.BLACK);
    		  g.draw(array2_Element10);
    		  
    		  
    		  parent.repaint();	  
    	}
    	@Override
    	public void run() {
    		while(true) {
    			parent.repaint();	
    		}
    	}
    }
    
    
    
    
    public static class BallWithOwnThread implements Runnable {
        private Color color;
        private int x, y, radius, milliseconds;
        private boolean blink, visible=true;
        private JComponent parent;
        int mode = 0;
        
        int t = 0;
        String s = "";
        
        public BallWithOwnThread(Color color, int x, int y, int radius, int milliseconds, JComponent parent,List<BallWithOwnThread> balls , RWsProblem p) {       	
        	this.color = color;
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.milliseconds = milliseconds;
            this.parent = parent;

            Timer timer = new Timer();
            if(x < 600) {
         	    timer.schedule(p.new DateTasks(Color.BLUE, x, 10, 14, 101,parent,balls,p), (long)(-1/(1.0/1000.0)*Math.log(Math.random())));
            }else {
            	timer.schedule(p.new DateTasks(Color.BLUE, 50 , 10, 142, 101,parent,balls,p), (long)(-1/(1.0/1000.0)*Math.log(Math.random())));
            }
            // create and start this object's thread
            new Thread(this).start();
        }
        
        
        
 
        public void paint(Graphics2D g) {
            // Draw the ball. Don't change state.
            if (visible) {
                g.setColor(color);
                g.fillOval(x-radius, y-radius, 2*radius, 2*radius);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
                g.setColor(Color.WHITE);
                g.drawString(s,x-radius + 4.5f,y-radius + 21);
            }
        }
 
        @Override
        public void run() {
        	
            for ( ; ; ) {
            	
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
 
                int xOld = x, yOld = y;
              
                
                if(color == Color.BLUE) {
                	 int value = ran.nextInt(100);
                	 s = ""+ value;
                	
                	
                	
                	while(true) {
                		try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                		if(y <= 130) {
                			y += 1;
                       	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                            parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                		}else {
                			break;
                		}
                	}
                	
                	
                	
            		while(true) {
           			 synchronized(canWriteMutex) {	
           				 if(canWrite == true) {
           					 canWrite = false;
           					 System.out.println("Enter write: [" + "]");
           					 break;
           				 }else {
           					 try { 
           						    canWriteMutex.wait(); 
           				         } 
           				         catch(InterruptedException e) { 
           				             e.printStackTrace(); 
           				         }
           				 }
           			 }	 		 
           		  }
                
           		
                	
            		
            		while(true) {
                		try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                		if(y <= 175) {
                			y += 1;
                       	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                            parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                		}else {
                			break;
                		}
                	}
            		
            		
            	   int index = ran.nextInt(10);
            	  
            		
                	
            	   
            	   if(index <= 2) {
                   	while(true) {
                   		try {
                               Thread.sleep(10);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                               return;
                           }
                   		if(x > pos[index]) {
                   			 x -= 1;
                          	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                               parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                   		}else {
                   			break;
                   		}
                   	}
               	}else {
               		while(true) {
                   		try {
                               Thread.sleep(10);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                               return;
                           }
                   		if(x < pos[index]) {
                   			 x += 1;
                          	   parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                               parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                   		}else {
                   			break;
                   		}
                   	}
               	}
            	   
            	   try {
                       Thread.sleep(1000);
                       data1[index] = value;
                       s = "";
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                       return;
                   }
            	   
            	   
            	   if(index <= 6) {
                   	while(true) {
                   		try {
                               Thread.sleep(10);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                               return;
                           }
                   		if(x < 375) {
                   			 x += 1;
                          	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                               parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                   		}else {
                   			break;
                   		}
                   	}
               	}else {
               		while(true) {
                   		try {
                               Thread.sleep(10);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                               return;
                           }
                   		if(x > 375) {
                   			 x -= 1;
                          	   parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                               parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                   		}else {
                   			break;
                   		}
                   	}
               	}
            	   

            	   
            	   
            	   synchronized(canReadMutex) {	
           			   canRead = false;
           		   }
            	       
            	   
            	   while(true) {
             			synchronized(rcMutex) {
             				if(readerCount == 0) {           					
             					break;
             				}else {
             					 try { 
             						 rcMutex.wait(); 
             				     } 
             				     catch(InterruptedException e) { 
             				         e.printStackTrace(); 
             				     }
             				}
             			}
             		}
            	   
            	   startCopy = true;
            	   
            	   
            	   
            	   synchronized(moveMutex) {
     					 try { 
     						moveMutex.wait(); 
       				     } 
       				     catch(InterruptedException e) { 
       				         e.printStackTrace(); 
       				     }
            	   }  
            	   
            	   	   
            	   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                       return;
                   }
            	   
            	   for(int i=0; i<10;i++) {
  						data2[i] = data1[i];
  					}
            	  
            	   
            	   
            	   startCopy = false;
            	   
            	   
            	   while(true) {
                  		try {
                              Thread.sleep(10);
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                              return;
                          }
                  		if(y > 130) {
                  			 y -= 1;
                         	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                              parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                  		}else {
                  			break;
                  		}
                  	 }
            	   
            	   
            	   
            	   synchronized(canReadMutex) {	
           			canRead = true;
           			canReadMutex.notifyAll();
           		   }
           		
           		   synchronized(canWriteMutex) {	
           			canWrite = true;
           			canWriteMutex.notifyAll();
           			 System.out.println("Levae write: [" + "]");
           		   }
            	   
            	   
           		  while(true) {
                		try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                		if(y > -20) {
                			 y -= 1;
                       	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                            parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                		}else {
                			 
                		}
                	 }
           		   
           		   
           		   
            	   
                	/*
                   if(y <= 130 && mode == 0) {
                	   y += 1;
                  	   parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                       parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                   }  
                   */

            		/*
                    if(y > 175 && mode == 0) {
                	   //color = Color.CYAN;  
	                   try { 
	           			 Thread.sleep(3000);
	           			 s = ""+(int)(Math.random() * 10);
	           			 data1[0]++;
	           			//System.out.println(data1[0]);
	           			 mode = 1;
	           	       } 
	           	       catch(InterruptedException e) {          
	           	       }
                  	   parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                       parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                   }
                   else if(x<375){
                	   x++;
                  	   parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                       parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                   }else {
                	   y--;
                  	   parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                       parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                   }
                   
                   */
                }
                
                
                
                
                if(color == Color.RED) {
                	
                	
                	
                	while(true) {
                		try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                		if(y >= 480) {
                			 y -= 1;
                       	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                            parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                		}else {
                			break;
                		}
                	}
                	
                	
                	while(true) {
	           			 synchronized(canReadMutex) {	
	           				 if(canRead == true) {
	           					 break;
	           				 }else {
	           					 try { 
	           						    canReadMutex.wait(); 
	           				         } 
	           				         catch(InterruptedException e) { 
	           				             e.printStackTrace(); 
	           				         }
	           				  }
	           			 }	 		 
           		   }
                	
                	
                	
                	while(true) {
                		try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                		if(y >= 425) {
                			 y -= 1;
                       	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                            parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                		}else {
                			break;
                		}
                	}
                	
                	
                	synchronized(rcMutex) {	
            			readerCount++;
            			System.out.println("Enter read: [" + "] Now Reader : " + readerCount );
            		}	 
                	
                	
                	int index = ran.nextInt(10);

                	if(index <= 2) {
                    	while(true) {
                    		try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                    		if(x > pos[index]) {
                    			 x -= 1;
                           	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                                parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                    		}else {
                    			break;
                    		}
                    	}
                	}else {
                		while(true) {
                    		try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                    		if(x < pos[index]) {
                    			 x += 1;
                           	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                                parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                    		}else {
                    			break;
                    		}
                    	}
                	}
                	
                	
                	
                	try {
                        Thread.sleep(1000);
                        s = ""+data2[index];
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                	
                	
                	if(index <= 6) {
                    	while(true) {
                    		try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                    		if(x < 375) {
                    			 x += 1;
                           	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                                parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                    		}else {
                    			break;
                    		}
                    	}
                	}else {
                		while(true) {
                    		try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                    		if(x > 375) {
                    			 x -= 1;
                           	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                                parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                    		}else {
                    			break;
                    		}
                    	}
                	}
                	
                	
                	while(true) {
                		try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                		if(y < 480) {
                			 y += 1;
                       	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                            parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                		}else {
                			break;
                		}
                	}

                	

                	synchronized(rcMutex) {	
            			readerCount--;
            			if(readerCount == 0) {
            				rcMutex.notifyAll();
            			}
            			System.out.println("Leave read: [" + "] Now Reader : " + readerCount );
            		}
                	
                	
                	
                	
                	while(true) {
                		try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                			y += 1;
                       	    parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                            parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                	}
                	
                	
                	/*
                	 if(y >= 480 && mode == 0) {
                  	   y -= 1;
                  	 parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                     parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                     }
                     else if(y < 480 && mode == 0) {
  	                   try { 
  	           			 Thread.sleep(3000); 
  	           			 mode = 1;
  	           	       } 
  	           	       catch(InterruptedException e) {          
  	           	       }
  	                   parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
  	                   parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                     }
                     else if(x<375){
                  	   x++;
                  	   parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                       parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                  	   //y -= dy;
                     }else {
                  	   y++;
                  	   parent.repaint(x-radius, y-radius, 2*radius, 2*radius);         
                       parent.repaint(xOld-radius, yOld-radius, 2*radius, 2*radius);
                     }
                     
                     */
                }
                
                
                
            }
        }
        
    }
       
        
        
    public class DateTasks extends TimerTask {
   	 private Color color;
        private int x, y, radius, milliseconds;
        private boolean blink, visible=true;
        private JComponent parent;
        private List<BallWithOwnThread> balls;
        private RWsProblem p;
		 public DateTasks(Color color, int x, int y, int radius, int milliseconds, JComponent parent,List<BallWithOwnThread> balls , RWsProblem p) {
			 super();
    	this.color = color;
       this.x = x;
       this.y = y;
       this.radius = radius;
       this.milliseconds = milliseconds;
       this.parent = parent;
       this.balls = balls;
       this.p = p;
		 }
		 @ Override
		 public void run() {
			 if(Math.random() >= 0.5) {
				 balls.add( new BallWithOwnThread(Color.BLUE,x, y, radius,  milliseconds, parent,balls,p) );	 
		   	  }else {
		   		 balls.add( new BallWithOwnThread(Color.RED,x, 590, radius, milliseconds, parent,balls,p) );	 
		   	  }
		 }
  }
        
 
}