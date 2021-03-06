package com.revature.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.dtos.ErrorResponse;
import com.revature.dtos.Principal;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.models.User;
import com.revature.repos.UserRepo;
import com.revature.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private final UserRepo userRepo = new UserRepo();
    private final UserService userService = new UserService(userRepo);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        String principalJSON = (String) req.getSession().getAttribute("principal");
        System.out.println(principalJSON);

        if (principalJSON == null) {
            ErrorResponse err = new ErrorResponse(401, "No principal object found on request.");
            respWriter.write(mapper.writeValueAsString(err));
            resp.setStatus(401);
            return;
        }

        Principal principal = mapper.readValue(principalJSON, Principal.class);

        if (!principal.getRole().equalsIgnoreCase("Admin")) {
            ErrorResponse err = new ErrorResponse(403, "Forbidden: your role does not permit you to access this endpoint.");
            respWriter.write(mapper.writeValueAsString(err));
            resp.setStatus(403);
            return;
        }

        try {

            String idParam = req.getParameter("id");

           if (idParam != null) {

               int id = Integer.parseInt(idParam);
               User user = userService.getUserById(id).get();
               String userJSON = mapper.writeValueAsString(user);
               respWriter.write(userJSON);
               System.out.println(user);

           }else {
               Set<User> users = userService.getAllUsers();
               String usersJSON = mapper.writeValueAsString(users);
               respWriter.write(usersJSON);
           }
            resp.setStatus(200);
        } catch (ResourceNotFoundException rnfe){

            resp.setStatus(404);
            ErrorResponse err = new ErrorResponse(404, "Users not found");
            respWriter.write(mapper.writeValueAsString(err));

        } catch (NumberFormatException | InvalidRequestException e){

            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(400, "Provided user id parameter is not usable");
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();

        try {
            User newUser = mapper.readValue(req.getInputStream(), User.class);
            userService.register(newUser);
            System.out.println(newUser);
            String newUserJSON = mapper.writeValueAsString(newUser);
            respWriter.write(newUserJSON);
            resp.setStatus(201);

        } catch (MismatchedInputException mie){

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
}
