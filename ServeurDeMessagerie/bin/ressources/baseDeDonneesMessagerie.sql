-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Jeu 08 Décembre 2016 à 12:14
-- Version du serveur :  5.7.9
-- Version de PHP :  5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `messagerie`
--
CREATE DATABASE IF NOT EXISTS `messagerie` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE `messagerie`;

-- --------------------------------------------------------

--
-- Structure de la table `groupe`
--

DROP TABLE IF EXISTS `groupe`;
CREATE TABLE IF NOT EXISTS `groupe` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `nomGroupe` varchar(30) NOT NULL,
  `avatar` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `groupe`
--

INSERT INTO `groupe` (`id`, `nomGroupe`, `avatar`) VALUES
(1, 'test', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `groupeuti`
--

DROP TABLE IF EXISTS `groupeuti`;
CREATE TABLE IF NOT EXISTS `groupeuti` (
  `idGroup` int(30) NOT NULL,
  `pseudoUtilisateur` varchar(30) NOT NULL,
  KEY `pseudoUtilisateur` (`pseudoUtilisateur`),
  KEY `idGroup` (`idGroup`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `groupeuti`
--

INSERT INTO `groupeuti` (`idGroup`, `pseudoUtilisateur`) VALUES
(1, 'bob38');

-- --------------------------------------------------------

--
-- Structure de la table `historique`
--

DROP TABLE IF EXISTS `historique`;
CREATE TABLE IF NOT EXISTS `historique` (
  `idMessage` int(30) NOT NULL,
  `pseudoUtilisateur` varchar(30) NOT NULL,
  `pieceJointe` varchar(30) NOT NULL,
  KEY `idMessage` (`idMessage`),
  KEY `pseudoUtilisateur` (`pseudoUtilisateur`),
  KEY `pieceJointe` (`pieceJointe`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `pseudo_utilisateur` varchar(30) NOT NULL,
  `pseudo_destinataire` varchar(11) NOT NULL,
  `date_envoi` date NOT NULL,
  `texte` text NOT NULL,
  `piece_jointe` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pseudo_utilisateur` (`pseudo_utilisateur`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `message`
--

INSERT INTO `message` (`id`, `pseudo_utilisateur`, `pseudo_destinataire`, `date_envoi`, `texte`, `piece_jointe`) VALUES
(1, 'bob38', 'f71dbe52628a3f83a77ab494817525c6', '2016-10-21', 'Coucou Toto38!', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `relation`
--

DROP TABLE IF EXISTS `relation`;
CREATE TABLE IF NOT EXISTS `relation` (
  `idUti1` varchar(30) NOT NULL,
  `idUti2` varchar(30) NOT NULL,
  `sontAmis` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `relation`
--

INSERT INTO `relation` (`idUti1`, `idUti2`, `sontAmis`) VALUES
('bob38', 'toto38', 1);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `pseudo` varchar(30) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `dateNaissance` date DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `mdp` varchar(50) NOT NULL,
  `dateInscription` date DEFAULT NULL,
  PRIMARY KEY (`pseudo`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`pseudo`, `nom`, `prenom`, `email`, `dateNaissance`, `avatar`, `mdp`, `dateInscription`) VALUES
('bob38', 'bob', 'bob', 'bob@yahoo.fr', NULL, NULL, '9f9d51bc70ef21ca5c14f3798a29d8', NULL),
('toto38', 'toto', 'toto', 'toto@gmail.com', '2016-10-12', NULL, 'f71dbe52628a3f83a77ab494817525c6', '2016-10-21');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


--
-- Utilisateur 'Client'
--

GRANT USAGE ON *.* TO 'client'@'localhost' IDENTIFIED BY PASSWORD '*1D47060D604E606DC61C1F7E1AA3FB502748C46F';

GRANT SELECT, INSERT, UPDATE, DELETE ON `messagerie`.* TO 'client'@'localhost';