/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.eg;

public class Account {
	private int id;
	private String paymentHistory;
	
	public Account() {
		//
	}
	public Account(int id, String paymentHistory) {
		setId(id);
		setPaymentHistory(paymentHistory);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPaymentHistory() {
		return paymentHistory;
	}
	public void setPaymentHistory(String paymentHistory) {
		this.paymentHistory = paymentHistory;
	}
}
