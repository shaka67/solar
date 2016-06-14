/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.sms.thirdparty.mandao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author: <a href="horus@ixiaopu.com">horus</a>
 */

public class MandaoMessageTemplate {

	private  static final String SENDMESSAGETEMPLATE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>"
					+ "<mdSmsSend_u xmlns=\"http://tempuri.org/\">"
						+ "<sn>%1$s</sn>"
						+ "<pwd>%2$s</pwd>"
						+ "<mobile>%3$s</mobile>"
						+ "<content>%4$s</content>"
						+ "<ext>%5$s</ext>"
						+ "<stime>%6$s</stime>"
						+ "<rrid>%7$s</rrid>"
					+ "</mdSmsSend_u>"
				+ "</soap:Body>"
			+ "</soap:Envelope>";
	
	public static String formatSendMsgTemp(MandaoSmsConfig msmo,String mobile,String content,String channel) throws UnsupportedEncodingException{
		return String.format(SENDMESSAGETEMPLATE, msmo.getSn(),getMD5(msmo.getSn()+msmo.getPassword()),mobile,new String((content+msmo.getSign(channel)).getBytes("utf-8")),msmo.getExt(channel),"","");
	}

	private static String getMD5(String sourceStr) throws UnsupportedEncodingException {
		String resultStr = "";
		try {
			final byte[] temp = sourceStr.getBytes();
			final MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(temp);
			final byte[] b = md5.digest();
			for (int i = 0; i < b.length; i++) {
				final char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
						'9', 'A', 'B', 'C', 'D', 'E', 'F' };
				final char[] ob = new char[2];
				ob[0] = digit[(b[i] >>> 4) & 0X0F];
				ob[1] = digit[b[i] & 0X0F];
				resultStr += new String(ob);
			}
			return resultStr;
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
