package khatoco.tvc.com.khatoco.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 11/14/16.
 */

public class GetPromotionResponse implements Serializable {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetPromotionResponse.Result result;

    public class Result implements Serializable {
        public boolean success;
        public ArrayList<GetPromotionResponse.Data> data;
        public String totalCount;
        public String message;
    }

    public class Data implements Serializable {
        public String promotioncode;
        public String name;
        public String price;
    }
}
