
import java.io.BufferedWriter;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
import java.awt.*;
import java.util.BitSet;



public class Huffman {
    static final boolean readFromFile = true;

    static PriorityQueue<Node1> Node1s = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static TreeMap<Character, String> codes = new TreeMap<>();
    static String text = "";
    static String encoded = "";
    static String decoded = "";
    static int ASCII[] = new int[128];
    public Huffman(File f) throws FileNotFoundException {
    	
        Scanner s= new Scanner(f);
        long startTime = System.nanoTime();
        handleNewText(s);
        long endTime = System.nanoTime();
        long timeElapsed=endTime-startTime;
        System.out.println("the execution time is = "+timeElapsed/1000000+"ms");
    }
public Huffman() throws FileNotFoundException {
	
    }
public Huffman(String a) throws FileNotFoundException {
	

try {
	decodeText();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}

    public static void main(String[] args) throws FileNotFoundException {
    
    }


    private static void handleDecodingNewText(Scanner scanner) {
        System.out.println("Enter the text to decode:");
        encoded = scanner.nextLine();
        System.out.println("Text to Decode: " + encoded);
        try {
			decodeText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    

    private static boolean handleNewText(Scanner scanner) {
        System.out.println("Enter the text:");
        while(scanner.hasNextLine())
        text += scanner.nextLine();
        
            ASCII = new int[128];
            Node1s.clear();
            codes.clear();
            encoded = "";
            decoded = "";
            System.out.println("Text: " + text);
            calculateCharIntervals(Node1s, true);
            buildTree(Node1s);
            generateCodes(Node1s.peek(), "");

            printCodes();
            System.out.println("-- Encoding/Decoding --");
            try {
				encodeText();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            return false;



    }


    private static void decodeText() throws IOException {
        decoded = "";
        Node1 Node = Node1s.peek();
        for (int i = 0; i < encoded.length(); ) {
            Node1 tmpNode1 = Node;
            while (tmpNode1.left != null && tmpNode1.right != null && i < encoded.length()) {
                if (encoded.charAt(i) == '0')
                    tmpNode1 = tmpNode1.right;
                else tmpNode1 = tmpNode1.left;
                i++;
            }
            if (tmpNode1 != null)
                
                    decoded += tmpNode1.character;
              

        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("decoded.txt"));
         		
         
         writer.write(decoded);
         writer.close();
         Desktop des= Desktop.getDesktop();
         File f0=new File("decoded.txt");
          des.open(f0);
        System.out.println("Decoded Text: " + decoded);
    }

    private static void encodeText() throws IOException {
        encoded = "";
       
        for (int i = 0; i < text.length(); i++) {
            encoded+= codes.get(text.charAt(i));
            
        }
        encoded+="\n";
        BufferedWriter writer = new BufferedWriter(new FileWriter("sulaiman.txt"));
       String s="the size is "+encoded.length()/8+"bytes";
        		
        
        writer.write(encoded);
        writer.write(s);
        writer.close();
        Desktop des= Desktop.getDesktop();
        File f0=new File("sulaiman.txt");
         des.open(f0);
        System.out.println("Encoded Text: " + encoded);
    }
  

    private static void buildTree(PriorityQueue<Node1> vector) {
    	int c=1;
        while (vector.size() > 1) {
        	Node1 n2 = vector.poll();
        	n2.setcount(c);
        	c++;
        	Node1 n = vector.poll();
        	n.setcount(c);
        	c++;
            vector.add(new Node1(n,n2));
            
        }
        
    }

    private static void printCodes() {
        System.out.println("--- Printing Codes ---");
        codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
    }

    private static void calculateCharIntervals(PriorityQueue<Node1> vector, boolean printIntervals) {
        if (printIntervals) System.out.println("-- intervals --");

        for (int i = 0; i < text.length(); i++)
            ASCII[text.charAt(i)]++;
        
        for (int i = 0; i < ASCII.length; i++)
            if (ASCII[i] > 0) {
                vector.add(new Node1(ASCII[i], ((char) i) ));
                if (printIntervals)
                    System.out.println("'" + ((char) i) + "' : " + ASCII[i]);
            }
        
    }

    private static void generateCodes(Node1 Node1, String s) {
        if (Node1 != null) {
            if (Node1.right != null)
                generateCodes(Node1.right, s + "0");

            if (Node1.left != null)
                generateCodes(Node1.left, s + "1");

            if (Node1.left == null && Node1.right == null)
                codes.put(Node1.character, s);
        }
    }
}
class Node1 {
    Node1 left, right;
    double value;
    char character;
    int count;

    public Node1(double value, char character) {
        this.value = value;
        this.character = character;
        left = null;
        right = null;
    }

    public Node1(Node1 left, Node1 right) {
        this.value = left.value + right.value;
        
        if (left.value < right.value) {
            this.right = right;
            this.left = left;
        } else {
            this.right = left;
            this.left = right;
        }
    }

	public void setcount(int size) {
		this.count=size;
		
	}
}

