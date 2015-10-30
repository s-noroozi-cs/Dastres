-- $$$$$$$$$$$$$$$$ database: dastres_slave $$$$$$$$$$$$$$$$$
ALTER TABLE `loc_info` ADD `fld_more_info` VARCHAR( 500 ) NULL ;
-- $$$$$$$$$$$$$$$$ database: dastres_slave $$$$$$$$$$$$$$$$$


-- ################ database: dastres_master ################
CREATE TABLE `user_loc` (
`fld_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
`fld_name` VARCHAR( 100 ) NOT NULL ,
`fld_longtitude` VARCHAR( 30 ) NOT NULL ,
`fld_latitude` VARCHAR( 30 ) NOT NULL ,
`fld_tags` VARCHAR( 300 ) NULL ,
`fld_more_info` VARCHAR( 500 ) NULL ,
`fld_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
`fld_accept` VARCHAR( 1 ) NOT NULL DEFAULT 'N'
) ENGINE = MYISAM ;
-- ################ database: dastres_master ################