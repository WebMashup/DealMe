package me.deal.shared;

/*
 * Relevant data received from the Yelp API.
 * See http://www.yelp.com/developers/documentation/v2/business
 */

public class BusinessInfo {
	
	private String yelpID; 
	private String name;
	private String imageUrl;
	private Double avgRating; // Numerical average Yelp rating of the business
	private String avgRatingImageUrl; // Image representing the average Yelp rating
	private Integer numReviews;
	private String mobileUrl;
	private String webUrl;
	
	public BusinessInfo(
		final String yelpID,
		final String name,
		final String imageUrl,
		final Double avgRating,
		final String avgRatingImageUrl,
		final Integer numReviews,
		final String mobileUrl,
		final String webUrl
		) {
		this.yelpID = yelpID;
		this.name = name;
		this.imageUrl = imageUrl;
		this.avgRating = avgRating;
		this.avgRatingImageUrl = avgRatingImageUrl;
		this.numReviews = numReviews;
		this.mobileUrl = mobileUrl;
		this.webUrl = webUrl;
	}
	
	public String getYelpID() {
		return yelpID;
	}
	
	public void setYelpID(final String yelpID) {
		this.yelpID = yelpID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Double getAvgRating() {
		return this.avgRating;
	}
	
	public void setAvgRating(final Double avgRating) {
		this.avgRating = avgRating;
	}

	public String getAvgRatingImageUrl() {
		return this.avgRatingImageUrl;
	}
	
	public void setAvgRatingImageUrl(final String avgRatingImageUrl) {
		this.avgRatingImageUrl = avgRatingImageUrl;
	}
	
	public Integer getNumReviews() {
		return this.numReviews;
	}
	
	public void setAvgRatingImageUrl(final Integer numReviews) {
		this.numReviews = numReviews;
	}
	
	public String getMobileUrl() {
		return this.mobileUrl;
	}
	
	public void setMobileUrl(final String mobileUrl) {
		this.mobileUrl = mobileUrl;
	}
	
	public String getWebUrl() {
		return this.webUrl;
	}
	
	public void setWebUrl(final String webUrl) {
		this.webUrl = webUrl;
	}
}
