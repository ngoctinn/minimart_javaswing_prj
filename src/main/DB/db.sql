USE [master]
GO
/****** Object:  Database [QuanLyBanHang]    Script Date: 4/10/2025 2:47:49 PM ******/
CREATE DATABASE [QuanLyBanHang]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QuanLyBanHang', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\QuanLyBanHang.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'QuanLyBanHang_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\QuanLyBanHang_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [QuanLyBanHang] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QuanLyBanHang].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QuanLyBanHang] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET ARITHABORT OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QuanLyBanHang] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QuanLyBanHang] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET  ENABLE_BROKER 
GO
ALTER DATABASE [QuanLyBanHang] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QuanLyBanHang] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET RECOVERY FULL 
GO
ALTER DATABASE [QuanLyBanHang] SET  MULTI_USER 
GO
ALTER DATABASE [QuanLyBanHang] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QuanLyBanHang] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QuanLyBanHang] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [QuanLyBanHang] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [QuanLyBanHang] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'QuanLyBanHang', N'ON'
GO
ALTER DATABASE [QuanLyBanHang] SET QUERY_STORE = ON
GO
ALTER DATABASE [QuanLyBanHang] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [QuanLyBanHang]
GO
/****** Object:  User [Tin]    Script Date: 4/10/2025 2:47:49 PM ******/
CREATE USER [Tin] FOR LOGIN [Tin] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [Kennzz]    Script Date: 4/10/2025 2:47:49 PM ******/
CREATE USER [Kennzz] FOR LOGIN [Kennzz] WITH DEFAULT_SCHEMA=[Kennzz]
GO
ALTER ROLE [db_owner] ADD MEMBER [Kennzz]
GO
/****** Object:  Schema [Kennzz]    Script Date: 4/10/2025 2:47:49 PM ******/
CREATE SCHEMA [Kennzz]
GO
/****** Object:  Table [dbo].[CHAMCONG]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CHAMCONG](
	[maChamCong] [varchar](20) NOT NULL,
	[namChamCong] [int] NULL,
	[thangChamCong] [int] NULL,
	[soGioLam] [int] NULL,
	[soNgayLam] [int] NULL,
	[luongThuong] [decimal](15, 2) NULL,
	[khauTru] [decimal](15, 2) NULL,
	[luongThucNhan] [decimal](15, 2) NULL,
	[maNV] [varchar](20) NOT NULL,
 CONSTRAINT [PK_maChamCong] PRIMARY KEY CLUSTERED 
(
	[maChamCong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CHITIETPHIEUNHAP]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CHITIETPHIEUNHAP](
	[maPN] [varchar](20) NOT NULL,
	[soLuong] [int] NULL,
	[giaNhap] [decimal](15, 2) NULL,
	[ngaySX] [date] NOT NULL,
	[hanSD] [date] NULL,
	[maLoHang] [varchar](20) NOT NULL,
 CONSTRAINT [PK_MAPHIEUNHAP_CHITIETPHIEUNHAP] PRIMARY KEY CLUSTERED 
(
	[maPN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CHITIETHOADON]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CHITIETHOADON](
	[maHoaDon] [varchar](20) NOT NULL,
	[maSP] [varchar](20) NULL,
	[soLuong] [int] NULL,
	[giaBan] [decimal](15, 2) NULL,
 CONSTRAINT [PK_MaHoaDon_ChiTietHoaDon] PRIMARY KEY CLUSTERED 
(
	[maHoaDon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CHUCVU]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CHUCVU](
	[maCV] [varchar](20) NOT NULL,
	[tenCV] [nvarchar](50) NULL,
	[trangThai] [bit] NULL,
 CONSTRAINT [PK__MACHUCVU] PRIMARY KEY CLUSTERED 
(
	[maCV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HOADON]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HOADON](
	[maHoaDon] [varchar](20) NOT NULL,
	[tongTien] [decimal](15, 2) NULL,
	[maKH] [varchar](20) NOT NULL,
	[maNV] [varchar](20) NOT NULL,
	[maKM] [varchar](20) NOT NULL,
	[thoiGianLap] [datetime] NULL,
	[trangThai] [bit] NULL,
 CONSTRAINT [PK_MaHoaDon] PRIMARY KEY CLUSTERED 
(
	[maHoaDon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HOPDONG]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HOPDONG](
	[maHopDong] [varchar](20) NOT NULL,
	[ngayBD] [date] NULL,
	[ngayKT] [date] NULL,
	[luongCB] [decimal](15, 2) NULL,
	[maNV] [varchar](20) NOT NULL,
	[trangThai] [bit] NULL,
	[loaiHopDong] [nvarchar](20) NULL,
 CONSTRAINT [PK_maHopDong] PRIMARY KEY CLUSTERED 
(
	[maHopDong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KHACHHANG]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KHACHHANG](
	[maKH] [varchar](20) NOT NULL,
	[hoTen] [nvarchar](50) NULL,
	[diaChi] [text] NULL,
	[SDT] [varchar](15) NULL,
	[gioiTinh] [nvarchar](10) NULL,
	[email] [varchar](255) NULL,
	[ngaySinh] [date] NULL,
	[trangThai] [bit] NULL,
 CONSTRAINT [PK_MAKHACHHANG] PRIMARY KEY CLUSTERED 
(
	[maKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KHUYENMAI]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KHUYENMAI](
	[maKM] [varchar](20) NOT NULL,
	[tenKM] [varchar](255) NULL,
	[dieuKien] [text] NULL,
	[ngayBD] [date] NULL,
	[ngayKT] [date] NULL,
	[phanTram] [decimal](5, 2) NULL,
	[trangThai] [bit] NULL,
 CONSTRAINT [PK_MaKhuyenMai] PRIMARY KEY CLUSTERED 
(
	[maKM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LOAISP]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LOAISP](
	[maLSP] [varchar](20) NOT NULL,
	[tenLSP] [nvarchar](50) NULL,
	[trangThai] [bit] NULL,
 CONSTRAINT [PK_maLSP] PRIMARY KEY CLUSTERED 
(
	[maLSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LOHANG]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LOHANG](
	[maLoHang] [varchar](20) NOT NULL,
	[maSP] [varchar](20) NOT NULL,
	[ngaySanXuat] [date] NULL,
	[ngayHetHan] [date] NULL,
	[soLuong] [int] NULL,
	[trangThai] [varchar](50) NULL,
 CONSTRAINT [PK_MaLoHang] PRIMARY KEY CLUSTERED 
(
	[maLoHang] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NHACUNGCAP]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NHACUNGCAP](
	[maNCC] [varchar](20) NOT NULL,
	[tenNCC] [nvarchar](50) NULL,
	[diaChi] [text] NULL,
	[SDT] [varchar](15) NULL,
	[trangThai] [bit] NULL,
 CONSTRAINT [PK_manhacungcap] PRIMARY KEY CLUSTERED 
(
	[maNCC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NHANVIEN]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NHANVIEN](
	[maNV] [varchar](20) NOT NULL,
	[hoTen] [nvarchar](50) NULL,
	[ngaySinh] [date] NULL,
	[gioiTinh] [nvarchar](10) NULL,
	[diaChi] [text] NULL,
	[email] [varchar](255) NULL,
	[SDT] [varchar](15) NULL,
	[trangThai] [bit] NULL,
	[maCV] [varchar](20) NOT NULL,
 CONSTRAINT [PK_maNhanVien] PRIMARY KEY CLUSTERED 
(
	[maNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PHANQUYEN]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PHANQUYEN](
	[maPhanQuyen] [varchar](5) NOT NULL,
	[maCV] [varchar](20) NOT NULL,
	[phanQuyen] [varchar](15) NOT NULL,
	[module] [varchar](20) NOT NULL,
	[trangThai] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[maPhanQuyen] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PHIEUNHAP]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PHIEUNHAP](
	[maPN] [varchar](20) NOT NULL,
	[ngayLap] [date] NULL,
	[gioLap] [time](7) NULL,
	[tongTien] [decimal](15, 2) NULL,
	[maNCC] [varchar](20) NOT NULL,
	[maNV] [varchar](20) NOT NULL,
	[trangThai] [bit] NULL,
 CONSTRAINT [PK__PHIEUNHAP] PRIMARY KEY CLUSTERED 
(
	[maPN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SANPHAM]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SANPHAM](
	[maSP] [varchar](20) NOT NULL,
	[tenSP] [nvarchar](255) NULL,
	[trangThai] [varchar](50) NULL,
	[donVi] [varchar](50) NULL,
	[hinhAnh] [text] NULL,
	[maLSP] [varchar](20) NOT NULL,
	[tonKho] [int] NULL,
	[giaBan] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[maSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[CHAMCONG]  WITH CHECK ADD  CONSTRAINT [fk_chamcong_nhanvien] FOREIGN KEY([maNV])
REFERENCES [dbo].[NHANVIEN] ([maNV])
GO
ALTER TABLE [dbo].[CHAMCONG] CHECK CONSTRAINT [fk_chamcong_nhanvien]
GO
ALTER TABLE [dbo].[CHITIETPHIEUNHAP]  WITH CHECK ADD  CONSTRAINT [FK_MaLoHang_ChiTietPhieuNhap] FOREIGN KEY([maLoHang])
REFERENCES [dbo].[LOHANG] ([maLoHang])
GO
ALTER TABLE [dbo].[CHITIETPHIEUNHAP] CHECK CONSTRAINT [FK_MaLoHang_ChiTietPhieuNhap]
GO
ALTER TABLE [dbo].[CHITIETPHIEUNHAP]  WITH CHECK ADD  CONSTRAINT [FK_PHIEUNHAP_CHITIETPHIEUNHAP] FOREIGN KEY([maPN])
REFERENCES [dbo].[PHIEUNHAP] ([maPN])
GO
ALTER TABLE [dbo].[CHITIETPHIEUNHAP] CHECK CONSTRAINT [FK_PHIEUNHAP_CHITIETPHIEUNHAP]
GO
ALTER TABLE [dbo].[CHITIETHOADON]  WITH CHECK ADD  CONSTRAINT [FK__CHITIETHO__maHoa__45F365D3] FOREIGN KEY([maHoaDon])
REFERENCES [dbo].[HOADON] ([maHoaDon])
GO
ALTER TABLE [dbo].[CHITIETHOADON] CHECK CONSTRAINT [FK__CHITIETHO__maHoa__45F365D3]
GO
ALTER TABLE [dbo].[CHITIETHOADON]  WITH CHECK ADD  CONSTRAINT [fk_maSP_HoaDon] FOREIGN KEY([maSP])
REFERENCES [dbo].[SANPHAM] ([maSP])
GO
ALTER TABLE [dbo].[CHITIETHOADON] CHECK CONSTRAINT [fk_maSP_HoaDon]
GO
ALTER TABLE [dbo].[HOADON]  WITH CHECK ADD  CONSTRAINT [FK_hoadon_khachhang] FOREIGN KEY([maKH])
REFERENCES [dbo].[KHACHHANG] ([maKH])
GO
ALTER TABLE [dbo].[HOADON] CHECK CONSTRAINT [FK_hoadon_khachhang]
GO
ALTER TABLE [dbo].[HOADON]  WITH CHECK ADD  CONSTRAINT [fk_hoadon_nhanvien] FOREIGN KEY([maNV])
REFERENCES [dbo].[NHANVIEN] ([maNV])
GO
ALTER TABLE [dbo].[HOADON] CHECK CONSTRAINT [fk_hoadon_nhanvien]
GO
ALTER TABLE [dbo].[HOADON]  WITH CHECK ADD  CONSTRAINT [FK_MaKM_HoaDon] FOREIGN KEY([maKM])
REFERENCES [dbo].[KHUYENMAI] ([maKM])
GO
ALTER TABLE [dbo].[HOADON] CHECK CONSTRAINT [FK_MaKM_HoaDon]
GO
ALTER TABLE [dbo].[HOPDONG]  WITH CHECK ADD  CONSTRAINT [fk_hopdong_nhanvien] FOREIGN KEY([maNV])
REFERENCES [dbo].[NHANVIEN] ([maNV])
GO
ALTER TABLE [dbo].[HOPDONG] CHECK CONSTRAINT [fk_hopdong_nhanvien]
GO
ALTER TABLE [dbo].[LOHANG]  WITH CHECK ADD  CONSTRAINT [FK_LOHANG_MASP] FOREIGN KEY([maSP])
REFERENCES [dbo].[SANPHAM] ([maSP])
GO
ALTER TABLE [dbo].[LOHANG] CHECK CONSTRAINT [FK_LOHANG_MASP]
GO
ALTER TABLE [dbo].[NHANVIEN]  WITH CHECK ADD  CONSTRAINT [fk_nhanvien_chucvu] FOREIGN KEY([maCV])
REFERENCES [dbo].[CHUCVU] ([maCV])
GO
ALTER TABLE [dbo].[NHANVIEN] CHECK CONSTRAINT [fk_nhanvien_chucvu]
GO
ALTER TABLE [dbo].[PHANQUYEN]  WITH CHECK ADD  CONSTRAINT [fk_PHANQUYEN_chucvu] FOREIGN KEY([maCV])
REFERENCES [dbo].[CHUCVU] ([maCV])
GO
ALTER TABLE [dbo].[PHANQUYEN] CHECK CONSTRAINT [fk_PHANQUYEN_chucvu]
GO
ALTER TABLE [dbo].[PHIEUNHAP]  WITH CHECK ADD  CONSTRAINT [fk_nhacungcap_phieunhap] FOREIGN KEY([maNCC])
REFERENCES [dbo].[NHACUNGCAP] ([maNCC])
GO
ALTER TABLE [dbo].[PHIEUNHAP] CHECK CONSTRAINT [fk_nhacungcap_phieunhap]
GO
ALTER TABLE [dbo].[PHIEUNHAP]  WITH CHECK ADD  CONSTRAINT [fk_phieunhap_nhanvien] FOREIGN KEY([maNV])
REFERENCES [dbo].[NHANVIEN] ([maNV])
GO
ALTER TABLE [dbo].[PHIEUNHAP] CHECK CONSTRAINT [fk_phieunhap_nhanvien]
GO
ALTER TABLE [dbo].[SANPHAM]  WITH CHECK ADD  CONSTRAINT [fk_loaisanpham_sanpham] FOREIGN KEY([maLSP])
REFERENCES [dbo].[LOAISP] ([maLSP])
GO
ALTER TABLE [dbo].[SANPHAM] CHECK CONSTRAINT [fk_loaisanpham_sanpham]
GO
ALTER TABLE [dbo].[PHANQUYEN]  WITH CHECK ADD CHECK  (([phanQuyen]='chinhsua' OR [phanQuyen]='chixem' OR [phanQuyen]='khongcoquyen'))
GO
/****** Object:  StoredProcedure [dbo].[LayDuLieuHoaDon]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[LayDuLieuHoaDon]
as
begin 
	select* from HOADON
end;
GO
/****** Object:  StoredProcedure [dbo].[LayDuLieuKhachHang]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[LayDuLieuKhachHang]
as
begin 
	select* from KHACHHANG
end;
GO
/****** Object:  StoredProcedure [dbo].[LayDuLieuLoHang]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[LayDuLieuLoHang]
as
begin 
	select* from LOHANG
end;
GO
/****** Object:  StoredProcedure [dbo].[LayDuLieuNhaCungCap]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[LayDuLieuNhaCungCap]
as
begin 
	select* from NHACUNGCAP;
end;
GO
/****** Object:  StoredProcedure [dbo].[LayDuLieuNhanVien]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[LayDuLieuNhanVien]
as
begin 
	select* from NHANVIEN;
end;
GO
/****** Object:  StoredProcedure [dbo].[LayDuLieuPhieuNhap]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[LayDuLieuPhieuNhap]
as
begin 
	select* from PHIEUNHAP;
end;
GO
/****** Object:  StoredProcedure [dbo].[LayDuLieuSanPham]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[LayDuLieuSanPham]
as
begin 
	select* from SANPHAM
end;
GO
/****** Object:  StoredProcedure [dbo].[TimKiemHoaDon]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[TimKiemHoaDon] @HoaDon varchar(20) = null
as
begin
	if @HoaDon is null
	begin
		exec LayDuLieuHoaDon;
		return
	end
	else
	begin
		select* from HOADON HD
		where HD.maHoaDon like '%' + @HoaDon + '%'
		return
	end
end

GO
/****** Object:  StoredProcedure [dbo].[TimKiemKhachHang]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[TimKiemKhachHang] @KhachHang varchar(20) = null
as
begin
	if @KhachHang is null
	begin
		exec LayDuLieuKhachHang;
		return
	end
	else
	begin
		select* from KHACHHANG KH
		where KH.SDT like '%' + @KhachHang + '%'
		return
	end
end

GO
/****** Object:  StoredProcedure [dbo].[TimKiemLoaiSanPham]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[TimKiemLoaiSanPham] @LoaiSanPham varchar(20) = null
as
begin
	if @LoaiSanPham is null
	begin
		exec LayDuLieuLoaiSanPham;
		return
	end
	else
	begin
		select* from LOAISP LSP
		where LSP.maLSP like '%' + @LoaiSanPham + '%' 
		or LSP.tenLSP like '%' + @LoaiSanPham + '%'
		return
	end
end

GO
/****** Object:  StoredProcedure [dbo].[TimKiemNhaCungCap]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[TimKiemNhaCungCap] @NCC varchar(20) = null
as
begin
	if @NCC is null
	begin
		exec LayDuLieuNhaCungCap;
		return
	end
	else
	begin
		select* from NHACUNGCAP NCC
		where NCC.maNCC like '%' + @NCC + '%' or NCC.tenNCC like '%' + @NCC + '%'
		return
	end
end
GO
/****** Object:  StoredProcedure [dbo].[TimKiemNhanVien]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[TimKiemNhanVien] @NV varchar(20) = null
as
begin
	if @NV is null
	begin
		exec LayDuLieuNhanVien;
		return
	end
	else
	begin
		select* from NHANVIEN NV
		where NV.maNV like '%' + @NV + '%' or NV.hoTen like '%' + @NV + '%'
		return
	end
end
GO
/****** Object:  StoredProcedure [dbo].[TimKiemSanPham]    Script Date: 4/10/2025 2:47:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[TimKiemSanPham] @k varchar(20) = null
as
begin
	if @k is null
	begin
		exec LayDuLieuSanPham;
		return
	end
	else
	begin
		select* from SANPHAM SP
		where SP.maSP like '%' + @k + '%' 
		or SP.tenSP like '%' + @k + '%'
		return
	end
end
GO
USE [master]
GO
ALTER DATABASE [QuanLyBanHang] SET  READ_WRITE 
GO
