package com.panpawelw.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.RealSolutionDAO;
import com.panpawelw.DAO.SolutionDAO;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.Solution;

@WebServlet("/solutionsadminpanel")
public class SolutionsAdminPanel extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SolutionDAO solutionDAO;

    public SolutionsAdminPanel() {
        super();
    }

    public void init() {
        if(solutionDAO == null) solutionDAO = new RealSolutionDAO(DbUtils.initDB());
    }

    public void setSolutionDAO(SolutionDAO solutionDAO) {
        this.solutionDAO = solutionDAO;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Solution> solutionsList = solutionDAO.loadAllSolutions();
        request.setAttribute("solutionslist", solutionsList);
        String errorMessage = (String) request.getAttribute("errormessage");
        if (errorMessage != null) {
            request.setAttribute("errormessage", errorMessage);
        }
        getServletContext().getRequestDispatcher("/jsp/solutionsadminview.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
