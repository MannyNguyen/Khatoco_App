package khatoco.tvc.com.khatoco.ui.objects;

/**
 * Created by prosoft on 11/11/16.
 */

public class AgencyInfo extends BaseModelObject {

    private String code;
    private String name;
    private String latitute;
    private String longtitute;

    public AgencyInfo(String code, String name, String latitute, String longtitute) {
        this.code = code;
        this.name = name;
        this.latitute = latitute;
        this.longtitute = longtitute;
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

    public String getLatitute() {
        return latitute;
    }

    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }

    public String getLongtitute() {
        return longtitute;
    }

    public void setLongtitute(String longtitute) {
        this.longtitute = longtitute;
    }

    @Override
    public String getKeyPath() {
        return "city";
    }

}
