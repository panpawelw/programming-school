package pl.pjm77.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.pjm77.DAO.RealSolutionDAO;
import pl.pjm77.DAO.SolutionDAO;
import pl.pjm77.misc.RealDataSource;
import pl.pjm77.misc.ValidateParameter;
import pl.pjm77.model.Solution;

@WebServlet("/deletesolution")
public class SolutionsAdminDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SolutionDAO solutionDAO;

    public SolutionsAdminDelete() {
        super();
    }

    public void init() {
        solutionDAO = new RealSolutionDAO(RealDataSource.initDB());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        long solutionId = ValidateParameter.checkLong(idParam, "Incorrect solution Id!");
        if (solutionId >= 0) {
            Solution solution = solutionDAO.loadSolutionById(solutionId);
            solutionDAO.deleteSolution(solution);
        }
        getServletContext().getRequestDispatcher("/solutionsadminpanel").forward(request, response);
    }
}