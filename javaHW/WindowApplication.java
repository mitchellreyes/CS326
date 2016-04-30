/**
Renee Iinuma
Homework 8
CS326
Dr. Mircea Nicolescu
December 3, 2014
*/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

// Drawing class
class BoxDrawing extends JComponent
{
	WindowApplication app;
	int redVal;
	int blueVal;
	int greenVal;
	// Constructor
	public BoxDrawing(WindowApplication a)
	{
		app = a;
	}
	public void paint(Graphics g)
	{
		Dimension d = getSize();
		Color newColor = new Color( redVal, greenVal, blueVal );
		g.setColor(newColor);
		g.fillRect(1, 1, d.width-2, d.height-2);
	}
	public void setRGB( int R, int G, int B)
	{
		redVal = R;
		greenVal = G;
		blueVal = B;
		repaint();
	}

}
// Application
public class WindowApplication extends JFrame
{
	protected BoxDrawing drawBox;
	protected JLabel labelR;
	protected JLabel labelG;
	protected JLabel labelB;
	protected JTextField tfR;
	protected JTextField tfG;
	protected JTextField tfB;
	protected JButton minusR, plusR;
	protected JButton minusG, plusG;
	protected JButton minusB, plusB;
	protected JButton buttonSave, buttonReset;
	protected JList listColors;
	static protected String colors[] = new String[11];
	static protected int rgb[][] = new int[11][3];
	protected int valR = 255, valG, valB;
	protected int selectedColor = 0;
	static protected int numColors = 0;

	// Main function
	public static void main(String argv []) throws IOException
	{

		// File IO
		FileInputStream istream = new FileInputStream( "colors.txt");
		InputStreamReader reader = new InputStreamReader( istream );
		StreamTokenizer tokens = new StreamTokenizer( reader );
		String s;
		int i = 0;

		// Read in from file
		while( tokens.nextToken() != tokens.TT_EOF )
		{
			colors[i] = (String) tokens.sval;
			tokens.nextToken();
			rgb[i][0] = (int) tokens.nval;
			tokens.nextToken();
			rgb[i][1] = (int) tokens.nval;
			tokens.nextToken();
			rgb[i][2] = (int) tokens.nval;

			i++;
		}
		numColors = i;
		istream.close();

		new WindowApplication("Color Sampler");
	}

	// Window constructor
	public WindowApplication(String title)
	{
		super(title);
		setBounds(100, 100, 450, 395);
		addWindowListener(new WindowDestroyer());
		drawBox = new BoxDrawing(this);
		labelR = new JLabel("Red:");
		labelG = new JLabel("Green:");
		labelB = new JLabel("Blue:");
		tfR = new JTextField("");
		tfG = new JTextField("");
		tfB = new JTextField("");
		minusR = new JButton("-");
		plusR = new JButton("+");
		minusG = new JButton("-");
		plusG = new JButton("+");
		minusB = new JButton("-");
		plusB = new JButton("+");
		buttonSave = new JButton("Save");
		buttonReset = new JButton("Reset");

		listColors = new JList();
		listColors.addListSelectionListener( new ListHandler(this));
		getContentPane().add(listColors);


		listColors.setListData( colors );
		getContentPane().add( listColors );

		listColors.setBounds( 275, 20, 150, 330 );

		// Add content
		getContentPane().add(listColors);
		getContentPane().setLayout(null);
		getContentPane().add(drawBox);
		getContentPane().add(labelR);
		getContentPane().add(labelG);
		getContentPane().add(labelB);
		getContentPane().add(tfR);
		getContentPane().add(tfG);
		getContentPane().add(tfB);
		getContentPane().add(minusR);
		getContentPane().add(plusR);
		getContentPane().add(minusG);
		getContentPane().add(plusG);
		getContentPane().add(minusB);
		getContentPane().add(plusB);		
		getContentPane().add(buttonSave);
		getContentPane().add(buttonReset);
		drawBox.setBounds(20, 20, 240, 150);

		// Red Label and text
		labelR.setBounds(20, 190, 50, 30);
		tfR.setBounds(90, 190, 50, 30);
		minusR.setBounds( 150, 190, 50, 30);
		plusR.setBounds( 210, 190, 50, 30);
		minusR.addActionListener( new ActionHandler(this) );
		plusR.addActionListener( new ActionHandler(this) );

		// Green Label and text
		labelG.setBounds(20, 230, 50, 30);
		tfG.setBounds(90, 230, 50, 30);
		minusG.setBounds(150, 230, 50, 30);
		plusG.setBounds(210, 230, 50, 30);
		minusG.addActionListener( new ActionHandler(this) );
		plusG.addActionListener( new ActionHandler(this) );

		// Blue Label and text
		labelB.setBounds(20, 270, 50, 30);
		tfB.setBounds(90, 270, 50, 30);
		minusB.setBounds(150, 270, 50, 30);
		plusB.setBounds(210, 270, 50, 30);
		minusB.addActionListener( new ActionHandler(this) );
		plusB.addActionListener( new ActionHandler(this) );

		// Save and reset
		buttonSave.setBounds(60, 320, 80, 30);
		buttonReset.setBounds(150, 320, 80, 30);
		buttonSave.addActionListener( new ActionHandler(this) );
		buttonReset.addActionListener( new ActionHandler(this) );

		// Set values
		String temp = "" + valR;
		tfR.setText( temp );
		temp = "" + valG;
		tfG.setText( temp );
		temp = "" + valB;
		tfB.setText( temp );
		setVisible(true);
	}

