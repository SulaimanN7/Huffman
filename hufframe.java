import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
public class hufframe  implements ActionListener{
	JFrame frame;
	JButton browse , encode ,build, decode;
	JPanel east,west,south, north;
	File f;
	String text,a="";
	
	
	public hufframe() {
		
		frame=new JFrame("Huffman");
		Container c= frame.getContentPane();
		c.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		browse=new JButton("Browse");
		browse.addActionListener(this);
		browse.setActionCommand("browse");
		
		encode= new JButton("Encode");
		encode.addActionListener(this);
		encode.setActionCommand("encode");
		
		decode= new JButton("Decode");
		decode.addActionListener(this);
		decode.setActionCommand("decode");
		
		build=new JButton("build");
		build.addActionListener(this);
		build.setActionCommand("build");
		
		north= new JPanel();
		west= new JPanel();
		east= new JPanel();
		south= new JPanel();
		
		north.add(decode);
		west.add(browse);
		east.add(encode);
		south.add(build);
		
		
		c.add(BorderLayout.NORTH,north);
		c.add(BorderLayout.WEST,west);
		c.add(BorderLayout.EAST,east);
		c.add(BorderLayout.SOUTH,south);
		
		
		frame.pack();
		frame.setVisible(true);
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("browse")) {
			JFileChooser fc=new JFileChooser("choose file");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result= fc.showOpenDialog(frame);
			if(result==JFileChooser.APPROVE_OPTION)
				f=fc.getSelectedFile();
			
		}
		else if(e.getActionCommand().equals("encode")) {
			try {
				Huffman huf=new Huffman(f);
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand().equals("decode")) {
			try {
				Huffman huf=new Huffman(a);
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		else if ( e.getActionCommand().equals("build")) {
			
			try {
				Scanner s1 = new Scanner(f);
				while(s1.hasNextLine()) {
					text=s1.nextLine();
				}
				
                Huffman huf=new Huffman();
               Node1 f= huf.Node1s.peek();
				
				
				NewWindow NW= new NewWindow(f);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}
		
		
		
		
	}
	
	public static void main (String[] args) {
		hufframe h = new hufframe();
	}
	
	

}





















class Tree extends JFrame{





public static int[] freqCount(String S){

int[] f=new int[10000];
for(int i=0;i<S.length();i++){

f[S.charAt(i)]++;

}
System.out.println(f[S.charAt(0)]+" "+S.charAt(0));
for(int i=0;i<9;i++) {
	System.out.println(f[S.charAt(i)]+" "+S.charAt(i));
}
return f;
}







}

class NewWindow{
	String encoded="";
Node1 ab;
ArrayList<Node1>node=new ArrayList<Node1>();
public int width=700; // this may be changed accodingly to fit the tree in the screen//
public int height=10;
ArrayList<Integer>x = new ArrayList<Integer>(); //list of x coordinates of the nodes in the tree//
ArrayList<Integer>y = new ArrayList<Integer>(); //list of y coordinates of the nodes in the tree//


public int index(Node1 n){
int a=0;
for(int i=0;i<node.size();i++){
if(n==node.get(i))
{
a=i;
break;
}
}
return a;
}



public void bfs(Node1 root){
Queue<Node1> q= new LinkedList<Node1>();
int c=1;

q.add(root);
while(!q.isEmpty()){
Node1 n=(Node1)q.remove();
node.add(n);
if(n.left!=null)
q.add(n.left);
if(n.right!=null)
q.add(n.right);
}
for(int i=0;i<node.size();i++){
x.add(0);
y.add(0);
}
x.set(0,width);
y.set(0,height);

for(int j=0;j<node.size();j++){
if(node.get(j).left!=null && node.get(j).right!=null){
x.set(index(node.get(j).left),x.get(j)-25*c);
x.set(index(node.get(j).right),x.get(j)+25*c);

y.set(index(node.get(j).left),y.get(j)+100);
y.set(index(node.get(j).right),y.get(j)+100);
c++;
}

}

ab.setcount(node.size());
}




public NewWindow(Node1 b) {

	ab=b;
	        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("HUFFMAN TREE GENERATION");
             
   
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());



                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
	 bfs(ab);

}



public class TestPane extends JPanel {

	private int b=node.size();
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(2800, 2800);
        }

	public TestPane(){
		ActionListener animate = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                repaint();
		b--;
            }
       	 };
        Timer timer = new Timer(100,animate);
        timer.start();
	setBackground(Color.WHITE);
	}


        @Override
        protected void paintComponent(Graphics g) {
	   
	    
            super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g.setColor(Color.RED);
	    

	    for(int j=node.size()-1;j>=0;j--){
		if(node.get(j).count<node.size()-b){			
				g2d.setColor(Color.YELLOW);
				g2d.fillRect(x.get(j),y.get(j), 23, 23);
				

				g2d.setColor(Color.BLACK);
				g2d.drawRect(x.get(j), y.get(j), 23, 23);
				g2d.setColor(Color.BLACK);
				g2d.drawString(Integer.toString((int)node.get(j).value),x.get(j)+5 , y.get(j)+13);
				g2d.drawString(Character.toString(node.get(j).character),x.get(j)+10 , y.get(j)+20);
				g2d.setColor(Color.BLACK);
				if((node.get(j)).left!=null){
					
					g2d.drawLine(x.get(j)+20,y.get(j)+20,x.get(index(node.get(j).left)),y.get(index(node.get(j).left)));

					g2d.drawString("1",(x.get(j)+x.get(index(node.get(j).left)))/2, (y.get(j)+y.get(index(node.get(j).left)))/2);
					g2d.setColor(Color.BLACK);

				}
				if((node.get(j)).right!=null){
					
					g2d.drawLine(x.get(j)+20,y.get(j)+20,x.get(index(node.get(j).right)),y.get(index(node.get(j).right)));
					g2d.drawString("0",(x.get(j)+x.get(index(node.get(j).right)))/2, (y.get(j)+y.get(index(node.get(j).right)))/2);
					g2d.setColor(Color.BLACK);
				}
			}
	}
		
           
    }
}
}


