package framesis.http;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.json.simple.JSONObject;

public class Dispatcher {

	private RpcApi rpcApi;
	
	public Dispatcher()
	{
		this.rpcApi = new RpcApi();
	}
	
	public JSONObject dispatch(JSONObject request)
	{
		String methodName = (String)request.get("method");
		int id = (Integer)request.get("id");
		String result = null;
		
		try {
			Method method = RpcApi.class.getMethod(methodName, null);
			System.out.println("RPC-method: " + methodName);
			result = (String)method.invoke(rpcApi);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject response = new JSONObject();
		response.put("jsonrpc", "2.0");
		response.put("id", id);
		response.put("result", result);
		
		return response;
	}
}
