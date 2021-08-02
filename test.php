<?php 
// INCLUDE
include 'myfunction.php';
$data = $_GET['data'];
$myjson = json_decode($data);
include 'connect.php';
foreach ($myjson as $key => $value) {
	$MASP = $myjson[$key]->MASP;
	$SOLUONG = $myjson[$key]->SOLUONG;
	$sql = "SELECT SOLUONG FROM `sanpham` WHERE MASP=$MASP";
	$result = $conn->query($sql);
	$row = $result->fetch_assoc();
	$SOLUONG += $row['SOLUONG'];
	$sql = "UPDATE `sanpham` SET SOLUONG=$SOLUONG WHERE MASP=$MASP";
	$result = $conn->query($sql);
}
?>