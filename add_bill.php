<?php 
// INCLUDE
include 'myfunction.php';

// MAIN

$TENBAN  = $_POST['TENBAN'];
$TRANGTHAIBAN = Get_TRANGTHAIBAN($TENBAN);
if ($TRANGTHAIBAN==0) { // NẾU BÀN CHƯA CÓ MÓN THÌ THÊM HÓA ĐƠN
	$MAHD=Add_hoadon($TENBAN);
}
else{ // NẾU BÀN CÓ MÓN RỒI THÌ LẤY MÃ HÓA ĐƠN CŨ
	$temp_MABAN = Get_MABAN($TENBAN);
	$MAHD = Get_MAHD($temp_MABAN);
}

$jsondata = $_POST['jsondata'];
$myjson1 = json_decode($jsondata);
Delete_chitietdonhang($MAHD);	
foreach ($myjson1 as $key => $value) {
			$tensp = $myjson1[$key]->TENSP;
			$masp= Get_MASP($tensp);
			$soluong = $myjson1[$key]->SOLUONG;
			$trangthaimon = 0;
			$chuthich = $myjson1[$key]->CHUTHICH;
			Add_chitietbanhang($MAHD,$masp,$soluong,$trangthaimon,$chuthich);
		}
Update_TRANGTHAI($TENBAN,1);  // XÁC NHẬN BÀN CÓ MÓN
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