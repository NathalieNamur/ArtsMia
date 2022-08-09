package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	//Metodo per popolare l'idMap con i dati del db:
	public void listObjects(Map<Integer,ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		
		try {
			
			Connection conn = DBConnect.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(!idMap.containsKey(res.getInt("object_id"))) {
					
					ArtObject a = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
													 res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
													 res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
													 res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				idMap.put(a.getId(), a);
				
				}
			}
				
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel metodo listObjects().");
		}
	}
	
	
	//Metodo che restituisce la lista delle adiacenze presenti nella idMap,
	//con il relativo peso: 
	public List<Adiacenza> getAdiacenze(Map<Integer,ArtObject> idMap){
		
		String sql = "SELECT e1.object_id AS id1, e2.object_id AS id2, COUNT(*) as peso "
				   + "FROM exhibition_objects e1, exhibition_objects e2 "
				   + "WHERE e1.exhibition_id = e2.exhibition_id "
				   + "AND e1.object_id > e2.object_id "
				   + "GROUP BY e1.object_id, e2.object_id";
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Adiacenza a = new Adiacenza(idMap.get(res.getInt("id1")),
											idMap.get(res.getInt("id2")), 
											res.getInt("peso"));
				result.add(a);
			}
			
			st.close();
			conn.close();
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel metodo getAdiacenze().");
			return null;
		}
	}
	
}
