package org.egorsemenovv.tennisscoreboard.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.egorsemenovv.tennisscoreboard.exception.InvalidParamsException;
import org.egorsemenovv.tennisscoreboard.exception.NoSuchResourceException;
import org.egorsemenovv.tennisscoreboard.exception.RepositoryException;
import org.egorsemenovv.tennisscoreboard.util.ExceptionHandler;

import java.io.IOException;

public class BaseServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            super.service(req, res);
        } catch (RepositoryException e) {
            ExceptionHandler.sendError(req, res,HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (InvalidParamsException e) {
            ExceptionHandler.sendError(req, res,HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (NoSuchResourceException e) {
            ExceptionHandler.sendError(req, res,HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
