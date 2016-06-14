DROP TABLE IF EXISTS `dynamic_sms`;
CREATE TABLE `dynamic_sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `thirdparty` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `config_options` varchar(1000) CHARACTER SET utf8mb4 NOT NULL COMMENT '配置列属性',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_thirdparty` (`thirdparty`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `dynamic_sms_mandao`;
CREATE TABLE `dynamic_sms_mandao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接入的app',
  `sn` varchar(256) CHARACTER SET utf8mb4 DEFAULT NULL,
  `pwd` varchar(256) CHARACTER SET utf8mb4 DEFAULT NULL,
  `special_service_number` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '特服号',
  `mainserviceurl` varchar(256) CHARACTER SET utf8mb4 DEFAULT NULL,
  `standbyserviceurl` varchar(256) CHARACTER SET utf8mb4 DEFAULT NULL,
  `soap_action` varchar(256) CHARACTER SET utf8mb4 DEFAULT NULL,
  `whitelist` varchar(1000) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ext_config` varchar(1000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '渠道扩展码关系',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app_id` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



DROP TABLE IF EXISTS `rabbitmq_config`;
CREATE TABLE `rabbitmq_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接入的app',
  `host` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `port` int(11) unsigned DEFAULT '5672',
  `virtual_host` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `requested_heartbeat` int(11) unsigned DEFAULT '10',
  `connection_timeout` int(11) unsigned DEFAULT '60000',
  `prefetch_count` int(11) unsigned DEFAULT '1',
  `concurrent_consumers` int(11) unsigned DEFAULT '1',
  `receive_timeout` int(11) unsigned DEFAULT '1000',
  `recovery_interval` int(11) unsigned DEFAULT '5000',
  `startup_timeout` int(11) unsigned DEFAULT '60000',
  `shutdown_timeout` int(11) unsigned DEFAULT '5000',
  `default_requeue_rejected` bit(1) NOT NULL DEFAULT b'1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app_id` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `jobserver_config`;
CREATE TABLE `jobserver_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接入的app',
  `instance_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `db_url` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `db_user` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `db_password` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app_id` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `mail_config`;
CREATE TABLE `mail_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接入的app',
  `protocol` varchar(8) CHARACTER SET utf8mb4 NOT NULL COMMENT '邮件发送协议',
  `smtp_host` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '邮件发送协议',
  `smtp_port` int(11) NOT NULL COMMENT '邮件发送端口',
  `smtp_props` varchar(1000) CHARACTER SET utf8mb4 NOT NULL COMMENT '邮件发送属性',
  `username` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `personal` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app` (`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `mogilefs_config`;
CREATE TABLE `mogilefs_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接入的app',
  `trackers` varchar(256) CHARACTER SET utf8mb4 NOT NULL COMMENT 'trackers地址',
  `reconnect_timeout` int(11) NOT NULL,
  `max_active` int(11) NOT NULL,
  `max_idle` int(11) NOT NULL,
  `domain` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `http_prefix` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app` (`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `search_config`;
CREATE TABLE `search_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接入的app',
  `cluster_nodes` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cluster_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_transport_sniff` bit(1) NOT NULL DEFAULT b'1',
  `client_transport_ignore_name` bit(1) NOT NULL DEFAULT b'0',
  `client_transport_ping_timeout` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_transport_nodes_interval` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dal_config_file` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `driver` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `url` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `datasource_class` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `datasource_props` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `index_definition_file` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `result_definition_file` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `search_definition_file` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `job_cron_expression` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `term_filters` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app` (`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `sms_config`;
CREATE TABLE `sms_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接入的app',
  `driver` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `url` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `datasource_class` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `datasource_props` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `sms_service_objects` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app` (`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `image_config`;
CREATE TABLE `image_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接入的app',
  `thirdparty` varchar(16) CHARACTER SET utf8mb4 NOT NULL COMMENT '图片第三方应用',
  `access_key` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `secret_key` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `namespace` varchar(16) CHARACTER SET utf8mb4 NOT NULL COMMENT '图片存储空间',
  `store_config` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '额外配置',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app` (`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `push_config`;
CREATE TABLE `push_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接入的app',
  `thirdparty` varchar(16) CHARACTER SET utf8mb4 NOT NULL COMMENT '消息推送第三方应用',
  `package_name` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `ios_secret` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `android_secret` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `ios_env` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `android_env` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `notify_type` int(11) NOT NULL,
  `pass_through` int(11) NOT NULL,
  `retries` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app` (`app_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
