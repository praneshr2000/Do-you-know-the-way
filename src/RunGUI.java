// Author: Pranesh Reddy Jambula

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JOptionPane;

public class RunGUI {
	
	private int size;
	private JFrame frame;
	private JTextField txtWelcomeToThe;
	private JButton[][] buttonArray;
	private int clicks;
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private Graph<String, Integer> currGraph;
	private boolean done;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("resource")
					Scanner input = new Scanner(System.in);
					System.out.println("Welcome to the best shortest path finder simulation! ");
					System.out.println();
					System.out.print("Please enter the size of the square grid greater than 2: ");
					String size = input.nextLine();
					boolean error = true;
					while (error) {
						try {
							if (Integer.parseInt(size) < 3) {
								throw new Exception();
							}
							RunGUI window = new RunGUI(Integer.parseInt(size));
							window.frame.setVisible(true);
							error = false;
						} catch (Exception e) {
							System.out.println();
							System.out.println("Enter a valid integer number greater than 2! ");
							System.out.print("Please enter the size of the square grid greater than 2: ");
							size = input.nextLine();
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RunGUI(int size) {
		this.size = size;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		startX = -1;
		startY = -1;
		endX = -1;
		endY = -1;
		done = false;
		
		currGraph = new Graph<>();
		
		clicks = 0;
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.getContentPane().setLayout(null);
		
		txtWelcomeToThe = new JTextField();
		txtWelcomeToThe.setHorizontalAlignment(SwingConstants.CENTER);
		txtWelcomeToThe.setFont(new Font("Comic Sans MS", Font.BOLD, 33));
		txtWelcomeToThe.setForeground(new Color(255, 255, 255));
		txtWelcomeToThe.setBackground(new Color(0, 0, 0));
		txtWelcomeToThe.setText("Welcome to the Pathfinder App!");
		txtWelcomeToThe.setBounds(380, 11, 617, 53);
		frame.getContentPane().add(txtWelcomeToThe);
		txtWelcomeToThe.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Instructions:");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(37, 71, 185, 47);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<html>1. First click on any of the box will be the starting point.<br/><br/>\r\n2. Second click will be the destination point.<br/><br/>\r\n3. Next, keep clicking on the grid to place obstacles.<br/><br/>\r\n4. Once satisfied with the course, click the <br/>\r\n    \"Get Path!\" Button, and the path will show up.<br/><br/>\r\n5. If you want to start over, click \"Play Again!\" button.<br/><br/>\r\nNOTE: Only horizontal and vertical moves allowed, no diagonals!</html>");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel_1.setBounds(37, 86, 497, 496);
		frame.getContentPane().add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(558, 91, 580, 580);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, size, 0, 0));
	
		buttonArray = new JButton[size][size];
		
		ButtonHandler buttonHandler = new ButtonHandler();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				JButton b = new JButton("");
				buttonArray[i][j] = b;  
				buttonArray[i][j].addActionListener(buttonHandler);
				panel.add(b);
			}
		}
		
		JButton btnNewButton_3 = new JButton("Play again!");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startX = -1;
				startY = -1;
				endX = -1;
				endY = -1;
				clicks = 0;		
				done = false;
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						buttonArray[i][j].setBackground(new Color(240, 240, 240));
						buttonArray[i][j].setIcon(null);
					}
				}
			}
		});
		btnNewButton_3.setFont(new Font("Times New Roman", Font.BOLD, 30));		
		btnNewButton_3.setBounds(306, 565, 210, 77);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton finalCalc = new JButton("Get Path!");
		finalCalc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					calculatePath(currGraph);
					done = true;
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, 
                            "No path is possible for the chosen start, end and the obstacles", 
                            "", 
                            JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		finalCalc.setFont(new Font("Times New Roman", Font.BOLD, 30));
		finalCalc.setBounds(37, 565, 210, 77);
		frame.getContentPane().add(finalCalc);
		
		frame.setBounds(100, 100, 1190, 875);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void calculatePath(Graph<String, Integer> g) {
		if (startX == endX && startY == endY) {
			JOptionPane.showMessageDialog(null, 
                    "You have chosen the same start and end points!", 
                    "", 
                    JOptionPane.INFORMATION_MESSAGE);
		} else if (!currGraph.containsVertex(startX + "," + startY) && !currGraph.containsVertex(endX + "," + endY)){
			JOptionPane.showMessageDialog(null, 
                    "You have put an obstacle over the start and the end point, so no path is possible!", 
                    "", 
                    JOptionPane.INFORMATION_MESSAGE);
		} else if (!currGraph.containsVertex(startX + "," + startY)) {
			JOptionPane.showMessageDialog(null, 
                    "You have put an obstacle over the start point, so no path is possible!", 
                    "", 
                    JOptionPane.INFORMATION_MESSAGE);
		} else if (!currGraph.containsVertex(endX + "," + endY)) {
			JOptionPane.showMessageDialog(null, 
                    "You have put an obstacle over the end point, so no path is possible!", 
                    "", 
                    JOptionPane.INFORMATION_MESSAGE);
		} else {
			Route route = ShortestPath.minPath(startX + "," + startY, endX + "," + endY, g);
			String printRoute = "";
			boolean trim = false;
			if (route.toString().length() >= 9) {
				int lengthOfStart = (startX + "," + startY).length();
				int lengthOfEnd = (endX + "," + endY).length();
				printRoute = route.toString().substring(lengthOfStart + 1, route.toString().length() - (lengthOfEnd + 1));
				trim = true;
			} else {
				printRoute = route.toString();
			}
			String[] points = printRoute.toString().split(";");
			
			for (String s : points) {
				String x = s.split(",")[0];
				String y = s.split(",")[1];
				if (printRoute.length() >= 1 && trim) {
					buttonArray[Integer.parseInt(y)][Integer.parseInt(x)].setBackground(new Color(0, 255, 0));
				}
			}
		}
	}
	
	
	public class ButtonHandler implements ActionListener{
		
		
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (source == buttonArray[i][j] && clicks <= 2 && !done) {
						clicks++;
						if (clicks == 1) {
							startX = j;
							startY = i;
							setIcon(buttonArray[i][j], "src/started.jpg");
						} else if (clicks == 2) {
							endX = j;
							endY = i;
							setIcon(buttonArray[i][j], "src/finish1.png");
							clicks++;
							Graph<String, Integer> g = CreateGraph.build(size);
							currGraph = g;
						} 
					} else if (source == buttonArray[i][j] && clicks > 2 && !done) {
						int removeX = j;
						int removeY = i;
						setIcon(buttonArray[i][j], "src/obstacle.png");
						
						for (String v : currGraph.listVertices()) {
							for (String c : currGraph.childVertices(v)) {
								if (c.equals(removeX + "," + removeY + "(1)")) {
									currGraph.removeEdge(1, v, removeX + "," + removeY);
								}
							}
						}
						if (currGraph.containsVertex(removeX + "," + removeY)) {
							currGraph.removeVertex(removeX + "," + removeY);
						}
					}
				}
			}
		}
		
		public void setIcon(JButton b, String imageFile) {
			Image image = null; 
			try {
				image = ImageIO.read(new File(imageFile)).getScaledInstance(b.getWidth(), b.getHeight(), Image.SCALE_DEFAULT);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			b.setIcon(new ImageIcon(image));
		}
		
	}
}
