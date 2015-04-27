<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.DuplicatePasswordPolicyException" %><%@
page import="com.liferay.portal.PasswordPolicyNameException" %><%@
page import="com.liferay.portal.security.ldap.LDAPSettingsUtil" %><%@
page import="com.liferay.portal.service.permission.PasswordPolicyPermissionUtil" %><%@
page import="com.liferay.password.policies.admin.web.search.OrganizationPasswordPolicyChecker" %><%@
page import="com.liferay.password.policies.admin.web.search.PasswordPolicyDisplayTerms" %><%@
page import="com.liferay.password.policies.admin.web.search.PasswordPolicySearch" %><%@
page import="com.liferay.password.policies.admin.web.search.UserPasswordPolicyChecker" %>

<%@ include file="/init-ext.jsp" %>