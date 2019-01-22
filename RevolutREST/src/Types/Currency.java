package Types;

public enum Currency {
	USD(1),EUR(1.14),RUB(0.015);

	private double rate;
	
	private Currency(double r) {rate=r;}
	
	public double getRate() {return rate;}
}
