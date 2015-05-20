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

package com.liferay.portal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfig;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigFactory;
import com.liferay.portal.kernel.editor.configuration.EditorOptions;
import com.liferay.portal.kernel.editor.configuration.EditorOptionsFactoryUtil;
import com.liferay.portal.kernel.editor.configuration.EditorConfigTransformer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorConfigFactoryImpl
	extends BaseEditorConfigurationFactoryImpl<EditorConfigContributor>
		implements EditorConfigFactory {

	@Override
	public EditorConfig getEditorConfig(
		String portletName, String editorConfigKey, String editorName,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		JSONObject configJSONObject = populateConfigJSONObject(
			portletName, editorConfigKey, editorName,
			inputEditorTaglibAttributes, themeDisplay, liferayPortletResponse);

		EditorOptions editorOptions = EditorOptionsFactoryUtil.getEditorOptions(
			portletName, editorConfigKey, editorName,
			inputEditorTaglibAttributes, themeDisplay, liferayPortletResponse);

		EditorConfigTransformer editorConfigTransformer =
			_editorConfigTransformerServiceTrackerMap.getService(editorName);

		return new EditorConfigImpl(
			configJSONObject, editorOptions, editorConfigTransformer,
			inputEditorTaglibAttributes, themeDisplay, liferayPortletResponse);
	}

	@Override
	protected ServiceTrackerMap<String, List<EditorConfigContributor>>
		getServiceTrackerMap() {

		return _editorConfigContributorServiceTrackerMap;
	}

	protected JSONObject populateConfigJSONObject(
		String portletName, String editorConfigKey, String editorName,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		JSONObject configJSONObject = JSONFactoryUtil.createJSONObject();

		List<EditorConfigContributor> editorConfigContributors =
			getContributors(portletName, editorConfigKey, editorName);

		Iterator<EditorConfigContributor> iterator = ListUtil.reverseIterator(
			editorConfigContributors);

		while (iterator.hasNext()) {
			EditorConfigContributor editorConfigContributor = iterator.next();

			editorConfigContributor.populateConfigJSONObject(
				configJSONObject, inputEditorTaglibAttributes, themeDisplay,
				liferayPortletResponse);
		}

		return configJSONObject;
	}

	private static final ServiceReferenceMapper<String, EditorConfigContributor>
		_editorConfigContributorServiceReferenceMapper =
			new BaseEditorConfigurationFactoryImpl.
				EditorServiceReferenceMapper();
	private static final ServiceTrackerMap
		<String, List<EditorConfigContributor>>
		_editorConfigContributorServiceTrackerMap =
			ServiceTrackerCollections.multiValueMap(
				EditorConfigContributor.class,
				"(|(editor.config.key=*)(editor.name=*)(javax.portlet.name=*))",
				_editorConfigContributorServiceReferenceMapper);
	private static final ServiceTrackerMap<String, EditorConfigTransformer>
		_editorConfigTransformerServiceTrackerMap =
			ServiceTrackerCollections.singleValueMap(
				EditorConfigTransformer.class, "editor.name");

	static {
		_editorConfigContributorServiceTrackerMap.open();

		_editorConfigTransformerServiceTrackerMap.open();
	}

}