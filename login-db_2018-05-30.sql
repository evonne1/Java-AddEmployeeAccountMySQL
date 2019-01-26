-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 30, 2018 at 06:16 AM
-- Server version: 5.5.34
-- PHP Version: 5.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `login`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblbank`
--

CREATE TABLE `tblbank` (
  `BankAcID` int(5) NOT NULL AUTO_INCREMENT,
  `Bank` varchar(20) DEFAULT NULL,
  `BSB` int(20) DEFAULT NULL,
  `Account` int(20) DEFAULT NULL,
  `empID` int(3) DEFAULT NULL,
  PRIMARY KEY (`BankAcID`),
  KEY `tblBank_fk` (`empID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `tblbank`
--

INSERT INTO `tblbank` (`BankAcID`, `Bank`, `BSB`, `Account`, `empID`) VALUES
(7, 'c ', 324, 24324, 4),
(8, 'c ', 3534, 12345, 1),
(9, 'd ', 5656, 1231, 5),
(10, 'e ', 150, 1500, 6);

-- --------------------------------------------------------

--
-- Table structure for table `tblemployee`
--

CREATE TABLE `tblemployee` (
  `empID` int(3) NOT NULL,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`empID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tblemployee`
--

INSERT INTO `tblemployee` (`empID`, `firstName`, `lastName`) VALUES
(2, ' Jane ', ' Benner'),
(3, ' Jan ', ' BEn'),
(4, ' Jason ', ' Bourne'),
(1, ' Peter ', ' Pan'),
(5, ' Ben ', ' Chris'),
(6, ' Dan ', ' Ellen');

-- --------------------------------------------------------

--
-- Table structure for table `tblusers`
--

CREATE TABLE `tblusers` (
  `userName` varchar(10) NOT NULL DEFAULT '',
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  `txtPassword` varchar(10) DEFAULT NULL,
  `AccessLevel` int(11) DEFAULT NULL,
  `Enabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tblusers`
--

INSERT INTO `tblusers` (`userName`, `firstName`, `lastName`, `txtPassword`, `AccessLevel`, `Enabled`) VALUES
('admin', 'Manager', 'Admin', 'tafe123', 2, 1),
('jimt', 'Jim', 'Tresintis', 'tafe123', 1, 1),
('fredr', 'Fred', 'Roberts', 'tafe123', 1, 1),
('sams', 'Sandra', 'Smith', 'tafe123', 1, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
