/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ph.activelearning;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ph.activelearning.reports.AdminReport;
import ph.activelearning.reports.GuestReport;
import ph.activelearning.reports.InstructorReport;
import ph.activelearning.reports.PdfReports;
import ph.activelearning.reports.Reports;
import ph.activelearning.reports.StudentReport;
import ph.activelearning.reports.UserReport;

/**
 *
 * @author alvar
 */


public class PDFSettingServlet extends HttpServlet {

    private String[] reportType = new String[7];
    private String specReport;
    private String dateStart;
    private String dateEnd;
      
    private String c1;
    private String c2;
    private String c3;
    private String c4;
    private String c5;
    private String c6;
    
    private String role1;
    private String role2;
    private String role3;
        
    private String specID;

    private String dateEnabled;
    private String courseEnabled;
    private String userEnabled;
    private String roleEnabled;    
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        ServletConfig config = getServletConfig();
        String driver = config.getInitParameter("jdbcDriver2");
        String dbUsername = config.getInitParameter("username2");
        String dbPassword = config.getInitParameter("password2");
        String dbUrl = config.getInitParameter("dbDriver2");
        Database db = new Database(driver, dbUrl, dbUsername, dbPassword);
        Database olddb = new Database(config.getInitParameter("jdbcDriver"),config.getInitParameter("dbDriver")
                ,config.getInitParameter("username"),config.getInitParameter("password"));
        String path = getServletContext().getRealPath("/");
        String user = session.getAttribute("username").toString();
        String cipher = session.getAttribute("Cipher").toString();
        String key = session.getAttribute("Key").toString();
        Reports report = new PdfReports();
        
        System.out.println(driver+"\n"+dbUsername+"\n"+dbPassword+"\n"+dbUrl);
       
        reportType[0] = (request.getParameter("r1"));
        reportType[1] = (request.getParameter("r2"));
        reportType[2] = (request.getParameter("r3"));
        reportType[3] = (request.getParameter("r4"));
        reportType[4] = (request.getParameter("r5"));
        reportType[5] = (request.getParameter("r6"));
        reportType[6] = (request.getParameter("r7"));
 
        Boolean old = true;
        String genReport = request.getParameter("genReport");
        
