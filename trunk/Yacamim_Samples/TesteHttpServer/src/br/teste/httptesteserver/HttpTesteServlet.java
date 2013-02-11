package br.teste.httptesteserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HttpTesteServlet
 */
public class HttpTesteServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7763825925164706810L;
	
	private static final Random randomSimulacao = new Random(10000000000L);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HttpTesteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			final HttpSession session = request.getSession(true);
			response.setContentType("text/plain; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();

			String newToken = getNewToken();
			response.addHeader("testToken", newToken);
			if(session.getAttribute("lastToken") == null) {
				writer.write("{'resposta':{'sessao':'nova sessão', \"token\":\"new\", \"testToken\":\"" + newToken + "\"}}");
			} else {
				String lastToken = (String)session.getAttribute("lastToken");
				String paramToken = request.getParameter("testToken");
				if(paramToken != null && lastToken != null && lastToken.equals(paramToken)) {
					writer.write("{'resposta':{'sessao':'velha sessão', \"token\":\"ok\", \"testToken\":\"" + newToken + "\"}}");
				} else {
					writer.write("{'resposta':{'sessao':'velha sessão', \"token\":\"error\", \"testToken\":\"" + lastToken + "\"}}");
				}
			}
			session.setAttribute("lastToken", newToken);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private String getNewToken() {
		long codigoL = randomSimulacao.nextLong();
		if(codigoL < 0) {
			codigoL *= -1;
		}
		return codigoL + "";
	}
	
}