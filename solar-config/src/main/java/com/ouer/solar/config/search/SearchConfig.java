/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.search;

import java.util.Map;

import com.google.common.collect.Maps;
import com.ouer.solar.StringPool;
import com.ouer.solar.StringUtil;
import com.ouer.solar.config.DataSourceConfig;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class SearchConfig extends DataSourceConfig {
	// es
	private String clusterNodes;
	private String clusterName;
	private boolean clientTransportSniff;
	private boolean clientTransportIgnoreClusterName;
	private String clientTransportPingTimeout;
	private String clientTransportNodesSamplerInterval;
	// index source
	private String dalConfigFile;
	// definition
	private String indexDefinitionFile;
	private String resultDefinitionFile;
	private String searchDefinitionFile;
	// ibs
	private String jobCronExpression;
	// term filter
	private String termFilters;

	public String getClusterNodes() {
		return clusterNodes;
	}

	public void setClusterNodes(String clusterNodes) {
		this.clusterNodes = clusterNodes;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public boolean isClientTransportSniff() {
		return clientTransportSniff;
	}

	public void setClientTransportSniff(boolean clientTransportSniff) {
		this.clientTransportSniff = clientTransportSniff;
	}

	public boolean isClientTransportIgnoreClusterName() {
		return clientTransportIgnoreClusterName;
	}

	public void setClientTransportIgnoreClusterName(
			boolean clientTransportIgnoreClusterName) {
		this.clientTransportIgnoreClusterName = clientTransportIgnoreClusterName;
	}

	public String getClientTransportPingTimeout() {
		return clientTransportPingTimeout;
	}

	public void setClientTransportPingTimeout(String clientTransportPingTimeout) {
		this.clientTransportPingTimeout = clientTransportPingTimeout;
	}

	public String getClientTransportNodesSamplerInterval() {
		return clientTransportNodesSamplerInterval;
	}

	public void setClientTransportNodesSamplerInterval(
			String clientTransportNodesSamplerInterval) {
		this.clientTransportNodesSamplerInterval = clientTransportNodesSamplerInterval;
	}

	public String getDalConfigFile() {
		return dalConfigFile;
	}

	public void setDalConfigFile(String dalConfigFile) {
		this.dalConfigFile = dalConfigFile;
	}

	public String getIndexDefinitionFile() {
		return indexDefinitionFile;
	}

	public void setIndexDefinitionFile(String indexDefinitionFile) {
		this.indexDefinitionFile = indexDefinitionFile;
	}

	public String getResultDefinitionFile() {
		return resultDefinitionFile;
	}

	public void setResultDefinitionFile(String resultDefinitionFile) {
		this.resultDefinitionFile = resultDefinitionFile;
	}

	public String getSearchDefinitionFile() {
		return searchDefinitionFile;
	}

	public void setSearchDefinitionFile(String searchDefinitionFile) {
		this.searchDefinitionFile = searchDefinitionFile;
	}

	public String getJobCronExpression() {
		return jobCronExpression;
	}

	public void setJobCronExpression(String jobCronExpression) {
		this.jobCronExpression = jobCronExpression;
	}

	public String getTermFilters() {
		return termFilters;
	}

	public void setTermFilters(String termFilters) {
		this.termFilters = termFilters;
	}

	public Map<String, Object> parseTermFilters() {
		final Map<String, Object> result = Maps.newHashMap();
		final String[] filters = StringUtil.split(termFilters, StringPool.Symbol.SEMICOLON);
		String key;
		String value;
		for (final String filter : filters) {
			key = StringUtil.split(filter, StringPool.Symbol.EQUALS)[0];
			value = StringUtil.split(filter, StringPool.Symbol.EQUALS)[1];
			result.put(key, value);
		}
		return result;
	}

	@Override
	public String toString() {
		return "SearchConfig [appId=" + appId + ", clusterNodes="
				+ clusterNodes + ", clusterName=" + clusterName
				+ ", clientTransportSniff=" + clientTransportSniff
				+ ", clientTransportIgnoreClusterName="
				+ clientTransportIgnoreClusterName
				+ ", clientTransportPingTimeout=" + clientTransportPingTimeout
				+ ", clientTransportNodesSamplerInterval="
				+ clientTransportNodesSamplerInterval + ", dalConfigFile="
				+ dalConfigFile + ", driver=" + driver + ", url=" + url
				+ ", username=" + username + ", password=" + password
				+ ", datasourceClass=" + datasourceClass + ", datasourceProps="
				+ datasourceProps + ", indexDefinitionFile="
				+ indexDefinitionFile + ", resultDefinitionFile="
				+ resultDefinitionFile + ", searchDefinitionFile="
				+ searchDefinitionFile + ", jobCronExpression="
				+ jobCronExpression + ", termFilters="
				+ termFilters + "]";
	}

}
