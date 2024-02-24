package khatoco.tvc.com.khatoco.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

import khatoco.tvc.com.khatoco.api.object_request_api.GetAgencyRequest;

/**
 * Created by prosoft on 11/21/16.
 */

public class GetProductOfTagResponse {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetProductOfTagResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public ArrayList<GetProductOfTagResponse.Data> data;
        public String totalCount;
        public String message;
    }

    public class Data implements Serializable{
        public String promotioncode;
        public String name;
        public String price;

    }

}
