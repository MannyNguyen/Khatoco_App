package khatoco.tvc.com.khatoco.api.object_request_api;

import java.util.ArrayList;

/**
 * Created by prosoft on 11/21/16.
 */

public class GetProductOfTagRequest {

    public int start = 0;
    public int limit = 15;
    public String formid;

    public ArrayList<GetProductOfTagRequest.Filter> fixedFilters = new ArrayList<>();

    public class Filter {
        public String field;
        public String op;
        public String value;
        public String type;

        public Filter(String field, String op, String value, String type) {
            this.field = field;
            this.op = op;
            this.value = value;
            this.type = type;
        }

    }
}
