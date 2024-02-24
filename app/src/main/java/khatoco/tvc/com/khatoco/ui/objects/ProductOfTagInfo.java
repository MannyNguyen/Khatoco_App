package khatoco.tvc.com.khatoco.ui.objects;

/**
 * Created by prosoft on 11/21/16.
 */

public class ProductOfTagInfo extends BaseModelObject{

    private String code;
    private String name;
    private String price;

    public ProductOfTagInfo(String code, String name, String price) {
        this.code = code;
        this.name = name;
    }

    private void update() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String getKeyPath() {
        return "productoftag";
    }

}
