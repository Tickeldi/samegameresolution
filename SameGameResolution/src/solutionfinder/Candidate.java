package solutionfinder;

import java.util.ArrayList;
import java.util.List;

public class Candidate {
	private List<State> weg = new ArrayList<>();
	private int score = 0;
	
	public Candidate(List<State> weg, int score) {
		this.weg = weg;
		this.score = score;
	}

	public List<State> getWeg() {
		return weg;
	}

	public int getScore() {
		return score;
	}
	
	public String toString() {
		String returnString = "";
		for(State x:weg){
			returnString += x + "\n";
		}
		return returnString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + score;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candidate other = (Candidate) obj;
		if (score != other.score)
			return false;
		return true;
	}
}
