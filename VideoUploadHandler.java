package com.toqqer.services.handler;

import java.util.logging.Logger;

import javax.servlet.http.Part;

import org.json.JSONObject;

import com.toqqer.Constants;
import com.toqqer.services.db.telo.bean.TqrUser;
import com.toqqer.services.db.telo.dao.TqrUserDAO;
import com.toqqer.services.db.telo.dao.commons.DAOProvider;
import com.toqqer.services.servlet.RequestParameters;
import com.toqqer.services.servlet.WTException;
import com.toqqer.services.util.MultipartUtil;

public class VideoUploadHandler extends BaseHandler {

	private static final Logger LOG = Logger.getLogger("tqr.logger");

	@Override
	public String process(RequestParameters parameters) throws WTException {

		String userId = parameters.getParameterValue(Constants.LOCAL_USR_ID);

		if (parameters.getGenericValue(Constants.VIDEO_PARAM) == null || userId == null) {
			return sendErrorResponse(Constants.FAILURE_RES, "missing userId or video Part");
		}

		TqrUserDAO dao = DAOProvider.getDAO(TqrUserDAO.class);

		TqrUser tqrUser = dao.find(Integer.parseInt(userId));

		if (tqrUser != null) {

			Part filePart = (Part) parameters.getGenericValue(Constants.VIDEO_PARAM);
			String fileName = MultipartUtil.extractFile(filePart);
			if (fileName != null) {
				tqrUser.setProfileImage(fileName);

				dao.update(tqrUser);

				JSONObject payloadObject = new JSONObject();
				payloadObject.put(Constants.STATUS, Constants.SUCCESS_RES);
				payloadObject.put(Constants.MESSAGE, Constants.MSG_SUCCESS);

				return payloadObject.toString();
			}

		}

		LOG.fine("Invalid data set. Didn't save the image.");

		return sendErrorResponse(Constants.FAILURE_RES, "Invalid data set. Didn't save the image.");
	}

}
