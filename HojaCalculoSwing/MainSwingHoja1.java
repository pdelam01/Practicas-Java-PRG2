import javax.swing.JOptionPane;

public class MainSwingHoja1 {
	static int count;
	static int count2;
	
	public static void main(String[] args) { 
		do{
			int filas=0, columnas=0;
			try {
				String entradaFilas = JOptionPane.showInputDialog("Numero de filas que desea tener");
				if(entradaFilas==null) {
					int salida=JOptionPane.showConfirmDialog(null, "¿Desea cancelar la operación?");
					if(salida==JOptionPane.YES_OPTION) {
						System.exit(-1);
					}else {
						//
					}
				}else {
					filas=Integer.parseInt(entradaFilas);
					String entradaColum=JOptionPane.showInputDialog("Numero de columnas que desea tener");
					if(entradaColum==null) {
						int salida1=JOptionPane.showConfirmDialog(null, "¿Desea cancelar la operación?");
						if(salida1==JOptionPane.YES_OPTION) {
							System.exit(-1);
						}else {
							//
						}
					}else {
						columnas=Integer.parseInt(entradaColum);
						if(columnas<=0 || columnas>18270 || filas<=0 || filas>18270) {
							JOptionPane.showMessageDialog(null, "Debe introducir numeros positivos mayores que 0!","Advertencia",JOptionPane.WARNING_MESSAGE);
							count2=0;
						}else {
							Frame interfaz = new Frame (filas,columnas);
							interfaz.creacionSwing();
							count2=1;
						}
					}
				}			
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Debe introducir un numero","Advertencia",JOptionPane.WARNING_MESSAGE);
				count=0;
			}
		}while(count==0 && count2==0);
	}
}
		

	
