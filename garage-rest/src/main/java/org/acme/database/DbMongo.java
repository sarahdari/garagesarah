package org.acme.database;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.Auto;
import org.acme.DBInterface;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jboss.logging.Logger;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;


//ook e e e e e 



@ApplicationScoped
public class DbMongo implements DBInterface {

	//

	@Inject MongoClient mongoClient;

	//DbMongo garage;


	private Logger LOGGER = Logger.getLogger(DbMongo.class);



	@Override
	public List<Auto> getGarage() {
		LOGGER.info("mostra tutte le auto contenute nel garage");
		List<Auto> list = new ArrayList<>();
		MongoCursor<Document> cursor = getCollection().find().iterator();

		try {
			while (cursor.hasNext()) {
				Document document = cursor.next();
				Auto auto = new Auto();
				auto.setId(document.getInteger("id"));
				auto.setColore(document.getString("colore"));
				auto.setMarca(document.getString("marca"));
				auto.setModello(document.getString("modello"));
				list.add(auto);
			}
		} finally {
			cursor.close();
		}
		LOGGER.debug(list);
		return list;
	}

	@Override
	public void aggiungiAuto(Auto auto) {
		LOGGER.info("aggiunge un'auto al garage: " + auto);
		Document doc = mongoClient.getDatabase("garage").getCollection("garage")
				.find().sort(new BasicDBObject("id", -1)).first();
		Gson gson = new Gson();
		if(doc!=null ) {
			String json = doc.toJson();
			Auto a = gson.fromJson(json, Auto.class);
			auto.setId(a.getId()+1);
		}
		Document document = new Document()
				.append("id", auto.getId())
				.append("colore", auto.getColore())
				.append("marca", auto.getMarca())
				.append("modello", auto.getModello());
		mongoClient.getDatabase("garage").getCollection("garage").insertOne(document);
	}


	private MongoCollection getCollection(){
		LOGGER.info("recupera la collection del db");
		LOGGER.debug(mongoClient.getDatabase("garage").getCollection("garage"));
		return mongoClient.getDatabase("garage").getCollection("garage");
	}



	@Override
	public Auto getAuto(int id) {
		LOGGER.info("mostra l'auto con id: " + id);
		Gson gson = new Gson();		
		String json= mongoClient.getDatabase("garage").getCollection("garage").find(new Document ("id",id)).first().toJson();
		Auto auto = gson.fromJson(json, Auto.class);
		LOGGER.debug(auto);
		return auto;
	}

	@Override
	public List<Auto> cercaColore(String colore) {
		LOGGER.info("ricerca l'auto per colore: " + colore);
		List<Auto> lista = new ArrayList<Auto>();
		Gson gson = new Gson();
		String json;
		FindIterable<Document> list = mongoClient.getDatabase("garage").getCollection("garage")
				.find(new Document("colore", colore));

		for (Document auto : list) {
			json = auto.toJson();
			lista.add(gson.fromJson(json, Auto.class));


		}
		LOGGER.debug(lista);
		return lista; 


	}



	@Override
	public void modificaGarage(int chiave, Auto auto) {
		LOGGER.info("sostituisce un'auto già presente nel garage con l'auto nuova: " + auto);
		Bson filtro = Filters.eq("id", chiave);
		auto.setId(chiave);
		Document document = new Document()
				.append("id", auto.getId())
				.append("colore", auto.getColore())
				.append("marca", auto.getMarca())
				.append("modello", auto.getModello());
		
		mongoClient.getDatabase("garage").getCollection("garage").replaceOne(filtro, document);
		LOGGER.debug(mongoClient.getDatabase("garage").getCollection("garage").replaceOne(filtro, document));
	}



	@Override
	public void eliminaAuto(int id) {
		LOGGER.info("elimina un auto tramite l'id: " + id);
		Auto auto = new Auto ("bianco","a1","audi");
		Gson gson = new Gson();		
		String json= mongoClient.getDatabase("garage").getCollection("garage").deleteOne(new Document ("id",id)).toString();
		json = gson.toJson(auto);
		auto = gson.fromJson(json, Auto.class); 
	}


	public boolean contains(Auto auto2) {
		LOGGER.info("verifica se un auto è contenuta nel garage");
		Document doc = mongoClient.getDatabase("garage").getCollection("garage")
				.find(new Document("id", auto2.getId())).first();
		return (doc != null);
	}




	@Override
	public List<Auto> ricerca(List<Condizione> condizioni) {
		LOGGER.info("ricerca delle auto secondo le condizioni: " + condizioni);

		List<Auto> lista = new ArrayList<Auto>();

		List<Bson> listaFiltriAnd = new ArrayList<Bson>();

		for(Condizione condizione: condizioni) {
			List<Bson> listaFiltriOr = new ArrayList<Bson>();
			for(String parametro: condizione.getParametri()) {
				Bson filtro = Filters.eq(condizione.getCampo(),parametro);
				listaFiltriOr.add(filtro);
			}

			LOGGER.info(listaFiltriOr.toString());

			Bson filtroOr = Filters.or(listaFiltriOr);
			listaFiltriAnd.add(filtroOr);
			LOGGER.debug(listaFiltriAnd);
		}

		LOGGER.info(listaFiltriAnd.toString());

		Bson filtroAnd = Filters.and(listaFiltriAnd);
		LOGGER.debug(filtroAnd);

		FindIterable<Document> listaDoc = mongoClient.getDatabase("garage").getCollection("garage").find(filtroAnd);

		LOGGER.info(listaDoc.toString());

		Gson gson = new Gson();
		String json;
		for(Document doc:listaDoc) {
			json = doc.toJson();
			lista.add(gson.fromJson(json, Auto.class));
			LOGGER.info(doc.toString());
		}

		return lista;
	}

}

