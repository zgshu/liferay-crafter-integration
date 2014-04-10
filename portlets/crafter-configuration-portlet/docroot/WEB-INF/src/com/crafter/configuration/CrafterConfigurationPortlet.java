package com.crafter.configuration;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class CrafterConfigurationPortlet extends MVCPortlet{

	public void submitPreferencesAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		String searchServerURL = ParamUtil.getString(actionRequest, "search_server_url");
		String crafterEndPoint = ParamUtil.getString(actionRequest, "crafter_end_point");
		try {
			if (Validator.isNotNull(searchServerURL)) {
				PortletPreferences preferences = PrefsPropsUtil.getPreferences();
				preferences.setValue("search.server.url", searchServerURL);
				preferences.setValue("crafter.end.point", crafterEndPoint);
				preferences.store();
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
	
	public void doView(
			RenderRequest request, RenderResponse response)
		throws IOException, PortletException {

		try {
			PortletPreferences preferences = PrefsPropsUtil.getPreferences();
			request.setAttribute("search_server_url", preferences.getValue("search.server.url", "empty"));
			request.setAttribute("crafter_end_point", preferences.getValue("crafter.end.point", "empty"));
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		include(viewTemplate, request, response);
	}

}
