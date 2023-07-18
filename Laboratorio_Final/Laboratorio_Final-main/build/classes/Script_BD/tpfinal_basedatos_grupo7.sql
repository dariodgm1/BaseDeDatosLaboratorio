-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-06-2023 a las 20:18:32
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tpfinal_basedatos_grupo7`
--
CREATE DATABASE IF NOT EXISTS `tpfinal_basedatos_grupo7` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tpfinal_basedatos_grupo7`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentarios`
--

CREATE TABLE IF NOT EXISTS `comentarios` (
  `Id_Comentarios` int(11) NOT NULL AUTO_INCREMENT,
  `Comentarios` varchar(30) NOT NULL,
  `Fecha_Avance` date NOT NULL,
  `Id_Tarea` int(11) NOT NULL,
  PRIMARY KEY (`Id_Comentarios`) USING BTREE,
  KEY `Id_Tarea` (`Id_Tarea`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo`
--

CREATE TABLE IF NOT EXISTS `equipo` (
  `Id_Proyecto` int(11) NOT NULL,
  `Id_Equipo` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(30) NOT NULL,
  `Fecha_Creacion` date NOT NULL,
  `Estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`Id_Equipo`),
  KEY `Id_Proyecto` (`Id_Proyecto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `equipo`
--

INSERT INTO `equipo` (`Id_Proyecto`, `Id_Equipo`, `Nombre`, `Fecha_Creacion`, `Estado`) VALUES
(3, 2, 'gabineta', '2023-06-19', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo_miembros`
--

CREATE TABLE IF NOT EXISTS `equipo_miembros` (
  `Id_MiembroEq` int(11) NOT NULL AUTO_INCREMENT,
  `Fecha_Incoporacion` date NOT NULL,
  `Id_Equipo` int(11) NOT NULL,
  `Id_Miembro` int(11) NOT NULL,
  PRIMARY KEY (`Id_MiembroEq`) USING BTREE,
  KEY `Id_Equipo` (`Id_Equipo`),
  KEY `Id_Miembro` (`Id_Miembro`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `equipo_miembros`
--

INSERT INTO `equipo_miembros` (`Id_MiembroEq`, `Fecha_Incoporacion`, `Id_Equipo`, `Id_Miembro`) VALUES
(5, '2023-06-19', 2, 12),
(6, '2023-06-19', 2, 11),
(7, '2023-06-19', 2, 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `miembro`
--

CREATE TABLE IF NOT EXISTS `miembro` (
  `Id_Miembro` int(11) NOT NULL AUTO_INCREMENT,
  `DNI` int(11) NOT NULL,
  `Apellido` varchar(30) NOT NULL,
  `Nombre` varchar(30) NOT NULL,
  `Estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`Id_Miembro`),
  UNIQUE KEY `DNI` (`DNI`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `miembro`
--

INSERT INTO `miembro` (`Id_Miembro`, `DNI`, `Apellido`, `Nombre`, `Estado`) VALUES
(10, 43491021, 'Melian', 'Gabriel', 1),
(11, 44480503, 'moll', 'jess', 1),
(12, 484545, 'becera', 'gajier', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyecto`
--

CREATE TABLE IF NOT EXISTS `proyecto` (
  `Id_Proyecto` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(30) NOT NULL,
  `Descripcion` varchar(30) NOT NULL,
  `Fecha_Inicio` date NOT NULL,
  `Estado` tinyint(1) NOT NULL,
  PRIMARY KEY (`Id_Proyecto`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proyecto`
--

INSERT INTO `proyecto` (`Id_Proyecto`, `Nombre`, `Descripcion`, `Fecha_Inicio`, `Estado`) VALUES
(2, 'pruba de lucas', 'jessineta', '2023-06-17', 1),
(3, 'prueba2', 'holamundo', '2023-06-01', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarea`
--

CREATE TABLE IF NOT EXISTS `tarea` (
  `Id_Tarea` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(30) NOT NULL,
  `Fecha_Creacion` date NOT NULL,
  `Fecha_Cierre` date NOT NULL,
  `Estado` tinyint(1) NOT NULL,
  `Id_MiembroEq` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id_Tarea`),
  KEY `Id_MiembrosEq` (`Id_MiembroEq`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tarea`
--

INSERT INTO `tarea` (`Id_Tarea`, `Nombre`, `Fecha_Creacion`, `Fecha_Cierre`, `Estado`, `Id_MiembroEq`) VALUES
(1, 'prueba', '2023-06-01', '2023-06-15', 1, 5),
(2, 'gabi', '2023-06-01', '2023-06-03', 1, 6),
(3, 'Tarea 1', '2023-06-17', '2023-08-17', 1, 5),
(4, 'Tarea 1', '2023-06-17', '2023-08-17', 1, 7);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comentarios`
--
ALTER TABLE `comentarios`
  ADD CONSTRAINT `comentarios_ibfk_1` FOREIGN KEY (`Id_Tarea`) REFERENCES `tarea` (`Id_Tarea`);

--
-- Filtros para la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD CONSTRAINT `equipo_ibfk_1` FOREIGN KEY (`Id_Proyecto`) REFERENCES `proyecto` (`Id_Proyecto`);

--
-- Filtros para la tabla `equipo_miembros`
--
ALTER TABLE `equipo_miembros`
  ADD CONSTRAINT `equipo_miembros_ibfk_1` FOREIGN KEY (`Id_Miembro`) REFERENCES `miembro` (`Id_Miembro`),
  ADD CONSTRAINT `equipo_miembros_ibfk_2` FOREIGN KEY (`Id_Equipo`) REFERENCES `equipo` (`Id_Equipo`);

--
-- Filtros para la tabla `tarea`
--
ALTER TABLE `tarea`
  ADD CONSTRAINT `tarea_ibfk_1` FOREIGN KEY (`Id_MiembroEq`) REFERENCES `equipo_miembros` (`Id_MiembroEq`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
