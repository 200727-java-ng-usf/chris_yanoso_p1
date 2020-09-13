package com.revature.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.Principal;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class RequestViewHelper {

    public String process(HttpServletRequest req) throws JsonProcessingException {
        switch (req.getRequestURI()) {

            case "/login.view":
            case "/reimbursement/login.view":
                return "partials/login.html";

            case "/home.view":
            case "/reimbursement/home.view":
                ObjectMapper mapper = new ObjectMapper();
                String principalJSON = (String) req.getSession().getAttribute("principal");
                Principal principal = mapper.readValue(principalJSON, Principal.class);
                String userRole = principal.getRole();
                switch (userRole.toLowerCase()) {
                    case "admin":
                        return "partials/admin-home.html";
                    case "employee":
                        return "partials/employee-home.html";
                    case "manager":
                        return "partials/manager-home.html";
                    default:
                        return "partials/bad-home.html";
                }
            case "/allUsers.view":
            case "/reimbursement/allUsers.view":
                return "partials/all-users.html";
            case "/userById.view":
            case "/reimbursement/userById.view":
                return "partials/user-by-id.html";
            case "/register.vew":
            case "/reimbursement/register.view":
                return "partials/register.html";
            default:
                return null;
        }
    }
}
