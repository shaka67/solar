DROP TABLE IF EXISTS `sms_send_record`;
CREATE TABLE `sms_send_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_id` int(3) DEFAULT NULL,
  `content` mediumtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `third_batch_id` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `third_result` mediumtext COLLATE utf8mb4_unicode_ci,
  `third_id` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `sms_mobile_map`;
CREATE TABLE `sms_mobile_map` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '号码',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送状态',
  `sms_send_record_id` bigint(20) DEFAULT NULL COMMENT '外键',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mobile_record_idx` (`sms_send_record_id`) USING BTREE,
  CONSTRAINT `sms_mobile_map_ibfk_1` FOREIGN KEY (`sms_send_record_id`) REFERENCES `sms_send_record` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=641 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信批次与号码对应表';