	// Define window adapter
	private class WindowDestroyer extends WindowAdapter 
	{
		// Close window
		public void windowClosing(WindowEvent e) 
		{
			try
			{

				// Output file
				FileOutputStream ostream = new FileOutputStream( "colors.txt" );
				PrintWriter writer = new PrintWriter( ostream );
				int i = 0;
				
				while( i < numColors )
				{
					writer.println( colors[i] + "\t" + rgb[i][0] + 
												"\t" + rgb[i][1] +
												"\t" + rgb[i][2] );
					i++;
				}
				writer.flush();
				ostream.close();
			}
			catch(IOException exc)
			{
				System.exit(0);
			}

			System.exit(0);
		}
	}

	//Define list listener
	private class ListHandler implements ListSelectionListener
	{
		WindowApplication frame;
		// List handler constructor
		public ListHandler( WindowApplication mainFrame )
		{
			frame = mainFrame;
			listColors.setSelectedIndex(0);
			valR = rgb[selectedColor][0];
			valG = rgb[selectedColor][1];
			valB = rgb[selectedColor][2];
			String temp = "" + valR;
			tfR.setText( temp );
			temp = "" + valG;
			tfG.setText( temp );
			temp = "" + valB;
			tfB.setText( temp );
			drawBox.setRGB( valR, valG, valB );
			frame.setTitle( "Color Sampler");
		}
		// Selection change
		public void valueChanged( ListSelectionEvent e)
		{
			if( e.getSource() == listColors )
			{
				if( !e.getValueIsAdjusting() )
				{
					selectedColor = listColors.getSelectedIndex();
					String str = (String) listColors.getSelectedValue();
					valR = rgb[selectedColor][0];
					valG = rgb[selectedColor][1];
					valB = rgb[selectedColor][2];
					String temp = "" + valR;
					tfR.setText( temp );
					temp = "" + valG;
					tfG.setText( temp );
					temp = "" + valB;
					tfB.setText( temp );
					drawBox.setRGB( valR, valG, valB );
					frame.setTitle( "Color Sampler");
				}
			}
		}
	}

	// Define action listener
	private class ActionHandler implements ActionListener
	{
		public WindowApplication frame;
		// Constructor
		public ActionHandler(WindowApplication mainFrame)
		{
			frame = mainFrame;
		}
		// Button click
		public void actionPerformed( ActionEvent e )
		{
			int tempVal;
			if( e.getSource() == buttonSave )
			{
				rgb[selectedColor][0] = valR;
				rgb[selectedColor][1] = valG;
				rgb[selectedColor][2] = valB;
				frame.setTitle( "Color Sampler");
			}
			else if( e.getSource() == buttonReset )
			{
				valR = rgb[selectedColor][0];
				valG = rgb[selectedColor][1];
				valB = rgb[selectedColor][2];
				frame.setTitle( "Color Sampler");
			}
			else if( e.getSource() == minusR )
			{
				valR = Integer.parseInt( tfR.getText() );
				if( valR > 0)
				{
					valR -= 5;
					frame.setTitle( "Color Sampler*");
				}
			}
			else if( e.getSource() == plusR )
			{
				valR = Integer.parseInt( tfR.getText() );
				if( valR < 255)
				{
					valR += 5;
					frame.setTitle( "Color Sampler*");
				}				
			}
			else if( e.getSource() == minusG )
			{
				valG = Integer.parseInt( tfG.getText() );
				if( valG > 0)
				{
					valG -= 5;
					frame.setTitle( "Color Sampler*");
				}				
			}
			else if( e.getSource() == plusG )
			{
				valG = Integer.parseInt( tfG.getText() );
				if( valG < 255)
				{
					valG += 5;
					frame.setTitle( "Color Sampler*");
				}		
			}
			else if( e.getSource() == minusB )
			{
				valB = Integer.parseInt( tfB.getText() );
				if( valB > 0)
				{
					valB -= 5;
					frame.setTitle( "Color Sampler*");
				}
			}
			else if( e.getSource() == plusB )
			{
				valB = Integer.parseInt( tfB.getText() );
				if( valB < 255)
				{
					valB += 5;
					frame.setTitle( "Color Sampler*");
				}	
			}

			// Set text fields
			String temp = "" + valR;
			tfR.setText( temp );
			temp = "" + valG;
			tfG.setText( temp );
			temp = "" + valB;
			tfB.setText( temp );
			drawBox.setRGB( valR, valG, valB );
		}
	}
}

