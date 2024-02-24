package khatoco.tvc.com.khatoco.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 11/11/16.
 */

public class GetAgencisResponse {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetAgencisResponse.Result result;

    public class Result implements Serializable {
        public boolean success ;
        public ArrayList<GetAgencisResponse.Data> data;
        public String totalCount;
        public String message;
    }

    public class Data implements Serializable{
        public String nadcode;
        public String name;
        public String address5;
        public String address6;
    }
}
