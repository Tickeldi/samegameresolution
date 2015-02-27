package solutionfinder;

import java.util.ArrayList;
import java.util.List;

public class Solutions {
	
	// private List<Candidate> candidates = new ArrayList<>();
	
	public Candidate getBestWay() {
		return bestWay;
	}

	public Solutions(PlayingField input) {
		way.add(input);
//		getSolutions(input.clone());
	}

	private int blockScore = 0;
	private int highestScore = Integer.MIN_VALUE;
	
	private Candidate bestWay;
	
	private List<PlayingField> way = new ArrayList<>();
	
	private void getSolutions(PlayingField input) {
		List<Block> candidates = input.getBlocks();
		
		if(candidates.size() > 0){
			for(Block candidate:candidates) {
				blockScore += blockScore(candidate.size());
				
				PlayingField next = input.clickAway(candidate);
				way.add(next);
//				getSolutions(next.clone());
				way.remove(way.size() - 1);
				
				blockScore -= blockScore(candidate.size());
			}
		}
		
		else {
			int testScore = blockLeftScore(input.notNull()) + blockScore;
			if(testScore > highestScore) {
//				bestWay = new Candidate(new ArrayList<PlayingField>(way), testScore);
				highestScore = testScore;
			}
		}
		
	}
	
	private int blockLeftScore(int blocksLeft) {
		final int[] score = {
				12800, 11250, 9800,	8450, 7200, 6050, 5000, 4050, 3200, 
				2450, 1800, 1250, 800, 450, 200, 50
		};
		if(blocksLeft > 15) return 0;
		else return score[blocksLeft];
	}
	
	private int blockScore(int numberOfBlocks) {
		int score = 20;
		for (int i = 3; i <= numberOfBlocks; i++) {
			score += (i * 10) - 5;
		}
		return score;
	}
}
