<?php 
// INCLUDE
include 'myfunction.php';

// MAIN
if(isset($_GET['tenban'])&&isset($_GET['jsondata'])){
$TENBAN  = $_GET['tenban'];
$TRANGTHAIBAN = Get_TRANGTHAIBAN($TENBAN);
$temp_MABAN = Get_MABAN($TENBAN);
$MAHD = Get_MAHD($temp_MABAN);
$jsondata = $_GET['jsondata'];
$myjson1 = json_decode($jsondata);
$count = @count($myjson1);

include 'connect.php';
$sql = "SELECT * FROM `chitietbanhang` WHERE MAHD=$MAHD";
$result = $conn->query($sql);
foreach ($myjson1 as $key => $value) {
$tensp = $myjson1[$key]->TENSP;
$MASP= Get_MASP($tensp);
$CHUTHICH = $myjson1[$key]->CHUTHICH;
$SOLUONG = $myjson1[$key]->SOLUONG;
$TRANGTHAIMON = $myjson1[$key]->TRANGTHAIMON;
include 'connect.php';
$sql = "SELECT * FROM `chitietbanhang` WHERE MAHD=$MAHD AND MASP=$MASP AND TRANGTHAIMON=$TRANGTHAIMON";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
	$row = $result->fetch_assoc();
	$SOLUONGtemp = $row['SOLUONG']-$SOLUONG;
	if($SOLUONGtemp!=0){
	$sql1 = "UPDATE `chitietbanhang` SET SOLUONG=$SOLUONGtemp WHERE MAHD=$MAHD AND MASP=$MASP AND TRANGTHAIMON=$TRANGTHAIMON;
			INSERT INTO `chitietbanhang` VALUES ('$MAHD','$MASP','$SOLUONG',2,'$CHUTHICH');";
			echo $sql1."<br>";
			$result1 = $conn->multi_query($sql1);
	
	}else{
	$sql1 = "UPDATE `chitietbanhang` SET TRANGTHAIMON=2 WHERE MAHD=$MAHD AND MASP=$MASP AND TRANGTHAIMON=$TRANGTHAIMON"; echo $sql1."<br>";
		$result1 = $conn->query($sql1);
	}
	
	}
}
echo "<br>Tính tổng tiền với MAHD=$MAHD";
$result = $conn->query($sql);
$conn->close();
TongTien($MAHD);
Update_TRANGTHAIBAN($TENBAN);
}
if(isset($_GET['tenban'])&&!isset($_GET['jsondata'])){
	include 'connect.php';
	$TENBAN  = $_GET['tenban'];
	$TRANGTHAIBAN = Get_TRANGTHAIBAN($TENBAN);
	$temp_MABAN = Get_MABAN($TENBAN);
	$MAHD = Get_MAHD($temp_MABAN);
	$sql1 = "UPDATE `chitietbanhang` SET TRANGTHAIMON=2 WHERE MAHD=$MAHD"; echo $sql1."<br>";
	$result1 = $conn->query($sql1);
	$conn->close();
	Update_TRANGTHAI($TENBAN,0);
}

?>