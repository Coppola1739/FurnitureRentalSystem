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

	public static final String FURNITURE_BY_STYLE = "SELECT * FROM `furniture` WHERE style_name like ?";
	public static final String FURNITURE_BY_CATEGORY = "SELECT * FROM `furniture` WHERE category_name like ?";
	public static final String FURNITURE_BY_ID = "SELECT * FROM `furniture` WHERE furniture_id like ?";
	public static final String FURNITURE_BY_STYLE_AND_CATEGORY = "SELECT * FROM `furniture` WHERE style_name LIKE ? AND category_name LIKE ?";
	
	/**
	 * Returns all furniture in the database
	 * @return an Arraylist with all furniture in the database
	 */
	
	public ArrayList<Furniture> getAllFurniture() {
		String selectFurniture = "SELECT * FROM `furniture`";
		ArrayList<Furniture> allFurniture = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement checkStmt = connection.prepareStatement(selectFurniture)) {
			try (ResultSet rs = checkStmt.executeQuery()) {
				while (rs.next()) {
					Furniture newFurniture = Furniture.builder().furnitureId(rs.getString("furniture_id"))
							.styleName(rs.getString("style_name")).categoryName(rs.getString("category_name"))
							.rentalRate(rs.getString("rental_rate")).quantity(rs.getInt("quantity")).build();
					allFurniture.add(newFurniture);
				}
			}
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
		return allFurniture;
	}

	
	/**
	 * Returns all furniture of the selected style 
	 * @param style a string representing the style (e.g., Rustic, Scandinavian, etc.)
	 * @return an ArrayList of furniture of the selected style
	 */
	
	public ArrayList<Furniture> getFurnitureByStyle(String style) {
		return this.searchQuery(FURNITURE_BY_STYLE, style);
	}

	
	/**
	 * Returns all furniture of the selected category 
	 * @param category a string representing the category(e.g., chair, table, etc)
	 * @return an ArrayList of furniture of the selected category
	 */
	
	public ArrayList<Furniture> getFurnitureByCategory(String category) {
		return this.searchQuery(FURNITURE_BY_CATEGORY, category);
	}
		
	/**
	 * Returns all furniture of the selected style and selected category 
	 * @param category -a string representing the category(e.g., chair, table, etc)
	 * @param style - a string representing the style (e.g., Rustic, Scandinavian, etc.)
	 * @return an ArrayList of furniture of the selected category
	 */
	
	public ArrayList<Furniture> getFurnitureByStyleAndCategory(String style, String category) {
		return this.searchQuery(FURNITURE_BY_STYLE_AND_CATEGORY, style, category);
	}

	
	/**
	 * Returns all furniture that have the entered string in their furniture ID
	 * @param id the string to be searched for
	 * @return an arrayList containing all furniture with @param id in their furniture ID
	 */
	
	public ArrayList<Furniture> getFurnitureById(String id) {
		String modifiedId = "%" + id + "%";
		return this.searchQuery(FURNITURE_BY_ID, modifiedId);
	}

	
	private ArrayList<Furniture> searchQuery(String selectFurniture, String arg) {
		ArrayList<Furniture> allFurniture = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement checkStmt = connection.prepareStatement(selectFurniture)) {
				checkStmt.setString(1, arg);
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
	
	private ArrayList<Furniture> searchQuery(String selectFurniture, String arg, String arg2) {
		ArrayList<Furniture> allFurniture = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement checkStmt = connection.prepareStatement(selectFurniture)) {
				checkStmt.setString(1, arg);
				checkStmt.setString(2, arg2);
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
