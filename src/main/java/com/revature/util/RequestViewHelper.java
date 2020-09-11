package com.revature.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {

    public String process(HttpServletRequest req){
        switch (req.getRequestURI()) {

            case "/login.view":
            case "/reimbursement/login.view":
                return "partials/login.html";

            case "/home.view":
            case "/reimbursement/home.view":
                return "partials/home.html";
            default:
                return null;
        }
    }
}
