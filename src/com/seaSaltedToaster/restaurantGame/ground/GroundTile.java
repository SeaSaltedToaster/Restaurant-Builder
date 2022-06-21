package com.seaSaltedToaster.restaurantGame.ground;

public class GroundTile {
	
	private int gridX, gridY;
	private int colorId, Id;
	
	public GroundTile(int gridX, int gridY, int colorId) {
		this.gridX = gridX;
		this.gridY = gridY;
		this.colorId = colorId;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}
	
}
