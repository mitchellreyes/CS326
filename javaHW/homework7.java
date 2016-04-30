import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.event.*;

public class homework7 extends JFrame
{
	protected static String colors[] = new String[11];
	protected static int rgb[][] = new int[11][3];

	protected JList colorList;

	public static void main(String argv[]) throws IOException
	{
		FileInputStream stream = new FileInputStream("colors.txt");
		InputStreamReader reader = new InputStreamReader(stream);
		StreamTokenizer tokens = new StreamTokenizer(reader);
		int i = 0;

		while(tokens.nextToken() != tokens.TT_EOF)
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


		new homework7("Window Application");
	}

	public homework7(String title)
	{
		super(title);
		setBounds(100, 100, 450, 395);
		addWindowListener(new WindowDestroyer());

		colorList = new Jlist();
		colorList.addListSelectionListener(new ListHandler(this));
		getContentPane().add(colorList);

		colorList.setListData(colors);
		getContentPane().add(colorList);

		getContentPane().setLayout(new GridLayout(1,1));
		

		setVisible(true);
	}

	private class WindowDestroyer extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	}
}

