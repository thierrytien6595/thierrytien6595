<?php
include 'myfunction.php';
if (isset($_GET['MANV'])&&isset($_GET['LYDO'])&&isset($_GET['TENBAN'])&&isset($_GET['DATA'])) {
	$MANV  = $_GET['MANV'];
	$LYDO  = $_GET['LYDO'];
	$TENBAN  = $_GET['TENBAN'];
	$MABAN = Get_MABAN($TENBAN);
	$MAHD = Get_MAHD($MABAN);
	$DATA = $_GET['DATA'];
	$myjson = json_decode($DATA);
	insert_huymon($MANV,$LYDO,$MAHD);
	insert_chitiethuymon($MAHD,$MASP,$SOLUONG,$myjson);
}
function insert_huymon($MANV,$LYDO,$MAHD){
	include 'connect.php';
	$sql = "INSERT INTO `huymon` (`MANV`, `LYDO`, `TIME`, `MAHD`) VALUES (1,'$LYDO',CURRENT_TIMESTAMP,$MAHD)";
	echo $sql."<br>";
	$result = $conn->query($sql); //echo $result;
	$conn->close();
}

function insert_chitiethuymon($MAHD,$MASP,$SOLUONG,$myjson)
{
	include 'connect.php';
	$sql = "SELECT ID FROM `huymon` ORDER BY ID DESC LIMIT 1";
	$result = $conn->query($sql);
	$row = $result->fetch_assoc();
	$ID = $row['ID'];
	foreach ($myjson as $key => $value) {
	$TENSP = $myjson[$key]->TENSP;
	$MASP= Get_MASP($TENSP);
	$SOLUONG = $myjson[$key]->SOLUONG;
	include 'connect.php';
	$sql = "INSERT INTO `chitiethuymon` VALUES($ID,$MASP,$SOLUONG)";
	echo $sql."<br>";
	$result = $conn->query($sql);
	$conn->close();
	}
}
?>