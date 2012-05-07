package me.deal.shared;

/*
 * Used in conjunction with the Yipit API to filter deal results by category.
 * See http://yipit.com/about/api/documentation/#tags
 */

public enum Category {
	RESTAURANTS,
	BARSLOUNGES,
	MASSAGE,
	FACIALS,
	NAILCARE,
	TANNING,
	HAIRSALONS,
	WAXING,
	SPA,
	TEETHWHITENING,
	VISIONEYECARE,
	MAKEUP,
	PILATES,
	YOGA,
	GYM,
	BOOTCAMP,
	MENSCLOTHING,
	WOMENSCLOTHING,
	GROCERIES,
	DESSERT;
	
	public String getSlug() {
		switch(this) {
			case RESTAURANTS:
				return "restaurants";
			case BARSLOUNGES:
				return "bar-club";
			case MASSAGE:
				return "massage";
			case FACIALS:
				return "facial";
			case NAILCARE:
				return "manicure-pedicure";
			case TANNING:
				return "tanning";
			case HAIRSALONS:
				return "hair-salon";
			case WAXING:
				return "hair-removal";
			case SPA:
				return "spa";
			case TEETHWHITENING:
				return "teeth-whitening";
			case VISIONEYECARE:
				return "eye-vision";
			case MAKEUP:
				return "makeup";
			case PILATES:
				return "pilates";
			case YOGA:
				return "yoga";
			case GYM:
				return "gym";
			case BOOTCAMP:
				return "boot-camp";
			case MENSCLOTHING:
				return "mens-clothing";
			case WOMENSCLOTHING:
				return "womens-clothing";
			case GROCERIES:
				return "food-grocery";
			case DESSERT:
				return "treats";
			default:
				return "";
		}
	}
		
	public static Category getCategory(String slug) {
		if(slug.equals("restaurants"))
			return RESTAURANTS;
		else if(slug.equals("bar-club"))
			return BARSLOUNGES;
		else if(slug.equals("massage"))
			return MASSAGE;
		else if(slug.equals("facial"))
			return FACIALS;
		else if(slug.equals("manicure-pedicure"))
			return NAILCARE;
		else if(slug.equals("tanning"))
			return TANNING;
		else if(slug.equals("hair-salon"))
			return HAIRSALONS;
		else if(slug.equals("hair-removal"))
			return WAXING;
		else if(slug.equals("spa"))
			return SPA;
		else if(slug.equals("teeth-whitening"))
			return TEETHWHITENING;
		else if(slug.equals("eye-vision"))
			return VISIONEYECARE;
		else if(slug.equals("makeup"))
			return MAKEUP;
		else if(slug.equals("pilates"))
			return PILATES;
		else if(slug.equals("yoga"))
			return YOGA;
		else if(slug.equals("gym"))
			return GYM;
		else if(slug.equals("boot-camp"))
			return BOOTCAMP;
		else if(slug.equals("mens-clothing"))
			return MENSCLOTHING;
		else if(slug.equals("womens-clothing"))
			return WOMENSCLOTHING;
		else if(slug.equals("food-grocery"))
			return GROCERIES;
		else if(slug.equals("treats"))
			return DESSERT;
		else
			return null;
	}
};