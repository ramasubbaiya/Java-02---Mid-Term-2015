/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 * Updated 2015 Mark Russell <mark.russell@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides an Account Balance and Basic Withdrawal/Deposit Operations
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    model.Account account = new model.Account();

    //doGet method starts here
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        try (PrintWriter out = response.getWriter()) {           
            //Gets balance
            out.println(account.getBalance());
        } catch (IOException ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("YOu have some thing wrong in" + ex.getMessage());
        }
    }

    //doPost method starts here
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String withdraw;
        String deposit;
        String close;

        try (PrintWriter out = response.getWriter()) {

            //Checks withdraw parameter
            if (request.getParameter("withdraw") != null) {
                account.withdraw(Double.parseDouble(request.getParameter("withdraw")));
            } 

            //Checks deposit parameter
            else if (request.getParameter("deposit") != null) {
                account.deposit(Double.parseDouble(request.getParameter("deposit")));
            } 

            //Checks close parameter
            else if ("true".equals(request.getParameter("close"))) {
                account.close();
            } 

            //For others
            else {
                out.println("Error: No parameters to process POST request");
            }

            doGet(request, response);
        } catch (IOException ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("YOu have some thing wrong in" + ex.getMessage());
        }
    }
}
