package laberinto;
import java.util.Scanner;

public class MainLaberinto {
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		int tamanio = Integer.parseInt(scan.nextLine());
		Laberintos laberintos = new Laberintos (tamanio,scan);
		
		
		laberintos.rellenarMatriz();
		laberintos.resolverLaberinto();
	
		scan.close();
	}

}
