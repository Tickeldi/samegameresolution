package solutionfinder;

import java.util.ArrayList;
import java.util.List;

public class Block {
	private List<Coordinate> block = new ArrayList<>();
	private PlayingField playingfield;
	
	public void addBlock(Block input) {
		this.block.addAll(input.getCoordinate());
	}
	
	public Block(List<Coordinate> block) {
		this.block = block;
	}

	public Block(PlayingField input) {
		this.playingfield = input;
	}

	public List<Coordinate> getCoordinate() {
		return block;
	}
	
	public void addCoordinate(Coordinate input) {
		block.add(input);
	}

	public int size() {
		return block.size();
	}
	
	public Coordinate getCoordinate(int i) {
		return block.get(i);
	}
	
	public String toString () {
		int[][] temp = new int[9][14];
		
		for(Coordinate x : block) {
			temp[x.getZeile()][x.getSpalte()] = playingfield.getValue(x);
		}
		
		String returnString = "";
		
		for(int x = 0; x < 9; x++) {
			for(int y = 0; y < 14; y++) {
				if(temp[x][y] != 0) {
					returnString += "[x]";
				}
				else {
					returnString += "[ ]";
				}
			}
			returnString += "\n";
		}
		
		return returnString;
	}
}
