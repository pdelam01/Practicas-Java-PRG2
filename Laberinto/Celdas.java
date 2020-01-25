package laberinto;

public class Celdas {
	private int filas;
	private int col;
	private char character;
	
	
	public Celdas(char carac, int f, int c) {
		this.filas=f;
		this.col=c;
		this.character=carac;
	}
	
	char getCharacter() {
		return this.character;
	}
	
	int getFilas() {
		return this.filas;
	}
	
	int getCol() {
		return this.col;
	}
 
    public void setCharacter(char character) {
        this.character = character;
    }
    
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celdas other = (Celdas) obj;
		if (character != other.character)
			return false;
		if (col != other.col)
			return false;
		if (filas != other.filas)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		if(getCharacter()=='*') {
			output.append("("+(getFilas()+1)+"/"+(getCol()+1)+")*");
		}else {
			output.append("("+(getFilas()+1)+"/"+(getCol()+1)+")");
		}
		return output.toString();
    }

}
