package org.acme.resource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.Auto;
import org.acme.DBInterface;
import org.acme.database.DBList;
import org.acme.database.DbRedis;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;
//import org.junit.jupiter.api.Test;

@QuarkusTest
public class DBInterfaceTest {

	@Inject
	DbRedis garage;
	//DBInterface garage= DbRedis.getInstance();
	Auto auto;


	@BeforeEach
	public void setup() {
		//garage = DbRedis.getInstance();
		auto= new Auto("rosso", "panda","fiat");
		garage.aggiungiAuto(auto);
	}



	@Test
	public void testGetGarage() {
		List<Auto> getGarage = garage.getGarage();
		assertAll("Get Garage", ()->assertEquals(garage.getGarage(), getGarage),
				()->assertNotNull(garage.getGarage()));
	}

	@Test
	public void testGetAuto() {
		assertNotNull(garage.getAuto(auto.getId()));

	}


	@Test
	public void testAggiungiAuto() {
		assertNotNull(garage.getGarage().contains(auto));
	}

	/*@Test
	public void testModificaColoreAuto() {
		garage.aggiungiAuto(auto);
		garage.modificaColoreAuto(6, "arcobaleno");
		assertEquals("arcobaleno", garage.getAuto(6).getColore());
	}*/

	
	@Test
	public void testCostruttoreAuto() {
		Auto a = new Auto("grigio","500","fiat");
		assertAll("Auto", () -> assertEquals("grigio",a.getColore()),
				() -> assertEquals("500",a.getModello()), ()->assertEquals("fiat", a.getMarca()));
	}

	@Test
	public void testEliminaAuto() {
		Auto punto = new Auto("nero", "punto","volkswagen");
		garage.aggiungiAuto(punto);
		garage.eliminaAuto(punto.getId());
		assert(!garage.getGarage().contains(punto));
	}

	@Test
	public void testModificaGarage1() {
		Auto autoNuova = new Auto("giallo", "Bug", "Volkswagen");
		garage.modificaGarage(1, autoNuova);
		assert(garage.getGarage().contains(autoNuova));
	}

	@Test
	public void testModificaGarage2() {
		//se provi a modificare con un id/chiave che non esiste
		Auto autoOutOfBounds = new Auto("","","");
		garage.modificaGarage(89, autoOutOfBounds);
		assert(!garage.getGarage().contains(autoOutOfBounds));
	}

	@Test
	public void testCercaColore1() {
		Auto auto2 = new Auto ("verde", "500", "fiat");
		garage.aggiungiAuto(auto2);
		for(Auto rossa:garage.cercaColore("rosso")) {
			assertEquals("rosso",rossa.getColore());
		}

	}

	@Test
	public void testCercaColore2() {
		assert(garage.cercaColore("blu").isEmpty());
	}


}
