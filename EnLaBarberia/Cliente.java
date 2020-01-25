package labarberia;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Cliente extends java.lang.Thread{
	
	public static NormalDistribution distribucionNormal;
	public static Barberia barberia;
	private int clientID;
	
	
	
	public Cliente(int j) {
		this.clientID=j;
		System.out.println("El cliente "+this.clientID+" se ha creado");
	}
	

	public int getClientID() {
		return clientID;
	}



	public void setClientID(int clientID) {
		this.clientID = clientID;
	}



	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(2000);
				barberia.entrarCliente();
				barberia.marcharSinAtender();
				barberia.sentar();
			} catch (Exception e) {
				System.out.println("");
			}
		}
	}

	

}
