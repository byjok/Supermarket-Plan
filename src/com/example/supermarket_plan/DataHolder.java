package com.example.supermarket_plan;

public class DataHolder {

	/**********************************************************************************************/
	private String city = "";

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String supermarketCity) {
		this.city = supermarketCity;
	}

	private int cityId;	

	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	private int cityPos;
	
	/**
	 * @return the cityPos
	 */
	public int getCityPos() {
		return cityPos;
	}

	/**
	 * @param cityPos the cityPos to set
	 */
	public void setCityPos(int cityPos) {
		this.cityPos = cityPos;
	}

	/**********************************************************************************************/	
	private String supermarket = "";

	/**
	 * @return the supermarket
	 */
	public String getSupermarket() {
		return supermarket;
	}

	/**
	 * @param supermarket
	 *            the supermarket to set
	 */
	public void setSupermarket(String supermarket) {
		this.supermarket = supermarket;
	}
	
	private int supermarketId;

	/**
	 * @return the supermarketId
	 */
	public int getSupermarketId() {
		return supermarketId;
	}

	/**
	 * @param supermarketId the supermarketId to set
	 */
	public void setSupermarketId(int supermarketId) {
		this.supermarketId = supermarketId;
	}
	
	private int supermarketPos;
	
	/**
	 * @return the supermarketPos
	 */
	public int getSupermarketPos() {
		return supermarketPos;
	}

	/**
	 * @param supermarketPos the supermarketPos to set
	 */
	public void setSupermarketPos(int supermarketPos) {
		this.supermarketPos = supermarketPos;
	}

	/**********************************************************************************************/
	private String address = "";

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	private int addressPos;

	/**
	 * @return the addressPos
	 */
	public int getAddressPos() {
		return addressPos;
	}

	/**
	 * @param addressPos the addressPos to set
	 */
	public void setAddressPos(int addressPos) {
		this.addressPos = addressPos;
	}
}
