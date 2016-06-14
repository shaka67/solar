/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.sms.server.mandao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.sms.api.SmsMessage;
import com.ouer.solar.sms.api.SmsResponse;
import com.ouer.solar.sms.api.SmsStatus;
import com.ouer.solar.sms.api.ThirdSmsSystemEnum;
import com.ouer.solar.sms.server.LimitedSmsService;
import com.ouer.solar.sms.thirdparty.mandao.MandaoConfigLocator;
import com.ouer.solar.sms.thirdparty.mandao.MandaoMessageTemplate;
import com.ouer.solar.sms.thirdparty.mandao.MandaoSmsConfig;
import com.ouer.solar.spring.SpringContextUtil;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class MandaoSmsService extends LimitedSmsService {

	private static final Logger LOG = LoggerFactory.getLogger(MandaoSmsService.class);

	private MandaoConfigLocator locator;

	@Override
	public void initialize() {
		this.locator = SpringContextUtil.getBean(MandaoConfigLocator.class);
	}

	@Override
	public boolean isInitialized() {
		return locator != null;
	}

	@Override
	public SmsResponse<String> send(SmsMessage message) {
		final SmsResponse<String> result = new SmsResponse<String>("短信已发送",ThirdSmsSystemEnum.MANDAO.getCode());
		LOG.info("get mandao's config");
		MandaoSmsConfig config;
		try {
			config = locator.getConfig(message.getAppId());
		} catch (final IOException e) {
			LOG.error("获取配置信息失败", e);
			result.setStatus(SmsStatus.FAILURE);
			result.setData("获取配置信息失败");
			return result;
		}

		String sendStr ;
		try {
			sendStr = MandaoMessageTemplate.formatSendMsgTemp(config, message.getMobile(),
					message.getContent(), String.valueOf(message.getChannel()));
			final URLConnection con = getConnection(config.getServiceURL(), config.getStandbyServiceURL());
			if (LOG.isInfoEnabled()) {
				LOG.info("send content:" + sendStr);
			}
			if (con != null) {
				sendMsg((HttpURLConnection)con, sendStr, config.getSoapAction());
				final String thirdBatchId = getReturnMsg((HttpURLConnection) con);

				result.setThirdBatchId(thirdBatchId);
				result.setThird(ThirdSmsSystemEnum.MANDAO.getCode());
			}else{
				throw new IOException("连接服务器失败!");
			}
		} catch (final UnsupportedEncodingException e) {
			LOG.error("对发送内容编码异常",e.getCause());
			result.setStatus(SmsStatus.FAILURE);
			result.setData("对发送内容编码异常");
		} catch (final IOException e) {
			LOG.error("IO异常",e.getCause());
			result.setStatus(SmsStatus.FAILURE);
			result.setData("IO异常");
		}
		return result;
	}

	private void sendMsg(HttpURLConnection httpconn,String xml,String soapAction) throws UnsupportedEncodingException, IOException {
		LOG.info("send content to the third system!");
		final ByteArrayOutputStream bout = new ByteArrayOutputStream();
		bout.write(xml.getBytes("GBK"));
		final byte[] b = bout.toByteArray();
		httpconn.setRequestProperty("Content-Length",
				String.valueOf(b.length));
		httpconn.setRequestProperty("Content-Type",
				"text/xml; charset=gb2312");
		httpconn.setRequestProperty("SOAPAction", soapAction);
		httpconn.setRequestMethod("POST");
		httpconn.setDoInput(true);
		httpconn.setDoOutput(true);

		final OutputStream out = httpconn.getOutputStream();
		out.write(b);
		out.close();
	}

	private String getReturnMsg(HttpURLConnection httpconn) throws IOException{
		LOG.info("receive returnCode from the third system!");
		final InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
		final BufferedReader in = new BufferedReader(isr);
		String thirdBatchId = "";
		String inputLine;
		while (null != (inputLine = in.readLine())) {
			final Pattern pattern = Pattern
					.compile("<mdSmsSend_uResult>(.*)</mdSmsSend_uResult>");
			final Matcher matcher = pattern.matcher(inputLine);
			while (matcher.find()) {
				thirdBatchId = matcher.group(1);
			}
		}
		return thirdBatchId;
	}

	private URLConnection getConnection(String serviceURL,String standbyserviceurl) throws IOException{
		URLConnection connection = null;
		connection = new URL(serviceURL).openConnection();
		if(connection == null){
			connection = new URL(standbyserviceurl).openConnection();
		}
		return connection;
	}

}
