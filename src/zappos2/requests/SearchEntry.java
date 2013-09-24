/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos2.requests;

/**
 *
 * @author RoyZheng
 */
public class SearchEntry {

    private int styleId;
    private int productId;
    private String brandName;
    private String productName;
    private String thumbnailImageUrl;
    private String originalPrice;
    private String percentOff;
    private String productUrl;

    public String toString() {
        return this.getProductId() + ":" + this.getProductName();
    }

    public String toPriceString() {
        return this.productName + " @ " + this.originalPrice + " with a " + this.percentOff + " discount, final price " + this.getPriceAfterDiscount();
    }

    /**
     * @return the styleId
     */
    public int getStyleId() {
        return styleId;
    }

    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @return the brandName
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @return the thumbnailImageUrl
     */
    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    /**
     * @return the originalPrice
     */
    public String getOriginalPrice() {
        return originalPrice;
    }

    /**
     * @return the percentOff
     */
    public String getPercentOff() {
        return percentOff;
    }

    /**
     * @return the productUrl
     */
    public String getProductUrl() {
        return productUrl;
    }

    public Double getPercentOffDouble() {
        return Double.parseDouble(getPercentOff().substring(0, getPercentOff().length() - 1));
    }

    public Double getOriginalPriceDouble() {
        return Double.parseDouble(getOriginalPrice().substring(1).replace(",", ""));
    }

    public Double getPriceAfterDiscount() {
        return (1.0 - this.getPercentOffDouble() / 100) * this.getOriginalPriceDouble();
    }
}
/*
   "styleId": "556677", 
   "productId": "123456", 
   "brandName": "Ugg", 
   "productName": "Classic Tall", 
   "thumbnailImageUrl": "http://www.zappos.com/images/image.jpg", 
   "originalPrice": "$198.00", 
   "price": "$198.00", 
   "percentOff": "19%", 
   "productUrl": "http://www.zappos.com/product/101183/color/381" 
 */