package com.rencomusic.apm.web;

import com.rencomusic.apm.persistence.RecipientService;
import com.rencomusic.apm.xlsx.XlsxReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/import-xlsx")
public class XlsxImportServlet extends HttpServlet {
    @Inject
    RecipientService recipientService;

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException,IOException {
        res.setContentType("text/html");
        PrintWriter pw=res.getWriter();
        try {
            ArrayList<String[]> matrix =
                    XlsxReader.readLines(new File("/tmp/test.xlsx"));
            for(int i = 0; i < matrix.size(); i++) {
                if (recipientService.findByMail(matrix.get(i)[1].toLowerCase()).size() == 0) {
                    System.out.println(matrix.get(i)[0] +" " + matrix.get(i)[1] + " "+ matrix.get(i)[2]);
                    recipientService.createRecipient(matrix.get(i)[0], matrix.get(i)[1], matrix.get(i)[2]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.println("<html><body>");
        pw.println("Import done...");
        pw.println("</body></html>");
        pw.close();
    }

}