<?php 
// INCLUDE
include 'myfunction.php';
if (!isset($_GET['TENNV'])) {
	$TENNV="THANH";
	$MANV = Get_MANV($TENNV);
}
if (isset($_GET['data'])) {
// data là dữ liệu mỗi lần insert đơn lẻ
$data = $_GET['data'];
$myjson = json_decode($data);
include 'connect.php';
foreach ($myjson as $key => $value) {
	(int)$MASP = $myjson[$key]->MASP;
	(int)$SOLUONG = $myjson[$key]->SOLUONG;
	(int)$SOLUONGdieu = $SOLUONG*20;
// xử lý thuốc
// 102 là điếu 3 số // 103 là gói 3 số // 108 là điếu JET // 109 là gói Jet // 104 là điếu mèo đỏ // 105 là gói mèo đỏ.
	// Xử lý theo nhập gói
	if (($MASP == 103 ) || ($MASP == 105 ) || ($MASP==109)) {
		//Cập nhật điếu
		echo $MASP;
		$mMASP=$MASP-1;
		$sql = "SELECT SOLUONG FROM `sanpham` WHERE MASP=$mMASP";	
		$result = $conn->query($sql);
		$row = $result->fetch_assoc();
		$SOLUONGdieu += $row['SOLUONG'];
		$sql = "UPDATE `sanpham` SET SOLUONG=$SOLUONGdieu WHERE MASP=$mMASP";
		$result = $conn->query($sql);
		//Cập nhật gói
		$SOLUONG = floor($SOLUONGdieu/20); // Số lượng gói thuốc.
		$sql = "UPDATE `sanpham` SET SOLUONG=$SOLUONG WHERE MASP=$MASP";
		$result = $conn->query($sql);
	}elseif (($MASP == 102 ) || ($MASP == 104 ) || ($MASP==108)) {
		$sql = "SELECT SOLUONG FROM `sanpham` WHERE MASP=$MASP";	
		$result = $conn->query($sql);
		$row = $result->fetch_assoc();
		$SOLUONG += $row['SOLUONG'];
		$SOLUONGdieu=$SOLUONG;
		$sql = "UPDATE `sanpham` SET SOLUONG=$SOLUONGdieu WHERE MASP=$MASP";
		$result = $conn->query($sql);
		//Cập nhật gói
		$MASP=$MASP+1;
		$SOLUONG = floor($SOLUONGdieu/20); // Số lượng gói thuốc.
		$sql = "UPDATE `sanpham` SET SOLUONG=$SOLUONG WHERE MASP=$MASP";
		$result = $conn->query($sql);
	}
	else{
		$sql = "SELECT SOLUONG FROM `sanpham` WHERE MASP=$MASP";
		$result = $conn->query($sql);
		$row = $result->fetch_assoc();
		$SOLUONG += $row['SOLUONG'];
		$sql = "UPDATE `sanpham` SET SOLUONG=$SOLUONG WHERE MASP=$MASP";
		$result = $conn->query($sql);
	}
}
// data1 là dữ liệu tổng kết lại lần nhập đó
}elseif(isset($_GET['data1']))
{
	$data = $_GET['data1'];
	$myjson = json_decode($data);
	include 'connect.php';
	$sql = "INSERT INTO `nhaphang` (`MANV`, `TIME`) VALUES ($MANV,CURRENT_TIMESTAMP)";
	$result = $conn->query($sql);
	$sql = "SELECT ID FROM `nhaphang` ORDER BY ID DESC LIMIT 1";
	$result = $conn->query($sql);
	$row = $result->fetch_assoc();
	$ID=$row['ID'];

	foreach ($myjson as $key => $value) {
		$MASP = $myjson[$key]->MASP;
		$SOLUONG = $myjson[$key]->SOLUONG;
		$sql = "INSERT INTO `chitietnhaphang` VALUES ($ID,$MASP,$SOLUONG)";
		$result = $conn->query($sql);
		}
}	

?>