package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao; 
	private Map<Integer,ArtObject> idMap;
	private Graph<ArtObject,DefaultWeightedEdge> grafo;
	
	
	
	public Model() {
		dao = new ArtsmiaDAO();
		idMap = new HashMap<Integer,ArtObject>();
	}
	
	
	
	//Metodo di creazione e popolamento grafo:
	public void creaGrafo() {
		
		//inizializzazione grafo:
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//popolamento vertici grafo:
		dao.listObjects(idMap);							//popolamento della idMap
		Graphs.addAllVertices(grafo, idMap.values());	//aggiunta dei valori della idMap al grafo
		
		//popolamento archi grafo 
		//(corrispondenti alle adiacenze):
		for(Adiacenza a : dao.getAdiacenze(idMap))
			Graphs.addEdgeWithVertices(grafo, a.getA1(), a.getA2(), a.getPeso());
		
	}
	
	public int numeroVertici( ) {
		return grafo.vertexSet().size();
	}
	
	public int numeroArchi( ) {
		return grafo.edgeSet().size();
	}
	

	//Metodo che restituisce l'artObjectt 
	//corrispondente all'id dato:
	public ArtObject getObject(int objectId) {
		return idMap.get(objectId);
	}
	
	
	//Metodo che calcola la dimensione della componente connessa
	//a cui appartiene il vertice dato:
	public int getDimComponenteConnessa(ArtObject vertice) {
		
		//Per ottenere la dimensione della componente connessa 		
		//contenente il vertice dato, è necessario:
		//visitare il grafo partendo dal vertice in questione,
		//salvare i vertici man mano visitati in un set
		//e ritornare la dimensione di tale set.
	
		
		//Set di vertici visitati: 
		Set<ArtObject> visitati = new HashSet<>();
		
		
		//Visita grafo:
		
		//-iteratore:
		DepthFirstIterator<ArtObject,DefaultWeightedEdge> it = 
				new DepthFirstIterator<ArtObject, DefaultWeightedEdge>(this.grafo, vertice);

		//-finchè l'iteratore trova vertici successivi,
		//questi sono da considerare:
		while (it.hasNext()) {
			visitati.add(it.next());
		}
	
		
		//Dimensione vertici visitati:
		return visitati.size();
	}
}
