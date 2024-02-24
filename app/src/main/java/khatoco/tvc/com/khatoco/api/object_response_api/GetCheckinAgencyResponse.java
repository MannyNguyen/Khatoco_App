package khatoco.tvc.com.khatoco.api.object_response_api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Quysunam on 11/24/2016.
 */

public class GetCheckinAgencyResponse {
    public String type;
    public int tid;
    public String action;
    public String method;
    public GetCheckinAgencyResponse.Result result;

    public class Result implements Serializable {
        public boolean success;
        public ArrayList<GetCheckinAgencyResponse.Data> data;
        public String title;
        public String message;
    }

    public class Data implements Serializable {
        public String systemno;

    }

}
