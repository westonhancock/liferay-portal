/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.kaleo.activator;

import com.liferay.portal.workflow.kaleo.manager.PortalKaleoManager;
import com.liferay.portal.workflow.kaleo.manager.PortalKaleoManagerUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 * @author Michael C. Han
 */
@Component(immediate = true)
public class KaleoActivator {

	@Activate
	protected void activate() throws Exception {
		PortalKaleoManager portalKaleoManager =
			PortalKaleoManagerUtil.getPortalKaleoManager();

		if (portalKaleoManager != null) {
			portalKaleoManager.deployKaleoDefaults();
		}
	}

}