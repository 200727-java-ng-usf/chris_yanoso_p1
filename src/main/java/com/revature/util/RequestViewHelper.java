package com.revature.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {

    public String process(HttpServletRequest req){
        switch (req.getRequestURI()) {
            default:
                return null;
        }
    }
}
