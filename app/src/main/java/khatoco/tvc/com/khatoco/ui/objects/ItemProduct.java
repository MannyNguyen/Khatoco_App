package khatoco.tvc.com.khatoco.ui.objects;

/**
 * Created by Quysunam on 11/9/2016.
 */

public class ItemProduct {
    private String productName;
    private int numberProduct;
    private int priceProduct;
    private int type;
    private int price;
    private String code;

    public ItemProduct(String productName, int numberProduct, int priceProduct, int price, int type, String code) {
        this.productName = productName;
        this.numberProduct = numberProduct;
        this.priceProduct = priceProduct;
        this.type = type;
        this.price = price;
        this.code = code;
    }

    public ItemProduct() {

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumberProduct() {
        return numberProduct;
    }

    public void setNumberProduct(int numberProduct) {
        this.numberProduct = numberProduct;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(int priceProduct) {
        this.priceProduct = priceProduct;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPrice(int price) {
        this.price = price;

    }

}
