package org.acme.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acme.Auto;
import org.acme.DBInterface;

public class DBHashMap implements DBInterface {

    private HashMap<Integer, Auto> garage = new HashMap<Integer, Auto>();
    private static Integer key = 1;
    
	//singleton
    private final static DBHashMap INSTANCE = new DBHashMap();
    
    private DBHashMap() {
    }
    
	//ritorna l'unica istanza del DBHashMap
    public static DBHashMap getInstance() {
        return INSTANCE;
    }
	// mostra tutto il garage
    @Override
    public List<Auto> getGarage() {
        return new ArrayList<Auto>(garage.values());
    }
	// recupera un'auto tramite l'id
    @Override
    public Auto getAuto(int id) {
        for (Map.Entry<Integer, Auto> auto : garage.entrySet()) {
            if (auto.getValue().getId() == id) {
                return auto.getValue();
            }
        }
        return null;
    }
    //così controlla id dell'auto, sotto la key

    public Auto getAutoFromKey(int key) {
        return garage.get(key);
    }

	//ricerca delle auto tramite un colore
    @Override
    public List<Auto> cercaColore(String colore) {
        List<Auto> lista = new ArrayList<Auto>();
        for (Map.Entry<Integer, Auto> auto : garage.entrySet()) {
            if (auto.getValue().getColore().equalsIgnoreCase(colore)) {
                lista.add(auto.getValue());
            }
        }
        return lista;
    }

	//aggiunge una nuova auto al garage
    @Override
    public void aggiungiAuto(Auto auto) {
    		  if (garage.size() == 0) {
    		   auto.setId(1);
    		  }
    		  else {
    		   auto.setId(garage.get(garage.size()).getId() + 1);
    		  }
    		  garage.put(key++, auto);
    		 }
   

    //sostituisce un'auto contenuta nel garage con una nuova auto
    @Override
    public void modificaGarage(int chiave, Auto auto) {
        garage.replace(chiave, auto);
    }


    @Override
    public void eliminaAuto(int id) {

    }


	@Override
	public List<Auto> ricerca(List<Condizione> condizioni) {
		// TODO Auto-generated method stub
		return null;
	}

    //verifica se un'auto è contenuta nel garage
    @Override
    public Boolean contiene(Auto auto){
        return garage.containsValue(auto);
    }

	
}

