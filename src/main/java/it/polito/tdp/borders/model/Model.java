package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private SimpleGraph<Country,DefaultEdge> grafo;
	private Map<Integer,Country> idMap;
	private BordersDAO dao;
	
	public Model() {
		this.dao=new BordersDAO();
		this.idMap= new HashMap<>();
	}
	
	public void creaGrafo(int anno) {
		this.grafo= new SimpleGraph<Country,DefaultEdge>(DefaultEdge.class);
		dao.loadAllCountries(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		for (Border b : dao.getCountryPairs(anno)) {
			if(b.getType()==1) {
				Graphs.addEdgeWithVertices(grafo, idMap.get(b.getIdStato1()), idMap.get(b.getIdStato2()));
			}
		}
		System.out.println("Grafo Creato!");
		System.out.println("# vertici: "+grafo.vertexSet().size());
		System.out.println("# archi: "+grafo.edgeSet().size());
		
	}
	
	public String stampaStatiConGrado() { //TODO
		String s="";
		for (Country c : this.grafo.vertexSet()) {
			s += c.toString()+grafo.degreeOf(c)+"\n";
		}
		return s;
	}

	public int numComponentiConnesse() {
		int tot=0;
		ConnectivityInspector<Country,DefaultEdge> ci = new ConnectivityInspector(grafo);
		for (Country c : grafo.vertexSet()) {
			
		}
		
		return tot;
	}
	
}
