package khatoco.tvc.com.khatoco.api.object_response_api;

import java.io.Serializable;

import java.util.ArrayList;

/**
 * Created by prosoft on 11/14/16.
 */

public class GetProductResponse implements Serializable{

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetProductResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public ArrayList<GetProductResponse.Data> data;
        public String totalCount;
        public String message;
    }

    public class Data implements Serializable{
        public String itemcode;
        public String itemname;
        public String baseprice1;
    }
}
