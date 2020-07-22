package com.tutorial.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tutorial.web.entity.Notice;

@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));


		String url = "jdbc:mysql://localhost:3306/java_tutorial?serverTimezone=UTC";
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, "root", "123456789a");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id); //ù��° ����ǥ���ٰ� id�� �����ϰڴ�.

			ResultSet rs = st.executeQuery(); //�������� �����ʿ� �������

			rs.next();//�����ʿ� �ִ� �������� ������

			String title = rs.getString("TITLE");
			Date regdate = rs.getDate("REGDATE");
			String writerId = rs.getString("WRITER_ID");
			int hit = rs.getInt("HIT");
			String content = rs.getString("CONTENT");
			String files = rs.getString("FILES");
			
			Notice notice = new Notice(
									id,
									title,
									regdate,
									writerId,
									hit,
									content,
									files
									);
			request.setAttribute("n", notice);
			
			/*
			// request ����� Ȱ��
			request.setAttribute("title", title);
			request.setAttribute("regdate", regdate);
			request.setAttribute("writerId", writerId);
			request.setAttribute("hit", hit);
			request.setAttribute("content", content);
			*/
			
			rs.close();
			st.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// redirect ex) �α��� ������ ��� �α��� �������� ����������, �Խñ� ��������� ����������� ������
		
		
		
		//forward ex) ���⼭ �۾��� ������ �ٸ� servlet(jsp)���� �̾�ްԲ� �ϰ� ������ ���
		request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp").forward(request, response);
	}
}