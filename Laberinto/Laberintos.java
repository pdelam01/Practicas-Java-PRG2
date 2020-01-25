package laberinto;

import java.util.ArrayList;
import java.util.Scanner;

public class Laberintos {
	private Celdas[][] laberinto;
	private int tamanio;
	Scanner scan;
	private ArrayList<Celdas> solucionAux;
	private ArrayList<Celdas> solucion;
	private ArrayList<Celdas> solucion2;
	
	
    public Laberintos(int tam, Scanner input) {
        this.laberinto = new Celdas[tam][tam];
        this.tamanio=tam;
		this.scan=input;
    }
    
    public void rellenarMatriz() {
		for(int i=0; i<this.tamanio;i++) {
			String line = this.scan.nextLine();
			char[] aux=line.toCharArray();
			
			for(int j=0;j<this.tamanio;j++) {
				Celdas casi = new Celdas(aux[j],i,j);
				this.laberinto[i][j]=casi;
			}
		}
	}
    
    public void resolverLaberinto() {
		if(this.laberinto[0][0].getCharacter()=='1' || this.laberinto[tamanio-1][tamanio-1].getCharacter()=='1') {
			System.out.println("NO.");
			System.exit(-1);
		}else {
			if(this.tamanio==1) {
				if(this.laberinto[0][0].getCharacter()=='0') {
					System.out.println("SI, SIN PREMIO.");
					System.out.println("(1,1)");
					System.exit(-1);
				}else {
					System.out.println("SI, CON PREMIO.");
					System.out.println("(1,1)*");
					System.exit(-1);
				}
			}else {
				int asterisco=0;
				for(int i=0;i<this.tamanio;i++) {
					for(int j=0; j<this.tamanio; j++) {
						if(this.laberinto[i][j].getCharacter()=='*') {
							asterisco=1;
						}
					}
				}
				if(asterisco==0) {
					solucion = new ArrayList<Celdas>();
					solucionAux = new ArrayList<Celdas>();
					solucionAux.add(this.laberinto[0][0]);
					resolverSinAsterisco(this.laberinto[0][0], this.laberinto[tamanio-1][tamanio-1], solucion, solucionAux);
					if(solucion.size()==0) {
						System.out.println("NO.");
					}else {
						System.out.println("SI, SIN PREMIO");
						String errorDisplay=solucion.toString();
						errorDisplay=errorDisplay.substring(1,errorDisplay.length()-1);
						System.out.println(errorDisplay.replaceAll(",","").replaceAll("/",","));
					}
				}else{
					solucion = new ArrayList<Celdas>();
					solucionAux = new ArrayList<Celdas>();
					solucion2= new ArrayList<Celdas>();
					solucionAux.add(this.laberinto[0][0]);
					int filaAst=0,colAst=0;
					for(int i=0;i<this.tamanio;i++) {
						for(int j=0; j<this.tamanio; j++) {
							if(this.laberinto[i][j].getCharacter()=='*') {
								this.laberinto[i][j].setCharacter('0');
								filaAst=i;
								colAst=j;
							}
						}
					}
					if(asteriscoConUnos(this.laberinto[filaAst][colAst])) {
						resolverSinAsterisco(this.laberinto[0][0], this.laberinto[this.tamanio-1][this.tamanio-1], solucion, solucionAux);
						System.out.println("SI, SIN PREMIO");
                        String errorDisplay = solucion.toString();
                        errorDisplay  = errorDisplay.substring(1, errorDisplay.length() - 1);
                        System.out.println(errorDisplay.replace(",", "").replace("/", ","));
					}else {
						resolverSinAsterisco(this.laberinto[0][0], this.laberinto[filaAst][colAst], solucion, solucionAux);
						if(solucion.size()==0) {
							System.out.println("NO.");
							System.exit(-1);
						}
						solucion2.addAll(solucion);
						solucion.removeAll(solucion);
						solucionAux.removeAll(solucionAux);
						resetearMatriz();
						
						resolverSinAsterisco(this.laberinto[filaAst][colAst], this.laberinto[this.tamanio-1][this.tamanio-1], solucion, solucionAux);
                        solucion2.addAll(solucion);
                        if(solucion.size()==0) {
                            System.out.println("NO.");
                        }else {
                            System.out.println("SI, CON PREMIO.");
                            this.laberinto[filaAst][colAst].setCharacter('*');
                            String errorDisplay = solucion2.toString();
                            errorDisplay  = errorDisplay.substring(1, errorDisplay.length() - 1);
                            System.out.println(errorDisplay.replace(",", "").replace("/", ","));
                        }
					}
				}
			}
		}
	}
    
   
    public void resolverSinAsterisco(Celdas casillaActual, Celdas casillaFin, ArrayList<Celdas> solucion, ArrayList<Celdas> solucionAux) {
    	if (casillaActual.equals(casillaFin)) {
    		compararSol(solucionAux,solucion);
    	}else {
    		int[][] movimientos={{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{1,-1}};
    		int filaNueva, colNueva;
            Celdas aux;
            
            for (int i = 0; i < movimientos.length; i++) {
                filaNueva = casillaActual.getFilas() + movimientos[i][0];
                colNueva = casillaActual.getCol() + movimientos[i][1];
                if(filaNueva>=0 && filaNueva<this.tamanio && colNueva>=0 && colNueva<this.tamanio) {
                	aux = this.laberinto[filaNueva][colNueva];
                	if(comprobarCabe(aux)) {
                    	switch(i) {
                    	case 0: 
                            if (arribaDisponible(aux)) {
                                solucionAux.add(aux);
                                casillaActual.setCharacter('@');
                                resolverSinAsterisco(aux, casillaFin, solucion, solucionAux);
                                casillaActual.setCharacter('0');
                                solucionAux.remove(aux);
                            }
                            break;
                    	case 1: 
                    		 if (arribaDiagonalDisponible(aux)) {
                    			 solucionAux.add(aux);
                                 casillaActual.setCharacter('@');
                                 resolverSinAsterisco(aux,casillaFin, solucion, solucionAux);
                                 casillaActual.setCharacter('0');
                                 solucionAux.remove(aux);
                             }
                    		 break;
                    	case 2:
                    		if (derechaDisponible(aux)) {
                    			solucionAux.add(aux);
                                casillaActual.setCharacter('@');
                                resolverSinAsterisco(aux,casillaFin, solucion, solucionAux);
                                casillaActual.setCharacter('0');
                                solucionAux.remove(aux);
                            }
                   		 	break;
                    	case 3:
                    		if (derechaDiagonalDisponible(aux)) {
                    			solucionAux.add(aux);
                                casillaActual.setCharacter('@');
                                resolverSinAsterisco(aux,casillaFin, solucion, solucionAux);
                                casillaActual.setCharacter('0');
                                solucionAux.remove(aux);
                            }
                   		 	break;
                    	case 4:
                    		if (abajoDisponible(aux)) {
                    			solucionAux.add(aux);
                                casillaActual.setCharacter('@');
                                resolverSinAsterisco(aux,casillaFin, solucion, solucionAux);
                                casillaActual.setCharacter('0');
                                solucionAux.remove(aux);
                            }
                   		 	break;
                    	case 5:
                    		if (abajoDiagonalDisponible(aux)) {
                    			solucionAux.add(aux);
                                casillaActual.setCharacter('@');
                                resolverSinAsterisco(aux, casillaFin,solucion, solucionAux);
                                casillaActual.setCharacter('0');
                                solucionAux.remove(aux);
                            }
                    		break;
                    	case 6:
                    		if (izquierdaDisponible(aux)) {
                    			solucionAux.add(aux);
                                casillaActual.setCharacter('@');
                                resolverSinAsterisco(aux,casillaFin, solucion, solucionAux);
                                casillaActual.setCharacter('0');
                                solucionAux.remove(aux);
                            }
                    		break;
                    	case 7:
                    		if (izquierdaDiagonalDisponible(aux)) {
                    			solucionAux.add(aux);
                                casillaActual.setCharacter('@');
                                resolverSinAsterisco(aux,casillaFin,solucion, solucionAux);
                                casillaActual.setCharacter('0');
                                solucionAux.remove(aux);
                            }
                   		 	break;
                    	}
                    }
                }
            }
    	}
    }
    
    public boolean asteriscoConUnos(Celdas cas) {
        int count = 0;
       
        int[][] movimientos = { {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {1,-1} };
       
        int filaNueva, colNueva;
        Celdas aux;
       
        for(int i=0;i<movimientos.length;i++) {
 
            filaNueva = cas.getFilas() + movimientos[i][0];
            colNueva = cas.getCol() + movimientos[i][1];
           
            if(filaNueva<0 || colNueva<0 || filaNueva>=this.tamanio || colNueva>=this.tamanio) {
                count ++;
            }else {
               
                aux = this.laberinto[filaNueva][colNueva];
                    switch (i) {
                        case 0:
                            if(!arribaDisponible(aux)) {
                                if(aux.getCharacter()!='/') {
                                    count++;
                                }
                            }
                            break;
                        case 1:
                            if(!arribaDiagonalDisponible(aux)) {
                                if(aux.getCharacter()!='/') {
                                    count++;
                                }
                            }
                            break;
                        case 2:
                            if(!derechaDisponible(aux)) {
                                if(aux.getCharacter()!='/') {
                                    count++;
                                }
                            }
                            break;
                        case 3:
                            if(!derechaDiagonalDisponible(aux)) {
                                if(aux.getCharacter()!='/') {
                                    count++;
                                }
                            }
                            break;
                        case 4:
                            if(!abajoDisponible(aux)) {
                                if(aux.getCharacter()!='/') {
                                    count++;
                                }
                            }
                            break;
                        case 5:
                            if(!abajoDiagonalDisponible(aux)) {
                                if(aux.getCharacter()!='/') {
                                    count++;
                                }
                            }
                            break;
                        case 6:
                            if(!izquierdaDisponible(aux)) {
                                if(aux.getCharacter()!='/') {
                                    count++;
                                }
                            }
                            break;
                        case 7:
                            if(!izquierdaDiagonalDisponible(aux)) {
                                if(aux.getCharacter()!='/') {
                                    count++;
                                }
                            }
                            break;
                    }
            }
        }
        if(count == 8) {
            return true;
        }else {
            return false;
        }
       
    }
    
    public void resetearMatriz() {
        
        for(int i=0;i<this.tamanio;i++) {
            for(int j=0;j<this.tamanio;j++) {
                if(this.laberinto[i][j].getCharacter()=='@') {
                    this.laberinto[i][j].setCharacter('0');
                }
            }
        }
    }
    
    public void compararSol(ArrayList<Celdas> solucionAux, ArrayList<Celdas> solucion) {
        if (solucion.size()==0 || solucion.size()>solucionAux.size()) {
            solucion.removeAll(solucion);
            solucion.addAll(solucionAux);
        }
    }
    
    @Override
    public String toString() {
		StringBuilder output = new StringBuilder();
		Celdas cell;
		for(int i=0;i<solucion.size();i++) {
			cell=solucion.get(i);
			output.append(cell.toString());
		}
		return output.toString();
    }
    
   
    public boolean comprobarCabe(Celdas cell) {
		boolean comprobar=false;
		if(cell.getFilas()>=0 && cell.getFilas()<=this.tamanio-1 && cell.getCol()>=0 && cell.getCol()<=this.tamanio-1) {
			comprobar=true;
		}
		return comprobar;
	}
    
    public boolean derechaDisponible(Celdas cellDestino) {
    	if (cellDestino.getCharacter()=='@' || cellDestino.getCharacter()=='1') {
        	return false;
        }
        return true;
    }
    
    public boolean derechaDiagonalDisponible(Celdas cellDestino) {
        if (cellDestino.getCharacter()=='@' || cellDestino.getCharacter()=='1') {
        	return false;
        }
        return true;
    }
    
    public boolean izquierdaDisponible(Celdas cellDestino) {
    	if (cellDestino.getCharacter()=='@' || cellDestino.getCharacter()=='1') {
        	return false;
        }
        return true;
    }
   
    public boolean izquierdaDiagonalDisponible(Celdas cellDestino) {
    	if (cellDestino.getCharacter()=='@' || cellDestino.getCharacter()=='1') {
        	return false;
        }
        return true;
    }
    
    public boolean arribaDisponible(Celdas cellDestino) {
    	if (cellDestino.getCharacter()=='@' || cellDestino.getCharacter()=='1') {
        	return false;
        }
        return true;
    }
    
    public boolean arribaDiagonalDisponible(Celdas cellDestino) {
    	if (cellDestino.getCharacter()=='@' || cellDestino.getCharacter()=='1') {
        	return false;
        }
        return true;
    }
    
    public boolean abajoDisponible(Celdas cellDestino) {
    	if (cellDestino.getCharacter()=='@' || cellDestino.getCharacter()=='1') {
        	return false;
        }
        return true;
    }
    
    public boolean abajoDiagonalDisponible(Celdas cellDestino) {
    	if (cellDestino.getCharacter()=='@' || cellDestino.getCharacter()=='1') {
        	return false;
        }
        return true;
    }
}
