package ua.kiev.shuriken.puzzle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ColoredPuzzle extends Puzzle{
	public static final int RANGE = 50;
	List<RGB> colorList = new ArrayList<RGB>();
	Color[][] numbersX;
	Color[][] numbersY;
	
	
	class Color{
		int value;
		RGB rgb;
		Color(int rgb, int value){
			this.value = value;
			this.rgb = new RGB(rgb);
		}
		
		Color(RGB rgb, int value){
			this.value = value;
			this.rgb = rgb;
		}
		
		@Override
		public String toString(){
			return Integer.toString(value) + ":" + Integer.toHexString(rgb.r*255*255+rgb.g*255+rgb.b);
		}
	}
	
	public void setNumbersX(int position, RGB[] rgb, int values[]){
		numbersX[position] = new Color[values.length];
		for(int i = 0; i<values.length; i++){
			numbersX[position][i] = new Color(rgb[i], values[i]);
		}
	}
	
	public void setNumbersY(int position, RGB[] rgb, int values[]){
		numbersY[position] = new Color[values.length];
		for(int i = 0; i<values.length; i++){
			numbersY[position][i] = new Color(rgb[i], values[i]);
		}
	}
	
	public ColoredPuzzle(int x, int y){
		super(x, y);
		numbersX = new Color[y][];
		numbersY = new Color[x][];
	}
	
	public static ColoredPuzzle getColoredPuzzleFromImage(BufferedImage image){
		ColoredPuzzle p = new ColoredPuzzle(image.getWidth(), image.getHeight());
		for(int y = 0; y<p.sizeY; y++){
			boolean counts = false;
			int counter = 0;
			RGB currentRGB = null;
			RGB[] arrayRGB = new RGB[0];
			int[] values = new int[0];
			for(int x = 0; x<p.sizeX; x++){
				if(RGB.isWhite(image.getRGB(x ,y))){
					if(counts){
						if(x != 0){
							arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
							arrayRGB[arrayRGB.length-1] = currentRGB;
							values = Arrays.copyOf(values, values.length+1);
							values[values.length-1] = counter;
							counter = 0;
						}
					}
					counts = false;
					currentRGB = new RGB(0xFFFFFF);
				} else {
					if(currentRGB == null){
						currentRGB = new RGB(0xFFFFFF);
					}
					if(new RGB(image.getRGB(x, y)).equalsTo(currentRGB,RANGE)){
						counter++;
					} else {
						if(x != 0 && !RGB.isWhite(currentRGB)){
							arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
							arrayRGB[arrayRGB.length-1] = currentRGB;
							values = Arrays.copyOf(values, values.length+1);
							values[values.length-1] = counter;
							counter = 0;
						}
						boolean found = false;
						for(int i = 0; i < p.colorList.size(); i++){
							if(new RGB(image.getRGB(x, y)).equalsTo(p.colorList.get(i),RANGE)){
								found = true;
								currentRGB = p.colorList.get(i);
							}
						}
						if(!found){
							p.colorList.add(new RGB(image.getRGB(x, y)));
							currentRGB = p.colorList.get(p.colorList.size()-1);
						}
						counter++;
					}
					if(x == p.sizeX-1){
						arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
						arrayRGB[arrayRGB.length-1] = currentRGB;
						values = Arrays.copyOf(values, values.length+1);
						values[values.length-1] = counter;
						counter = 0;
					}
					counts = true;
				}
			}
			p.setNumbersX(y, arrayRGB, values);
		}
		for(int x = 0; x<p.sizeX; x++){
			boolean counts = false;
			int counter = 0;
			RGB currentRGB = null;
			RGB[] arrayRGB = new RGB[0];
			int[] values = new int[0];
			for(int y = 0; y<p.sizeY; y++){
				if(RGB.isWhite(image.getRGB(x ,y))){
					if(counts){
						if(y != 0){
							arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
							arrayRGB[arrayRGB.length-1] = currentRGB;
							values = Arrays.copyOf(values, values.length+1);
							values[values.length-1] = counter;
							counter = 0;
						}
					}
					counts = false;
					currentRGB = new RGB(0xFFFFFF);
				} else {
					if(currentRGB == null){
						currentRGB = new RGB(0xFFFFFF);
					}
					if(new RGB(image.getRGB(x, y)).equalsTo(currentRGB,RANGE)){
						counter++;
					} else {
						if(y != 0 && !RGB.isWhite(currentRGB)){
							arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
							arrayRGB[arrayRGB.length-1] = currentRGB;
							values = Arrays.copyOf(values, values.length+1);
							values[values.length-1] = counter;
							counter = 0;
						}
						boolean found = false;
						for(int i = 0; i < p.colorList.size(); i++){
							if(new RGB(image.getRGB(x, y)).equalsTo(p.colorList.get(i),RANGE)){
								found = true;
								currentRGB = p.colorList.get(i);
							}
						}
						if(!found){
							p.colorList.add(new RGB(image.getRGB(x, y)));
							currentRGB = p.colorList.get(p.colorList.size()-1);
						}
						counter++;
					}
					if(y == p.sizeY-1){
						arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
						arrayRGB[arrayRGB.length-1] = currentRGB;
						values = Arrays.copyOf(values, values.length+1);
						values[values.length-1] = counter;
						counter = 0;
					}
					counts = true;
				}
			}
			p.setNumbersY(x, arrayRGB, values);
		}
		for(int x = 0; x<p.sizeX; x++){
			if(p.numbersY[x].length > p.maxNumberY){
				p.maxNumberY = p.numbersY[x].length;
			}
		}
		for(int y = 0; y<p.sizeY; y++){
			if(p.numbersX[y].length > p.maxNumberX){
				p.maxNumberX = p.numbersX[y].length;
			}
		}
		return p;
	}
	
	public static ColoredPuzzle getColoredPuzzle(String path){
		File f = new File(path);
		BufferedImage image = null;
		try {
			image = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ColoredPuzzle p = new ColoredPuzzle(image.getWidth(), image.getHeight());
		for(int y = 0; y<p.sizeY; y++){
			boolean counts = false;
			int counter = 0;
			RGB currentRGB = null;
			RGB[] arrayRGB = new RGB[0];
			int[] values = new int[0];
			for(int x = 0; x<p.sizeX; x++){
				if(RGB.isWhite(image.getRGB(x ,y))){
					if(counts){
						if(x != 0){
							arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
							arrayRGB[arrayRGB.length-1] = currentRGB;
							values = Arrays.copyOf(values, values.length+1);
							values[values.length-1] = counter;
							counter = 0;
						}
					}
					counts = false;
					currentRGB = new RGB(0xFFFFFF);
				} else {
					if(currentRGB == null){
						currentRGB = new RGB(0xFFFFFF);
					}
					if(new RGB(image.getRGB(x, y)).equalsTo(currentRGB,RANGE)){
						counter++;
					} else {
						if(x != 0 && !RGB.isWhite(currentRGB)){
							arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
							arrayRGB[arrayRGB.length-1] = currentRGB;
							values = Arrays.copyOf(values, values.length+1);
							values[values.length-1] = counter;
							counter = 0;
						}
						boolean found = false;
						for(int i = 0; i < p.colorList.size(); i++){
							if(new RGB(image.getRGB(x, y)).equalsTo(p.colorList.get(i),RANGE)){
								found = true;
								currentRGB = p.colorList.get(i);
							}
						}
						if(!found){
							p.colorList.add(new RGB(image.getRGB(x, y)));
							currentRGB = p.colorList.get(p.colorList.size()-1);
						}
						counter++;
					}
					if(x == p.sizeX-1){
						arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
						arrayRGB[arrayRGB.length-1] = currentRGB;
						values = Arrays.copyOf(values, values.length+1);
						values[values.length-1] = counter;
						counter = 0;
					}
					counts = true;
				}
			}
			p.setNumbersX(y, arrayRGB, values);
		}
		for(int x = 0; x<p.sizeX; x++){
			boolean counts = false;
			int counter = 0;
			RGB currentRGB = null;
			RGB[] arrayRGB = new RGB[0];
			int[] values = new int[0];
			for(int y = 0; y<p.sizeY; y++){
				if(RGB.isWhite(image.getRGB(x ,y))){
					if(counts){
						if(y != 0){
							arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
							arrayRGB[arrayRGB.length-1] = currentRGB;
							values = Arrays.copyOf(values, values.length+1);
							values[values.length-1] = counter;
							counter = 0;
						}
					}
					counts = false;
					currentRGB = new RGB(0xFFFFFF);
				} else {
					if(currentRGB == null){
						currentRGB = new RGB(0xFFFFFF);
					}
					if(new RGB(image.getRGB(x, y)).equalsTo(currentRGB,RANGE)){
						counter++;
					} else {
						if(y != 0 && !RGB.isWhite(currentRGB)){
							arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
							arrayRGB[arrayRGB.length-1] = currentRGB;
							values = Arrays.copyOf(values, values.length+1);
							values[values.length-1] = counter;
							counter = 0;
						}
						boolean found = false;
						for(int i = 0; i < p.colorList.size(); i++){
							if(new RGB(image.getRGB(x, y)).equalsTo(p.colorList.get(i),RANGE)){
								found = true;
								currentRGB = p.colorList.get(i);
							}
						}
						if(!found){
							p.colorList.add(new RGB(image.getRGB(x, y)));
							currentRGB = p.colorList.get(p.colorList.size()-1);
						}
						counter++;
					}
					if(y == p.sizeY-1){
						arrayRGB = Arrays.copyOf(arrayRGB, arrayRGB.length+1);
						arrayRGB[arrayRGB.length-1] = currentRGB;
						values = Arrays.copyOf(values, values.length+1);
						values[values.length-1] = counter;
						counter = 0;
					}
					counts = true;
				}
			}
			p.setNumbersY(x, arrayRGB, values);
		}
		for(int x = 0; x<p.sizeX; x++){
			if(p.numbersY[x].length > p.maxNumberY){
				p.maxNumberY = p.numbersY[x].length;
			}
		}
		for(int y = 0; y<p.sizeY; y++){
			if(p.numbersX[y].length > p.maxNumberX){
				p.maxNumberX = p.numbersX[y].length;
			}
		}
		return p;
	}
}
