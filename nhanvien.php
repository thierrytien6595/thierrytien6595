<?php
include 'myfunction.php';
if (isset($_GET['TENNV'])&&isset($_GET['LUONG'])&&isset($_GET['SDT'])){
	$TENNV  = $_GET['TENNV'];
	$MANV = Get_MANV($TENNV);
	$LUONG  = $_GET['LUONG'];
	$SDT 	= $_GET['SDT'];
	if ($MANV==null) {
		insert_nhanvien($TENNV,$LUONG,$SDT);
	}else{
		update_nhanvien($MANV,$TENNV,$LUONG,$SDT);
	}
}
if (isset($_GET['MANV'])&&!isset($_GET['LUONG'])){
		$MANV=$_GET['MANV'];
		delete_nhanvien($MANV);
	}

function update_nhanvien($MANV,$TENNV,$LUONG,$SDT){
	include 'connect.php';
	$sql = "UPDATE `nhanvien` SET `TENNV`='$TENNV',`LUONG`='$LUONG',`SDT`='$SDT' WHERE MANV=$MANV";
	echo $sql;
	$result = $conn->query($sql); //echo $result;
	$conn->close();
}
function delete_nhanvien($MANV){
	include 'connect.php';
	$sql = "DELETE FROM `nhanvien` WHERE MANV=$MANV";
	echo $sql;
	$result = $conn->query($sql); //echo $result;
	$conn->close();
}
function insert_nhanvien($TENNV,$LUONG,$SDT){
	include 'connect.php';
	$sql = "INSERT INTO `nhanvien` (`TENNV`, `LUONG`,`SDT`) VALUES ('$TENNV',$LUONG,$SDT)";
	echo $sql;
	$result = $conn->query($sql); //echo $result;
	$conn->close();
}
?>