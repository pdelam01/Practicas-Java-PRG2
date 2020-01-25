
import java.util.ArrayList;
import java.util.Scanner;

public class MainHojaCalculo {
	private static ArrayList<HojaCalculo> hojas = new ArrayList<HojaCalculo>();

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		
		int numhojas=1;
		try {
			numhojas = reader.nextInt();
			
		}catch (Exception e) {
			System.out.println("Entrada inválida");
		}
		//Quitamos el /n
		reader.nextLine();
		
		if(numhojas<=0) {
			System.out.println("Entrada inválida");
			System.exit(-1);
		}
		
		
		int col=1;
		int fila=1;
		for(int k=0;k<numhojas;k++) {
			try {
				col = reader.nextInt();
				fila = reader.nextInt();
			} catch (Exception e) {
				System.out.println("Entrada inválida");
			}
			reader.nextLine();
			
			if(col<=0 || fila<=0 || col>18270 || fila>18270) {
				System.out.println("Entrada inválida");
				System.exit(-1);
			}
	
			//Objeto tipo HojaCalculo
			HojaCalculo hoja = new HojaCalculo (col,fila);
			hoja.leerMatriz();
			//hoja.resolverHoja();
			hojas.add(hoja);	
		}
		
		//Resolvemos para cada hoja de calculo introducida
		for(int i=0;i<hojas.size();i++) {
			HojaCalculo hojaAux;
			hojaAux=hojas.get(i);
			//hojaAux.imprimirMatrizSolucion();
		}
		reader.close();
	}
	
	
	/*panel.setPreferredSize(new Dimension(1000,700));
	panel.setSize(250,100);
	panel.setVisible(true);
	panel.add(norte, BorderLayout.NORTH);
	panel.add(sur, BorderLayout.SOUTH);*/
	
	/*JFrame panel = new JFrame("Ventana Guardar");
	JPanel norte=new JPanel();
	JPanel sur=new JPanel();
	JLabel et=new JLabel("Ha seleccionado guardar:");
	*/
}
