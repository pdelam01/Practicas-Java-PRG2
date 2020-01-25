import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class Frame extends JFrame{
	private static final long serialVersionUID = 1L;
	private int filas;
	private int col;
	private JFrame frame;
	private JMenuBar barra;
	private JMenu op1,op2;
	private JMenuItem guardar,nueva,cargar,rehacer,deshacer;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JButton op3;
	
	Celdas[][] cel;
	HojaCalculo hoja;
	Celdas celda;
	Celdas [][] aux;
	String auxiliare [];
	String sol[][];
	int solucion[][];
	int siguiente=0;
	int atras=0;
	boolean buleano=false;
	private static ArrayList <Celdas> hojas = new ArrayList<Celdas>();
	
	public Frame(int filas, int columnas) {
		this.filas=filas;
		this.col=columnas;
		frame=new JFrame("Hoja de Calculo");
		frame.setPreferredSize(new Dimension(1000,700));
		frame.setSize(150,200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}
	
	public void creacionSwing() {
		JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		barra=new JMenuBar();
		frame.setJMenuBar(barra);
		barra.setBackground(Color.LIGHT_GRAY);
		
		op1=new JMenu("Archivo");
		op2=new JMenu("Menú");
		op3=new JButton("Resolver");
		barra.add(op1);
		barra.add(op2);
		barra.add(op3);
		
		guardar=new JMenuItem("Archivar"); 	
		nueva =new JMenuItem("Nueva Hoja");
		cargar =new JMenuItem("Cargar");
		rehacer=new JMenuItem("Rehacer");
		deshacer=new JMenuItem("Deshacer");
		op1.add(guardar);
		op1.add(nueva);
		op1.add(cargar);
		op2.add(rehacer);
		op2.add(deshacer);

		modelo=new DefaultTableModel(this.filas, this.col){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int fil, int col) {
				if(col==0) {
					return false;
				}else {
					return super.isCellEditable(fil,col);
				}
			}
		};
		
		String columna[] = new String[this.col+1];
	    columna[0]="";
	    String aux[] = new String[18955];
	    
	    sol = new String[this.filas][this.col+1];
	    inicializaColumnaMatriz(aux);
	    
		for (int i=0;i<=this.col;i++){
			columna[i]=aux[i];
		}
		modelo.setColumnIdentifiers(columna);
		
		for (int i = 0; i < this.filas; i++) {
			modelo.setValueAt(i + 1, i, 0);
		}
		
		frame.getContentPane().add(scroll);
		
		tabla=new JTable(modelo);
		listenerModeloTabla(tabla);
		tabla.getColumnModel().getColumn(0).setCellRenderer(tabla.getTableHeader().getDefaultRenderer());
		tabla.setAutoResizeMode(0);
		
		tabla.setAutoResizeMode(0);
		tabla.getTableHeader().setReorderingAllowed(false);

		scroll.setViewportView(tabla);
		frame.pack();
		
		/* Key Strokes */
		KeyStroke ksGuardar=KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK);
		guardar.setAccelerator(ksGuardar);
		
		
		/* Listener que nos permite crear nuevas hojas */
		nueva.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				valoresEntrada();
			}
		});
		
		/* Listener nos permite resolver la hoja */
		op3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				recorrerTabla(filas,col);
				actualizarTabla(filas, col);
			}
		});
		
		/* Listener nos permite cargar un archivo */
		cargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buleano=true;
				int salida=JOptionPane.showConfirmDialog(null, "¿Desea cargar un trabajo anterior?");
				if(salida==JOptionPane.YES_OPTION) {
					hacerComandoCargar();
					buleano=false;
				}
			}
		});
		
		/* Listener nos permite guardar un archivo */
		guardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buleano=true;
				int salida=JOptionPane.showConfirmDialog(null, "¿Desea guardar el trabajo?");
				if(salida==JOptionPane.YES_OPTION) {
					recorrerTabla(filas, col);
					hacerComandoGuardar();
					buleano=false;
				}
			}
		});
		
		/* Listener deshacer */
		deshacer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(atras>0) {
					buleano=true;
					sol[hojas.get(atras-1).getFilas()][hojas.get(atras-1).getCol()]="0";
					cambiarValoresModelo();
					atras--;
					buleano=false;
				}else {
					buleano=true;
					JOptionPane.showMessageDialog(null, "No se puede deshacer más");
					buleano=false;
				}
			}
		});
		
		/* Listener rehacer */
		rehacer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(atras<hojas.size()) {
					buleano=true;
					sol[hojas.get(atras).getFilas()][hojas.get(atras).getCol()]=(hojas.get(atras).getCharacter());
					cambiarValoresModelo();
					atras++;
					buleano=false;
					
				}else {
					buleano=true;
					JOptionPane.showMessageDialog(null, "No se puede rehacer más");
					buleano=false;
				}
			}
		});
    }
	
	/* Funcion con la cual recorremos toda la tabla */
	public void recorrerTabla(int filas, int col) {
		Celdas celdas;
		this.aux=new Celdas [filas][col+1];
		cel=new Celdas[this.filas][this.col+1];
		for(int i=0;i<this.filas;i++) {
			for(int j=1;j<=this.col;j++) {
				celdas = new Celdas((String)tabla.getValueAt(i,j),filas,col);
				
				cel[i][j]=celdas;
				//Las celdas que no tengan valor, las seteamos a 0
				if(celdas.getCharacter()==null) {
					tabla.setValueAt("0",i,j);
					cel[i][j].setCharacter("0");
				}
			}
		}
		//Nos permite resolver la tabla planteada
		hoja = new HojaCalculo (col,filas);
		hoja.resolverHoja(cel,aux);
		hoja.imprimirMatrizSolucion(cel);
	}
	
	/* Funcion con la cual actualizamos los valores de la tabla */
	public void actualizarTabla(int fila, int col) {
		for(int i=0;i<this.filas;i++) {
			for(int j=1;j<=this.col;j++) {
				//Cambiamos los valores de la tabla
				modelo.setValueAt(this.aux[i][j].getCharacter(), i, j);
			}
		}
	}
	
	/* Funcion nos permite poder guardar los valores de la tabla creada */
	public void hacerComandoGuardar() {
		FileWriter escritor = null;
		try {
			JFileChooser jfc=new JFileChooser();
			jfc.showSaveDialog(null);
			String nombreFichero=jfc.getSelectedFile().getAbsolutePath();
			
			escritor = new FileWriter(nombreFichero);
			escritor.write(col+" "+filas);
			escritor.write("\n");
			for(int i=0;i<this.filas;i++) {
				for(int j=1;j<=this.col;j++) {
					if(j==this.col) {
						escritor.write(this.aux[i][j].getCharacter());
					}else {
						escritor.write(this.aux[i][j].getCharacter()+" ");
					}
				}
				escritor.write("\n");
			}
			
		} catch (IOException exception) {
		    System.err.println("Error salvando");
		    exception.printStackTrace();
		} finally {
		    if (escritor != null) {
			try {
			    escritor.close();

			} catch (IOException exception) {
			    System.err.println("Error cerrando escritor");
			    exception.printStackTrace();
				} 
			}
		} 
	}
	
	
	/* Funcion nos permite poder cargar valores de un archivo de texto a la tabla */
	public  void hacerComandoCargar() {
		FileReader lector = null;
		try {
			JFileChooser jfc=new JFileChooser();
			jfc.showOpenDialog(null);
			String nombreFichero=jfc.getSelectedFile().getAbsolutePath();
			lector = new FileReader(nombreFichero);
			BufferedReader br = new BufferedReader(lector);
			String s=br.readLine();
			StringTokenizer st = new StringTokenizer (s);
			    
			for(int l=0;l<this.filas;l++) {
				modelo.removeRow(0);
			 }
			    
			// Valores para la primera columna, que es la cabecera lateral.
			col=Integer.parseInt(st.nextToken());
            this.filas=Integer.parseInt(st.nextToken());
			String columna[] = new String[this.col+1];
			columna[0]="";
			String aux[] = new String[18955];
			    
			sol = new String[this.filas][this.col+1];
			inicializaColumnaMatriz(aux);
			    
			for (int i=0;i<=this.col;i++){
				columna[i]=aux[i];
			}
			modelo.setColumnIdentifiers(columna);
			String s2;
				
			for (int i=0;i<this.filas;i++){
				s=br.readLine();
				st = new StringTokenizer (s);
				for (int j=1;j<=col;j++){
					s2 = st.nextToken();
			        if (s2.compareTo("0")==0) {
			            s2=" ";
			        }
			        sol[i][j]=s2; 			           
			    }
				modelo.insertRow(i, sol[i]); 
				  
			}
			for (int i=0;i<this.filas;i++) {
				modelo.setValueAt(i+1, i, 0);
				tabla.getColumnModel().getColumn(0).setCellRenderer(tabla.getTableHeader().getDefaultRenderer());
			}
		} catch (Exception exception) {
			System.err.println("Error cargando");
			exception.printStackTrace();
		} finally {
			if (lector != null) {
				try {
				    lector.close();
				} catch (IOException exception) {
				    System.err.println("Error cerrando lector");
				    exception.printStackTrace();
				}
			} 
		}
	}
	
	/* Listener de la tabla modelo */
	private void listenerModeloTabla(JTable tabla) {
		tabla.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent evento) {
				int fila=tabla.getSelectedRow();
				int columna=tabla.getSelectedColumn();
				//Si la fila o col es -1, la seteamos a 0
				if (fila==-1) {
					fila=0;
				}
				if (columna==-1) {
					columna=1;		
				}
				//Llamamos a la funcion de actualizar la tabla modelo con el valor de la fila y col dados
				actualizarTablaModelo(evento, fila, columna);
			}
		});
	}
		
	/* Funcion nos permite setearcorrectamente los valores A-Z de las columnas de una tabla */	
	public void inicializaColumnaMatriz(String[] aux2) {
		String[] valores = new String[] { "", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
					"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		int cont=1;
		aux2[0]="";
		for (int i=0;i<27;i++){
			for (int j=0;j<27;j++){
				for (int k=1;k<27;k++){
					aux2[cont]=valores[i]+valores[j]+valores[k];
					cont++;
				}
			}
		}
		for (int i=0;i<this.filas;i++) {
			for (int j=1;j<=this.col;j++) {
				sol[i][j]="";
			}
		}
	}
	
	/* Funcion nos permite actualizar la tabla modelo */
	public void actualizarTablaModelo(TableModelEvent evento, int fila, int col) {
		if((evento.getType()==TableModelEvent.UPDATE) && (buleano==false)) {
			TableModel model = ((TableModel)(evento.getSource()));
			siguiente++;
			atras++;
			//Obtenermos el valor de la celda introducida segun la fila y col dadas
			String valorCelda = String.valueOf(model.getValueAt(fila, col));
			Celdas celda = new Celdas(valorCelda, fila, col);
				
			//Anyadimos la celda al arrayList
			hojas.add(celda);
			rellenaMatrizSol();
		}
	}

	/* Funcion nos permite rellenar la matriz solucion (la de rehacer/deshacer) con 0 o valores deshechos/rehechos */
	private void rellenaMatrizSol() {
		for (int i = 0; i < this.filas; i++) {
			for (int j=1;j<this.col+1;j++) {
				Object valor = modelo.getValueAt(i, j);
				String cadenaAux =String.valueOf(valor);
				if (valor!=null) {
					sol[i][j]=cadenaAux;
				} else {
					sol[i][j]="0";
				}
			}
		}
	}
	
	/* Funcion muestra los valores de la tabla y los actualiza */
	public void cambiarValoresModelo(){	
		buleano=true;
		for (int i=0;i<this.filas;i++){
			for (int j=1;j<=this.col;j++){
				if (sol[i][j]=="0") {
					modelo.setValueAt(null,i,j);
				}else {
				modelo.setValueAt(String.valueOf(sol[i][j]),i,j);
				}
			}
		}
		buleano=false;
	}
	
	/* Funcion nos permite checkear que valores son introducidos para las filas y columnas */
	public void valoresEntrada() {
		String entradaFilas = JOptionPane.showInputDialog("Numero de filas que desea tener");
		if(entradaFilas==null) {
			int salida=JOptionPane.showConfirmDialog(null, "¿Desea cancelar la operación?");
			if(salida==JOptionPane.YES_OPTION) {
				
			}
		}else {
			filas=Integer.parseInt(entradaFilas);
			String entradaColum=JOptionPane.showInputDialog("Numero de columnas que desea tener");
			if(entradaColum==null) {
				int salida1=JOptionPane.showConfirmDialog(null, "¿Desea cancelar la operación?");
				if(salida1==JOptionPane.YES_OPTION) {
				}	
			}else {
				col=Integer.parseInt(entradaColum);
				if(col<=0 || col>18270 || filas<=0 || filas>18270) {
					JOptionPane.showMessageDialog(null, "Debe introducir numeros positivos mayores que 0!","Advertencia",JOptionPane.WARNING_MESSAGE);
				}else {
					Frame interfaz = new Frame (filas,col);
					interfaz.creacionSwing();
					frame.dispose();
				}
			}
		}
	}
	
	
	
	
	
}
