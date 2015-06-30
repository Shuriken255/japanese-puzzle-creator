package ua.kiev.shuriken.puzzle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;


public class Puzzle {
	int sizeX;
	int sizeY;
	int[][] numbersX;
	int[][] numbersY;
	
	public int maxNumberX = 0;
	public int maxNumberY = 0;
	
	public Puzzle(int x, int y){
		sizeX = x;
		sizeY = y;
		numbersX = new int[y][];
		numbersY = new int[x][];
	}
	
	public int getSizeX(){
		return sizeX;
	}
	
	public int getSizeY(){
		return sizeY;
	}
	
	public int[] getNumbersX(int y){
		return numbersX[y];
	}
	
	public int[] getNumbersY(int x){
		return numbersY[x];
	}
	
	public void setNumbersX(int y, int[] array){
		numbersX[y] = array;
	}
	
	public void setNumbersY(int x, int[] array){
		numbersY[x] = array;
	}
	
	public static Puzzle getPuzzleFromImage(BufferedImage image){
		Puzzle p = new Puzzle(image.getWidth(), image.getHeight());
		for(int y = 0; y<p.sizeY; y++){
			boolean counts = false;
			int counter = 0;
			int[] array = new int[0];
			for(int x = 0; x<p.sizeX; x++){
				if(RGB.isBlack(image.getRGB(x, y))){
					counts = true;
					counter++;
					if(x == p.sizeX-1){
						array = Arrays.copyOf(array, array.length+1);
						array[array.length-1] = counter;
						counter = 0;
					}
				} else {
					if(counts){
						array = Arrays.copyOf(array, array.length+1);
						array[array.length-1] = counter;
						counts = false;
						counter = 0;
					}
				}
			}
			p.setNumbersX(y, array);
		}
		
		for(int x = 0; x<p.sizeX; x++){
			boolean counts = false;
			int counter = 0;
			int[] array = new int[0];
			for(int y = 0; y<p.sizeY; y++){
				if(RGB.isBlack(image.getRGB(x, y))){
					counts = true;
					counter++;
					if(y == p.sizeY-1){
						array = Arrays.copyOf(array, array.length+1);
						array[array.length-1] = counter;
						counter = 0;
					}
				} else {
					if(counts){
						array = Arrays.copyOf(array, array.length+1);
						array[array.length-1] = counter;
						counts = false;
						counter = 0;
					}
				}
			}
			p.setNumbersY(x, array);
		}
		for(int x = 0; x<p.sizeX; x++){
			if(p.getNumbersY(x).length > p.maxNumberY){
				p.maxNumberY = p.getNumbersY(x).length;
			}
		}
		for(int y = 0; y<p.sizeY; y++){
			if(p.getNumbersX(y).length > p.maxNumberX){
				p.maxNumberX = p.getNumbersX(y).length;
			}
		}
		return p;
	}
	
	public static Puzzle getPuzzle(String path){
		File f = new File(path);
		BufferedImage image = null;
		try {
			image = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Puzzle p = new Puzzle(image.getWidth(), image.getHeight());
		for(int y = 0; y<p.sizeY; y++){
			boolean counts = false;
			int counter = 0;
			int[] array = new int[0];
			for(int x = 0; x<p.sizeX; x++){
				if(RGB.isBlack(image.getRGB(x, y))){
					counts = true;
					counter++;
					if(x == p.sizeX-1){
						array = Arrays.copyOf(array, array.length+1);
						array[array.length-1] = counter;
						counter = 0;
					}
				} else {
					if(counts){
						array = Arrays.copyOf(array, array.length+1);
						array[array.length-1] = counter;
						counts = false;
						counter = 0;
					}
				}
			}
			p.setNumbersX(y, array);
		}
		
		for(int x = 0; x<p.sizeX; x++){
			boolean counts = false;
			int counter = 0;
			int[] array = new int[0];
			for(int y = 0; y<p.sizeY; y++){
				if(RGB.isBlack(image.getRGB(x, y))){
					counts = true;
					counter++;
					if(y == p.sizeY-1){
						array = Arrays.copyOf(array, array.length+1);
						array[array.length-1] = counter;
						counter = 0;
					}
				} else {
					if(counts){
						array = Arrays.copyOf(array, array.length+1);
						array[array.length-1] = counter;
						counts = false;
						counter = 0;
					}
				}
			}
			p.setNumbersY(x, array);
		}
		for(int x = 0; x<p.sizeX; x++){
			if(p.getNumbersY(x).length > p.maxNumberY){
				p.maxNumberY = p.getNumbersY(x).length;
			}
		}
		for(int y = 0; y<p.sizeY; y++){
			if(p.getNumbersX(y).length > p.maxNumberX){
				p.maxNumberX = p.getNumbersX(y).length;
			}
		}
		return p;
	}
}