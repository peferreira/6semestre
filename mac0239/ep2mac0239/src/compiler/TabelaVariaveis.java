package compiler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TabelaVariaveis {

	private Map<String, Incrementador> map;
	
	public Map<String, Incrementador> getMap() {
		return map;
	}
	public TabelaVariaveis(){
		map = new HashMap<String, Incrementador>();
	}
	void addToTable(String s, Incrementador i){
		if(map.containsKey(s) == false){
			map.put(s, i);
		}
	}
	Incrementador getIncremento(String s){
		return map.get(s);
	}
	
	void printHashKeys(){
		for(Entry<String, Incrementador> entry: map.entrySet() ){
			System.out.println(entry.getKey());
		}
	}
	
}
