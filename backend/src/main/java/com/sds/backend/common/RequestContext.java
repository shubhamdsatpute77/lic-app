package com.sds.backend.common;

import jakarta.servlet.http.HttpServletRequest;

public class RequestContext {
    public static final String REQUEST_ID = "requestId";
    public static final String START_TIME = "requestStartTime";

    public static String getRequestId(HttpServletRequest request) {
        return (String) request.getAttribute(REQUEST_ID);
    }

    public static void setRequestId(HttpServletRequest request, String requestId) {
        request.setAttribute(REQUEST_ID, requestId);
    }

    public static void setStartTime(HttpServletRequest request, long startTime) {
        request.setAttribute(START_TIME, startTime);
    }

    public static long getExecutionTime(HttpServletRequest request) {
        Long startTime = (Long) request.getAttribute(START_TIME);
        if (startTime == null) {
            return 0;
        }
        return System.currentTimeMillis() - startTime;
    }
}
