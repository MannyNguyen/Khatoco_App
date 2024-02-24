package khatoco.tvc.com.khatoco.api.object_request_api;

import java.util.ArrayList;

import khatoco.tvc.com.khatoco.ui.objects.ProductFormat;
import khatoco.tvc.com.khatoco.ui.objects.SubmitFormat;

/**
 * Created by prosoft on 11/24/16.
 */

public class SubmitRequest {

    public String orderno;
    public String orderdate;
    public String status;
    public String  currencycode;
    public int currencyrate;
    public String customercode;
    public String customername;
    public int netamount;
    public int finamount;
    public int disamount;
    public int vatamount;
    public int grossamount;

    public ArrayList<ProductFormat> details = new ArrayList<>();

    public SubmitRequest(SubmitFormat submitFormat){
        this.orderno = submitFormat.getOrderno();
        this.orderdate = submitFormat.getOrderdate();
        this.status = submitFormat.getStatus();
        this.currencycode = submitFormat.getCurrencycode();
        this.currencyrate = submitFormat.getCurrencyrate();
        this.customercode = submitFormat.getCustomercode();
        this.customername = submitFormat.getCustomername();
        this.netamount = submitFormat.getNetamount();
        this.finamount = submitFormat.getFinamount();
        this.disamount = submitFormat.getDisamount();
        this.vatamount = submitFormat.getVatamount();
        this.grossamount = submitFormat.getGrossamount();
        this.details = submitFormat.getProductFormatArrayList();

    }
}
