/**
 * Copyright (C) 2005-2014 Rivet Logic Corporation.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
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
