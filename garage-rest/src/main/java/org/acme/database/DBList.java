package org.acme.database;

import java.util.ArrayList;
import java.util.List;

import org.acme.Auto;
import org.acme.DBInterface;

public class DBList implements DBInterface{

	private List <Auto> garage = new ArrayList<Auto>();
	//singleton
	private final static DBList INSTANCE = new DBList();

	private DBList() {}
	
	//ritorna l'unica istanza del DbList
	public static DBList getInstance() {
		return INSTANCE;
	}
	//ritorna l'intero garage
	@Override
	public List<Auto> getGarage() {
		return garage;
	}
	//mostra l'auto sezionata tramite id
	@Override
	public Auto getAuto(int id) {
		for(Auto auto:garage) {
			if (auto.getId() == id) {
				return auto;
			}
		}
		return null;
	}
	//ricerca delle auto tramite un colore
	@Override
	public List<Auto> cercaColore(String colore) {
		List<Auto> risultatoRicerca = new ArrayList<Auto>();
		for(Auto auto:garage) {
			if(auto.getColore().equalsIgnoreCase(colore)) {
				risultatoRicerca.add(auto);
			}
		}
		return risultatoRicerca;
	}
	//aggiunge una nuova auto al garage
	@Override
	public void aggiungiAuto(Auto auto) {
		if(!auto.equals(null)) {
			garage.add(auto);
		}
	}
	
	//sostituisce un'auto contenuta nel garage con una nuova auto
	@Override
	public void modificaGarage(int chiave, Auto auto) {
		for(int i=0; i<garage.size(); i++) {
			if(garage.get(i).getId() == chiave) {
				garage.set(i, auto);
			}
		}
	}

	//elimina un'auto contenuta nel garage
	@Override
	public void eliminaAuto(int id) {
		garage.remove(INSTANCE.getAuto(id));

	}
	//ricerca delle auto su un singolo campo 
	public List<Auto> ricercaSingoloCampo (Condizione condizione) {
		String campo = condizione.getCampo();
		List<String> parametri = condizione.getParametri();
		List<Auto> risRicerca = new ArrayList<Auto>();

		if(campo.equalsIgnoreCase("colore")){
			for (String parametro : parametri) {
				for (Auto auto : garage) {
					if(auto.getColore().equalsIgnoreCase(parametro)) {
						risRicerca.add(auto);
						return risRicerca;
					}
				}

			}

		}

		if(campo.equalsIgnoreCase("marca")){
			for (String parametro : parametri) {
				for (Auto auto : garage) {
					if(auto.getColore().equalsIgnoreCase(parametro)) {
						risRicerca.add(auto);
						return risRicerca;
					}
				}

			}

		}

		if(campo.equalsIgnoreCase("modello")){
			for (String parametro : parametri) {
				for (Auto auto : garage) {
					if(auto.getColore().equalsIgnoreCase(parametro)) {
						risRicerca.add(auto);
						return risRicerca;
					}
				}

			}
		}

		return null;

	}



	//ricerca generica delle auto tramite uno o pi?? parametri (o condizioni)
	@Override
	public List<Auto> ricerca(List<Condizione> condizioni) {
		List<Auto> risRicerca = new ArrayList<Auto>();

		for (Condizione condizione: condizioni) {
			List<Auto> lista = new ArrayList<Auto>();
			List<Auto> listaAppoggio = ricercaSingoloCampo(condizione);

			for (Auto auto: listaAppoggio) {
				for (Auto auto1 : risRicerca) {
					if(auto.getId() == auto1.getId()) {
						lista.add(auto);
					}
				}
			}
			risRicerca = lista;
		}
		return risRicerca;

	}
	// verifica se un'auto ?? contenuta nel garage
	@Override
	public Boolean contiene(Auto auto){
		return garage.contains(auto);
	}


}
