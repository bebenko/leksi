delimiter $$

CREATE TABLE IF NOT EXISTS `dict_portugal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `meaningsfmt` varchar(5000) DEFAULT NULL,
  `idiomsfmt` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `word_idx` (`word`)
) ENGINE=InnoDB AUTO_INCREMENT=5181 DEFAULT CHARSET=utf8$$


CREATE TABLE IF NOT EXISTS `dict_slovak` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `meaningsfmt` varchar(5000) DEFAULT NULL,
  `idiomsfmt` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `word_idx` (`word`)
) ENGINE=InnoDB AUTO_INCREMENT=5181 DEFAULT CHARSET=utf8$$
