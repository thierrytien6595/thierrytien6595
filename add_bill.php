<?php 
// INCLUDE
include 'myfunction.php';
// MAIN
$TENBAN  = $_GET['TENBAN'];
$TRANGTHAIBAN = Get_TRANGTHAIBAN($TENBAN);
if ($TRANGTHAIBAN==0) { // NẾU BÀN CHƯA CÓ MÓN THÌ THÊM HÓA ĐƠN
	$MAHD=Add_hoadon($TENBAN);
}
else{ // NẾU BÀN CÓ MÓN RỒI THÌ LẤY MÃ HÓA ĐƠN CŨ
	$temp_MABAN = Get_MABAN($TENBAN);
	$MAHD = Get_MAHD($temp_MABAN);
}
$jsondata = $_GET['jsondata'];
$myjson1 = json_decode($jsondata);

include 'connect.php';

    foreach ($myjson1 as $key => $value) {
	$tensp = $myjson1[$key]->TENSP;
	$MASP= Get_MASP($tensp);
	$CHUTHICH = $myjson1[$key]->CHUTHICH;
	$SOLUONG = $myjson1[$key]->SOLUONG; // 5
	$mSOLUONG = $SOLUONG;
	(int)$MonPhu = $myjson1[$key]->MonPhu;
	include 'connect.php';
	$sql = "SELECT * FROM `chitietbanhang` WHERE MAHD=$MAHD AND MASP=$MASP AND TRANGTHAIMON=0";
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		$CHUTHICH = $CHUTHICH." ".$row['CHUTHICH'];
		$SOLUONG = $SOLUONG + $row['SOLUONG'];
		$sql = "UPDATE `chitietbanhang` SET SOLUONG=$SOLUONG,CHUTHICH='$CHUTHICH' WHERE MAHD=$MAHD AND MASP=$MASP AND TRANGTHAIMON=0;";
		// Nếu SP có tính số lượng tức $MonPhu=0
		if (xulythuoc($MASP,$mSOLUONG)==false) {
			if ($MonPhu==0) {
				$sql.= "UPDATE `sanpham` SET SOLUONG=SOLUONG-$mSOLUONG WHERE MASP=$MASP";
			// Nếu SP có tính số lượng nhưng có món phụ thuộc (=-1 tức là món không tính số lượng được)
			}elseif($MonPhu!=-1){
				$sql.= "UPDATE `sanpham` SET SOLUONG=SOLUONG-$mSOLUONG WHERE MASP=$MonPhu";
			}
		}
		$result = $conn->multi_query($sql);
 		}
 		else{
 		$sql = "INSERT INTO `chitietbanhang` VALUES ('$MAHD','$MASP','$SOLUONG',0,'$CHUTHICH');";
 		// Nếu SP có tính số lượng tức $MonPhu=0
 		if (xulythuoc($MASP,$mSOLUONG)==false) {
	 		if ($MonPhu==0) {
	 			$sql.= "UPDATE `sanpham` SET SOLUONG=SOLUONG-$mSOLUONG WHERE MASP=$MASP";	
	 		}elseif($MonPhu!=-1){
	 			$sql.= "UPDATE `sanpham` SET SOLUONG=SOLUONG-$mSOLUONG WHERE MASP=$MonPhu";	
	 		}
	 	}
		$result = $conn->multi_query($sql);
 		}
	}
Update_TRANGTHAI($TENBAN,1);  // XÁC NHẬN BÀN CÓ MÓN
TongTien($MAHD);
responseApp($TENBAN,$jsondata);

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
		$SOLUONG = $row['SOLUONG'] - $SOLUONG;
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
		$SOLUONG = $row['SOLUONG']-$SOLUONG;
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