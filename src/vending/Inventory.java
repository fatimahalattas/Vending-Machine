package vending;

import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {
	
	// map of item and its quantity
	private Map<T, Integer> inventory = new HashMap<>();
	
	public int getQuantity(T item) {
		Integer quantity = inventory.get(item);
		return quantity;
	}
	
	public void put(T item, Integer quantity) {
		inventory.put(item, quantity);
	}
	
	public void add (T item) {
		Integer quantity = inventory.get(item);
		inventory.put(item, quantity+1);
	}
	public boolean hasItemQuantiy(T item) {
		return inventory.get(item) > 0;
	}
	public void deduct (T item) {
		if (hasItemQuantiy(item)) {
			Integer quantity = inventory.get(item);
			inventory.put(item, quantity+-1);
		}
	}
	
	public void clear() {
		inventory.clear();
	}

}
