package HojaCalculo;

import java.util.ArrayList;
import java.util.Scanner;

public class aux {
		Scanner scan;
		private Celdas[][] celda;
		private int [][] aux;
		private int col;
		private int fila;
		private ArrayList<Celdas> casillas;
		private ArrayList<Celdas> hojas;
		
		public aux(int c, int f, Scanner input) {
			this.celda = new Celdas[f][c];
	        this.fila=f;
	        this.col=c;
			this.scan=input;
		}
		
		
		public void leerMatriz() {
			for(int i=0;i<this.fila;i++) {
				String line = this.scan.nextLine();
				String [] secciones = line.split(" ");
				for(int j=0;j<this.col;j++) {
					Celdas cel = new Celdas(secciones[j], i, j);
					this.celda[i][j]=cel;
				}
			}
		}
		
		
		public void imprimirMatriz() {
			for(int i=0;i<this.fila;i++) {
				for(int j=0;j<this.col;j++) {
					System.out.print(this.celda[i][j].getCharacter()+"     ");
				}
				System.out.println();
			}
		}
		
		
		public void resolverHoja(){
			String formula[];
			String [] auxiliar=null;
			
			this.aux = new int [this.fila][this.col];
			
			for(int i=0;i<this.fila;i++) {
				for(int j=0;j<this.col;j++) {
					if(this.celda[i][j].esFormula()==true) {
						formula=this.celda[i][j].getCharacter().split("=");
						auxiliar=formula[1].split("\\+");
						
						aux[i][j]=-99999;
						
						int suma=0;					
						for(int l=0;l<auxiliar.length;l++) {
							suma=obtenerCelda(auxiliar[l],suma,i,j);
						}
					}else {
						aux[i][j]=Integer.parseInt(this.celda[i][j].getCharacter());
					}
				}				
			}
		}
		
		public void imprimirAux() {
			for(int i=0;i<this.fila;i++) {
				for(int j=0;j<this.col;j++) {
					System.out.print(this.aux[i][j]);
				}
				System.out.println();
			}
		}
		
		
		
		public void imprimirMatrizSolucion() {
			for(int i=0;i<this.fila;i++) {
				for(int j=0;j<this.col;j++) {
					System.out.print(this.celda[i][j].getCharacter());
				}
				System.out.println();
			}
		}
		
		public int obtenerCelda (String aux, int sum, int i, int j) {
			String auxN="";
			String auxL="";
			int suma;
			for(int k=0; k<aux.length();k++) {
				char caracter = aux.charAt(k);
				int ascii;
				ascii=(int)caracter;
				if(ascii>=65 && ascii<=90) {
					auxL=auxL+String.valueOf(caracter);
				}else {
					auxN=auxN+String.valueOf(caracter);
				}
			}
			
			int fila=conversionNum(auxN);
			int col=conversionLetra(auxL);
			
			//mierda(sum);
			suma = sumaCeldas(fila-1,col-1,sum);
			
			this.celda[i][j].setCharacter(String.valueOf(suma));
			return suma;
			
		}
		
		
		public int conversionLetra(String aux) {
			int auxNum=0;
			for(int i=aux.length()-1; i>=0;i--) {
				char caracter = aux.charAt(aux.length()-1-i);
				int ascii;
				ascii=(int)caracter;
				auxNum+=(int)(ascii-64)*Math.pow(26, i);
			}
			return auxNum;
		}
		
		
		public int conversionNum(String aux) {
			return Integer.parseInt(aux);
		}
		
		
		public boolean comprueba () {
		int count=0;
			for(int i=0;i<this.fila;i++) {
				for (int j=0; j<this.col;j++) {
					if(this.aux[i][j]==-99999) {
						count++;
					}
				}
					
			}
			if(count==0) {
				return false;
			}
				return true;
			
		}
		
		
		public int sumaCeldas(int fila, int col, int sum) {
			
			sum+=(this.aux[fila][col]);
			return sum;
			
		}
		
		
}


