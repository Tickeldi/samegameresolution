package solutionfinder;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Solution {
	private List<State> workPath = new ArrayList<>();
	private List<Block> bestWay = new ArrayList<>();
	
	int bestScore = 0;
//	private int percent = 0;
	
	public Solution(PlayingField playingfield) {
		workPath.add(new State(playingfield, 0));
		State next;
		long time = System.currentTimeMillis();
		
		while(workPath.size() > 0 && System.currentTimeMillis() - time < 120000) {
//			bestWay.get(bestWay.size() - 1).killBranchIf(bestCandidate.getScore());
			next = workPath.get(workPath.size() - 1).getNext();
			if(next != null) {
//				if(bestScore > State.BLOCKLEFT_SCORE[0]) {
//					int highestPossibleScore = next.getRawScore();
//					if((next.getPlayingfield().notNull()
//							- ((next.getPlayingfield().sortsLeft() - 1) * 2)) % 2 != 0) highestPossibleScore += State.BLOCKLEFT_SCORE[1];
//					else highestPossibleScore += State.BLOCKLEFT_SCORE[0];
//					highestPossibleScore += ((next.getPlayingfield().sortsLeft() - 1) * 2) * 20;
//					highestPossibleScore += State.blockScore(
//									next.getPlayingfield().notNull()
//									- ((next.getPlayingfield().sortsLeft() - 1) * 2));
//					if(highestPossibleScore > bestScore) workPath.add(next);
//					else {
////						bestWay.remove(bestWay.size() - 1);
//					}
//					
//				}
//				else workPath.add(next);
				workPath.add(next);
			}
			else {
				if(workPath.get(workPath.size() - 1).getScore() > bestScore) {
					bestScore = workPath.get(workPath.size() - 1).getScore();
					bestWay = new ArrayList<>();
					for(State state: workPath) if(state.getCurrentCandidate() - 1 >= 0) bestWay.add(state.getCandidates().get(state.getCurrentCandidate() - 1));
					System.out.println("Aktuell bestes gefundenes Ergebnis: " + bestScore);
					boolean fromHere = false;
					for(int i = 0; i < workPath.size() - 1; i++) {
						if(!fromHere && workPath.get(i).getCurrentCandidate() > 1) fromHere = true;
						if(fromHere) System.out.print(workPath.get(i).getCurrentCandidate() + "/" + workPath.get(i).getNumberOfCandidates() + "("+i+")");
						if(fromHere && i < workPath.size() - 2) System.out.print(" | ");
						
					}
					System.out.println();
				}
				else killUselessBranches((100F -((float)workPath.get(workPath.size() - 1).getScore() / ((float)bestScore / 100F))) / 3F);
//				else killUselessBranches((100F -(10000F / (((float)bestCandidate.getScore() / 100F)))) / 3F);
				workPath.remove(workPath.size() - 1);
			}
		}
	}
	
	public List<Block> getBestWay() {
		return this.bestWay;
	}
	
	private void killUselessBranches (float percent) {
		int numberOfBranches = (int)(percent * ((float) workPath.size() / 100F));
		
		for(int i = 0; i < numberOfBranches; i++) {
			workPath.remove(workPath.size() - 1);
		}
		
		//if (numberOfBranches > 0) System.out.println("Killed " + numberOfBranches + " Branches!");
	}
	
	public void applySolution()  throws HeadlessException, AWTException {
		BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		boolean frameFound = false;
		int advance;
		int x = 0;
		int y = 0;
			
		for (y = 0; y < image.getHeight() - 271 && !frameFound; y++) {
		    for (x = 0; x < image.getWidth() - 421 && !frameFound; x++) {
		    	advance = PlayingField.isFrame(image,x,y);
		    	if(advance < 0) {
		    		frameFound = true;
	    		//System.out.println(x+"!"+y);
		        }
	    		else {
		    		x += advance;
		    	}
		    }
		}
		
		for (Block block:bestWay) {
			Coordinate coordinate = block.getCoordinate(0);
			System.out.println(block);
			clickCoordinate(y,x,coordinate.getZeile(), coordinate.getSpalte());
			new Robot().delay(250);
		}
				
	}
	
	private void clickCoordinate(int starty, int startx, int zeile, int spalte) throws AWTException {
		starty += 15;
		startx += 15;
		
		Robot mouse = new Robot();
		mouse.mouseMove(startx + (spalte * 30), starty + (zeile * 30));
		mouse.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		mouse.delay(50);
		mouse.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		mouse.delay(100);
		mouse.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		mouse.delay(50);
		mouse.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);		
	}
	
	
	
}
