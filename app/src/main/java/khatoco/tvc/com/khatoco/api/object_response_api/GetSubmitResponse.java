package khatoco.tvc.com.khatoco.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prosoft on 11/24/16.
 */

public class GetSubmitResponse implements Serializable {

    public String type;
    public int tid;
    public String action;
    public String method;
    public GetSubmitResponse.Result result;

    public class Result implements Serializable {
        public boolean success;
        public ArrayList<GetSubmitResponse.Data> data;
        public String title;
        public String message;
    }

    public class Data implements Serializable {
        public String orderno;

    }

}
