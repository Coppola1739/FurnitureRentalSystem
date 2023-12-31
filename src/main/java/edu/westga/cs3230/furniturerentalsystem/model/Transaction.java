package edu.westga.cs3230.furniturerentalsystem.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Transaction {

	@NonNull
	@Setter
	private String rentalId;

	@NonNull
	private String memberId;

	@NonNull
	private String employeeNum;

	@NonNull
	private List<Furniture> selectedItems;

	public String getFurnitureIds() {
		StringBuilder furnitureIds = new StringBuilder();
		for (Furniture furniture : this.selectedItems) {
			if (!furnitureIds.toString().contains(furniture.getFurnitureId())) {
				furnitureIds.append(furniture.getFurnitureId()).append(",");
			}
		}
		return this.removeLastComma(furnitureIds);
	}

	public String getQuantities() {
		String[] ids = this.getFurnitureIds().split(",");
		Map<String, Integer> furnitureCountMap = this.countFurnitureQuantities();
		StringBuilder quantities = new StringBuilder();

		for (String id : ids) {
			quantities.append(furnitureCountMap.get(id)).append(",");
		}

		return this.removeLastComma(quantities);
	}

	public String getCosts() {
		String[] ids = this.getFurnitureIds().split(",");
		StringBuilder costs = new StringBuilder();

		for (String id : ids) {
			for (Furniture furniture : this.selectedItems) {
				if (furniture.getFurnitureId().equals(id)) {
					costs.append(furniture.getRentalRate()).append(",");
					break;
				}
			}
		}

		return this.removeLastComma(costs);
	}

	public String generateReceipt() {
		StringBuilder receipt = new StringBuilder();
		receipt.append("Rental ID: ").append(this.rentalId).append("\n");
		receipt.append("Member ID: ").append(this.memberId).append("\n");
		receipt.append("Employee Number: ").append(this.employeeNum).append("\n\n");
		receipt.append("Furniture Rented:\n");
		for (Furniture furniture : this.selectedItems) {
			receipt.append(furniture.toString()).append("\n");
		}

		double totalCost = 0.0;
		for (String costStr : this.getCosts().split(",")) {
			totalCost += Double.parseDouble(costStr);
		}
		receipt.append("\nTotal Cost: $").append(String.format("%.2f", totalCost));

		return receipt.toString();
	}

	private Map<String, Integer> countFurnitureQuantities() {
		Map<String, Integer> countMap = new HashMap<>();
		for (Furniture furniture : this.selectedItems) {
			countMap.put(furniture.getFurnitureId(), countMap.getOrDefault(furniture.getFurnitureId(), 0) + 1);
		}
		return countMap;
	}

	private String removeLastComma(StringBuilder builder) {
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}

}
