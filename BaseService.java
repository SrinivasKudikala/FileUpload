package com.toqqer.services.servlet;

/*import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;*/
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.toqqer.services.servlet.RequestParameters;

public class BaseService extends HttpServlet {
	
	private static final long serialVersionUID = -1588841710602556169L;
	
	
	
	public RequestParameters readRequestParameters(HttpServletRequest request) {

		@SuppressWarnings("unchecked")
		Map<String, String[]> map = request.getParameterMap();
		
		return new RequestParameters(map);
	}

	public HashMap<String, String> readRequestParameters(HttpServletRequest request) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		@SuppressWarnings("unchecked")
		Map<String, String[]> map = request.getParameterMap();

	    Set<Entry<String, String[]>> set = map.entrySet();
	    Iterator<Entry<String, String[]>> it = set.iterator();
	    
	    Map.Entry<String, String[]> entry = null;
	    String paramName = null;
	    String[] paramValues = null;
	    
	    while (it.hasNext()) {
	        entry = (Entry<String, String[]>) it.next();
	        paramName = entry.getKey();
	        paramValues = entry.getValue();
	        
	        if (paramValues.length > 0) {
	            String paramValue = paramValues[0];
	            parameters.put(paramName, paramValue);
	        } 
	    }
	    
	    return parameters;
	}
}
