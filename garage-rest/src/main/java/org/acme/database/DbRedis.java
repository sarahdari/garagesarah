package org.acme.database;

import io.quarkus.redis.client.RedisClient;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.RedisClusterClient;

import com.google.gson.Gson;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.Auto;
import org.acme.DBInterface;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class DbRedis implements DBInterface {

	DbRedis garage;
	
	private Logger LOGGER = Logger.getLogger(DbRedis.class);


	@Inject
	RedisClient redisClient;
	Auto auto = new Auto ("bianco", "panda", "fiat");
	Gson gson = new Gson();
	String json;


	@Override
	public Auto getAuto(int id) {
		try {
			Response response = redisClient.get(id+"");
			Auto auto = new Auto("bianco", "c2","citroen");
			Auto automobile = auto.parseToAuto(response.toString());
			return automobile;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	//aggiunge un'auto al garage
	@Override
	public void aggiungiAuto(Auto auto) {
		redisClient.set(Arrays.asList(auto.getId().toString(), auto.toString()));
	}

	//elimina un'auto dal garage, tramite l'id
	@Override
	public void eliminaAuto(int id) {
		redisClient.del(Arrays.asList(auto.getId().toString(), auto.toString()));
	}
	//cerca auto tramite un colore
	@Override
	public List<Auto> cercaColore(String colore) {
		LOGGER.info("ricerca dell'auto per colore: " +  colore);
		Response chiavi = redisClient.keys("*");
		List<String> lista = new ArrayList<String>();
		List<Auto> listaAuto = new ArrayList<Auto>();
		List<Auto> risRicerca = new ArrayList<Auto>();

		if(chiavi.size() == 0) {
			for(Response valChiave: chiavi) {
				lista.add(redisClient.get(valChiave+"").toString());
			}

			Collections.sort(lista);
			for(String s: lista) {
				Auto auto = gson.fromJson(s.toString(), Auto.class);
				listaAuto.add(auto);
			}

			for(Auto auto: listaAuto) {
				if (auto.getColore().equalsIgnoreCase(colore)) {
					risRicerca.add(auto);
				}
			}
		}
		LOGGER.debug(risRicerca);
		return risRicerca;
		
	}

	//cerca l'auto tramite uno o più colori
	public List<Auto> cercaColori (List<String> colori) {
		LOGGER.info("ricerca dell'auto per colori: " +  colori);
		Response chiavi = redisClient.keys("*");
		List<String> lista = new ArrayList<String>();
		List<Auto> listaAuto = new ArrayList<Auto>();
		List<Auto> risRicerca = new ArrayList<Auto>();

		if(chiavi.size() == 0) {
			for(Response valChiave: chiavi) {
				lista.add(redisClient.get(valChiave+"").toString());
			}

			Collections.sort(lista);
			for(String s: lista) {
				Auto auto = gson.fromJson(s.toString(), Auto.class);
				listaAuto.add(auto);
			}
			for (String colore : colori) {
				for(Auto auto: listaAuto) {
					if (auto.getColore().equalsIgnoreCase(colore)) {
						risRicerca.add(auto);
					}
				}
			}
		}
		LOGGER.debug(risRicerca);
		return risRicerca;
	}
	// cerca auto tramite uno o più modelli 
	public List<Auto> cercaModelli (List<String> modelli) {
		LOGGER.info("ricerca dell'auto per modelli: " +  modelli);
		Response chiavi = redisClient.keys("*");
		List<String> lista = new ArrayList<String>();
		List<Auto> listaAuto = new ArrayList<Auto>();
		List<Auto> risRicerca = new ArrayList<Auto>();

		if(chiavi.size() == 0) {
			for(Response valChiave: chiavi) {
				lista.add(redisClient.get(valChiave+"").toString());
			}

			Collections.sort(lista);
			for(String s: lista) {
				Auto auto = gson.fromJson(s.toString(), Auto.class);
				listaAuto.add(auto);
			}
			for (String modello : modelli) {
				for(Auto auto: listaAuto) {
					if (auto.getColore().equalsIgnoreCase(modello)) {
						risRicerca.add(auto);
					}
				}
			}
		}
		LOGGER.debug(risRicerca);
		return risRicerca;
	}

	// cerca auto tramite una o più marche
	public List<Auto> cercaMarche (List<String> marche) {
		LOGGER.info("ricerca dell'auto per marche: " +  marche);
		Response chiavi = redisClient.keys("*");
		List<String> lista = new ArrayList<String>();
		List<Auto> listaAuto = new ArrayList<Auto>();
		List<Auto> risRicerca = new ArrayList<Auto>();

		if(chiavi.size() == 0) {
			for(Response valChiave: chiavi) {
				lista.add(redisClient.get(valChiave+"").toString());
			}

			Collections.sort(lista);
			for(String s: lista) {
				Auto auto = gson.fromJson(s.toString(), Auto.class);
				listaAuto.add(auto);
			}
			for (String marca : marche) {
				for(Auto auto: listaAuto) {
					if (auto.getColore().equalsIgnoreCase(marca)) {
						risRicerca.add(auto);
					}
				}
			}
		}
		LOGGER.debug(risRicerca);
		return risRicerca;
	}

	//ricerca su un singolo campo
	public List<Auto> cercaSingoloCampo (List<String> ricerca, String campo) {
		LOGGER.info("ricerca secondo i parametri: " + ricerca + ", sul campo: " + campo);
		if (campo.equalsIgnoreCase("colore")) {
			LOGGER.debug(cercaColori(ricerca));
			return cercaColori(ricerca);
		}

		else if (campo.equalsIgnoreCase("modello")) {
			LOGGER.debug(cercaModelli(ricerca));
			return cercaModelli(ricerca);
		}

		else if (campo.equalsIgnoreCase("marca")) {
			LOGGER.debug(cercaMarche(ricerca));
			return cercaMarche(ricerca);
		}

		return null; 
	}



	// ricerca generico 
	@Override
	public List<Auto> ricerca(List<Condizione> condizioni) {
		LOGGER.info("ricerca delle auto secondi i paramentri: " + condizioni);
		List<Auto> risRicerca = new ArrayList<Auto>();

		for (Condizione condizione : condizioni) {
			if (risRicerca.isEmpty()) {
				risRicerca = cercaSingoloCampo (condizione.getParametri(), condizione.getCampo());
			}

			else {
				List<Auto> listaCampo = cercaSingoloCampo (condizione.getParametri(), condizione.getCampo());
				List<Auto> listaAppoggio = new ArrayList<Auto>();
				for (Auto a : listaCampo) {
					for (Auto b: listaAppoggio) {
						if (a.getId().equals(b.getId())) {
							listaAppoggio.add(a);
						}
					}
				}
				LOGGER.debug(listaAppoggio);
				risRicerca=listaAppoggio;
			}
		}
		LOGGER.debug(risRicerca);
		return risRicerca;
	}
	// sostituisce un'auto nel garage con un'auto nuova
	@Override
	public void modificaGarage(int chiave, Auto auto) {
		Response chiavi = redisClient.keys("*");
		for (Response key : chiavi) {
			if (key.equals(chiave)) {
				redisClient.set(Arrays.asList(key.toString(), auto.toString()));

			}	
		}
	}

	//DA RIFARE
	@Override
	public List<Auto> getGarage() {



		return null;

	}

	// verifica se un'auto è contenuta nel garage
	@Override
	public Boolean contiene(Auto auto) {
		return garage.getGarage().contains(auto);

	}



}
