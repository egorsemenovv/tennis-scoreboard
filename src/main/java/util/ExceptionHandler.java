package util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ExceptionHandler {
    public static void sendError(HttpServletRequest req, HttpServletResponse res, int code, String message) throws ServletException, IOException {
        req.setAttribute("errorCode", code);
        req.setAttribute("errorMessage", message);
        req.getRequestDispatcher("/jsp/error-page.jsp").forward(req, res);
        res.flushBuffer();
    }
}
