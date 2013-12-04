package compiler;

public class Incrementador {
	String variavel;
	int inicio;
	int incrementador;
	int fim;

	public Incrementador(String variavel, int inicio, int fim) {
		this.variavel = variavel;
		this.inicio = inicio;
		this.fim = fim;
		this.incrementador = inicio-1;
	}

	public int getInicio() {
		return inicio;
	}

	public int getFim() {
		return fim;
	}

	public boolean incrementa() {
		if (incrementador < fim) {
			incrementador++;
			return true;
		} else {
			return false;
		}

	}

	public int getIncrementador() {
		return incrementador;
	}

	public String getVariavel() {
		return variavel;
	}

	boolean decrementa() {
		if(incrementador > inicio){
			incrementador--;
			return true;
		}
		else
			return false;
	}

	public void inicializa() {
		incrementador = inicio-1;
	}

}
