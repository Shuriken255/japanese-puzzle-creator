package ua.kiev.shuriken.puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
	
	
	
public class Converter{
	public BufferedImage loadImage(String path){
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Failed to load " + path + "!!!");
			return null;
		}
	}
	
	public boolean puzzleIsColored(String path){
		BufferedImage image = loadImage(path);
		for(int y = 0; y<image.getHeight(); y++){
			for(int x = 0; x<image.getWidth(); x++){
				RGB color = new RGB(image.getRGB(x, y));
				if(!RGB.isBlack(color) && !RGB.isWhite(color)){
					return true;
				}
			}
		}
		return false;
	}
	
	private void drawGrid(Graphics gr, Puzzle p){
		gr.setColor(Color.black);
		
		int maxX = (p.sizeX+p.maxNumberX)*20+1;
		int maxY = (p.sizeY+p.maxNumberY)*20+1;
			gr.fillRect(19,19,maxX+2,3);
			gr.fillRect(19,19,3,maxY);
			gr.fillRect(19,maxY+18,maxX+2,3);
			gr.fillRect(maxX+19,19,3,maxY+2);
			gr.fillRect(19+p.maxNumberX*20, 19, 3, maxY);
			gr.fillRect(19, 19+p.maxNumberY*20, maxX+2, 3);
			for(int y = 1; y<p.sizeY; y++){
				gr.drawLine(20,
						20+y*20+p.maxNumberY*20,
						24+p.maxNumberX*20+p.sizeX*20-2,
						20+y*20+p.maxNumberY*20);
			}
			for(int x = 1; x<p.sizeX; x++){
				gr.drawLine(20+x*20+p.maxNumberX*20,
						20,
						20+x*20+p.maxNumberX*20,
						20+p.maxNumberY*20+p.sizeY*20-2);
			}
			
			for(int y = 5; y<p.sizeY; y +=5){
				gr.fillRect(20,
						19+y*20+p.maxNumberY*20,
						p.maxNumberX*20+p.sizeX*20+2,
						3);
			}
			for(int x = 5; x<p.sizeX; x +=5){
				gr.fillRect(19+x*20+p.maxNumberX*20,
						20,
						3,
						p.maxNumberY*20+p.sizeY*20);
			}

	}
	
	
	
	private void drawNumbers(Graphics gr, Puzzle p){
		for(int i = p.numbersX.length-1; i>=0; i--){
			if(p.numbersX[i].length >= 0){
				for(int j = p.numbersX[i].length-1; j >= 0; j--){
					if(p.numbersX[i][j]<10){
						gr.drawString(Integer.toString(p.numbersX[i][j]),
								27+p.maxNumberX*20-p.numbersX[i].length*20+j*20,
								35+p.maxNumberY*20+i*20);
					} else {
						if(p.numbersX[i][j]<100){
							gr.drawString(Integer.toString(p.numbersX[i][j]),
									24+p.maxNumberX*20-p.numbersX[i].length*20+j*20,
									35+p.maxNumberY*20+i*20);
						} else {
							gr.setFont(new Font(gr.getFont().getName(),
									gr.getFont().getStyle(),
									10));
							gr.drawString(Integer.toString(p.numbersX[i][j]),
									22+p.maxNumberX*20-p.numbersX[i].length*20+j*20,
									35+p.maxNumberY*20+i*20);
							gr.setFont(new Font(gr.getFont().getName(),
									gr.getFont().getStyle(),
									12));
						}
					}
				}
			}
		}
		for(int i = p.numbersY.length-1; i>=0; i--){
			if(p.numbersY[i].length >= 0){
				for(int j = p.numbersY[i].length-1; j >= 0; j--){
					if(p.numbersY[i][j]<10){
						gr.drawString(Integer.toString(p.numbersY[i][j]),
								27+p.maxNumberX*20+i*20,
								p.maxNumberY*20-p.numbersY[i].length*20+j*20+35);
					} else {
						if(p.numbersX[i][j]<100){
							gr.drawString(Integer.toString(p.numbersY[i][j]),
									24+p.maxNumberX*20+i*20,
									p.maxNumberY*20-p.numbersY[i].length*20+j*20+35);
						} else {
							gr.setFont(new Font(gr.getFont().getName(),
									gr.getFont().getStyle(),
									10));
							gr.drawString(Integer.toString(p.numbersY[i][j]),
									22+p.maxNumberX*20+i*20,
									p.maxNumberY*20-p.numbersY[i].length*20+j*20+35);
							gr.setFont(new Font(gr.getFont().getName(),
									gr.getFont().getStyle(),
									12));
						}
					}
				}
			}
		}
	}
	
	private void drawColoredNumbers(Graphics gr, ColoredPuzzle p){
		for(int i = p.numbersX.length-1; i>=0; i--){
			if(p.numbersX[i].length >= 0){
				for(int j = p.numbersX[i].length-1; j >= 0; j--){
					gr.setColor(new Color(p.numbersX[i][j].rgb.r,
							p.numbersX[i][j].rgb.g,
							p.numbersX[i][j].rgb.b));
					gr.fillRect(p.maxNumberX*20-p.numbersX[i].length*20+j*20+20, 
							p.maxNumberY*20+i*20+20, 20, 20);
					if(p.numbersX[i][j].rgb.r < 100 &&
							p.numbersX[i][j].rgb.g  < 100 &&
							p.numbersX[i][j].rgb.b  < 100){
						gr.setColor(Color.white);
					} else {
						gr.setColor(Color.black);
					}
					if(p.numbersX[i][j].value<10){
						gr.drawString(Integer.toString(p.numbersX[i][j].value),
								27+p.maxNumberX*20-p.numbersX[i].length*20+j*20,
								35+p.maxNumberY*20+i*20);
					} else {
						if(p.numbersX[i][j].value<100){
							gr.drawString(Integer.toString(p.numbersX[i][j].value),
									24+p.maxNumberX*20-p.numbersX[i].length*20+j*20,
									35+p.maxNumberY*20+i*20);
						} else {
							gr.setFont(new Font(gr.getFont().getName(),
									gr.getFont().getStyle(),
									10));
							gr.drawString(Integer.toString(p.numbersX[i][j].value),
									22+p.maxNumberX*20-p.numbersX[i].length*20+j*20,
									35+p.maxNumberY*20+i*20);
							gr.setFont(new Font(gr.getFont().getName(),
									gr.getFont().getStyle(),
									12));
						}
					}
				}
			}
		}
		for(int i = p.numbersY.length-1; i>=0; i--){
			if(p.numbersY[i].length >= 0){
				for(int j = p.numbersY[i].length-1; j >= 0; j--){
					gr.setColor(new Color(p.numbersY[i][j].rgb.r,
							p.numbersY[i][j].rgb.g,
							p.numbersY[i][j].rgb.b));
					gr.fillRect(p.maxNumberX*20+i*20+20, 
							p.maxNumberY*20-p.numbersY[i].length*20+20+j*20, 20, 20);
					if(p.numbersY[i][j].rgb.r < 100 &&
							p.numbersY[i][j].rgb.g < 100 &&
							p.numbersY[i][j].rgb.b < 100){
						gr.setColor(Color.white);
					} else {
						gr.setColor(Color.black);
					}
					if(p.numbersY[i][j].value<10){
						gr.drawString(Integer.toString(p.numbersY[i][j].value),
								27+p.maxNumberX*20+i*20,
								p.maxNumberY*20-p.numbersY[i].length*20+j*20+35);
					} else {
						if(p.numbersY[i][j].value<100){
							gr.drawString(Integer.toString(p.numbersY[i][j].value),
									24+p.maxNumberX*20+i*20,
									p.maxNumberY*20-p.numbersY[i].length*20+j*20+35);
						} else {
							gr.setFont(new Font(gr.getFont().getName(),
									gr.getFont().getStyle(),
									10));
							gr.drawString(Integer.toString(p.numbersY[i][j].value),
									22+p.maxNumberX*20+i*20,
									p.maxNumberY*20-p.numbersY[i].length*20+j*20+35);
							gr.setFont(new Font(gr.getFont().getName(),
									gr.getFont().getStyle(),
									12));
						}
					}
				}
			}
		}
	}
	
	public void drawAnswer(Puzzle p, Graphics gr, BufferedImage puzzleImage){
		for(int x = 0; x<puzzleImage.getWidth(); x++){
			for(int y = 0; y<puzzleImage.getHeight(); y++){
				gr.setColor(new Color(puzzleImage.getRGB(x, y)));
				gr.fillRect((1+p.maxNumberX+x)*20, (1+p.maxNumberY+y)*20, 20, 20);
			}
		}
	}
	
	public void convertBlackWhitePuzzle(String from, String to, String format){
		Puzzle p = Puzzle.getPuzzleFromImage(loadImage(from));
		BufferedImage bf = new BufferedImage((p.sizeX+p.maxNumberX+2)*20,
				(p.sizeY+p.maxNumberY+2)*20,
				BufferedImage.TYPE_INT_RGB);
		Graphics gr = bf.getGraphics();
		gr.setColor(Color.white);
		gr.fillRect(0, 0, bf.getWidth(), bf.getHeight());
		gr.setColor(Color.black);
		drawNumbers(gr, p);
		drawGrid(gr, p);
		File f = new File(to);
		try {
			ImageIO.write(bf, format, f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't export file to "+to);
		}
	}
	
	public void convertColoredPuzzle(String from, String to, String format){
		ColoredPuzzle p = ColoredPuzzle.getColoredPuzzleFromImage(loadImage(from));
		BufferedImage bf = new BufferedImage((p.sizeX+p.maxNumberX+2)*20,
				(p.sizeY+p.maxNumberY+2)*20,
				BufferedImage.TYPE_INT_RGB);
		Graphics gr = bf.getGraphics();
		gr.setColor(Color.white);
		gr.fillRect(0, 0, bf.getWidth(), bf.getHeight());
		gr.setColor(Color.black);
		this.drawColoredNumbers(gr, p);
		this.drawGrid(gr, p);
		File f = new File(to);
		try {
			ImageIO.write(bf, format, f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't export file to "+to);
		}
	}
	
	public void convertBlackWhitePuzzle(String from, String to, String format, String answer){
		Puzzle p = Puzzle.getPuzzleFromImage(loadImage(from));
		BufferedImage bf = new BufferedImage((p.sizeX+p.maxNumberX+2)*20,
				(p.sizeY+p.maxNumberY+2)*20,
				BufferedImage.TYPE_INT_RGB);
		Graphics gr = bf.getGraphics();
		gr.setColor(Color.white);
		gr.fillRect(0, 0, bf.getWidth(), bf.getHeight());
		gr.setColor(Color.black);
		this.drawNumbers(gr, p);
		this.drawGrid(gr, p);
		File f = new File(to);
		try {
			ImageIO.write(bf, format, f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't export file to "+to);
		}
		
		drawAnswer(p, gr, loadImage(from));
		drawGrid(gr, p);
		f = new File(answer);
		try {
			ImageIO.write(bf, format, f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't export file to "+to);
		}
	}
	
	public void convertColoredPuzzle(String from, String to, String format, String answer){
		ColoredPuzzle p = ColoredPuzzle.getColoredPuzzleFromImage(loadImage(from));
		BufferedImage bf = new BufferedImage((p.sizeX+p.maxNumberX+2)*20,
				(p.sizeY+p.maxNumberY+2)*20,
				BufferedImage.TYPE_INT_RGB);
		Graphics gr = bf.getGraphics();
		gr.setColor(Color.white);
		gr.fillRect(0, 0, bf.getWidth(), bf.getHeight());
		gr.setColor(Color.black);
		this.drawColoredNumbers(gr, p);
		this.drawGrid(gr, p);
		File f = new File(to);
		try {
			ImageIO.write(bf, format, f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't export file to "+to);
		}
		drawAnswer(p, gr, loadImage(from));
		drawGrid(gr, p);
		f = new File(answer);
		try {
			ImageIO.write(bf, format, f);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't export file to "+to);
		}
	}
}