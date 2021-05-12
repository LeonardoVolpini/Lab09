package it.polito.tdp.borders.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private SimpleGraph<Country,DefaultEdge> grafo;
	private Map<Integer,Country> idMap;
	private BordersDAO dao;
	private Map<Country,Country> visita;
	
	public Model() {
		this.dao=new BordersDAO();
		this.idMap= new HashMap<>();
		dao.loadAllCountries(idMap);
	}
	
	
	public void creaGrafo(int anno) {
		this.grafo= new SimpleGraph<Country,DefaultEdge>(DefaultEdge.class);
		
		Graphs.addAllVertices(grafo, idMap.values());
		for (Border b : dao.getCountryPairs(anno)) {
			if(b.getType()==1) {
				Graphs.addEdgeWithVertices(grafo, idMap.get(b.getIdStato1()), idMap.get(b.getIdStato2()));
			}
		}
		for (Country c : idMap.values()) {
			if (grafo.degreeOf(c)==0)
				grafo.removeVertex(c);
		}
		System.out.println("Grafo Creato!");
		System.out.println("# vertici: "+grafo.vertexSet().size());
		System.out.println("# archi: "+grafo.edgeSet().size());
		
	}
	
	public String stampaStatiConGrado() {
		String s="";
		for (Country c : this.grafo.vertexSet()) {
			s += c.toString()+grafo.degreeOf(c)+"\n";
		}
		return s;
	}
	
	public List<Country> statiRaggiungibili(Country c ){
		List<Country> stati= new LinkedList<>();
		BreadthFirstIterator<Country,DefaultEdge> it= new BreadthFirstIterator<>(grafo,c);
		
		visita = new HashMap<>();
		visita.put(c, null);
		
		it.addTraversalListener(new TraversalListener<Country,DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				DefaultEdge arco = e.getEdge();
				Country c1= grafo.getEdgeSource(arco);
				Country c2= grafo.getEdgeTarget(arco);
				if (visita.containsKey(c1) && !visita.containsKey(c2)) {
					visita.put(c2, c1); //c2 scoperto da c1
				}
				else if (visita.containsKey(c2) && !visita.containsKey(c1)) {
					visita.put(c1, c2); //c1 scoperto da c2
				}
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
			}
		});
		while (it.hasNext()) {
			Country paese= it.next();
			stati.add(paese);
		}
		return stati;
	}

	public int numComponentiConnesse() {
		ConnectivityInspector<Country,DefaultEdge> ci = new ConnectivityInspector(grafo);
		return ci.connectedSets().size();
	}


	public Collection<Country> getAllStates() {
		LinkedList<Country> temp = new LinkedList<>(idMap.values());
		Collections.sort(temp);
		return temp;
	}
	
}
