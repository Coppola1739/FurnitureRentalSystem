package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Transaction {

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
