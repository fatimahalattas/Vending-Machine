package vending;

public class NotSufficientAmountException extends RuntimeException {

private String message;
	
	public NotSufficientAmountException (String string) {
		this.message = string;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
