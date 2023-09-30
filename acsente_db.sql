CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` char(15) DEFAULT NULL,
  `password` char(30) DEFAULT NULL,
  `phone` char(15) NOT NULL,
  `shop_name` char(30) DEFAULT NULL,
  `email` char(60) DEFAULT NULL,
  `is_foreign` tinyint(1) NOT NULL,
  `is_seller` tinyint(1) NOT NULL,
  `address` char(60) DEFAULT NULL,
  `total_products` int DEFAULT NULL,
  `total_clients` int DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` char(60) NOT NULL,
  `category_tag` char(15) DEFAULT NULL,
  `type_tag` char(15) DEFAULT NULL,
  `color` char(15) NOT NULL,
  `size` char(10) NOT NULL,
  `composition` char(30) DEFAULT NULL,
  `country_of_origin` char(30) DEFAULT NULL,
  `is_per_piece` tinyint(1) DEFAULT NULL,
  `thickness` enum('두꺼움','보통','얇음') DEFAULT NULL,
  `transparency` enum('있음','보통','없음') DEFAULT NULL,
  `elasticity` enum('좋음','보통','없음') DEFAULT NULL,
  `lining` enum('있음','없음','기모안감') DEFAULT NULL,
  `laundry_info` char(30) DEFAULT NULL,
  `additional_info` text,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `order_date` date NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `carts` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `carts_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `wishlists` (
  `wishlist_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`wishlist_id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `wishlists_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `wishlists_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
