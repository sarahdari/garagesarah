package org.acme.resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.Auto;
import org.acme.DBInterface;
import org.acme.database.Condizione;
import org.acme.database.DBHashMap;
import org.acme.database.DbMongo;
import org.jboss.logging.Logger;

@Path("/garage")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GarageResource {

    

	private Logger LOGGER = Logger.getLogger(DbMongo.class);
	
	@Inject
	   DbMongo garage;

    @GET
    public List<Auto> getGarage(){
    	LOGGER.info("mostra tutte le auto contenute nel garage");
        return garage.getGarage();
    }

    @POST
    public void aggiungiAuto(Auto auto){
    	LOGGER.info("aggiunge un'auto al garage" + auto);
        garage.aggiungiAuto(auto);
    }

    @GET
    @Path("/auto/colore/{colore}")
    public List<Auto> cercaColoreAuto(@PathParam("colore") String colore) {
    	LOGGER.info("ricerca le auto per colore: " + colore);
        return garage.cercaColore(colore);
    }

    @GET
    @Path("/auto/marcamodello")
    public List<Auto> getMarcaModello(@PathParam("marca") String marca, @PathParam("modello")String modello){
        List<Auto> listaRicerca = new ArrayList<Auto>();
        for(Auto auto:garage.getGarage()) {
            if(auto.getMarca().equalsIgnoreCase(marca) && auto.getModello().equalsIgnoreCase(modello)) {
                listaRicerca.add(auto);
            }
        }
        LOGGER.debug(listaRicerca);
        return listaRicerca;
    }

    @DELETE
    @Path("/{id}")
    public Response rimuoviAuto(@PathParam("id") Integer id) {
    	LOGGER.info("rimuove un'auto dal garage tramite l'id: " + id);
        for(int i=0; i<garage.getGarage().size();i++) {
            if(garage.getGarage().get(i).getId()==id) {
            	
            	LOGGER.debug( garage.getGarage().remove(i));
                garage.getGarage().remove(i);
            }
        }
        LOGGER.error("L'auto non è stata rimossa dal garage");
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/{id}")
    public void cambiaAuto(@PathParam("id")Integer id, Auto auto){
    	
    	LOGGER.info("sostituisce un'auto già presente nel garage con un'altra auto: " + auto);
    	LOGGER.debug(auto);
        
    	garage.modificaGarage(id, auto);

    }
    
    
    @POST
    @Path("/ricerca")
    public List<Auto> ricerca (List<Condizione> condizioni) {
    	
    	LOGGER.info("ricerca per: " + condizioni);
    	LOGGER.debug(garage.ricerca(condizioni));
    	
    	return garage.ricerca(condizioni);
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   

   /* @PUT
    @Path("/auto/{id}/{colore}")
    public void cambiaColoreAuto(@PathParam("id") Integer id, @PathParam("colore") String colore){
        garage.modificaColoreAuto(id, colore);
    }*/

}
