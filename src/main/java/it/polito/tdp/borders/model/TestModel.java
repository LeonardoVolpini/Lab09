package it.polito.tdp.borders.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		System.out.println("Creo il grafo relativo al 2000");
		model.creaGrafo(2000);
		
		System.out.println(model.stampaStatiConGrado());
		
		System.out.println("Numero di componenti connesse:"+model.numComponentiConnesse());
		
		Country paese = new Country(305,"AUS","Austria");
		
		System.out.println(model.statiRaggiungibili(paese));
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
