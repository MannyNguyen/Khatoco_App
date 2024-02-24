package khatoco.tvc.com.khatoco.ui.objects;

import java.util.ArrayList;

/**
 * Created by prosoft on 11/24/16.
 */

public class SubmitFormat  extends BaseModelObject{
    private String orderno;
    private String orderdate;
    private String status;
    private String  currencycode;
    private int currencyrate;
    private String customercode;
    private String customername;
    private int netamount;
    private int finamount;
    private int disamount;
    private int vatamount;
    private int grossamount;
    private int latitude;
    private String checkindate;

    private ArrayList<ProductFormat> productFormatArrayList = new ArrayList<>();

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    public int getCurrencyrate() {
        return currencyrate;
    }

    public void setCurrencyrate(int currencyrate) {
        this.currencyrate = currencyrate;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
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

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public String getCheckindate() {
        return checkindate;
    }

    public int getFinamount() {
        return finamount;
    }

    public void setFinamount(int finamount) {
        this.finamount = finamount;
    }

    public void setCheckindate(String checkindate) {
        this.checkindate = checkindate;
    }

    public ArrayList<ProductFormat> getProductFormatArrayList() {
        return productFormatArrayList;
    }

    public void setProductFormatArrayList(ArrayList<ProductFormat> productFormatArrayList) {
        this.productFormatArrayList = productFormatArrayList;
    }

    @Override
    public String getKeyPath() {
        return "submit";
    }
}
