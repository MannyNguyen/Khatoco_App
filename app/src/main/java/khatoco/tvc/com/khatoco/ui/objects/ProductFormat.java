package khatoco.tvc.com.khatoco.ui.objects;

/**
 * Created by prosoft on 11/24/16.
 */

public class ProductFormat  extends BaseModelObject{

    private String orderno;
    private int orderline;
    private String itemcode;
    private String itemname;
    private int value9;
    private int salequantity;
    private int unitprice;
    private int netamount;
    private int disamount;
    private int finamount;
    private int vatamount;
    private int grossamount;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public int getOrderline() {
        return orderline;
    }

    public void setOrderline(int orderline) {
        this.orderline = orderline;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getValue9() {
        return value9;
    }

    public void setValue9(int value9) {
        this.value9 = value9;
    }

    public int getSalequantity() {
        return salequantity;
    }

    public void setSalequantity(int salequantity) {
        this.salequantity = salequantity;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(int unitprice) {
        this.unitprice = unitprice;
    }

    public int getNetamount() {
        return netamount;
    }

    public void setNetamount(int netamount) {
        this.netamount = netamount;
    }

    public int getDisamount() {
        return disamount;
    }

    public void setDisamount(int disamount) {
        this.disamount = disamount;
    }

    public int getFinamount() {
        return finamount;
    }

    public void setFinamount(int finamount) {
        this.finamount = finamount;
    }

    public int getVatamount() {
        return vatamount;
    }

    public void setVatamount(int vatamount) {
        this.vatamount = vatamount;
    }

    public int getGrossamount() {
        return grossamount;
    }

    public void setGrossamount(int grossamount) {
        this.grossamount = grossamount;
    }

    @Override
    public String getKeyPath() {
        return "product";
    }
}
