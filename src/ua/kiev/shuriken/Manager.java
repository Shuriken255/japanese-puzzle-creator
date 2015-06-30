package ua.kiev.shuriken;

import java.io.File;
import ua.kiev.shuriken.puzzle.*;

public class Manager implements Runnable{
	static Converter convert = new Converter();
	
	static String pathToPictures;
	static String pathToPuzzles;
	static String pathToAnswers;
	private static Window window;
	
	static String format = "png";
	//p(b1)*p(A/B1)+p(b2)*p(A/B2);
	static void setWindow(Window w){
		window = w;
	}
	
	static void setPaths(String pictures, String puzzles, String answers){
		pathToPictures = pictures;
		pathToPuzzles = puzzles;
		pathToAnswers = answers;
	}
	
	static String[] list;
	static String getList(String path){
		File f = new File(path);
		if(f.exists() && f.isDirectory()){
			list = f.list();
			StringBuilder sb = new StringBuilder();
			if(list.length == 0){
				sb.append("Pictures list is empty.\n" +
						"Add some pictures to 'pictures' folder " +
						"to convert your pictures into puzzles.");
			} else {
				for(int i = 0; i < list.length; i++){
					sb.append(list[i] + " - ");
					if(convert.puzzleIsColored(path+"\\"+list[i])){
						ColoredPuzzle p = ColoredPuzzle.getColoredPuzzle(path+"\\"+list[i]);
						sb.append("Colored, " + Integer.toString(p.getSizeX()) +
								"x" + Integer.toString(p.getSizeY()));
					} else {
						Puzzle p = Puzzle.getPuzzle(path+"\\"+list[i]);
						sb.append("Black-White, " + Integer.toString(p.getSizeX()) +
								"x" + Integer.toString(p.getSizeY()));
					}
					if(i != list.length-1){
						sb.append("\n");
					}
				}
			}
			return sb.toString();
		} else {
			return null;
		}
	}
	
	@Override
	public void run(){
		if(window.generateAnswers){
			for(String s:list){
				window.l.setText("Progress: converting " + s);
				if(convert.puzzleIsColored(pathToPictures + "\\" + s)){
					StringBuilder sb = new StringBuilder();
					sb.append(pathToPuzzles + "\\" + s);
					sb.delete(sb.length()-4, sb.length());
					sb.append("."+format);
					StringBuilder sba = new StringBuilder();
					sba.append(pathToAnswers + "\\" + s);
					sba.delete(sba.length()-4, sb.length());
					sba.append("."+format);
					convert.convertColoredPuzzle(pathToPictures+"\\"+s,
							sb.toString(), format,
							sba.toString());
				} else {
					StringBuilder sb = new StringBuilder();
					sb.append(pathToPuzzles + "\\" + s);
					sb.delete(sb.length()-4, sb.length());
					sb.append("."+format);
					StringBuilder sba = new StringBuilder();
					sba.append(pathToAnswers + "\\" + s );
					sba.delete(sba.length()-4, sb.length());
					sba.append("."+format);
					convert.convertBlackWhitePuzzle(pathToPictures+"\\"+s,
							sb.toString(), format,
							sba.toString());
				}
			}
		} else {
			for(String s:list){
				window.l.setText("Progress: converting " + s);
				if(convert.puzzleIsColored(pathToPictures + "\\" + s)){
					StringBuilder sb = new StringBuilder();
					sb.append(pathToPuzzles + "\\" + s);
					sb.delete(sb.length()-4, sb.length());
					sb.append("."+format);
					convert.convertColoredPuzzle(pathToPictures+"\\"+s,
							sb.toString(), format);
				} else {
					StringBuilder sb = new StringBuilder();
					sb.append(pathToPuzzles + "\\" + s);
					sb.delete(sb.length()-4, sb.length());
					sb.append("."+format);
					convert.convertBlackWhitePuzzle(pathToPictures+"\\"+s,
							sb.toString(), format);
				}
			}
		}
		window.l.setText("Progress: Done!");
	}
	
	public static void deletePuzzles(){
		new Thread(){
			public void run(){
				window.l.setText("Progress: scanning answers directory...");
				File directory = new File(pathToPuzzles);
				String[] files = directory.list();
				for(String s:files){
					new File(pathToPuzzles+"\\"+s).delete();
					window.l.setText("Progress: deleting "+s);
				}
				window.l.setText("Progress: Done!");
			}
		}.start();
	}
	
	public static void deleteAnswers(){
		new Thread(){
			public void run(){
				window.l.setText("Progress: scanning answers directory...");
				File directory = new File(pathToAnswers);
				String[] files = directory.list();
				for(String s:files){
					new File(pathToAnswers+"\\"+s).delete();
					window.l.setText("Progress: deleting "+s);
				}
				window.l.setText("Progress: Done!");
			}
		}.start();
	}
}
