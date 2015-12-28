-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- Hoszt: localhost
-- Létrehozás ideje: 2013. okt. 16. 17:48
-- Szerver verzió: 5.5.20
-- PHP verzió: 5.3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Adatbázis: `conf`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet: `config`
--

CREATE TABLE IF NOT EXISTS `config` (
  `confkey` varchar(256) COLLATE utf8_hungarian_ci NOT NULL,
  `value` varchar(256) COLLATE utf8_hungarian_ci NOT NULL,
  KEY `key` (`confkey`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- A tábla adatainak kiíratása `config`
--

INSERT INTO `config` (`confkey`, `value`) VALUES
('auth.scope', 'SUPERM');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
