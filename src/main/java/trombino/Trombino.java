/*


 * 
 * 
 * 
 * 
 * 
 * 
 * Copyright (C) 2014, Teyssier Loic

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

 */
package trombino;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import dao.UserDAO;

/**
 * Servlet implementation class Trombino
 */
@WebServlet("/Trombino")
public class Trombino extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Trombino() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response); //TODO : voir si utile.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setAttribute("page", "home");
		
		User user;
		
		// Check if a user is connected :
		if(request.getSession().getAttribute("user")==null){
			response.sendRedirect("auth");
			return;
		}
		else
			user = (User) request.getSession().getAttribute("user");
			
		UserDAO userDAO = new UserDAO();
		List<User> noCommentsList= userDAO.getMembersWithoutUserComment(user);
		
		request.setAttribute("noCommentsList", noCommentsList);
		getServletContext().getRequestDispatcher("/trombino.jsp").forward(request, response);

	}

}
