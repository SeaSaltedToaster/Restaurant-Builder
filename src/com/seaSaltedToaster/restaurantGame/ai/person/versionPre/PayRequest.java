package com.seaSaltedToaster.restaurantGame.ai.person.versionPre;

import com.seaSaltedToaster.restaurantGame.objects.TableComponent;

public class PayRequest {
	
	//Table being paid at, and the pay amount
	private TableComponent table;
	private int payAmount;

	public PayRequest(TableComponent table, int payAmount) {
		this.table = table;
		this.payAmount = payAmount;
	}

	public TableComponent getTable() {
		return table;
	}

	public void setTable(TableComponent table) {
		this.table = table;
	}

	public int getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}

}
