<?php
if (isset($_GET['TENNV'])&&isset($_GET['LUONG'])) {
	$TENNV  = $_GET['TENNV'];
	$LUONG  = $_GET['LUONG'];
	insert_nhanvien($MANV,$LYDO,$MAHD,$DATA);
}
function insert_nhanvien($TENNV,$LUONG){
	include 'connect.php';
	$sql = "INSERT INTO `nhanvien` (`TENNV`, `LUONG`) VALUES ('$TENNV',$LUONG)";
	echo $sql;
	$result = $conn->query($sql); //echo $result;
	$conn->close();
}
?>