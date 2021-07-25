<?php
if (isset($_GET['MANV'])&&isset($_GET['LYDO'])&&isset($_GET['MAHD'])&&isset($_GET['DATA'])) {
	$MANV  = $_GET['MANV'];
	$LYDO  = $_GET['LYDO'];
	$MAHD = $_GET['MAHD'];
	$DATA = $_GET['DATA'];
	insert_huymon($MANV,$LYDO,$MAHD,$DATA);
}
function insert_huymon($MANV,$LYDO,$MAHD,$DATA){
	include 'connect.php';
	$sql = "INSERT INTO `huymon` (`MANV`, `LYDO`, `TIME`, `MAHD`) VALUES (1,'$LYDO',CURRENT_TIMESTAMP,$MAHD)";
	echo $sql;
	$result = $conn->query($sql); //echo $result;
	$conn->close();
}
?>