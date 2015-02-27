package solutionfinder;

public class Coordinate {
	private int zeile;
	private int spalte;
	
	public Coordinate(Coordinate input) {
		this.spalte = input.spalte;
		this.zeile = input.zeile;
	}
	
	public Coordinate(int zeile, int spalte) {
		this.spalte = spalte;
		this.zeile = zeile;
	}
	
	public int getZeile() {
		return this.zeile;
	}
	
	public int getSpalte() {
		return this.spalte;
	}
	
	public String toString() {
		return "(" + zeile + "x" + spalte + ")";
	}
}
