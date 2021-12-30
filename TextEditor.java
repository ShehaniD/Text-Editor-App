import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {

	JTextArea textArea;
	JScrollPane scrollpane;
	JLabel fontlabel;
	JSpinner fontSizeSpinner;
	JButton fontColor;
	JComboBox fontBox;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem, exitItem, saveItem;

	TextEditor() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Text Editor");
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);

		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(450, 450));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 50));

		scrollpane = new JScrollPane(textArea);
		scrollpane.setPreferredSize(new Dimension(450, 450));
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		fontlabel = new JLabel("Font: ");

		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(
						new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));

			}

		});

		fontColor = new JButton("Color");
		fontColor.addActionListener(this);

		// take all fonts in java and put them in the array
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);

		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");

		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);

		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);

		this.setJMenuBar(menuBar);
		this.add(fontlabel);
		this.add(fontSizeSpinner);
		this.add(fontColor);
		this.add(fontBox);
		this.add(scrollpane);

		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fontColor) {
			JColorChooser colorChooser = new JColorChooser();

			Color color = colorChooser.showDialog(null, "Choose A Color", Color.black);

			textArea.setForeground(color);
		}

		if (e.getSource() == fontBox) {
			textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));

		}

		if (e.getSource() == openItem) {
			  if(e.getSource()==openItem) {
				   JFileChooser fileChooser = new JFileChooser();
				   fileChooser.setCurrentDirectory(new File("."));
				   FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
				   fileChooser.setFileFilter(filter);
				   
				   int response = fileChooser.showOpenDialog(null);
				   
				   if(response == JFileChooser.APPROVE_OPTION) {
				    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				    Scanner fileIn = null;
				    
				    try {
				     fileIn = new Scanner(file);
				     if(file.isFile()) {
				      while(fileIn.hasNextLine()) {
				       String line = fileIn.nextLine()+"\n";
				       textArea.append(line);
				      }
				     }
				    } catch (FileNotFoundException e1) {
				     // TODO Auto-generated catch block
				     e1.printStackTrace();
				    }
				    finally {
				     fileIn.close();
				    }
				   }
				  }
		}
		

		if (e.getSource() == saveItem) {
			JFileChooser fileChooser = new JFileChooser();

			// Default file path to project folder
			fileChooser.setCurrentDirectory(new File("."));
			int response = fileChooser.showSaveDialog(null);

			// Check if the response is 0 or 1
			if (response == JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;

				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					fileOut.close();
				}
			}
		}
	}
}
