package ph.activelearning;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.text.producer.DefaultTextProducer;

public class CaptchaServlet extends HttpServlet
{
    Captcha captcha;
	
	// Returns true if given two strings are same
	static boolean checkCaptcha(String captcha, String user_captcha)
	{
		return captcha.equals(user_captcha);
	}
	
	// Generates a CAPTCHA of given length
	
        private BufferedImage generateCaptchaVer2(int n){
            
            String chrs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            char[] c = chrs.toCharArray();
            
            Color backgroundColor = Color.WHITE;
            
            captcha = new Captcha.Builder(120, 70)
            .addText(new DefaultTextProducer(n,c))
            .addNoise()
            .addBackground(new FlatColorBackgroundProducer(backgroundColor))
            .build();
            
            
            
            return captcha.getImage();
        }
        
       
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        
           HttpSession session = req.getSession();
           
            System.out.println((String)req.getParameter("cuser"));
           
            
            if(captcha.isCorrect((String)req.getParameter("cuser"))){
                   
                    res.sendRedirect("success.jsp");}
                else{
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        ImageIO.write(generateCaptchaVer2(Integer.parseInt(getInitParameter("captchaLength"))), "png", output);
                        String imageAsBase64 = Base64.getEncoder().encodeToString(output.toByteArray());
                        
                        session.setAttribute("CaptchaImage", imageAsBase64);
                        
                       res.sendRedirect("error_6.jsp");}
                
                
            
         }
        
        
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        HttpSession session = request.getSession();
        
        try{
            if(session!=null){
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(generateCaptchaVer2(Integer.parseInt(getInitParameter("captchaLength"))), "png", output);
                String imageAsBase64 = Base64.getEncoder().encodeToString(output.toByteArray());
                
              
                session.setAttribute("CaptchaImage", imageAsBase64);
                
                response.sendRedirect("captcha.jsp"); 
             
        }
        else{
            throw new NullValueException();
        }
       
        }catch(NullValueException e){
            System.err.println(e);
        }
        
    }
	

}