package movie_class;

import java.util.Enumeration;
import java.util.Vector;

class Customer {
	private String 	_name;
	private Vector 	_rentals = new Vector();
	
	public Customer(String name) {
		_name = name;
	}
	
	public void addRental(Rental arg) {
		_rentals.addElement(arg);
	}
	public String getName() {
		return _name;
	}
	
	public String statement() {
		double 		totalAmount 			= 0;
		int			frequentRenterPoints 	= 0;
		Enumeration rentals 				= _rentals.elements();
		String		result 					= "대여기록 " + getName() + "\n";
		
		while(rentals.hasMoreElements()) {
			double 	thisAmount 	= 0;
			Rental 	each 		= (Rental) rentals.nextElement();
			//비디오 종류별 대여료 계산
			switch(each.getMovie().getpriceCode()) {
			case Movie.REGULAR:
				thisAmount += 2;
				if(each.getDaysRented() > 2) {
					thisAmount += (each.getDaysRented() - 2) * 1.5;
				}
				break;
			case Movie.NEW_RELEASE:
				thisAmount += each.getDaysRented() * 3;
				break;
			case Movie.CHILDRENS:
				thisAmount += 1.5;
				if(each.getDaysRented() > 3) {
					thisAmount += (each.getDaysRented() - 3) * 1.5;
				}
				break;
			}
			//적립 포인트를 1포인트 증가
			frequentRenterPoints++;
			
			//최신물을 이틀 이상 대여하면 보너스 지급
			if((each.getMovie().getpriceCode() == Movie.NEW_RELEASE) && (each.getDaysRented() > 1)) {
				frequentRenterPoints++;
			}
			
			//현재까지 누적된 총 대여료
			result 		+= "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";
			totalAmount += thisAmount;
		}
			
		//밑글 추가
		result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
		result += "You earrned " + String.valueOf(frequentRenterPoints) + "frequent renter points" + "\n";
		
		return result;
	}
}
