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
 */

public class FurnitureDao {

	public ArrayList<Furniture> getAllFurniture() {
		ArrayList<Furniture> allFurniture = new ArrayList<>();
		String selectFurniture = "SELECT * FROM `furniture`";

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

	public ArrayList<Furniture> getFurnitureByStyle(String style) {
		ArrayList<Furniture> allFurniture = new ArrayList<>();
		String selectFurniture = "SELECT * FROM `furniture` WHERE style_name like '" + style + "'";

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

	public ArrayList<Furniture> getFurnitureByCategory(String category) {
		ArrayList<Furniture> allFurniture = new ArrayList<>();
		String selectFurniture = "SELECT * FROM `furniture` WHERE category_name like '" + category + "'";

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

	public ArrayList<Furniture> getFurnitureById(String id) {
		ArrayList<Furniture> allFurniture = new ArrayList<>();
		String selectFurniture = "SELECT * FROM `furniture` WHERE furniture_id like '%" + id + "%'";

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
