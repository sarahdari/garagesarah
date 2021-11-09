package org.acme;

import java.util.List;

import org.acme.database.Condizione;

public interface DBInterface {

	List<Auto> getGarage();

	Auto getAuto(int id);

	List<Auto> cercaColore(String colore);

	void aggiungiAuto(Auto auto);

	void modificaGarage(int chiave, Auto auto);

	void eliminaAuto(int id);
	
	List<Auto> ricerca(List<Condizione> condizioni);

}
