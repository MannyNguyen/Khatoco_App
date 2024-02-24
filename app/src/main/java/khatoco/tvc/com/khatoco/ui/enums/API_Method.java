package khatoco.tvc.com.khatoco.ui.enums;

/**
 * Created by prosoft on 12/24/15.
 */
public enum API_Method {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DOWNLOAD("DOWNLOAD");

    private final String value;

    API_Method(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }


}
