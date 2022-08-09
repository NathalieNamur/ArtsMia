package it.polito.tdp.artsmia.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;

public class TestArtsmiaDAO {

	public static void main(String[] args) {

		ArtsmiaDAO dao = new ArtsmiaDAO();
		
		Map<Integer,ArtObject> idMap = new HashMap<>();
		dao.listObjects(idMap);
		
		System.out.println("Numero artObject: "+idMap.size());
		
		System.out.println("Numero adiacenze: "+dao.getAdiacenze(idMap).size());
	}

}
