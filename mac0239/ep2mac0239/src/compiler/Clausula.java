package compiler;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Clausula {

	ArrayList<Predicado> predicados;
	ArrayList<Restricao> restricoes;
	TabelaVariaveis table;
	ArrayList<Incrementador> incrementadores;

	public Clausula() {
		predicados = new ArrayList<Predicado>();
		restricoes = new ArrayList<Restricao>();
		table = new TabelaVariaveis();
		incrementadores = new ArrayList<Incrementador>();
	}


	public void addRestricao(Restricao r ){
		restricoes.add(r);
	}
	
	public void addPredicado(Predicado p) {
		p.setTableVariaveis(table);
		predicados.add(p);
	}

	public void print() {
		/*
		 * int i; Incrementador inc; i = predicados.size()-1; boolean saturado =
		 * false; while ( i >= 0) { inc = incrementadores.get(i); saturado =
		 * inc.incrementa(); if(saturado == true) i--;
		 */
		String[] identif = new String[table.getMap().entrySet().size()];
		int i = 0;
		for (Entry<String, Incrementador> entry : table.getMap().entrySet()) {
			String key = entry.getKey();
			identif[i] = key;
			i++;
		}

		/*
		 * for (i = 0; i < identif.length; i++) {
		 * System.out.println(identif[i]); }
		 */
		/*printaRestricoes();*/

		backtracking(0, identif);

	}
	void printaRestricoes(){
		for(Restricao r: restricoes){
			r.print();
			System.out.print(" ");
		}
		
	}
	
	void backtracking(int i, String[] identif) {
		if (i == identif.length) {
			for( int r = 0; r < restricoes.size(); r++){
				
				if((restricoes.get(r).satisfazRestricao(table)) == false){
					return;
				}
			}
			Predicado p = predicados.get(0);
			p.print();
			for (int j = 1; j < predicados.size(); j++) {
				System.out.print(" ");
				p = predicados.get(j);
				p.print();
			}
			System.out.println(".");
		}

		else {
			Incrementador inc = table.getIncremento(identif[i]);
			while (inc.incrementa()) {
				backtracking(i + 1, identif);
			}
			inc.inicializa();
		}
	}

	public void insereVariavel(Incrementador incremento) {
		table.addToTable(incremento.getVariavel(), incremento);
	}
}
