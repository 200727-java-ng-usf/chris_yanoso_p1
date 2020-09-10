package com.revature.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.dtos.Credentials;
import com.revature.dtos.ErrorResponse;
import com.revature.dtos.Principal;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.User;
import com.revature.repos.UserRepo;
import com.revature.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private final UserRepo userRepo = new UserRepo();
    private final UserService userService = new UserService(userRepo);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.setStatus(204);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");


        try {

            Credentials creds = mapper.readValue(req.getInputStream(), Credentials.class);

            User authUser = userService.authenticate(creds.getUsername(), creds.getPassword()).get();
            Principal principal = new Principal(authUser);

            HttpSession session = req.getSession();
            session.setAttribute("principal", principal.stringify());

            resp.setStatus(204);
        } catch (MismatchedInputException | InvalidRequestException ie){

            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(400 ,"Bad Request");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);

        } catch (AuthenticationException ae){

            resp.setStatus(401);
            ErrorResponse err = new ErrorResponse(401, ae.getMessage());
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);

        } catch (Exception e){

            e.printStackTrace();
            resp.setStatus(500);
            ErrorResponse err = new ErrorResponse(500, "Coding error");
            respWriter.write(mapper.writeValueAsString(err));
        }

    }
}
