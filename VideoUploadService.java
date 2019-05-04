package com.toqqer.services.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.toqqer.Constants;
import com.toqqer.services.handler.ImageUploadHandler;

@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 20)
public class VideoUploadService extends BaseService {

	private static final long serialVersionUID = -6526202046675893427L;
	private static final Logger LOG = Logger.getLogger("tqr.logger");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Only use this for ease of testing. Otherwise need soapUI to post data
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// super.doPost(request, response);

		System.out.println("Processing VideoUploadService request");

		response.setContentType("text/json");

		Part filePart = request.getPart(Constants.IMAGE_PARAM);
		// Collection filePart2 = request.getParts();
		RequestParameters params = readRequestParameters(request);
		params.addGenericValue(Constants.IMAGE_PARAM, filePart);

		ImageUploadHandler handler = new ImageUploadHandler();
		String jsonResponse = null;
		try {
			jsonResponse = handler.process(params);
		} catch (WTException e) {
			throw new IOException(e);
		}

		PrintWriter pw = response.getWriter();
		pw.print(jsonResponse);
		LOG.info("Responded with " + jsonResponse);
		pw.close();
	}

}
