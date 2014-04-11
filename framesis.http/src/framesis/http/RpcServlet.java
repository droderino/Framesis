package framesis.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RpcServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5606101983646553418L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		JSONObject jRequest = this.readRequest(request);
		Dispatcher dispatcher = new Dispatcher();
		
		JSONObject jResponse = dispatcher.dispatch(jRequest);
		if(jResponse != null)
		{
			StringWriter sw = new StringWriter();
			try {
				jResponse.writeJSONString(sw);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.getWriter().write(sw.toString());
		}
	}
	
	private JSONObject readRequest(HttpServletRequest request)
	{
		try {
			BufferedReader reader = request.getReader();
			char[] buffer = new char[1024];
			StringBuffer string = new StringBuffer();
			
			int i=0;
			while((i = reader.read(buffer)) > 0)
			{
				string.append(buffer);
			}
			
			JSONParser parser = new JSONParser();
			return (JSONObject)parser.parse(string.toString());
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
