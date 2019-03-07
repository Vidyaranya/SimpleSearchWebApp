import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

@WebServlet(urlPatterns = {"/Search"})
public class Search extends HttpServlet {

    public void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException,
            IOException {
        PrintWriter out = response.getWriter();
        out.println("hello");
        final String filePath = "/WEB-INF/exercise.json";
        ServletContext context = this.getServletContext();
        String pathname =context.getRealPath(filePath);
        String searchString = request.getParameter("name");
        FileReader reader = new FileReader(pathname);

        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray jsonObject = (JSONArray) jsonParser.parse(reader);
            Iterator iterator = jsonObject.iterator();
            while (iterator.hasNext()) {
                out.println(iterator.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
