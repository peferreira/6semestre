package compiler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeradorCnf {
	ArrayList<Clausula> clausulas;
	private Map<String, Integer> mapaCnf;
	int numberOfVariables;
	boolean state;
	public GeradorCnf(ArrayList<Clausula> clausulas){
		this.clausulas = clausulas;
		numberOfVariables = 1;
		this.mapaCnf = new HashMap<String,Integer>();
		this.state = false;
	}
	void setState(boolean state){
		this.state = state;
	}
	boolean getState(){
		return state;
	}
	int getNumberOfVariables(){
		return numberOfVariables;
	}
	void addToMap(String key){
		mapaCnf.put(key, numberOfVariables);
		numberOfVariables++;
	}
	
	void print(){
		 for (Clausula c : clausulas)
		    {
			 
			   for(Predicado p: c.getPredicados()){
				   hello(p.getIdentificador(), p.getArgumentos());
			   }
		    }
	}
	
	void hello(String key, ArrayList<String> argumentos){
		StringBuilder sb = new StringBuilder();

		for (Object obj : argumentos) {
		  sb.append(obj.toString());
		  sb.append("");
		}
		String finalString = sb.toString();
		System.out.println(key+finalString);
	}
	
	
	
	boolean isInMapCnf(String key){
		return mapaCnf.containsKey(key);
	}
	
	Map<String, Integer> getMapCnf(){
		return mapaCnf;
	}
	
	
}
