package com.revature.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.ErrorResponse;
import com.revature.dtos.Principal;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Reimbursement;
import com.revature.repos.ReimbursementRepo;
import com.revature.services.ReimbursementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet("/reimbursements/*")
public class ReimbursementServlet extends HttpServlet {

    private final ReimbursementRepo reimbursementRepo = new ReimbursementRepo();
    private final ReimbursementService reimbursementService = new ReimbursementService(reimbursementRepo);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        String principalJSON = (String) req.getSession().getAttribute("principal");
        System.out.println(principalJSON);

        if (principalJSON == null) {
            ErrorResponse err = new ErrorResponse(401, "No principal object found on request");
            respWriter.write(mapper.writeValueAsString(err));
            resp.setStatus(401);
            return;
        }

        Principal principal = mapper.readValue(principalJSON, Principal.class);
        try {
            String idParam = req.getParameter("id");
            String statusParam = req.getParameter("status");
            int userId = principal.getId();
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Reimbursement reimbursement = reimbursementService.getReimbursementById(id);
                String reimbursementJSON = mapper.writeValueAsString(reimbursement);
                if (principal.getRole().equalsIgnoreCase("Manager") || userId == reimbursement.getAuthorId()) {
                    respWriter.write(reimbursementJSON);
                    System.out.println(reimbursement);
                } else {
                    ErrorResponse err = new ErrorResponse(403, "You Do not have access to this reimbursement");
                    respWriter.write(mapper.writeValueAsString(err));
                    resp.setStatus(403);
                    return;
                }
            } else if (statusParam != null) {
                switch (statusParam.toLowerCase()) {
                    case "resolved":
                        if (principal.getRole().equalsIgnoreCase("Manager")) {
                            Set<Reimbursement> resolvedReimbursements = reimbursementService.getAllResolvedReimbursements();
                            String resolvedReimbursementsJSON = mapper.writeValueAsString(resolvedReimbursements);
                            respWriter.write(resolvedReimbursementsJSON);
                        } else {
                            Set<Reimbursement> resolvedReimbursementByUserId = reimbursementService.getResolvedReimbursementByUserId(userId);
                            String resolvedReimbursementByUserIdJSON = mapper.writeValueAsString(resolvedReimbursementByUserId);
                            respWriter.write(resolvedReimbursementByUserIdJSON);
                        }
                        break;
                    case "pending":
                        if (principal.getRole().equalsIgnoreCase("Manager")) {
                            Set<Reimbursement> pendingReimbursements = reimbursementService.getAllPendingReimbursements();
                            String pendingReimbursementsJSON = mapper.writeValueAsString(pendingReimbursements);
                            respWriter.write(pendingReimbursementsJSON);
                        } else {
                            Set<Reimbursement> pendingReimbursementByUserId = reimbursementService.getPendingReimbursementByUserId(userId);
                            String pendingReimbursementByUserIdJSON = mapper.writeValueAsString(pendingReimbursementByUserId);
                            respWriter.write(pendingReimbursementByUserIdJSON);
                        }
                        break;
                    default:
                        ErrorResponse err = new ErrorResponse(400, "Provided status parameter is not usable");
                        resp.setStatus(400);
                        respWriter.write(mapper.writeValueAsString(err));
                        return;
                }
            } else {
                if (principal.getRole().equalsIgnoreCase("Manager")) {
                    Set<Reimbursement> allReimbursements = reimbursementService.getAllPendingReimbursements();
                    allReimbursements.addAll(reimbursementService.getAllResolvedReimbursements());
                    String allReimbursementsJSON = mapper.writeValueAsString(allReimbursements);
                    respWriter.write(allReimbursementsJSON);
                } else {
                    Set<Reimbursement> allReimbursementsByUserId = reimbursementService.getPendingReimbursementByUserId(userId);
                    allReimbursementsByUserId.addAll(reimbursementService.getResolvedReimbursementByUserId(userId));
                    String allReimbursementsByUserIdJSON = mapper.writeValueAsString(allReimbursementsByUserId);
                    respWriter.write(allReimbursementsByUserIdJSON);
                }
            }
            resp.setStatus(200);

        } catch (ResourceNotFoundException rnfe){
            resp.setStatus(404);
            ErrorResponse err = new ErrorResponse(404, rnfe.getMessage());
            respWriter.write(mapper.writeValueAsString(err));

        }catch (NumberFormatException | InvalidRequestException e) {

            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(400, "Provided user id parameter is not usable");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
