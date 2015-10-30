-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 26, 2015 at 03:23 PM
-- Server version: 5.6.12
-- PHP Version: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `dastres_master`
--
DROP DATABASE `dastres_master`;
CREATE DATABASE `dastres_master` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `dastres_master`;

-- --------------------------------------------------------

--
-- Table structure for table `cfg_sys`
--
-- Creation: Jun 16, 2015 at 11:54 PM
-- Last update: Jun 19, 2015 at 10:15 PM
--

DROP TABLE IF EXISTS `cfg_sys`;
CREATE TABLE IF NOT EXISTS `cfg_sys` (
  `fld_id` int(11) NOT NULL AUTO_INCREMENT,
  `fld_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `fld_value` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`fld_id`),
  UNIQUE KEY `fld_name` (`fld_name`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `slvaves`
--
-- Creation: Jun 26, 2015 at 05:42 PM
--

DROP TABLE IF EXISTS `slvaves`;
CREATE TABLE IF NOT EXISTS `slvaves` (
  `fld_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fld_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `fld_address` varchar(100) COLLATE utf8_bin NOT NULL,
  `fld_disable` varchar(1) COLLATE utf8_bin NOT NULL,
  `fld_last_ping_time` int(10) unsigned NOT NULL DEFAULT '0',
  `fld_min_ping_time` int(10) unsigned NOT NULL DEFAULT '0',
  `fld_max_ping_time` int(10) unsigned NOT NULL DEFAULT '0',
  `fld_sum_ping_time` bigint(20) unsigned NOT NULL DEFAULT '0',
  `fld_count_ping` int(10) unsigned NOT NULL DEFAULT '0',
  `fld_rec_size` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`fld_id`),
  UNIQUE KEY `fld_name` (`fld_name`,`fld_address`),
  UNIQUE KEY `fld_name_2` (`fld_name`),
  UNIQUE KEY `fld_address` (`fld_address`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=44 ;
--
-- Database: `dastres_slave`
--
DROP DATABASE `dastres_slave`;
CREATE DATABASE `dastres_slave` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `dastres_slave`;

-- --------------------------------------------------------

--
-- Table structure for table `loc_info`
--
-- Creation: Jun 12, 2015 at 03:30 PM
-- Last update: Jun 26, 2015 at 06:33 PM
-- Last check: Jun 12, 2015 at 03:30 PM
--

DROP TABLE IF EXISTS `loc_info`;
CREATE TABLE IF NOT EXISTS `loc_info` (
  `fld_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fld_point` point NOT NULL,
  `fld_name` varchar(200) COLLATE utf8_bin NOT NULL,
  `fld_insertion_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`fld_id`),
  KEY `fld_name` (`fld_name`),
  SPATIAL KEY `fld_point` (`fld_point`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1307 ;

-- --------------------------------------------------------

--
-- Table structure for table `loc_tag`
--
-- Creation: Jun 12, 2015 at 03:40 PM
-- Last update: Jun 26, 2015 at 06:33 PM
--

DROP TABLE IF EXISTS `loc_tag`;
CREATE TABLE IF NOT EXISTS `loc_tag` (
  `fld_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fld_loc_id` int(10) unsigned NOT NULL,
  `fld_tag` varchar(200) COLLATE utf8_bin NOT NULL,
  `fld_insertion_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`fld_id`),
  KEY `fld_location_id` (`fld_loc_id`,`fld_tag`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=209 ;
