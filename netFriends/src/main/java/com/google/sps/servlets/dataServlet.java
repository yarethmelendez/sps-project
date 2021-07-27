
package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/** Servlet responsible for data. */
@WebServlet("/user-Info")
public class dataServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Sanitize user input to remove HTML tags and JavaScript.
    String name = Jsoup.clean(request.getParameter("name"), Whitelist.none());
    String lastname = Jsoup.clean(request.getParameter("lastname"), Whitelist.none());
    String birthday = Jsoup.clean(request.getParameter("birthday"), Whitelist.none());
    String email = Jsoup.clean(request.getParameter("email"), Whitelist.none());
    String password = Jsoup.clean(request.getParameter("password"), Whitelist.none());
    String interest = Jsoup.clean(request.getParameter("interest"), Whitelist.none());


    //interact with datastore
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Task");
    FullEntity dataEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("name", name)
            .set("lastname", lastname)
            .set("birthday", birthday)
            .set("email", email)
            .set("password", password)
            .set("interest", interest)
            .build();
    datastore.put(dataEntity);

    response.sendRedirect("https://ymelendez-sps-summer21.appspot.com/index.html");
    System.out.print("succesful" + "name: "+ name);
  }
}
