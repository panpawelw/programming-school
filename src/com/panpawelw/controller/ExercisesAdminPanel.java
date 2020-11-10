package com.panpawelw.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.panpawelw.DAO.ExerciseDAO;
import com.panpawelw.DAO.RealExerciseDAO;
import com.panpawelw.misc.DbUtils;
import com.panpawelw.model.Exercise;

@WebServlet("/exercisesadminpanel")
public class ExercisesAdminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ExerciseDAO exerciseDAO;

    public ExercisesAdminPanel() {
        super();
    }

	public void init() {
		if(exerciseDAO == null) exerciseDAO = new RealExerciseDAO(DbUtils.initDB());
	}

	public void setExerciseDAO(ExerciseDAO exerciseDAO) {
		this.exerciseDAO = exerciseDAO;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Exercise> exercisesList = exerciseDAO.loadAllExercises();
		request.setAttribute("exerciseslist", exercisesList);
		String errorMessage = (String) request.getAttribute("errorMessage");
		if (errorMessage != null) {
			request.setAttribute("errorMessage", errorMessage);
		}
		getServletContext().getRequestDispatcher("/jsp/exercisesadminview.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}