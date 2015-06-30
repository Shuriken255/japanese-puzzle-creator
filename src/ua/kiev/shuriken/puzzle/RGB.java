package ua.kiev.shuriken.puzzle;

// Red Green Blue
public class RGB{
	int r, g, b;
	public RGB(int rgb){
		r = (rgb) >> 16 & 0xFF;
		g = (rgb) >> 8 & 0xFF;
		b = (rgb) & 0xFF;
	}
	
	public boolean equalsTo(RGB color, int range){
		int difference = Math.abs(color.r-r)
				+ Math.abs(color.g-g)
				+ Math.abs(color.b-b);
		if(difference <= range){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isWhite(RGB color){
		if(color.r > 240 && color.g > 240 && color.b > 240){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isWhite(int col){
		RGB color = new RGB(col);
		if(color.r > 240 && color.g > 240 && color.b > 240){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isBlack(RGB color){
		if(color.r < 15 && color.g < 15 && color.b < 15){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isBlack(int col){
		RGB color = new RGB(col);
		if(color.r < 15 && color.g < 15 && color.b < 15){
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString(){
		return r + " " + g + " " + b;
	}
}
