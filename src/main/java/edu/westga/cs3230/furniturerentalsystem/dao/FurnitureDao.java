package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.furniturerentalsystem.model.Furniture;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;

/**
 * Furniture DAO Class for accessing furniture table in database
 * @author Nicolas Sacandy Fall 2023
 */

public class FurnitureDao {

	private static final String CLOSING_QUOTATION = "'";

	
	
	/**
	 * Returns all furniture in the database
	 * @return an Arraylist with all furniture in the database
	 */
	
	public ArrayList<Furniture> getAllFurniture() {
		String selectFurniture = "SELECT * FROM `furniture`";
		return this.searchQuery(selectFurniture);
	}

	
	/**
	 * Returns all furniture of the selected style 
	 * @param style a string representing the style (e.g., Rustic, Scandinavian, etc.)
	 * @return an ArrayList of furniture of the selected style
	 */
	
	public ArrayList<Furniture> getFurnitureByStyle(String style) {
		String selectFurniture = "SELECT * FROM `furniture` WHERE style_name like '" + style + CLOSING_QUOTATION;
		return this.searchQuery(selectFurniture);
	}

	
	/**
	 * Returns all furniture of the selected category 
	 * @param category a string representing the category(e.g., chair, table, etc)
	 * @return an ArrayList of furniture of the selected category
	 */
	
	public ArrayList<Furniture> getFurnitureByCategory(String category) {
		String selectFurniture = "SELECT * FROM `furniture` WHERE category_name like '" + category + CLOSING_QUOTATION;
		return this.searchQuery(selectFurniture);
	}
	
	//TODO un-string-concatenate these methods
	
	
	public ArrayList<Furniture> getFurnitureByStyleAndCategory(String style, String category) {
		String selectFurniture = "SELECT * FROM `furniture` WHERE category_name LIKE '" + category + CLOSING_QUOTATION + " AND style_name LIKE '" + style + CLOSING_QUOTATION;
		return this.searchQuery(selectFurniture);
	}

	
	/**
	 * Returns all furniture that have the entered string in their furniture ID
	 * @param id the string to be searched for
	 * @return an arrayList containing all furniture with @param id in their furniture ID
	 */
	
	public ArrayList<Furniture> getFurnitureById(String id) {
		String selectFurniture = "SELECT * FROM `furniture` WHERE furniture_id like '%" + id + "%'";
		return this.searchQuery(selectFurniture);
	}

	private ArrayList<Furniture> searchQuery(String selectFurniture) {
		ArrayList<Furniture> allFurniture = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement checkStmt = connection.prepareStatement(selectFurniture)) {
			try (ResultSet rs = checkStmt.executeQuery()) {
				while (rs.next()) {
					Furniture newFurniture = Furniture.builder().furnitureId(rs.getString("furniture_id"))
							.styleName(rs.getString("style_name")).categoryName(rs.getString("category_name"))
							.rentalRate(rs.getString("rental_rate")).build();
					allFurniture.add(newFurniture);
				}
			}
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
		return allFurniture;
	}
}
