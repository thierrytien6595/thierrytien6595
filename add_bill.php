<?php 
// $TENBAN1  = $_POST['TENBAN'];
// $jsondata1 = $_POST['jsondata'];
// $myjson = json_decode($jsondata1);
// class SanPham{
// 	function SanPham($TENBAN,$jsondata){
// 		$this -> TENBAN=$TENBAN;
// 		$this -> jsondata=$jsondata;
// 	}
// }
// $SPArray = array();
// foreach ($myjson as $key => $value) {
// 	array_push($SPArray, new SanPham($TENBAN1,$myjson[$key]->TENSP));
// }
// echo json_encode($SPArray);
include 'myfunction.php';
$MASP=1;
$SOLUONG=2;
$MAHD=Add_hoadon("BÀN 11");
Add_chitietbanhang($MAHD,$MASP,$SOLUONG);
?>