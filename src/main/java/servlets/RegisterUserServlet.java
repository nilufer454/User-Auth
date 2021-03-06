package servlets;

import com.google.appengine.repackaged.com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;
import com.trainingproject.helper.DbConnect;
import com.trainingproject.model.Password;
import com.trainingproject.model.Token;
import com.trainingproject.model.User;
import com.trainingproject.model.UserAuth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Base64;


@WebServlet(name = "register", urlPatterns = "/register")
public class RegisterUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        Gson gson = new Gson();
        String json = "";
        if (UserAuth.newAccountAuthorization(username)==true)
        {


             User user = new User(username, email, password.toCharArray(), Password.getNextSalt());

             User.addNewUserToDataBase(user);
             out.print(gson.toJson(HttpStatusCodes.STATUS_CODE_CREATED));
            /* Token token = new Token(username, Token.nextToken());

             token.addTokenToDataBase();
             json = gson.toJson(token);
             out.print( json);*/
        }
        else
        out.print(gson.toJson(HttpStatusCodes.STATUS_CODE_UNAUTHORIZED));
    }

}
