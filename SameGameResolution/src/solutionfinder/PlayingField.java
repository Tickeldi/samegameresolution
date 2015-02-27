package solutionfinder;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PlayingField {
	
	public static final int WHITE = 0xffffffff;
	
	private final int[][] field;
	private boolean[][] grouped = new boolean[9][14];
	
	public PlayingField() {
		field = new int[9][14];
	}
	
	private int[][] fieldClone() {
		int[][] newField = new int[9][14];
		for(int zeile = 0; zeile < newField.length; zeile++) {
			newField[zeile] = this.field[zeile].clone();
		}
		return newField;
	}
	
	public PlayingField(int[][] inputfield) {
		field = inputfield;
	}
	
	public PlayingField(PlayingField input) {
		this.field = input.field;
	}
	
	private Block getBlock(int zeile, int spalte) {
		Block group = new Block(this);
		grouped[zeile][spalte] = true;
		
		if(field[zeile][spalte] != 0) {
			group.addCoordinate(new Coordinate(zeile, spalte));
		}
		else return group;
		
		if(zeile < 8 
				&& field[zeile + 1][spalte] != 0
				&& !grouped[zeile + 1][spalte]
				&& field[zeile + 1][spalte] == field[zeile][spalte]
				){
			group.addBlock(getBlock(zeile +1, spalte));
		}
		if(zeile > 0
				&& field[zeile - 1][spalte] != 0
				&& !grouped[zeile - 1][spalte]
				&& field[zeile - 1][spalte] == field[zeile][spalte]
				){
			group.addBlock(getBlock(zeile -1, spalte));
		}
		if(spalte < 13
				&& field[zeile][spalte + 1] != 0
				&& !grouped[zeile][spalte + 1]
				&& field[zeile][spalte + 1] == field[zeile][spalte]
				){
			group.addBlock(getBlock(zeile, spalte + 1));
		}
		if(spalte > 0
				&& field[zeile][spalte - 1] != 0
				&& !grouped[zeile][spalte - 1]
				&& field[zeile][spalte - 1] == field[zeile][spalte]
				){
			group.addBlock(getBlock(zeile, spalte - 1));
		}
		
		return group;
	}
	
	public List<Block> getBlocks() {
		List<Block> blocks = new ArrayList<>();
		Block block = new Block(this);
		
		for(int zeile = 0; zeile < field.length; zeile++) {
			for(int spalte = 0; spalte < field[0].length; spalte++) {
				if(!grouped[zeile][spalte]) {
					block = getBlock(zeile,spalte);
					if(block.size() > 1) {
						blocks.add(block);
					}
				}
			}
		}
		
		return blocks;
	}
	
	public PlayingField clickAway(Block block) {
		
		int[][] tempField = this.fieldClone();
		
		//Gewählte Blöcke löschen
		for (int i = 0; i < block.size(); i++) {
			tempField[block.getCoordinate(i).getZeile()][block.getCoordinate(i).getSpalte()] = 0;
		}
		
		//Alle Blöcke nach unten fallen lassen
		for (int zeile = 0; zeile < tempField.length; zeile++) {
			for (int spalte = 0; spalte < tempField[0].length; spalte++) {
				if (tempField[zeile][spalte] == 0) {
					for(int up = zeile; up > 0; up--) {
						tempField[up][spalte] = tempField[up - 1][spalte];
						tempField[up - 1][spalte] = 0;
					}
				}
			}
		}
		
		//Alle Blöcke nach rechts hin aufrücken
		for(int spalte = tempField[0].length - 1; spalte > 0; spalte--) {
			if(tempField[8][spalte] == 0) {
				int distance;
				for (distance = 1; spalte - distance > 0 && tempField[8][spalte - distance] == 0; distance++);
				for (int zeile = 0; zeile < tempField.length; zeile++) {
					tempField[zeile][spalte] = tempField[zeile][spalte - distance];
					tempField[zeile][spalte - distance] = 0;
				}
			}
		}
		
		return new PlayingField(tempField);
	}
	
	public String toString() {
		String returnString = "";
		
		for(int zeile = 0; zeile < field.length; zeile++) {
			for(int spalte = 0; spalte < field[0].length; spalte++) {
				if(field[zeile][spalte] != 0) {
					returnString = returnString + "[" + field[zeile][spalte] + "]";
				}
				else {
					returnString = returnString + "[*]";
				}
			}
			returnString = returnString + "\n";
		}
		
		return returnString;
	}
	
	public int notNull() {
		int notNull = 0;
	
		for(int zeile = 0; zeile < field.length; zeile++) {
			for(int spalte = 0; spalte < field[0].length; spalte++) {
				if(field[zeile][spalte] != 0) notNull++;
			}
		}
		
		return notNull;
	}
	
	public int sortsLeft() {
		List<Integer> sorts = new ArrayList<>();
		for(int y = 0; y < this.field.length && sorts.size() < 5; y++) {
			for (int x = 0; x < this.field[y].length && sorts.size() < 5; x++) {
				if(!sorts.contains(this.field[y][x])) sorts.add(this.field[y][x]);
			}
		}
		return sorts.size();
	}
	
	public static PlayingField grabPlayingField() throws HeadlessException, AWTException {
		BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		boolean frameFound = false;
		Frame frame = null;
		int advance;
		
		for (int y = 0; y < image.getHeight() - 271 && !frameFound; y++) {
		    for (int x = 0; x < image.getWidth() - 421 && !frameFound; x++) {
		    	advance = isFrame(image,x,y);
		    	if(advance < 0) {
		    		frameFound = true;
		    		frame = new Frame(x + 1, y + 1, image);
		    		//System.out.println(x+"!"+y);
		        }
		    	else {
		    		x += advance;
		    	}
		    }
		}
		
		if(!frameFound) return null;
		
		return frame.getPlayingField();
		
	}
	
	public static int isFrame(BufferedImage image, int x, int y) {
		if(image.getRGB(x + 421, y) != WHITE || image.getRGB(x + 421, y + 271) != WHITE){
			return 420;
		}
		if(image.getRGB(x, y) != WHITE || image.getRGB(x, y + 271) != WHITE){
			return 0;
		}
		if(image.getRGB(x, y) == WHITE
				&& image.getRGB(x + 421, y) == WHITE
				&& image.getRGB(x + 421, y + 271) == WHITE
				&& image.getRGB(x, y + 271) == WHITE){
			
			for(int i = 0; i <= 421; i++) {
				if( image.getRGB(x + i, y) != WHITE) return i;
			}
			for(int i = 0; i <= 421; i++) {
				if( image.getRGB(x + i, y + 271) != WHITE) return i;
			}
			for(int i = 0; i <= 271; i++) {
				if( image.getRGB(x , y + i) != WHITE) return 0;
			}
			for(int i = 0; i <= 271; i++) {
				if( image.getRGB(x + 421, y + i) != WHITE) return 0;
			}
			
			int whites = 0;
			
			for(int i = 1; i <= 420; i++) {
				if(image.getRGB(x + i, y + 1) == WHITE) whites++;
			}
			if(whites / 420 == 1) return 420;
			whites = 0;
			
			for(int i = 1; i <= 420; i++) {
				if(image.getRGB(x + i, y + 270) == WHITE) whites++;
			}
			if(whites / 420 == 1) return 420;
			whites = 0;
			
			for(int i = 1; i <= 270; i++) {
				if(image.getRGB(x + 1, y + i) == WHITE) whites++;
			}
			if(whites / 270 == 1) return 420;
			whites = 0;
			
			for(int i = 1; i <= 270; i++) {
				if(image.getRGB(x + 420, y + i) == WHITE) whites++;
			}
			if(whites / 270 == WHITE) return 0;
			whites = 0;	
		}
		return -1;
	}
	
	public int getValue(Coordinate input) {
		return this.field[input.getZeile()][input.getSpalte()];
	}

}
