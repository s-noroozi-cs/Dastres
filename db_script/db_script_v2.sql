-- $$$$$$$$$$$$$$$$ database: dastres_slave $$$$$$$$$$$$$$$$$
ALTER TABLE `loc_info` ADD `fld_trns_ticket` VARCHAR( 100 ) NULL ,ADD INDEX ( `fld_trns_ticket` ) ;

CREATE TABLE `trust_host` (
`fld_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
`fld_address` VARCHAR( 100 ) NOT NULL ,
UNIQUE (
`fld_address`
)
) ENGINE = InnoDB;
-- $$$$$$$$$$$$$$$$ database: dastres_slave $$$$$$$$$$$$$$$$$

-- ################ database: dastres_master ################
CREATE TABLE `search_log` (
`fld_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
`fld_keyword` VARCHAR( 150 ) NOT NULL ,
`fld_region` VARCHAR( 300 ) NOT NULL ,
`fld_user_agent` VARCHAR( 200 ) NOT NULL ,
`fld_wait_time` INT NOT NULL ,
`fld_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
INDEX ( `fld_keyword` , `fld_wait_time`, `fld_date`)
) ENGINE = InnoDB;

-- ################ database: dastres_master ################