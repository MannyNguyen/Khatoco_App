package khatoco.tvc.com.khatoco.ui.objects;

import java.io.Serializable;

/**
 * Created by prosoft on 11/11/16.
 */

public class RouteInfo extends BaseModelObject {

    private String code;
    private String name;

    public RouteInfo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private void update() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getKeyPath() {
        return "city";
    }
}
