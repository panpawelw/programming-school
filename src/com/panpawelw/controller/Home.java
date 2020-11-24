package com.panpawelw.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.LastSolutionDAO;
import com.panpawelw.DAO.RealLastSolutionDAO;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.misc.ValidateParameter;
import com.panpawelw.model.LastSolution;

@WebServlet(value = "/")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LastSolutionDAO lastSolutionDAO;

    public Home() {
        super();
    }

    public void init() throws ServletException {
        if(lastSolutionDAO == null) lastSolutionDAO = new RealLastSolutionDAO(DbUtils.initDB());
    }

    public void setLastSolutionDAO(LastSolutionDAO lastSolutionDAO) {
        this.lastSolutionDAO = lastSolutionDAO;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String initParam = request.getServletContext().getInitParameter("last-solutions");
        long howMany = ValidateParameter.checkLong(initParam, "Parameter " +
                "must be a value of type long using default 5 instead!");
        if(howMany < 0) howMany = 5;
        List<LastSolution> lastSolutions = lastSolutionDAO.loadMostRecentSolutions(howMany);
        request.setAttribute("lastsolutions", lastSolutions);
        getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
    }
}
