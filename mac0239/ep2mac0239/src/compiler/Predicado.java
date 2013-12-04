package compiler;

import java.util.ArrayList;

public class Predicado {
	boolean negado = false;
	private String identificador;
	private ArrayList<String> argumentos;
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
		/*Incrementador i = new Incrementador(arg, 0, 5);*/
		i.inicializa();
		argumentos.add(arg);
		if (table.getMap().containsKey(arg) == false) {
			table.addToTable(arg, i);
		} else {
			/*System.out.println(arg + "não inserido pois ja esta na tabela de variaveis da clausula");*/
		}
	}

	void printArg() {
		Incrementador inc;
		System.out.print("(");
		inc = table.getIncremento(argumentos.get(0));
		System.out.print(inc.getIncrementador());
		for(int i = 1; i < argumentos.size(); i++){
			inc = table.getIncremento(argumentos.get(i));
			System.out.print(","+ inc.getIncrementador());
		}
		/*for (String s : argumentos) {
			inc = table.getIncremento(s);
			System.out.print("," + inc.getIncrementador());
		}*/
		System.out.print(")");
	}

	public void print() {
		if (negado == true) {
			System.out.print("-");
		}
		System.out.print(identificador);
		printArg();
	}

}
