import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

/**
 * A class which contains the logic to find the names matching the given name passed from the jsp
 */
@WebServlet(urlPatterns = {
        "/Search"
})
public class Search extends HttpServlet {

    // A constant which is the query parameter passed from the jsp
    final String searchParameter = "name";
    // A constant which is the key containing the field that we are searching. For now both this and searchParameter
    // are same. However, in the future their might be changes in one of the files and it's better to have different
    // parameters for each of them for maintainability.
    final String jsonKeyToSearch = "name";

    /**
     * Gets the search parameter, finds the appopriate names from the json and returns the matching values.
     * @param request Request received from the jsp. Contains the name to be searched.
     * @param response Response returned which contains the name(s) matching the given input name.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        final String filePath = "/WEB-INF/exercise.json";
        ServletContext context = this.getServletContext();
        String pathname = context.getRealPath(filePath);

        String searchString = request.getParameter(searchParameter);
        FileReader reader = new FileReader(pathname);
        JSONParser jsonParser = new JSONParser();
        boolean isMatchFound = false;

        try {
            JSONArray employeeData = (JSONArray) jsonParser.parse(reader);
            Iterator iterator = employeeData.iterator();
            while (iterator.hasNext()) {
                JSONObject individualEmployee = (JSONObject) iterator.next();
                String currentEmployeeName = (String) individualEmployee.get(searchParameter);
                if (currentEmployeeName.toLowerCase().contains(searchString.toLowerCase())) {
                    out.println(currentEmployeeName);
                    isMatchFound = true;
                }
            }
            if (!isMatchFound) {
                out.println("No match found");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        out.close();
    }
}