package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
	
	private static final String URL = "jdbc:mysql://localhost:3306/nations";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Inserire il nome della nazione da cercare: ");
		String nationName = sc.next();
		sc.close();
		
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)){
			
			 String sql = "SELECT countries.name, countries.country_id, regions.name, continents.name"
					 + " FROM countries "
					 + " JOIN regions "
					 + " ON countries.region_id = regions.region_id "
					 + " JOIN continents "
					 + " ON regions.continent_id = continents.continent_id "
					 + " WHERE countries.name LIKE ? "
					 + " ORDER BY countries.name ";
			
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, nationName);
				try (ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						final String name = rs.getString(1);
						final int id = rs.getInt(2);
						final String regionName = rs.getString(3);
						final String continentName = rs.getString(4);
						
						System.out.println(id + " - " + name + " - " + regionName + " - " + continentName);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
