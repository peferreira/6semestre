package compiler;

import java.util.ArrayList;

public class Predicado {
	boolean negado = false;
	private String identificador;
	private ArrayList<String> argumentos;

	public String getIdentificador() {
		return identificador;
	}

	public ArrayList<String> getArgumentos() {
		return argumentos;
	}

	TabelaVariaveis table;

	public Predicado(String identificador) {
		this.identificador = identificador;
		argumentos = new ArrayList<String>();
	}

	void setTableVariaveis(TabelaVariaveis table) {
		this.table = table;
	}

	boolean getNegado() {
		return negado;
	}

	void setNegado(boolean negado) {
		this.negado = negado;
	}

	public void addArg(String arg, Incrementador i) {
		/* Incrementador i = new Incrementador(arg, 0, 5); */
		i.inicializa();
		argumentos.add(arg);
		if (table.getMap().containsKey(arg) == false) {
			table.addToTable(arg, i);
		} else {
			/*
			 * System.out.println(arg +
			 * "não inserido pois ja esta na tabela de variaveis da clausula");
			 */
		}
	}

	void printArg() {
		Incrementador inc;
		String s = "";
		System.out.print(identificador);
		System.out.print("(");
		inc = table.getIncremento(argumentos.get(0));
		s = s + Integer.toString(inc.getIncrementador());
		System.out.print(inc.getIncrementador());
		for (int i = 1; i < argumentos.size(); i++) {
			inc = table.getIncremento(argumentos.get(i));
			System.out.print("," + inc.getIncrementador());
			s = s + Integer.toString(inc.getIncrementador());
		}

		/*
		 * for (String s : argumentos) { inc = table.getIncremento(s);
		 * System.out.print("," + inc.getIncrementador()); }
		 */
		System.out.print(")");
	}

	String geraChave() {
		Incrementador inc;
		String s = "";
		inc = table.getIncremento(argumentos.get(0));
		s = s + Integer.toString(inc.getIncrementador());
		for (int i = 1; i < argumentos.size(); i++) {
			inc = table.getIncremento(argumentos.get(i));
			s = s + Integer.toString(inc.getIncrementador());
		}
		return identificador + s;
	}

	public void print(GeradorCnf gerCnf) {
		String s;
		boolean stateOfCnf;
		int variable;
		stateOfCnf = gerCnf.getState();
		if (negado == true) {
			System.out.print("-");
		}
		if (stateOfCnf == true) {
			s = geraChave();
			if (gerCnf.isInMapCnf(s) == false) {
				gerCnf.addToMap(s);
				variable = gerCnf.getMapCnf().get(s);
				System.out.print(variable);
			} else {
				variable = gerCnf.getMapCnf().get(s);
				System.out.print(variable);
			}
		}
		else{
			printArg();
		}

	}

}
