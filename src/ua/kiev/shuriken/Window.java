package ua.kiev.shuriken;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	
	Manager manager = new Manager();
	String currentFormat = "png";
	boolean generateAnswers = true;
	
	JLabel picturesList = new JLabel();
	JTextArea list = new JTextArea();
	JScrollPane pane = new JScrollPane(list);
	JButton refresh = new JButton("Refresh");
	JCheckBox b = new JCheckBox("Generate answers");
	JLabel l = new JLabel();
	JLabel format = new JLabel();
	JButton export = new JButton("Export");
	JButton deleteAnswers = new JButton("Clear answers folder");
	JButton deletePuzzles = new JButton("Clear puzzles folder");
	
	ButtonGroup group = new ButtonGroup();
	JRadioButton png = new JRadioButton("PNG", true);
	JRadioButton jpg = new JRadioButton("JPG");
	JRadioButton bmp = new JRadioButton("BMP");
	JRadioButton gif = new JRadioButton("GIF");
	
	
	@SuppressWarnings("static-access")
	public Window(){
		super("Japanese Puzzle Creator");
		createGUI();
		manager.setWindow(this);
		manager.setPaths("pictures", "puzzles", "answers");
	}
	
	@SuppressWarnings("static-access")
	public void createGUI(){
		this.setVisible(true);
		this.setBounds(500, 200, 300, 510);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		this.add(picturesList);
		picturesList.setBounds(112, 0, 270, 20);
		
		pane.setBounds(10, 20, 270, 200);
		pane.setBorder(BorderFactory.createLineBorder(Color.black));
		list.setAlignmentY(TOP_ALIGNMENT);
		list.setAlignmentX(LEFT_ALIGNMENT);
		list.setEditable(false);
		list.setText(Manager.getList("pictures"));
		list.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(pane);
		
		this.add(refresh);
		refresh.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				list.setText(Manager.getList("pictures"));
			}
		});
		refresh.setBounds(10, 225, 270, 40);
		
		this.add(b);
		b.setSelected(true);
		b.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(generateAnswers){
					generateAnswers = false;
				} else {
					generateAnswers = true;
				}
			}
		});
		b.setBounds(10, 280, 270, 20);
		b.setVisible(true);
		
		this.add(l);
		l.setBounds(10, 350, 270, 20);
		l.setText("Progress: Loading GUI...");
		l.setVisible(true);
		
		this.add(export);
		export.setBounds(10, 375, 270, 40);
		export.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Thread t = new Thread(manager);
				t.start();
			}
		});
		
		this.add(format);
		format.setBounds(10, 300, 270, 20);
		format.setText("Format to use:");
		
		group.add(png);
		group.add(jpg);
		group.add(bmp);
		group.add(gif);
		this.add(png);
		this.add(jpg);
		this.add(bmp);
		this.add(gif);
		
		png.setBounds(10, 320, 60, 20);
		jpg.setBounds(80, 320, 60, 20);
		bmp.setBounds(150, 320, 60, 20);
		gif.setBounds(220, 320, 60, 20);
		
		png.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.format = "png";
			}
		});
		
		jpg.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.format = "jpg";
			}
		});
		
		bmp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.format = "bmp";
			}
		});
		
		gif.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.format = "gif";
			}
		});
		
		this.add(deleteAnswers);
		deleteAnswers.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.deleteAnswers();
			}
		});
		deleteAnswers.setBounds(10, 425, 270, 20);
		
		
		this.add(deletePuzzles);
		deletePuzzles.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.deletePuzzles();
			}
		});
		deletePuzzles.setBounds(10, 450, 270, 20);
		
		
		
		l.setText("Progress: Analyzing 'pictures' folder...");
		picturesList.setText("Pictures list");
		l.setText("Progress: Setting");
	}
}