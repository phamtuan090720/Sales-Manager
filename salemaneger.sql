-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: salemanager
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chitetnhaphang`
--

DROP TABLE IF EXISTS `chitetnhaphang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitetnhaphang` (
  `IDPhieuNhap` int NOT NULL,
  `IDHangHoa` int NOT NULL,
  `ThanhTien` double NOT NULL,
  `DonGia` double NOT NULL,
  PRIMARY KEY (`IDPhieuNhap`,`IDHangHoa`),
  KEY `hanghoa_idHangHoa_idx` (`IDHangHoa`),
  CONSTRAINT `hanghoa_idHangHoa` FOREIGN KEY (`IDHangHoa`) REFERENCES `hanghoa` (`idHangHoa`),
  CONSTRAINT `phieunhaphang_IDPhieuNhapHang` FOREIGN KEY (`IDPhieuNhap`) REFERENCES `phieunhaphang` (`IDPhieuNhapHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitetnhaphang`
--

LOCK TABLES `chitetnhaphang` WRITE;
/*!40000 ALTER TABLE `chitetnhaphang` DISABLE KEYS */;
/*!40000 ALTER TABLE `chitetnhaphang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitiethoadon`
--

DROP TABLE IF EXISTS `chitiethoadon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitiethoadon` (
  `IDHoaDon` int NOT NULL,
  `SoLuong` int NOT NULL,
  `DonGia` double NOT NULL,
  `IDHangHoa` int NOT NULL,
  PRIMARY KEY (`IDHoaDon`,`IDHangHoa`),
  KEY `fk_chitiethoadon_hanghoa1_idx` (`IDHangHoa`),
  CONSTRAINT `fk_chitiethoadon_hanghoa1` FOREIGN KEY (`IDHangHoa`) REFERENCES `hanghoa` (`idHangHoa`),
  CONSTRAINT `fk_chitiethoadon_hoadon1` FOREIGN KEY (`IDHoaDon`) REFERENCES `hoadon` (`idHoaDon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitiethoadon`
--

LOCK TABLES `chitiethoadon` WRITE;
/*!40000 ALTER TABLE `chitiethoadon` DISABLE KEYS */;
/*!40000 ALTER TABLE `chitiethoadon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitietphieuxuat`
--

DROP TABLE IF EXISTS `chitietphieuxuat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitietphieuxuat` (
  `IDHangHoa` int NOT NULL,
  `IDPhieuXuat` int NOT NULL,
  PRIMARY KEY (`IDHangHoa`,`IDPhieuXuat`),
  KEY `fk_chitietphieuxuat_phieuxuathang1_idx` (`IDPhieuXuat`),
  CONSTRAINT `fk_chitietphieuxuat_hanghoa1` FOREIGN KEY (`IDHangHoa`) REFERENCES `hanghoa` (`idHangHoa`),
  CONSTRAINT `fk_chitietphieuxuat_phieuxuathang1` FOREIGN KEY (`IDPhieuXuat`) REFERENCES `phieuxuathang` (`IDPhieuXuat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitietphieuxuat`
--

LOCK TABLES `chitietphieuxuat` WRITE;
/*!40000 ALTER TABLE `chitietphieuxuat` DISABLE KEYS */;
/*!40000 ALTER TABLE `chitietphieuxuat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitiettrahang`
--

DROP TABLE IF EXISTS `chitiettrahang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitiettrahang` (
  `IDHangHoa` int NOT NULL,
  `IDPhieuTra` int NOT NULL,
  `NgayTra` datetime NOT NULL,
  PRIMARY KEY (`IDHangHoa`,`IDPhieuTra`),
  KEY `fk_chitiettrahang_phieutrahang1_idx` (`IDPhieuTra`),
  CONSTRAINT `fk_chitiettrahang_hanghoa1` FOREIGN KEY (`IDHangHoa`) REFERENCES `hanghoa` (`idHangHoa`),
  CONSTRAINT `fk_chitiettrahang_phieutrahang1` FOREIGN KEY (`IDPhieuTra`) REFERENCES `phieutrahang` (`IDPhieuTraHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitiettrahang`
--

LOCK TABLES `chitiettrahang` WRITE;
/*!40000 ALTER TABLE `chitiettrahang` DISABLE KEYS */;
/*!40000 ALTER TABLE `chitiettrahang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hanghoa`
--

DROP TABLE IF EXISTS `hanghoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hanghoa` (
  `idHangHoa` int NOT NULL AUTO_INCREMENT,
  `TenHang` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `LoaiHang` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `XuatXu` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `NgaySX` datetime NOT NULL,
  `HanSD` datetime NOT NULL,
  `GiaBan` double NOT NULL,
  `DonViTInh` double NOT NULL,
  PRIMARY KEY (`idHangHoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hanghoa`
--

LOCK TABLES `hanghoa` WRITE;
/*!40000 ALTER TABLE `hanghoa` DISABLE KEYS */;
/*!40000 ALTER TABLE `hanghoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoadon`
--

DROP TABLE IF EXISTS `hoadon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoadon` (
  `idHoaDon` int NOT NULL AUTO_INCREMENT,
  `NgayLap` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `ThanhTien` double NOT NULL,
  `VAT` double NOT NULL,
  `IDNhanVienBanHang` int NOT NULL,
  `IDKhachHangThanThiet` int NOT NULL,
  PRIMARY KEY (`idHoaDon`),
  KEY `fk_hoadon_khachhangthanthiet1_idx` (`IDKhachHangThanThiet`),
  KEY `fk_hoadon_nhanvienbanhang1_idx` (`IDNhanVienBanHang`),
  CONSTRAINT `fk_hoadon_khachhangthanthiet1` FOREIGN KEY (`IDKhachHangThanThiet`) REFERENCES `khachhangthanthiet` (`idKhachHangThanThiet`),
  CONSTRAINT `fk_hoadon_nhanvienbanhang1` FOREIGN KEY (`IDNhanVienBanHang`) REFERENCES `nhanvienbanhang` (`idNhanVienBanHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoadon`
--

LOCK TABLES `hoadon` WRITE;
/*!40000 ALTER TABLE `hoadon` DISABLE KEYS */;
/*!40000 ALTER TABLE `hoadon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khachhangthanthiet`
--

DROP TABLE IF EXISTS `khachhangthanthiet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khachhangthanthiet` (
  `idKhachHangThanThiet` int NOT NULL AUTO_INCREMENT,
  `CMND` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `SDT` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `DiaChi` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Diem` int NOT NULL,
  PRIMARY KEY (`idKhachHangThanThiet`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khachhangthanthiet`
--

LOCK TABLES `khachhangthanthiet` WRITE;
/*!40000 ALTER TABLE `khachhangthanthiet` DISABLE KEYS */;
/*!40000 ALTER TABLE `khachhangthanthiet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kiemke_hanghoa`
--

DROP TABLE IF EXISTS `kiemke_hanghoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kiemke_hanghoa` (
  `IDPhieuKiemKe` int NOT NULL,
  `ÌDHangHoa` int NOT NULL,
  PRIMARY KEY (`IDPhieuKiemKe`,`ÌDHangHoa`),
  KEY `fk_kiemke_hanghoa_hanghoa1_idx` (`ÌDHangHoa`),
  CONSTRAINT `fk_kiemke_hanghoa_hanghoa1` FOREIGN KEY (`ÌDHangHoa`) REFERENCES `hanghoa` (`idHangHoa`),
  CONSTRAINT `fk_kiemke_hanghoa_phieukiemke1` FOREIGN KEY (`IDPhieuKiemKe`) REFERENCES `phieukiemke` (`idPhieuKiemKe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kiemke_hanghoa`
--

LOCK TABLES `kiemke_hanghoa` WRITE;
/*!40000 ALTER TABLE `kiemke_hanghoa` DISABLE KEYS */;
/*!40000 ALTER TABLE `kiemke_hanghoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhanvien`
--

DROP TABLE IF EXISTS `nhanvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhanvien` (
  `MaNhaVien` int NOT NULL AUTO_INCREMENT,
  `TenNhanVien` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `NghiepVu` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `TaiKhoan` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `MatKhau` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`MaNhaVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhanvien`
--

LOCK TABLES `nhanvien` WRITE;
/*!40000 ALTER TABLE `nhanvien` DISABLE KEYS */;
/*!40000 ALTER TABLE `nhanvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhanvienbanhang`
--

DROP TABLE IF EXISTS `nhanvienbanhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhanvienbanhang` (
  `idNhanVienBanHang` int NOT NULL AUTO_INCREMENT,
  `Luong` double NOT NULL,
  PRIMARY KEY (`idNhanVienBanHang`),
  CONSTRAINT `fk_nhanvienbanhang_nhanvien1` FOREIGN KEY (`idNhanVienBanHang`) REFERENCES `nhanvien` (`MaNhaVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhanvienbanhang`
--

LOCK TABLES `nhanvienbanhang` WRITE;
/*!40000 ALTER TABLE `nhanvienbanhang` DISABLE KEYS */;
/*!40000 ALTER TABLE `nhanvienbanhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieukiemke`
--

DROP TABLE IF EXISTS `phieukiemke`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieukiemke` (
  `idPhieuKiemKe` int NOT NULL AUTO_INCREMENT,
  `NgayTao` datetime NOT NULL,
  `SoLuong` datetime NOT NULL,
  `IDQuanLy` int NOT NULL,
  PRIMARY KEY (`idPhieuKiemKe`),
  KEY `fk_phieukiemke_quanly1_idx` (`IDQuanLy`),
  CONSTRAINT `fk_phieukiemke_quanly1` FOREIGN KEY (`IDQuanLy`) REFERENCES `quanly` (`idQuanLy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieukiemke`
--

LOCK TABLES `phieukiemke` WRITE;
/*!40000 ALTER TABLE `phieukiemke` DISABLE KEYS */;
/*!40000 ALTER TABLE `phieukiemke` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieunhaphang`
--

DROP TABLE IF EXISTS `phieunhaphang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieunhaphang` (
  `IDPhieuNhapHang` int NOT NULL AUTO_INCREMENT,
  `NgayTao` datetime NOT NULL,
  `SoLuong` int NOT NULL,
  `NhaCungCap` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `IDQuanLy` int NOT NULL,
  PRIMARY KEY (`IDPhieuNhapHang`),
  KEY `quanly_IDQuanLy_idx` (`IDQuanLy`),
  CONSTRAINT `quanly_IDQuanLy` FOREIGN KEY (`IDQuanLy`) REFERENCES `quanly` (`idQuanLy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieunhaphang`
--

LOCK TABLES `phieunhaphang` WRITE;
/*!40000 ALTER TABLE `phieunhaphang` DISABLE KEYS */;
/*!40000 ALTER TABLE `phieunhaphang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieuthongke`
--

DROP TABLE IF EXISTS `phieuthongke`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieuthongke` (
  `idPhieuThongKe` int NOT NULL AUTO_INCREMENT,
  `NgayThongKe` datetime NOT NULL,
  `DoanhThu` double NOT NULL,
  `Thu` double NOT NULL,
  `Chi` double NOT NULL,
  `IDQuanLy` int NOT NULL,
  PRIMARY KEY (`idPhieuThongKe`),
  KEY `quanly_IDQuanLy_id` (`IDQuanLy` DESC) /*!80000 INVISIBLE */,
  KEY `fk_phieuthongke_quanly1` (`IDQuanLy`),
  CONSTRAINT `fk_phieuthongke_quanly1` FOREIGN KEY (`IDQuanLy`) REFERENCES `quanly` (`idQuanLy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieuthongke`
--

LOCK TABLES `phieuthongke` WRITE;
/*!40000 ALTER TABLE `phieuthongke` DISABLE KEYS */;
/*!40000 ALTER TABLE `phieuthongke` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieutrahang`
--

DROP TABLE IF EXISTS `phieutrahang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieutrahang` (
  `IDPhieuTraHang` int NOT NULL AUTO_INCREMENT,
  `NgayTao` datetime NOT NULL,
  `SoLuong` int NOT NULL,
  `ÌDQuanLy` int NOT NULL,
  PRIMARY KEY (`IDPhieuTraHang`),
  KEY `fk_phieutrahang_quanly1_idx` (`ÌDQuanLy`),
  CONSTRAINT `fk_phieutrahang_quanly1` FOREIGN KEY (`ÌDQuanLy`) REFERENCES `quanly` (`idQuanLy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieutrahang`
--

LOCK TABLES `phieutrahang` WRITE;
/*!40000 ALTER TABLE `phieutrahang` DISABLE KEYS */;
/*!40000 ALTER TABLE `phieutrahang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieuxuathang`
--

DROP TABLE IF EXISTS `phieuxuathang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieuxuathang` (
  `IDPhieuXuat` int NOT NULL,
  `NgayTao` datetime NOT NULL,
  `SoLuong` int NOT NULL,
  `IDQuanLy` int NOT NULL,
  PRIMARY KEY (`IDPhieuXuat`),
  KEY `fk_phieuxuathang_quanly1_idx` (`IDQuanLy`),
  CONSTRAINT `fk_phieuxuathang_quanly1` FOREIGN KEY (`IDQuanLy`) REFERENCES `quanly` (`idQuanLy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieuxuathang`
--

LOCK TABLES `phieuxuathang` WRITE;
/*!40000 ALTER TABLE `phieuxuathang` DISABLE KEYS */;
/*!40000 ALTER TABLE `phieuxuathang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quanly`
--

DROP TABLE IF EXISTS `quanly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quanly` (
  `idQuanLy` int NOT NULL AUTO_INCREMENT,
  `LuongCoBan` double NOT NULL,
  `PhuCap` double NOT NULL,
  `TienThuong` double NOT NULL,
  PRIMARY KEY (`idQuanLy`),
  CONSTRAINT `fk_quanly_nhanvien1` FOREIGN KEY (`idQuanLy`) REFERENCES `nhanvien` (`MaNhaVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quanly`
--

LOCK TABLES `quanly` WRITE;
/*!40000 ALTER TABLE `quanly` DISABLE KEYS */;
/*!40000 ALTER TABLE `quanly` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'salemanager'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-04 13:06:27
