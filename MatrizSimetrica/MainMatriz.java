import java.util.Scanner;
import java.util.ArrayList;


public class MainMatriz {
	private static ArrayList<Integer> matrices = new ArrayList<Integer>();
	private static Scanner scan = new Scanner(System.in);
	private static boolean comprobante;

	public static void main(String[] args) {
		int tam = scan.nextInt();
		
		try {
			if(tam<1) {
				System.out.println("Entrada Inválida.");
			}else {
				int [][]matrizA = new int[tam][tam];
				int [][]matrizRaices = new int [tam][tam];
				int [][]matrizTraspuestaRaices =new int [tam][tam];
				comprobante=true;
			
				anyadirArrayList(tam*tam);
				arrayListAMatriz(matrizA, 0, 0, tam, 0);
				raicesMatriz(matrizA,matrizRaices, 0, 0, tam);
				matrizTraspuesta(matrizRaices,matrizTraspuestaRaices, tam, 0, 0);
			
				comprobante=compararMatrices(matrizRaices, matrizTraspuestaRaices, 0, 0, tam);
				if(comprobante==true) {
					System.out.println("La matriz de tamaño "+tam+" es de raíz entera simétrica.");
				}else {
					System.out.println("La matriz de tamaño "+tam+" no es de raíz entera simétrica.");
				}
			}
			scan.close();
		}catch(Exception e){
			System.out.println("Entrada Inválida.");
		}
	}	
	
    private static void anyadirArrayList (int tam) {
		if(tam!=0) {
			if(scan.hasNext()) {
				int num = scan.nextInt();
				if(num<0){
				    System.out.println("Entrada Inválida.");
				    System.exit(-1);
				}
				matrices.add(num);
				anyadirArrayList(tam-1);
			}
		}
	}
	
	private static void arrayListAMatriz(int matrizA[][], int i, int j, int tam, int k) {
		if(i<tam) {
			if(j<tam) {
				matrizA[i][j]=matrices.get(k);
				j++;
				k++;
				arrayListAMatriz(matrizA, i, j, tam, k);
			}else {
				j=0;
				i++;
				arrayListAMatriz(matrizA, i, j, tam, k);
			}
		}
	}
	
	
	private static int sacarRaices (int num, int cont) {
		int result=cont*cont;
		if(result>num) {
			return cont-1;
		}else {
			cont++;
			return sacarRaices(num, cont);
		}
	}
	
	private static void raicesMatriz (int matrizA[][], int matrizB[][], int i, int j, int tam) {
		int num,raiz;
		if(i<tam) {
			if(j<tam) {
				num=matrizA[i][j];
				raiz=sacarRaices(num,1);
				matrizB[i][j]=raiz;
				j++;
				raicesMatriz(matrizA,matrizB, i, j,tam);
			}else {
				j=0;
				i++;
				raicesMatriz(matrizA,matrizB, i, j, tam);
			}
		}
	 }
	
	private static void matrizTraspuesta(int matrizA[][],int matrizB[][], int tam, int i, int j){
		if(i<tam) {
			if(j<tam) {
				if(i==j) {
					matrizB[i][j]=matrizA[i][j];
					j++;
					matrizTraspuesta(matrizA,matrizB, tam, i, j);
				}else {
					matrizB[i][j]=matrizA[j][i];
					j++;
					matrizTraspuesta(matrizA,matrizB, tam, i, j);
				}
			}else {
				j=0;
				i++;
				matrizTraspuesta(matrizA,matrizB, tam, i, j);
			}
		}	
	}
	
		
	
	private static boolean compararMatrices(int matrizA[][], int matrizB[][], int i, int j, int tam) {
		if(i<tam) {
			if(j<tam) {
				if(matrizA[i][j]==matrizB[i][j]) {
					j++;
					compararMatrices(matrizA, matrizB, i, j, tam);
				}else {
					comprobante=false;
				}
			}else {
				j=0;
				i++;
				compararMatrices(matrizA, matrizB, i, j, tam);
			}
		}
		return comprobante;
	}
	
}

