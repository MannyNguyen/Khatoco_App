package khatoco.tvc.com.khatoco.ui.enums;

/**
 * Created by prosoft on 1/12/16.
 */
public enum ModelEvent {
    INITIAL(0),

    LOGIN_SUCCESS(0),
    LOGIN_FAIL(1),
    LOGIN_ALREADY(2),
    GET_ROUTE_SUCCESS(3),
    GET_AGENCY_SUCCESS(4),
    GET_TASK_SUCCESS(6),

    GET_LIST_TYPE_PRODUCT_SUCCESS(7),
    GET_LIST_PRODUCT_SUCCESS(8),
    GET_LIST_PROMOTION_SUCCESS(9),
    GET_LIST_PRODUCT_TAG_SUCCESS(10),
    POST_CHECK_IN_SUCCESS(11),
    POST_SUBMIT_SUCCESS(12),
    POST_CHECKIN_AGENCY_SUCCESS(13),


    ERRORUNSPECIFIED_ERROR(32);

    private final int value;

    ModelEvent(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
