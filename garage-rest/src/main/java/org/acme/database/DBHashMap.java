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

    private final static DBHashMap INSTANCE = new DBHashMap();
    
    private DBHashMap() {
    }

    public static DBHashMap getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Auto> getGarage() {
        return new ArrayList<Auto>(garage.values());
    }

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
   

    //modifica dalla chiave e non dall'id
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

	
}

