package solutionfinder;

import java.util.ArrayList;
import java.util.List;

public class State {
	private static List<State> states = new ArrayList<>();
	private PlayingField playingfield;
	private int score;
	private int candidate = 0;
	
//	final static int[] BLOCKLEFT_SCORE = {
//		12800, 11250, 9800,	8450, 7200, 6050, 5000, 4050, 3200, 
//		2450, 1800, 1250, 800, 450, 200, 50
//};
	
	final static int[] BLOCKLEFT_SCORE = {
		11250, 9800, 8450, 8450, 7200, 6050, 5000, 4050, 3200, 
		2450, 1800, 1250, 800, 450, 200, 50
};
	
	private List<Block> candidates; 
	
	public State(PlayingField playingfield, int score) {
		this.playingfield = playingfield;
		this.score = score;
		
		this.candidates = this.playingfield.getBlocks();
	}
	
	public static void add(PlayingField playingfield, int score) {
		states.add(new State(playingfield, score));
	}
	
	public static void remove() {
		states.remove(states.size() - 1);
	}
	
//	public void nextCandidate() {
//		this.candidate++;
//	}
	
	public void killBranch() {
		this.candidate = this.candidates.size();
	}
	
	public State getNext() {
		if(candidate < candidates.size()) {
			Block x = this.candidates.get(candidate);
			PlayingField y = this.playingfield.clickAway(x);
			this.candidate++;
			return new State (y, blockScore(x.size()) + this.score);
		}
		return null;
	}
	
	public static int blockScore(int numberOfBlocks) {
		if (numberOfBlocks < 1) return 0;
		int score = 20;
		for (int i = 3; i <= numberOfBlocks; i++) {
			score += (i * 10) - 5;
		}
		return score;
	}
	
	private static int blockLeftScore(int blocksLeft) {
		if(blocksLeft > 15) return 0;
		else return BLOCKLEFT_SCORE[blocksLeft];
	}

	public static List<State> getStates() {
		return states;
	}

	public PlayingField getPlayingfield() {
		return playingfield;
	}

	public int getScore() {
		return score + blockLeftScore(playingfield.notNull());
	}
	
	public int getRawScore() {
		return score;
	}
	
	public int getNumberOfCandidates () {
		return candidates.size();
	}
	
	public int getCurrentCandidate () {
		return candidate;
	}
	
	public List<Block> getCandidates () {
		return this.candidates;
	}
	
	public String toString() {
		return this.playingfield + "\n" + "Score: " + getScore() + "\n\n";
	}
	
	
}
