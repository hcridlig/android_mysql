-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 31 mars 2022 à 16:57
-- Version du serveur :  5.7.31
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `android`
--

-- --------------------------------------------------------

--
-- Structure de la table `association`
--

DROP TABLE IF EXISTS `association`;
CREATE TABLE IF NOT EXISTS `association` (
  `pseudo` varchar(30) COLLATE latin1_bin NOT NULL,
  `password` varchar(30) COLLATE latin1_bin NOT NULL,
  `association` varchar(15) COLLATE latin1_bin NOT NULL,
  `semaine` varchar(60) COLLATE latin1_bin NOT NULL,
  `niveau` varchar(18) COLLATE latin1_bin NOT NULL,
  `commentaire` varchar(80) COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`pseudo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Déchargement des données de la table `association`
--

INSERT INTO `association` (`pseudo`, `password`, `association`, `semaine`, `niveau`, `commentaire`) VALUES
('1', '6', 'Ping Pong', '[Mercredi]', 'Intermédiare', 'commentaire'),
('t', '5', 'Ping Pong', '[Mercredi]', 'Intermédiare', 'commentaire');

-- --------------------------------------------------------

--
-- Structure de la table `spinner`
--

DROP TABLE IF EXISTS `spinner`;
CREATE TABLE IF NOT EXISTS `spinner` (
  `id` int(11) NOT NULL,
  `asso` varchar(15) COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Déchargement des données de la table `spinner`
--

INSERT INTO `spinner` (`id`, `asso`) VALUES
(1, 'Ping Pong'),
(2, 'Foot'),
(3, 'Basket'),
(4, 'Boxe');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