        if(getReportRequest(reportType) != null){
            System.out.println("report says: "+getReportRequest(reportType));
            if("r5".equals(getReportRequest(reportType))){
                System.out.println("r5 was called");
                
                response.sendRedirect("display.jsp");
            }
            else if("r6".equals(getReportRequest(reportType))){
                System.out.println("r6 was called");
                report = new AdminReport(olddb, path,old);
                report.generate();
                session.setAttribute("Date", report.getDateTime());
                response.sendRedirect("display.jsp");
            }
            else if("r7".equals(getReportRequest(reportType))){
                System.out.println("r7 was called");
                report = new GuestReport (olddb, path,session.getAttribute("username").toString(),cipher,key);
                report.generate();
                session.setAttribute("Date", report.getDateTime());
                response.sendRedirect("display.jsp");
            }
            else if(genReport== null){
                System.out.println("genreport says: "+ genReport);
                session.setAttribute("reportType", getReportRequest(reportType));
                response.sendRedirect("PdfOptions.jsp");}
        }
        else{
            System.out.println("It got in here");
           
            getReportFilters(request, response, session);
            boolean dateEnabledB = "on".equals(dateEnabled);
            boolean courseEnabledB = "on".equals(courseEnabled);
            boolean roleEnabledB = "on".equals(roleEnabled);


            printInfo();
            
            String role = session.getAttribute("role").toString();
            
            //TODO: MAKE OVERRIDE METHOD CALLS FOR THE REPORTS GIVEN A SPECIFIC CASE:
            
            if(role.equals("admin")){
                if(specReport.equals("r2")){
                    if(dateEnabledB && roleEnabledB){
                        //generate report with date specified and courses specified
                        report = new AdminReport(db, path, roleList(), LocalDate.parse(dateStart), LocalDate.parse(dateEnd));
                        report.generate();
                        
                    }else if(roleEnabledB){
                        //generate report with course specified
                        report = new AdminReport(db, path, roleList());
                        report.generate();
                        
                    }else if(dateEnabledB){
                        //generate report with date specified
                        report = new AdminReport(db, path, LocalDate.parse(dateStart), LocalDate.parse(dateEnd));
                        report.generate();
                        
                    }else{
                        //generate report with nothing specified
                        report = new AdminReport(db, path);
                        report.generate();
                    }
                }
                
            }
            else if(role.equals("instructor")){
                
                //Courses Handled Report
                if(specReport.equals("r3")){ 
                    if(dateEnabledB && courseEnabledB){
                        //generate report with date specified and courses specified
                        
                    }else if(courseEnabledB){
                        //generate report with course specified
                        
                    }else if(dateEnabledB){
                        //generate report with date specified
                        
                    }else{
                        //generate report with nothing specified
                        report = new InstructorReport(db,path,user);
                        report.generate();
                    }
                }  
                if(specReport.equals("r4")){
                    //generate feedback report
                }
            }
            else if(role.equals("student")){
                
                //Courses Enrolled Report
                if(specReport.equals("r1")){
                    if(dateEnabledB && courseEnabledB){
                        //generate report with date specified and courses specified
                        
                    }else{
                        //generate report with nothing specified
                        report = new StudentReport(db, path, session.getAttribute("username").toString());
                        report.generate();
                    }
                }
            }
        
        
        session.setAttribute("Date", report.getDateTime());
        
        System.out.println(getServletContext().getRealPath("/"));
        response.sendRedirect("display.jsp");
            
        }
}    
    public String getReportRequest(String[] reports){
        
        for (String report : reports) {
            if (report != null){
                return report;}
        }
        return null;
    }
    
    public void getReportFilters(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        //Gets the type of report chosen by the user
            
            

            //Report date inquiry values
            dateStart = request.getParameter("dateStart");
            dateEnd = request.getParameter("dateEnd");

            //specific report
            specReport = (String) session.getAttribute("reportType");
            
            //Specific courses filtered
            c1 = request.getParameter("c1");
            c2 = request.getParameter("c2");
            c3 = request.getParameter("c3");
            c4 = request.getParameter("c4");
            c5 = request.getParameter("c5");
            c6 = request.getParameter("c6");

            //Specific user id
            specID = request.getParameter("specID");

            //Roles
            role1 = request.getParameter("role1");
            role2 = request.getParameter("role2");
            role3 = request.getParameter("role3");
            
            //Checkboxes used to determine if a certain filter has been requested by the user
            dateEnabled = request.getParameter("dateEnabled");
            courseEnabled = request.getParameter("courseEnabled");
            userEnabled = request.getParameter("userEnabled");
            roleEnabled = request.getParameter("roleEnabled");
    }
    public void printInfo(){
        System.out.println("Valid Report: "+ specReport);
        System.out.println(dateStart);
        System.out.println(dateEnd);
        System.out.println("c1: "+ c1);
        System.out.println("c2: "+ c2);
        System.out.println("c3: "+ c3);
        System.out.println("c4: "+ c4);
        System.out.println("c5: "+ c5);
        System.out.println("c6: "+ c6);
        System.out.println("specID: "+ specID);
        
        System.out.println("dateEnabled: "+dateEnabled);
        System.out.println("courseEnabled: "+courseEnabled);
        System.out.println("userEnabled: "+userEnabled);
        
    }
    
    public String[] filterValues(){
        String[] values = {dateEnabled,dateStart,dateEnd,courseEnabled,c1,c2,c3,c4,c5,c6,userEnabled,specID};
        
        return values;
    }
    
    public List<String> courseList() {
        List<String> activeCourses = new ArrayList<>();

        // Check if c1 to c6 are enabled and add them to the list if they are
        if (c1 != null && "on".equals(c1)) {
            activeCourses.add("Course 1");
        }
        if (c2 != null && "on".equals(c2)) {
            activeCourses.add("Course 2");
        }
        if (c3 != null && "on".equals(c3)) {
            activeCourses.add("Course 3");
        }
        if (c4 != null && "on".equals(c4)) {
            activeCourses.add("Course 4");
        }
        if (c5 != null && "on".equals(c5)) {
            activeCourses.add("Course 5");
        }
        if (c6 != null && "on".equals(c6)) {
            activeCourses.add("Course 6");
        }

        return activeCourses;
    }
    
    public List<String> roleList() {
        List<String> activeRoles = new ArrayList<>();

        // Check if c1 to c6 are enabled and add them to the list if they are
        if (role1 != null && "on".equals(role1)) {
            activeRoles.add("admin");
        }
        if (role2 != null && "on".equals(role2)) {
            activeRoles.add("instructor");
        }
        if (role3 != null && "on".equals(role3)) {
            activeRoles.add("student");
        }
        return activeRoles;
    }

   

}