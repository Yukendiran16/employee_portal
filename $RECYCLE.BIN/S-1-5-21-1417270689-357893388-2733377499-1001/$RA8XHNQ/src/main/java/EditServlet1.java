
package com.app.studytonight;   
import java.io.IOException;     
import java.io.PrintWriter;     
    
import javax.servlet.ServletException;      
import javax.servlet.annotation.WebServlet;     
import javax.servlet.http.HttpServlet;      
import javax.servlet.http.HttpServletRequest;   
import javax.servlet.http.HttpServletResponse;      
@WebServlet("/EditServlet")   
public class EditServlet1 extends HttpServlet {     
    protected void doGet(HttpServletRequest request, HttpServletResponse response)      
           throws ServletException, IOException {   
        response.setContentType("text/html");     
        PrintWriter out=response.getWriter();   
        out.println("<h1>Update Student</h1>");   
        String sid=request.getParameter("id");    
        int id1=Integer.parseInt(sid);      
            
        stu e1=stuDao.getStudentById(id1);      
            
        out.print("<form action='EditServlet2' method='post'>");    
        out.print("<table>");   
        out.print("<tr><td></td><td><input type='hidden' name='id' value='"+e1.getId1()+"'/></td></tr>");     
        out.print("<tr><td>Name:</td><td><input type='text' name='name' value='"+e1.getName1()+"'/></td></tr>");      
        out.print("<tr><td>Age:</td><td><input type='age' name='age' value='"+e1.getAge1()+"'/></td></tr>");      
        out.print("<tr><td>Course:</td><td><input type='course' name='course' value='"+e1.getCourse1()+"'/></td></tr>");      
        out.print("<tr><td>City:</td><td>");      
        out.print("<select name='city' style='width:150px'>");      
        out.print("<option>Delhi</option>");      
        out.print("<option>Noida</option>");      
        out.print("<option>Raipur</option>");     
        out.print("<option>Bhopal</option>");     
        out.print("</select>");     
        out.print("</td></tr>");      
        out.print("<tr><td colspan='2'><input type='submit' value='Edit & Save '/></td></tr>");     
        out.print("</table>");      
        out.print("</form>");   
            
        out.close();    
    }   
}   
	