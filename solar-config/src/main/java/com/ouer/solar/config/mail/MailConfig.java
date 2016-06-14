/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.config.mail;

import com.ouer.solar.config.BaseConfig;


/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MailConfig extends BaseConfig {

	private String protocol;
	private String smtpHost;
	private int smtpPort;
	private String smtpProps;
	private String username;
	private String password;
	private String personal;

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpProps() {
//		final Properties props = new Properties();
//		final String[] pairs = StringUtil.split(smtpProps, StringPool.Symbol.COMMA);
//		String key;
//		String value;
//		for (final String pair : pairs) {
//			key = StringUtil.split(pair, StringPool.Symbol.EQUALS)[0];
//			value = StringUtil.split(pair, StringPool.Symbol.EQUALS)[1];
//			props.put(key, value);
//		}
//		return props;
		return smtpProps;
	}

	public void setSmtpProps(String smtpProps) {
		this.smtpProps = smtpProps;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	@Override
	public String toString() {
		return "MailConfig [appId=" + appId + ", protocol=" + protocol
				+ ", smtpHost=" + smtpHost + ", smtpPort=" + smtpPort
				+ ", smtpProps=" + smtpProps + ", username=" + username
				+ ", password=" + password + ", personal=" + personal + "]";
	}

}
