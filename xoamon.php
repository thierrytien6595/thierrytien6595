<?php 
// INCLUDE
include 'myfunction.php';

// MAIN
$TENBAN  = $_GET['TENBAN'];
$TRANGTHAIBAN = Get_TRANGTHAIBAN($TENBAN);
$temp_MABAN = Get_MABAN($TENBAN);
$MAHD = Get_MAHD($temp_MABAN);
$jsondata = $_GET['jsondata'];
$myjson1 = json_decode($jsondata);
$count = @count($myjson1);

include 'connect.php';
$sql = "SELECT * FROM `chitietbanhang` WHERE MAHD=$MAHD";
$result = $conn->query($sql);
foreach ($myjson1 as $key => $value) {
$tensp = $myjson1[$key]->TENSP;
$masp= Get_MASP($tensp);
$CHUTHICH = $myjson1[$key]->CHUTHICH;
$SOLUONG = $myjson1[$key]->SOLUONG;
$TRANGTHAIMON = $myjson1[$key]->TRANGTHAIMON;
(int)$MonPhu = $myjson1[$key]->MonPhu;
include 'connect.php';
$sql = "SELECT * FROM `chitietbanhang` WHERE MAHD=$MAHD AND MASP=$masp AND TRANGTHAIMON=$TRANGTHAIMON";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
	$row = $result->fetch_assoc();
	$CHUTHICH = $row['CHUTHICH'];
	$soluong=$SOLUONG;
	$SOLUONG = $row['SOLUONG']-$SOLUONG;
	if($SOLUONG!=0){
	$sql1 = "UPDATE `chitietbanhang` SET SOLUONG=$SOLUONG,CHUTHICH='$CHUTHICH' WHERE MAHD=$MAHD AND MASP=$masp AND TRANGTHAIMON=$TRANGTHAIMON;";
	if (xulythuoc($masp,$soluong)==false) {
		if ($MonPhu==0) {
			echo $MonPhu;
		$sql1.= "UPDATE `sanpham` SET SOLUONG=SOLUONG+$soluong WHERE MASP=$masp";
		}elseif ($MonPhu!=-1) {
			$sql1.= "UPDATE `sanpham` SET SOLUONG=SOLUONG+$soluong WHERE MASP=$MonPhu";
		}
	}
	}else{
	$sql1 = "DELETE FROM `chitietbanhang` WHERE MAHD=$MAHD AND MASP=$masp AND TRANGTHAIMON=$TRANGTHAIMON;";
	if (xulythuoc($masp,$soluong)==false) {
		if ($MonPhu==0) {
			echo $MonPhu;
			$sql1.= "UPDATE `sanpham` SET SOLUONG=SOLUONG+$soluong WHERE MASP=$masp";
		}elseif ($MonPhu!=-1) {
			$sql1.= "UPDATE `sanpham` SET SOLUONG=SOLUONG+$soluong WHERE MASP=$MonPhu";
		}
	}
	}
	$result1 = $conn->multi_query($sql1);
	}
	$conn->close();
}
include 'connect.php';
$sql = "SELECT * FROM `chitietbanhang` WHERE MAHD=$MAHD";
$result = $conn->query($sql);

if (($result->num_rows)==0) {
	Delete_HOADON($MAHD);
	Update_TRANGTHAI($TENBAN,0);
}
responseApp($TENBAN,$jsondata);
TongTien($MAHD);

function responseApp($TENBAN,$jsondata){
			$myjson = json_decode($jsondata);
			class SanPham{
			function SanPham($TENBAN,$jsondata){
				$this -> TENBAN=$TENBAN;
				$this -> jsondata=$jsondata;
			}
		}
		$SPArray = array();
		foreach ($myjson as $key => $value) {
			array_push($SPArray, new SanPham($TENBAN,$myjson[$key]->TENSP));
		}
		echo json_encode($SPArray);
		}

function xulythuoc($MASP,$SOLUONG){
	include 'connect.php';
	// Nếu là điếu
	if ($MASP==102||$MASP==104||$MASP==108) {
		$sql = "SELECT SOLUONG FROM `sanpham` WHERE MASP=$MASP";	
		$result = $conn->query($sql);
		$row = $result->fetch_assoc();
		$SOLUONG += $row['SOLUONG'];
		$sql = "UPDATE `sanpham` SET SOLUONG=$SOLUONG WHERE MASP=$MASP";
		$result = $conn->query($sql);
		//Cập nhật gói
		$MASP+=1; // MASP của gói
		$SOLUONG = floor($SOLUONG/20); // Số lượng gói thuốc.
		$sql = "UPDATE `sanpham` SET SOLUONG=$SOLUONG WHERE MASP=$MASP";
		$result = $conn->query($sql);
		return true;
	}
	// Nếu là gói
	if ($MASP==103||$MASP==105||$MASP==109) {
		$MASP-=1;
		$SOLUONG*=20;
		$sql = "SELECT SOLUONG FROM `sanpham` WHERE MASP=$MASP";	
		$result = $conn->query($sql);
		$row = $result->fetch_assoc();
		$SOLUONG += $row['SOLUONG'];
		$sql = "UPDATE `sanpham` SET SOLUONG=$SOLUONG WHERE MASP=$MASP";
		$result = $conn->query($sql);
		//Cập nhật gói
		$MASP+=1; // MASP của gói
		$SOLUONG = floor($SOLUONG/20); // Số lượng gói thuốc.
		$sql = "UPDATE `sanpham` SET SOLUONG=$SOLUONG WHERE MASP=$MASP";
		$result = $conn->query($sql);	
		return true;
	}
	return false;
}
?>