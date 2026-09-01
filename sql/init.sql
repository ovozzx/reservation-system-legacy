-- =============================================
-- reservation-system-legacy 초기 설정
-- DB: MySQL 8 / Schema: cafe_reservation
-- =============================================

CREATE DATABASE IF NOT EXISTS cafe_reservation;
USE cafe_reservation;

-- =============================================
-- 테이블 생성
-- =============================================

CREATE TABLE `users` (
  `user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `salt` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_used` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_users_email` (`email`),
  CONSTRAINT `ck_users_role` CHECK (`role` IN ('USER','ADMIN'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `menu` (
  `menu_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` int NOT NULL,
  `is_available` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_used` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y',
  `category_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `orders` (
  `user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_used` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y',
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `AMOUNT` int DEFAULT '0',
  PRIMARY KEY (`order_id`),
  KEY `fk_orders_user` (`user_id`),
  CONSTRAINT `fk_orders_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `ck_orders_status` CHECK (`status` IN ('ORDERED','MAKING','DONE','CANCELED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `order_item` (
  `order_id` bigint DEFAULT NULL,
  `menu_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_used` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y',
  `order_item_id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`order_item_id`),
  KEY `fk_order_item_menu` (`menu_id`),
  KEY `fk_order_item_order` (`order_id`),
  CONSTRAINT `fk_order_item_menu` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`),
  CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `seat` (
  `seat_id` bigint NOT NULL AUTO_INCREMENT,
  `seat_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_used` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y',
  `SEAT_ROW` int DEFAULT NULL,
  `SEAT_COL` int DEFAULT NULL,
  `STATUS` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'AVAILABLE',
  PRIMARY KEY (`seat_id`),
  UNIQUE KEY `uk_seat_number` (`seat_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `payment` (
  `payment_id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `imp_uid` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `merchant_uid` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` int NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'READY',
  `pay_method` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_used` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`payment_id`),
  UNIQUE KEY `uk_payment_merchant_uid` (`merchant_uid`),
  UNIQUE KEY `uk_payment_imp_uid` (`imp_uid`),
  KEY `fk_payment_order` (`order_id`),
  CONSTRAINT `fk_payment_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `ck_payment_status` CHECK (`status` IN ('READY','PAID','FAILED','CANCELLED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `reservation` (
  `reservation_id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `seat_id` bigint NOT NULL,
  `reserve_date` date NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_used` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`reservation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- 초기 데이터: 사용자 (포장 주문용 GUEST 계정)
-- =============================================

INSERT INTO users (user_id, password, salt, role) VALUES ('GUEST', 'GUEST', 'GUEST', 'USER');

-- =============================================
-- 초기 데이터: 메뉴
-- =============================================

INSERT INTO menu (menu_id, name, price, category_id, image_path, description) VALUES ('MENU_1', '아메리카노', 100, '1', 'https://slamburger.co.kr/cdn/shop/files/HOT2.png?v=1747235759&width=750', '진한 에스프레소 커피');
INSERT INTO menu (menu_id, name, price, category_id, image_path, description) VALUES ('MENU_2', '카페라떼', 5000, '1', 'https://slamburger.co.kr/cdn/shop/files/HOT2.png?v=1747235759&width=750', '진한 에스프레소 커피');
INSERT INTO menu (menu_id, name, price, category_id, image_path, description) VALUES ('MENU_3', '카푸치노', 5500, '1', 'https://slamburger.co.kr/cdn/shop/files/HOT2.png?v=1747235759&width=750', '진한 에스프레소 커피');
INSERT INTO menu (menu_id, name, price, category_id, image_path, description) VALUES ('MENU_4', '치즈케이크', 6500, '1', 'https://slamburger.co.kr/cdn/shop/files/HOT2.png?v=1747235759&width=750', '진한 에스프레소 커피');

-- =============================================
-- 초기 데이터: 좌석
-- =============================================

INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('L1', 1, 1);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('L2', 1, 2);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('L3', 1, 3);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('L4', 1, 4);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('R1', 2, 1);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('R2', 2, 2);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('R3', 2, 3);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('R4', 2, 4);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('W1', 3, 1);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('W2', 3, 2);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('W3', 3, 3);
INSERT INTO seat (seat_number, SEAT_ROW, SEAT_COL) VALUES ('W4', 3, 4);
