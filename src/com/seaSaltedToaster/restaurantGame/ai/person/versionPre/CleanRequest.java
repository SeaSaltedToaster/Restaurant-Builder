package com.seaSaltedToaster.restaurantGame.ai.person.versionPre;

import com.seaSaltedToaster.restaurantGame.objects.TableComponent;

public class CleanRequest {
	
	//The table and seat that must be cleaned
	private TableComponent tableComp;
	private int claimedSeat;

	public CleanRequest(TableComponent tableComp, int claimedSeat) {
		this.tableComp = tableComp;
		this.claimedSeat = claimedSeat;
	}

	public TableComponent getTableComp() {
		return tableComp;
	}

	public void setTableComp(TableComponent tableComp) {
		this.tableComp = tableComp;
	}

	public int getClaimedSeat() {
		return claimedSeat;
	}

	public void setClaimedSeat(int claimedSeat) {
		this.claimedSeat = claimedSeat;
	}

	
}
