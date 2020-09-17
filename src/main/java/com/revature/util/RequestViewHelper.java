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
            case "/terminate.view":
            case "/reimbursement/terminate.view":
                return "partials/terminate.html";
            case "/updateUser.view":
            case "/reimbursement/updateUser.view":
                return "partials/update-user.html";
            case "/allReimbursements.view":
            case "/reimbursement/allReimbursements.view":
                return "partials/all-reimbursements.html";
            case "/reimbursementById.view":
            case "/reimbursement/reimbursementById.view":
                return "partials/reimbursementById.html";
            case "/allPendingReimbursements.view":
            case "/reimbursement/allPendingReimbursements.view":
                return "partials/all-pending-reimbursements.html";
            case "/allResolvedReimbursements.view":
            case "/reimbursement/allResolvedReimbursements.view":
                return "partials/all-resolved-reimbursements.html";
            case "/userReimbursementById.view":
            case "/reimbursement/userReimbursementById.view":
                return "partials/user-reimbursement-by-id.html";
            case "/newReimbursements.view":
            case "/reimbursement/newReimbursements.view":
                return "partials/new-reimbursement.html";
            case "/updateReimbursement.view":
            case "/reimbursement/updateReimbursement.view":
                return "partials/update-reimbursement.html";
            default:
                return null;
        }
    }
}
