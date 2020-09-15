package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.ErrorResponse;
import com.revature.dtos.Principal;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
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
import java.util.Optional;

@WebServlet("/change/users/*")
public class UserChangeServlet extends HttpServlet {

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
            ErrorResponse err = new ErrorResponse(401, "no principal object found on request");
            respWriter.write(mapper.writeValueAsString(err));
            resp.setStatus(401);
            return;
        }

        Principal principal = mapper.readValue(principalJSON, Principal.class);

        if (!principal.getRole().equalsIgnoreCase("Admin")){
            ErrorResponse err = new ErrorResponse(403, "Forbidden: your role does not permit you to access this endpoint.");
            respWriter.write(mapper.writeValueAsString(err));
            resp.setStatus(403);
            return;
        }

        try {

            String idParam = req.getParameter("id");

            if (idParam != null) {

                int id = Integer.parseInt(idParam);
                userService.terminateUserById(id);
                User user = userService.getUserById(id).get();
                String userJSON = mapper.writeValueAsString(user);
                respWriter.write(userJSON);
                System.out.println(user);
            }
            resp.setStatus(200);
        } catch (ResourceNotFoundException rnfe){
            resp.setStatus(404);
            ErrorResponse err = new ErrorResponse(404, rnfe.getMessage());
            respWriter.write(mapper.writeValueAsString(err));

        } catch (NumberFormatException | InvalidRequestException e){

            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(400, "Provided user id is not usable");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);

        } catch (Exception e){

            e.printStackTrace();
            resp.setStatus(500);
            ErrorResponse err = new ErrorResponse(500, "Server Side Error");
            respWriter.write(mapper.writeValueAsString(err));
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();

        try {
            String idParam = req.getParameter("id");
            int id = Integer.parseInt(idParam);
            if (idParam != null) {
                User oldUser = userService.getUserById(id).get();
                User newUser = mapper.readValue(req.getInputStream(), User.class);
                if (!oldUser.equals(newUser)) {
                    if (!oldUser.getUsername().equals(newUser.getUsername())) {
                        if (newUser.getUsername() != null && !newUser.getUsername().trim().equals("")) {
                            Optional<User> check = userService.getUserByUsername(newUser.getUsername());
                            if (!check.isPresent()) {
                                oldUser.setUsername(newUser.getUsername());
                            }
                        }
                    }
                    if (!oldUser.getEmail().equals(newUser.getEmail())) {
                        if (newUser.getEmail() != null && !newUser.getEmail().trim().equals("")) {
                            Optional<User> check = userService.getUserByEmail(newUser.getEmail());
                            if (!check.isPresent()) {
                                oldUser.setEmail(newUser.getEmail());
                            }
                        }
                    }
                    if (!oldUser.getPassword().equals(newUser.getPassword())) {
                        if (newUser.getPassword() != null && !newUser.getPassword().trim().equals("")) {
                            oldUser.setPassword(newUser.getPassword());
                        }
                    }
                    if (!oldUser.getFirstName().equals(newUser.getFirstName())) {
                        if (newUser.getFirstName() != null && !newUser.getFirstName().trim().equals("")) {
                            oldUser.setFirstName(newUser.getFirstName());
                        }
                    }
                    if (!oldUser.getLastName().equals(newUser.getLastName())) {
                        if (newUser.getLastName() != null && !newUser.getLastName().trim().equals("")) {
                            oldUser.setLastName(newUser.getLastName());
                        }
                    }
                    if (!oldUser.getUserRole().equals(newUser.getUserRole())) {
                        if (newUser.getUserRole() != null && !newUser.getLastName().trim().equals("")) {
                            oldUser.setUserRole(newUser.getUserRole());
                        }
                    }
                }
                userService.update(oldUser);
                String userJSON = mapper.writeValueAsString(oldUser);
                respWriter.write(userJSON);
                resp.setStatus(200);
            } else {
                ErrorResponse err = new ErrorResponse(400, "Bad Request: id Parameter needed");
                respWriter.write(mapper.writeValueAsString(err));
                resp.setStatus(400);
            }
            } catch(InvalidRequestException ire){
                resp.setStatus(400);
                ErrorResponse err = new ErrorResponse(400, ire.getMessage());
                respWriter.write(mapper.writeValueAsString(err));

            } catch(ResourceNotFoundException rnf){
                resp.setStatus(404);
                ErrorResponse err = new ErrorResponse(404, rnf.getMessage());
                respWriter.write(mapper.writeValueAsString(err));
            } catch(Exception e){
                e.printStackTrace();
                resp.setStatus(500);
                ErrorResponse err = new ErrorResponse(500, "Server Side Error");
                respWriter.write(mapper.writeValueAsString(err));
            }
    }
}
