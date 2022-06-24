package vending;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VendingMachineImpl implements VendingMachine {
	
	private Item currentItem;
	private Inventory<Item> itemInventory = new Inventory<>();
    private long currentBalance; 
    private Inventory<Coin> cashInventory = new Inventory<Coin>();
    private long totalSales;


    public VendingMachineImpl(){
        initialize();
    }
    
    private void initialize(){ 
    	//initialize machine with 5 coins of each denomination //and 5 cans of each Item 
    	for(Coin c : Coin.values()){ cashInventory.put(c, 5); } for(Item i : Item.values()){ itemInventory.put(i, 5); } }
    

	@Override
	public void reset() {
		cashInventory.clear();
		itemInventory.clear();
		currentItem = null;
		currentBalance=0;
		totalSales=0;
	}

	@Override
	public long selectItemAndGetPrice(Item item) {
		if (itemInventory.hasItemQuantiy(item)) {
			currentItem =item;
			return currentItem.getPrice();
		}
		
		throw new SoldOutException("Sold Out, Please buy another item");

		}



	@Override
	public void insertCoin(Coin coin) {
		currentBalance += coin.getValue();
		cashInventory.add(coin);
		}

	@Override
	public List<Coin> refund() {
		List<Coin> change = getChange(currentBalance);
		updateCashInventory(change);
		currentBalance= 0;
		currentItem = null;
		return change;
	}
	
	public boolean hasPaidFullAmount() {
		if (currentBalance >= currentItem.getPrice()) {
			return true;
		}
		throw new NotSufficientAmountException("Sorry, No Sufficient Amount, insert more coins please");	
	}
	
	private List<Coin> getChange (long amount) throws NotSufficientChangeException{
		
		List<Coin> changes = Collections.EMPTY_LIST;
		if(amount > 0){
			changes = new ArrayList<>();
			long balance = amount;
			while (balance > 0) {
			if (balance >= Coin.QUARTER.getValue()&& cashInventory.hasItemQuantiy(Coin.QUARTER)) {
				changes.add(Coin.QUARTER);
				balance -= Coin.QUARTER.getValue();
				continue;
			}
			else if (balance >= Coin.DIME.getValue()&& cashInventory.hasItemQuantiy(Coin.QUARTER)) {
				changes.add(Coin.DIME);
				balance -= Coin.DIME.getValue();
				continue;
		}
			else if (balance >= Coin.NICKLE.getValue()&& cashInventory.hasItemQuantiy(Coin.QUARTER)) {
				changes.add(Coin.NICKLE);
				balance -= Coin.NICKLE.getValue();
				continue;
		}
			else if (balance >= Coin.PENNY.getValue()&& cashInventory.hasItemQuantiy(Coin.QUARTER)) {
				changes.add(Coin.PENNY);
				balance -= Coin.PENNY.getValue();
				continue;
		}
			else {
			throw new NotSufficientChangeException("Sorry, No Sufficient Change Available");
			}
			}
		}
			
		return changes;
		
	}
	
	private void updateCashInventory(List<Coin> change) { 
		for(Coin c : change){ 
			cashInventory.deduct(c); 
			} 
		}
	
	private boolean hasSuffientChangeForAmount(long amount) {
		boolean hasChange = true;
		try {
			getChange(amount);
		}catch(NotSufficientChangeException nsce){
			return hasChange=false;
			
		}
		return hasChange;
	}
	
	private boolean hasSuffientCange() {
		return (hasSuffientChangeForAmount(currentBalance-currentItem.getPrice()));
	}

	@Override
	public Bucket<Item, List<Coin>> collectItemAndChange() {
		// TODO Auto-generated method stub
		Item item = collectItem();
		List<Coin> change = collectChange();
		
		totalSales += currentItem.getPrice();
		
		return new Bucket<>(item,change);
		
	}
	
	private Item collectItem() throws NotSufficientChangeException, NotSufficientAmountException{
		if(hasPaidFullAmount()) {
			if(hasSuffientCange()) {
				itemInventory.deduct(currentItem);
				return currentItem;
			}
			throw new NotSufficientChangeException("Sorry, No Sufficient Change Available");

		}
		long remainingBalance = (currentItem.getPrice()-currentBalance);
		throw new NotSufficientAmountException ("not enogh amount, remaining " + remainingBalance);
	}
	
	private List<Coin> collectChange() throws NotSufficientChangeException{
		long cangeAmount = currentBalance - currentItem.getPrice();
		List<Coin> change = getChange(cangeAmount);
		updateCashInventory(change);
		currentBalance = 0;
		currentItem = null;
		return change;
		
	
	}
	
	public long getTotalSales() {
		return totalSales;
	}
	
	}


