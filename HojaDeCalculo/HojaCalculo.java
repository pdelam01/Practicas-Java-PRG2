package HojaCalculo;
import java.util.Scanner;

public class HojaCalculo {
		Scanner scan;
		private Celdas[][] celda;
		private int[][] aux;
		private int col;
		private int fila;
		
		/*
		 * Constructor de la clase HojaCalculo
		 */
		public HojaCalculo(int c, int f) {
			this.celda = new Celdas[f][c];
	        this.fila=f;
	        this.col=c;
			//this.scan=input;
		}
		
		/*
		 * Leemos la matriz introducida
		 */
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
		
		/*
		 * Funcion resolver hoja
		 */
		public void resolverHoja(){
			String formula[];
			String [] auxiliar=null;
			
			this.aux = new int[this.fila][this.col];
			
			inicializarFormula();
			
			while(true) {
				for(int i=0;i<this.fila;i++) {
					for(int j=0;j<this.col;j++) {
						
						if(((int)this.celda[i][j].getCharacter().charAt(0)>=65) && (this.celda[i][j].getCharacter().charAt(0)!=61)){
							formula=this.celda[i][j].getCharacter().split("=");
							this.celda[i][j].setCharacter(formula[0]);
							auxiliar=formula[0].split("\\+");
						
							int suma=0;	
							if(comprobarA2(auxiliar)==true) {
								for(int l=0;l<auxiliar.length;l++) {
									suma=obtenerCelda(auxiliar[l],suma,i,j);
									
						      	}
							celda[i][j].setCharacter(String.valueOf(aux[i][j]));
							}
							
						}else {
							
							if((this.celda[i][j].esFormula()==true)) {
								formula=this.celda[i][j].getCharacter().split("=");
								this.celda[i][j].setCharacter(formula[1]);
								auxiliar=formula[1].split("\\+");
							
								int suma=0;	
								if(comprobarA2(auxiliar)==true) {
									for(int l=0;l<auxiliar.length;l++) {
										suma=obtenerCelda(auxiliar[l],suma,i,j);
							      	}
								celda[i][j].setCharacter(String.valueOf(aux[i][j]));
								}
							}else {
								aux[i][j]=Integer.parseInt(this.celda[i][j].getCharacter());
							}
						}
					}				
				}
				if(comprueba()==false){
					break;
				}
			}
		}
		
		/*
		 * Funcion que nos permite transformar en la matriz aux la casilla con formula por un -9999
		 */
		public void inicializarFormula() {
			for(int i=0;i<this.fila;i++) {
				for(int j=0;j<this.col;j++) {
					if(this.celda[i][j].esFormula()==true) {
						this.aux[i][j]=-9999;
					}else {
						aux[i][j]=Integer.parseInt(this.celda[i][j].getCharacter());
					}
				}
			}
		}
		
		/*
		 * Funcion nos permite entrar en bucle en la matriz hasta que toda ella está resulta
		 * Nos permite hallar solucion para casillas cuyo resultado depende de otra que es una formula
		 */
		public boolean comprobarA2 (String[] auxiliar) {
			for (int i=0;i<auxiliar.length;i++) {
				String auxN="";
				String auxL="";
				
				for(int k=0; k<auxiliar[i].length();k++) {
					char caracter = auxiliar[i].charAt(k);
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
			
				if(this.aux[fila-1][col-1]==-9999) {
					return false;
				}
			}
			return true;
		}
		
		/*
		 * Funcion imprime la matriz solucion
		 */
		public void imprimirMatrizSolucion() {
			for(int i=0;i<this.fila;i++) {
				for(int j=0;j<this.col;j++) {
					if(j==this.col-1) {
						System.out.print(this.celda[i][j].getCharacter());
					}else {
						System.out.print(this.celda[i][j].getCharacter()+"  ");
					}
				}
				System.out.println();
			}
		}
		
		/*
		 * Funcion nos permite sacar de cada string que hayamos obtenido del split en resolverHoja
		 * transformar las letras y numeros de la casilla por su correspondiente valor en ascii
		 */
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

			suma = sumaCeldas(fila-1,col-1,sum);
			
			if (suma!=-9999) {
				this.celda[i][j].setCharacter(String.valueOf(suma));
				this.aux[i][j]=suma;
			}else {
				this.aux[i][j]=-9999;
			}
			return suma;
			
		}
		
		/*
		 * Transformamos las letras de las casillas por su valor en ascii
		 */
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
		
		/*
		 * Filtramos el numero de la casilla
		 * Comprobamos aqui a su vez que no se escriba por pantalla letras en minuscula
		 */
		public int conversionNum(String aux) {
			int lol=0;
			for(int i=aux.length()-1; i>=0;i--) {
				char caracter = aux.charAt(aux.length()-1-i);
				int ascii;
				ascii=(int)caracter;
				if(ascii>=97 && ascii<=122) {
					System.out.println("Entrada inválida");
					System.exit(-1);
				}else {
					lol=Integer.parseInt(aux);
				}
			}
			return lol;
		}
		
		/*
		 * Metodo nos permite sumar las casillas 
		 */
		public int sumaCeldas(int fila, int col, int sum) {
		if(this.celda[fila][col].character.charAt(0)!='=' && this.aux[fila][col]!=-9999) {
			sum+=Integer.parseInt(this.celda[fila][col].character);	
			}else {
				sum=-9999;
			}
			return sum;
		}
		
		/*
		 * Funcion comprueba si queda o no una formula (=) en la matriz
		 */
		public boolean comprueba () {
		int count=0;
			for(int i=0;i<this.aux.length;i++) {
				for (int j=0; j<this.aux[0].length;j++) {
					if(this.aux[i][j]==-9999) {
						count++;
					}
				}		
			}
			if(count==0) {
				return false;
			}else {
				return true;
			}
		}
		
		
		
		
		
}

