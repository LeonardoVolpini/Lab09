package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer,Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c= new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				idMap.put(rs.getInt("ccode"), c);
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {
		String sql= "SELECT c.state1no AS s1, c.state2no AS s2, conttype "
				+ "FROM contiguity c "
				+ "WHERE c.year<=? AND c.state1no>c.state2no";
		List<Border> result= new ArrayList<Border>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs= st.executeQuery();
			while(rs.next()) {
				Border b = new Border(rs.getInt("s1"),rs.getInt("s2"),rs.getInt("conttype"));
				result.add(b);
			}
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		return result;
	}
}
