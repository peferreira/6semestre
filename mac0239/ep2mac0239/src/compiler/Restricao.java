package compiler;

import java.util.ArrayList;

public class Restricao {
	String comparador;
	ArrayList<Tupla> esq;
	ArrayList<Tupla> dir;

	public Restricao() {
		esq = new ArrayList<>();
		dir = new ArrayList<>();
	}
	void setComparador(String comparador){
		this.comparador = comparador;
	}
	void addEsq(String v, String sinal) {
		Tupla t = new Tupla(v, sinal);
		esq.add(t);
	}

	void addDir(String v, String sinal) {
		Tupla t = new Tupla(v, sinal);
		dir.add(t);
	}
	
	boolean realizaOperacao(int num1, int num2){
		if(comparador.equals("=")){
			return num1 == num2;
		}
		else if(comparador.equals("<")){
			return num1 < num2;
		}
		else if(comparador.equals(">")){
			return num1 > num2;
		}
		else if(comparador.equals("!=")){
			return num1 != num2;
		}
		return false;		
	}
	
	
	
	
	public boolean satisfazRestricao(TabelaVariaveis table){
		int resultEsq = 0,resultDir = 0, sinal = 0;
		Tupla temp;
		for(int i = 0; i < esq.size(); i++){
			temp = esq.get(i);
			sinal = temp.getSinal();
			
			resultEsq = resultEsq + sinal*table.getMap().get(esq.get(i).getVar()).getIncrementador();
		}
		temp = null;
		for(int i = 0; i < dir.size(); i++){
			temp = dir.get(i);
			sinal = temp.getSinal();
			resultDir = resultDir + sinal*table.getMap().get(dir.get(i).getVar()).getIncrementador();
		}
		boolean b = realizaOperacao(resultEsq, resultDir);
		return b;
	}
	public void print() {
		for(Tupla t: esq){
			if(t.getSinal() == -1){
				System.out.print("-");
			}
			System.out.print(t.getVar());
		}
		System.out.print(comparador);
		for(Tupla t: dir){
			if(t.getSinal() == -1){
				System.out.print("-");
			}
			System.out.print(t.getVar());
		}
	}
}



class Tupla {
	String v;
	String sinal;

	Tupla(String v, String sinal) {
		this.v = v;
		this.sinal = sinal;
	}
	
	int getSinal(){
		if(sinal == "+"){
			return 1;
		}
		else{
		return -1;
		}
	}
	String getVar(){
		return v;
	}
}