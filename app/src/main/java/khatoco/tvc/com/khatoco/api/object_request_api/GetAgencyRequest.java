package khatoco.tvc.com.khatoco.api.object_request_api;

import java.util.ArrayList;

/**
 * Created by prosoft on 11/11/16.
 */

public class GetAgencyRequest {
    public int start = 0;
    public String isShortData = "Y";
    public int limit = 15;

    public ArrayList<GetAgencyRequest.Filter> fixedFilters = new ArrayList<>();

    public class Filter {
        public String field;
        public String op;
        public Object value;
        public String type;

        public Filter(String field, String op, Object value, String type) {
            this.field = field;
            this.op = op;
            this.value = value;
            this.type = type;
        }

        public Filter(String field, String op, String value) {
            this.field = field;
            this.op = op;
            this.value = value;
        }

    }
}
