/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.sms.model;

import java.io.Serializable;

/**
 *
 * @author: <a href="horus@ixiaopu.com">horus</a>
 */

public class SmsMobileMap implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2635727303601159184L;
		
		private Long id;
		//手机号码列表，以逗号分隔
		private String mobile;
		//发送状态（1 发送成功，2：发送失败，3：发送中）
		private String status;
		
		private Long smsSendRecordId;
		
		private Long idRaw;

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Long getSmsSendRecordId() {
			return smsSendRecordId;
		}

		public void setSmsSendRecordId(Long smsSendRecordId) {
			this.smsSendRecordId = smsSendRecordId;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getIdRaw() {
			return idRaw;
		}

		public void setIdRaw(Long idRaw) {
			this.idRaw = idRaw;
		}
		
		
}
