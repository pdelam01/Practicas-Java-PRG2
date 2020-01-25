

public class Celdas {
	private int filas;
	private int col;
	String character;
	
	public void setFilas(int filas) {
		this.filas = filas;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public Celdas(String carac, int f, int c) {
		this.filas=f;
		this.col=c;
		this.character=carac;
	}
	
	String getCharacter() {
		return this.character;
	}
	
	int getFilas() {
		return this.filas;
	}
	
	int getCol() {
		return this.col;
	}
	

	public boolean esFormula() {
		boolean formula=false;
		if(character.charAt(0)=='=') {
			formula=true;
		}
		return formula;
	}
}
