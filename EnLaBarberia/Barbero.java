package labarberia;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class Barbero extends Thread{

	public static ExponentialDistribution distribucionExponencial;
	public static Barberia barberia;
	private int barberoID;

	public Barbero(int i) {
		this.barberoID=i;
	}
	
	public int getBarberoID() {
		return barberoID;
	}

	public void setBarberoID(int barberoID) {
		this.barberoID = barberoID;
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(2000);
				barberia.entrar();
				barberia.atiendeCliente();
				barberia.cortaPelo();
			} catch (Exception e) {
				System.out.println("");
			}
		}
		
	}

}
