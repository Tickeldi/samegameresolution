package solutionfinder;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Frame {

	int x;
	int y;
	BufferedImage input;
	HashMap<Long, Integer> fieldCategories = new HashMap<>();
	
	public Frame(int x, int y, BufferedImage input) {
		this.x = x;
		this.y = y;
		this.input = input;
	}
	
	private int getFieldNumber(int x, int y) {
		long summary = 0;
		for(int i = 0; i < 30; i++) {
			for(int j = 0; j < 30; j++) {
				summary = summary + input.getRGB(x + i, y + j);
			}
		}
		
		int testIfBlack = (int) (summary / 900);
		
		if(testIfBlack / 1000000 == -16 && Long.toHexString(testIfBlack).startsWith("ffffffffff")) return 0;
		
		if(fieldCategories.size() < 5 && !fieldCategories.containsKey(summary)) {
			fieldCategories.put(summary, fieldCategories.size() + 1);
		}
			
		return fieldCategories.get(summary);

	}

	
	public PlayingField getPlayingField() {
		int[][] playingField = new int[9][14];
		
		for(int x = 0; x < 14; x++){
			for(int y = 0; y < 9; y++) {
				playingField[y][x] = getFieldNumber(this.x + (x * 30), this.y + (y * 30));
			}
		}
		
		return new PlayingField(playingField);
	}
	
}
