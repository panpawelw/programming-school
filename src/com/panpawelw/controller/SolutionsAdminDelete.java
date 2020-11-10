package com.panpawelw.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.RealSolutionDAO;
import com.panpawelw.DAO.SolutionDAO;
import com.panpawelw.misc.ValidateParameter;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.Solution;

@WebServlet("/deletesolution")
public class SolutionsAdminDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SolutionDAO solutionDAO;

    public SolutionsAdminDelete() {
        super();
    }

    public void init() {
        if(solutionDAO == null) solutionDAO = new RealSolutionDAO(DbUtils.initDB());
    }

    public void setSolutionDAO(SolutionDAO solutionDAO) {
        this.solutionDAO = solutionDAO;
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