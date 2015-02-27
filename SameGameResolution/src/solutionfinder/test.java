package solutionfinder;

import java.awt.AWTException;
import java.awt.HeadlessException;

public class test {

	public static void main(String[] args) throws HeadlessException, AWTException {
		// TODO Auto-generated method stub
		PlayingField field = PlayingField.grabPlayingField();
		System.out.println(field);
		
		Solution test = new Solution(field);
		test.applySolution();
		/*
[*][*][*][*][*][*][*][*][*][*][*][*][*][*]
[*][*][*][*][*][*][*][*][*][*][*][*][*][*]
[*][*][*][*][*][*][*][*][*][*][*][*][*][*]
[*][*][*][*][*][*][*][*][*][2][*][*][*][*]
[*][*][*][*][*][1][*][*][*][5][*][*][*][*]
[4][5][*][1][5][2][*][*][*][2][*][*][*][*]
[2][3][*][2][1][4][5][*][*][1][*][1][*][1]
[2][2][*][3][4][5][2][*][1][4][4][4][5][4]
[2][3][2][2][5][3][5][1][5][3][4][3][5][5]
		 */
	/*	
		Coordinate[][] testfeld = {
				{null,null,null,null,null,new Coordinate(6,1,4),new Coordinate(7,1,2),new Coordinate(8,1,2),new Coordinate(9,1,2)},
				{null,null,null,null,null,new Coordinate(6,2,5),new Coordinate(7,2,3),new Coordinate(8,2,2),new Coordinate(9,2,3)},
				{null,null,null,null,null,null,null,null,new Coordinate(9,3,2)},
				
				};*/
	}

}
