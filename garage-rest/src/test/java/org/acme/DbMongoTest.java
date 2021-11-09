package org.acme;



import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.acme.database.Condizione;
import org.acme.database.DbMongo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DbMongoTest {

	@Inject
	DbMongo garage;


	/*public void tearDown() {
		garage = null
		assertNull(garage);
	}*/
	// testa il metodo getGarage()
	@Test
	@Order(1)
	public void getGarage() {
		assertNotNull(garage);	
	}
	
	//testa il metodo aggiungiAuto() e lo esegue per primo
	@Test
	@Order(1)
	public void aggiungiAuto() {
		Auto auto = new Auto("bianco","a1","audi");
		garage.aggiungiAuto(auto);
		assertNotNull(garage.getAuto(auto.getId()));
	}
	
	// testa il metodo getAuto() e lo esegue per secondo
	@Test
	@Order(2)
	public void getAuto() {
		Auto auto = new Auto ("bianco","a1","audi");
		garage.aggiungiAuto(auto);
		assertNotNull(garage.getAuto(auto.getId()));
		assertNotNull(garage.getAuto(auto.getId()).getMarca());
		assertNotNull(garage.getAuto(auto.getId()).getColore());
		assertNotNull(garage.getAuto(auto.getId()).getModello());

	}
	
	// testa il metodo modificaGarage() e lo esegue per terzo
	@Test
	@Order(3)
	void testModificaGarage() {
		Auto autoNuova = new Auto("rosso", "punto", "fiat");
		garage.modificaGarage(1, autoNuova);
		assertEquals(autoNuova.getId(), garage.getAuto(autoNuova.getId()).getId());
		assertEquals(autoNuova.getColore(), garage.getAuto(autoNuova.getId()).getColore());
		assertEquals(autoNuova.getMarca(), garage.getAuto(autoNuova.getId()).getMarca());
		assertEquals(autoNuova.getModello(), garage.getAuto(autoNuova.getId()).getModello());

	}
	//testa il metodo elimaAuto() e lo esegue per quarto
	@Test
	@Order(4)
	public void eliminaAuto() {
		Auto auto = new Auto("bianco", "a1", "audi");
		garage.aggiungiAuto(auto);
		garage.eliminaAuto(auto.getId());
		assert (!garage.contains(auto));
	}

	//testa il metodo cercaColore() e lo esegue per quinto
	@Test
	@Order(5)
	public void cercaColore() {
		for(Auto auto:garage.cercaColore("bianco")) {
			assertEquals("bianco",auto.getColore()); 
		}

	}
	
	//testa il metodo ricerca() e lo esegue per quinto
	@Test
	@Order(5)

    public void ricerca2() {
		List<String> marche = new ArrayList<String>();
		marche.add("citroen");
		marche.add("fiat");
		
		List<String> colori = new ArrayList<String>();
		colori.add("giallo");
		colori.add("verde");
		
		List<String> modelli = new ArrayList<String>();
		modelli.add("c1");
		modelli.add("panda");
		
		Condizione a = new Condizione ("marca", marche);
		Condizione b = new Condizione ("colore", colori);
		Condizione c = new Condizione ("modello", modelli);
		
		List <Condizione> condizioni = new ArrayList<Condizione> ();
		condizioni.add(a);
		condizioni.add(b);
		condizioni.add(c);
		
		
		for (Auto auto : garage.ricerca(condizioni)) {
			assert ((auto.getMarca().equalsIgnoreCase("citroen")||auto.getMarca().equalsIgnoreCase("fiat"))
					&& (auto.getColore().equalsIgnoreCase("giallo") || auto.getColore().equalsIgnoreCase("verde"))
					&& (auto.getModello().equalsIgnoreCase("c1") || auto.getModello().equalsIgnoreCase("panda")));
			
			
		}
		
		
		
		
	}
}









/*Auto auto = new Auto("nero", "panda", "fiat");
garage.modificaGarage(3, auto);

assertEquals("nero", garage.getAuto(3).getColore());

 *}*/


//assert(garage.cercaColore("blu").isEmpty());
