<?php 
// INCLUDE
include 'myfunction.php';
if (isset($_GET['data'])) {
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
}elseif(isset($_GET['data1']))
{
	$data = $_GET['data1'];
	$myjson = json_decode($data);
	include 'connect.php';
	$sql = "INSERT INTO `nhaphang` (`MANV`, `TIME`) VALUES (1,CURRENT_TIMESTAMP)";
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