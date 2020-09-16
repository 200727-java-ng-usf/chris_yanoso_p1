package com.revature.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.dtos.ErrorResponse;
import com.revature.dtos.Principal;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
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
import java.sql.Timestamp;
import java.util.HashSet;
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
                            if (resolvedReimbursements.isEmpty()){
                                ErrorResponse err = new ErrorResponse(404, "There are no Resolved reimbursements");
                                respWriter.write(mapper.writeValueAsString(err));
                                resp.setStatus(404);
                                return;
                            }
                            String resolvedReimbursementsJSON = mapper.writeValueAsString(resolvedReimbursements);
                            respWriter.write(resolvedReimbursementsJSON);
                        } else {
                            Set<Reimbursement> resolvedReimbursementByUserId = reimbursementService.getResolvedReimbursementByUserId(userId);
                            if (resolvedReimbursementByUserId.isEmpty()){
                                ErrorResponse err = new ErrorResponse(404, "There are no Resolved reimbursements");
                                respWriter.write(mapper.writeValueAsString(err));
                                resp.setStatus(404);
                                return;
                            }
                            String resolvedReimbursementByUserIdJSON = mapper.writeValueAsString(resolvedReimbursementByUserId);
                            respWriter.write(resolvedReimbursementByUserIdJSON);
                        }
                        break;
                    case "pending":
                        if (principal.getRole().equalsIgnoreCase("Manager")) {
                            Set<Reimbursement> pendingReimbursements = reimbursementService.getAllPendingReimbursements();
                            if (pendingReimbursements.isEmpty()){
                                ErrorResponse err = new ErrorResponse(404, "There are no Pending reimbursements");
                                respWriter.write(mapper.writeValueAsString(err));
                                resp.setStatus(404);
                                return;
                            }
                            String pendingReimbursementsJSON = mapper.writeValueAsString(pendingReimbursements);
                            respWriter.write(pendingReimbursementsJSON);
                        } else {
                            Set<Reimbursement> pendingReimbursementByUserId = reimbursementService.getPendingReimbursementByUserId(userId);
                            if (pendingReimbursementByUserId.isEmpty()){
                                ErrorResponse err = new ErrorResponse(404, "There are no Pending reimbursements");
                                respWriter.write(mapper.writeValueAsString(err));
                                resp.setStatus(404);
                                return;
                            }
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
                    Set<Reimbursement> allReimbursements = new HashSet<>();
                    Set<Reimbursement> pendingReimbursements = reimbursementService.getAllPendingReimbursements();
                    if (!pendingReimbursements.isEmpty()){
                        allReimbursements.addAll(pendingReimbursements);
                    }
                    Set<Reimbursement> resolvedReimbursements = reimbursementService.getAllResolvedReimbursements();
                    if (!resolvedReimbursements.isEmpty()) {
                        allReimbursements.addAll(reimbursementService.getAllResolvedReimbursements());
                    }
                    if (allReimbursements.isEmpty()){
                        ErrorResponse err = new ErrorResponse(404, "No Reimbursements found!");
                        resp.setStatus(404);
                        respWriter.write(mapper.writeValueAsString(err));
                        return;
                    }
                    String allReimbursementsJSON = mapper.writeValueAsString(allReimbursements);
                    respWriter.write(allReimbursementsJSON);
                } else {
                    Set<Reimbursement> allReimbursements = new HashSet<>();
                    Set<Reimbursement> pendingReimbursements = reimbursementService.getPendingReimbursementByUserId(userId);
                    if (!pendingReimbursements.isEmpty()){
                        allReimbursements.addAll(pendingReimbursements);
                    }
                    Set<Reimbursement> resolvedReimbursements = reimbursementService.getResolvedReimbursementByUserId(userId);
                    if (!resolvedReimbursements.isEmpty()) {
                        allReimbursements.addAll(reimbursementService.getAllResolvedReimbursements());
                    }
                    if (allReimbursements.isEmpty()){
                        ErrorResponse err = new ErrorResponse(404, "No Reimbursements found!");
                        resp.setStatus(404);
                        respWriter.write(mapper.writeValueAsString(err));
                        return;
                    }
                    String allReimbursementsJSON = mapper.writeValueAsString(allReimbursements);
                    respWriter.write(allReimbursementsJSON);
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();

        try{
            Reimbursement newReimbursement = mapper.readValue(req.getInputStream(), Reimbursement.class);
            reimbursementService.createNewReimbursement(newReimbursement);
            System.out.println(newReimbursement);
            String newReimbursementJSON = mapper.writeValueAsString(newReimbursement);
            respWriter.write(newReimbursementJSON);
            resp.setStatus(201);

        }catch (MismatchedInputException mie){

            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(400, "Bad request: provided data can not be used");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);

        } catch (ResourcePersistenceException rpe){

            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(400, rpe.getMessage());
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);

        } catch (Exception e) {

            e.printStackTrace();
            resp.setStatus(500);
            ErrorResponse err = new ErrorResponse(500, "Server side Problem");
            respWriter.write(mapper.writeValueAsString(err));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            if (idParam != null){
                int id = Integer.parseInt(idParam);
                Reimbursement statusReimbursement = reimbursementService.getReimbursementById(id);
                if (principal.getRole().equalsIgnoreCase("Manager")){
                    switch(statusParam.toLowerCase()){
                        case "approved":
                            statusReimbursement.setResolverId(principal.getId());
                            Timestamp resolvedApprove = new Timestamp(System.currentTimeMillis());
                            statusReimbursement.setResolved(resolvedApprove);
                            reimbursementService.approveReimbursement(statusReimbursement);
                            break;
                        case "denied":
                            statusReimbursement.setResolverId(principal.getId());
                            Timestamp resolvedDeny = new Timestamp(System.currentTimeMillis());
                            statusReimbursement.setResolved(resolvedDeny);
                            reimbursementService.denyReimbursement(statusReimbursement);
                            break;
                        case "default":
                            System.out.println("bad param");
                            break;
                    }
                    respWriter.write(mapper.writeValueAsString(statusReimbursement));

                } else {
                    Reimbursement newReimbursement = mapper.readValue(req.getInputStream(), Reimbursement.class);
                    if (!statusReimbursement.equals(newReimbursement)){
                        if (statusReimbursement.getAmount() != newReimbursement.getAmount()){
                            if (newReimbursement.getAmount() > 0){
                                statusReimbursement.setAmount(newReimbursement.getAmount());
                            }
                        }
                        if (!statusReimbursement.getDescription().equals(newReimbursement.getDescription())){
                            if (newReimbursement.getDescription() != null && newReimbursement.getDescription().trim().equals("")){
                                statusReimbursement.setDescription(newReimbursement.getDescription());
                            }
                        }
                        if (!statusReimbursement.getReimbursementType().equals(newReimbursement.getReimbursementType())){
                            if (newReimbursement.getReimbursementType() != null && newReimbursement.getReimbursementType().toString().trim().equals("")){
                                statusReimbursement.setReimbursementType(newReimbursement.getReimbursementType());
                            }
                        }
                        reimbursementService.updateReimbursement(statusReimbursement);
                    }
                    String reimbursementJSON = mapper.writeValueAsString(statusReimbursement);
                    respWriter.write(reimbursementJSON);
                }
                resp.setStatus(200);
            } else {
                ErrorResponse err = new ErrorResponse(400, "Bad Request: id Parameter needed");
                respWriter.write(mapper.writeValueAsString(err));
                resp.setStatus(400);
            }
        } catch (InvalidRequestException ire){
            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(400, ire.getMessage());
            respWriter.write(mapper.writeValueAsString(err));

        } catch (ResourceNotFoundException rnf){
            resp.setStatus(404);
            ErrorResponse err = new ErrorResponse(404, rnf.getMessage());
            respWriter.write(mapper.writeValueAsString(err));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            ErrorResponse err = new ErrorResponse(500, "Server Side Error");
            respWriter.write(mapper.writeValueAsString(err));
        }
    }
}
