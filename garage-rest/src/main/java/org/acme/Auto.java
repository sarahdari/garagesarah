package org.acme;

import com.google.gson.Gson;

//V3
public class Auto {

	private String colore;
	private String modello;
	private String marca;
	private Integer id;
	

	public Auto() {};

	public Auto(String colore, String modello, String marca) {
		this.colore = colore;
		this.marca = marca;
		this.modello = modello;
		
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getColore() {
		return colore;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	





	@Override
	public String toString() {
		return "Auto [colore=" + colore + ", modello=" + modello + ", marca=" + marca + ", id=" + id + "]";
	}

	public Auto parseToAuto (String s) {
		Auto auto = new Auto("bianco", "audi", "a1");
		Gson gson = new Gson();
		String jsonString = gson.toJson(auto);
		auto = gson.fromJson(jsonString, Auto.class);

		return auto;


	}

	
}
