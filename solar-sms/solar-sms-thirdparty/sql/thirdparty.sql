DROP TABLE IF EXISTS `mandao_app_ssn`;
CREATE TABLE `mandao_app_ssn` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` int(3) DEFAULT NULL,
  `special_service_number` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ÌØ·þºÅ',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_app_id` (`app_id`),
  UNIQUE KEY `idx_ssn` (`special_service_number`)
) ENGINE=InnoDB AUTO_INCREMENT=90007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;