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
	$masp= Get_MASP($tensp);
	$CHUTHICH = $myjson1[$key]->CHUTHICH;
	$SOLUONG = $myjson1[$key]->SOLUONG;
	include 'connect.php';
	$sql = "SELECT * FROM `chitietbanhang` WHERE MAHD=$MAHD AND MASP=$masp AND TRANGTHAIMON=0";
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		$CHUTHICH = $CHUTHICH." ".$row['CHUTHICH'];
		$SOLUONG = $SOLUONG + $row['SOLUONG'];
		$sql = "UPDATE `chitietbanhang` SET SOLUONG=$SOLUONG,CHUTHICH='$CHUTHICH' WHERE MAHD=$MAHD AND MASP=$masp AND TRANGTHAIMON=0";
		$result = $conn->query($sql);
 		}
 		else{
 		$sql = "INSERT INTO `chitietbanhang` VALUES ('$MAHD','$masp','$SOLUONG',0,'$CHUTHICH')";
 		$result = $conn->query($sql);
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
?>