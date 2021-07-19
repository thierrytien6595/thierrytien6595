<?php
include 'myfunction.php';
$TENBAN  = $_GET['TENBAN'];
$MABANgheptoi = $_GET['MABANgheptoi'];
// lấy mã hóa đơn của bàn cần xóa
$temp_MABAN = Get_MABAN($TENBAN);
$MAHDxoa = Get_MAHD($temp_MABAN);
// Lấy mã hóa đơn của bàn cần ghép vào
$MAHD = Get_MAHD($MABANgheptoi);

include 'connect.php';
$sql = "SELECT * FROM `chitietbanhang` WHERE MAHD=$MAHDxoa";
echo $sql."<br>";
$result = $conn->query($sql);
while($oldrow = $result->fetch_assoc()) {
// Tìm trong đơn này có các món của đơn trước hay không
$newsql="SELECT * FROM `chitietbanhang` WHERE MAHD=$MAHD AND MASP=".$oldrow['MASP']." AND TRANGTHAIMON=".$oldrow['TRANGTHAIMON'];
echo $newsql."<br>";
	$newresult = $conn->query($newsql);
//Nếu có thì update lại số lượng với chú thích
	if ($newresult->num_rows > 0) {
		$newrow = $newresult->fetch_assoc();
		$CHUTHICH = $newrow['CHUTHICH']." ".$oldrow['CHUTHICH'];
		$SOLUONG = $newrow['SOLUONG'] + $oldrow['SOLUONG'];
		$sql1 = "UPDATE `chitietbanhang` SET SOLUONG=$SOLUONG,CHUTHICH='$CHUTHICH' WHERE MAHD=$MAHD AND MASP=".$oldrow['MASP']." AND TRANGTHAIMON=".$oldrow['TRANGTHAIMON'];
		echo $sql1."<br>";
		$result1 = $conn->query($sql1);
	}
// Nếu không có thì thêm món mới
	else{
		$oldMASP=$oldrow['MASP'];
		$oldSOLUONG=$oldrow['SOLUONG'];
		$oldTRANGTHAIMON=$oldrow['TRANGTHAIMON'];
		$oldCHUTHICH=$oldrow['CHUTHICH'];
		$sql = "INSERT INTO `chitietbanhang` VALUES ('$MAHD','$oldMASP','$oldSOLUONG','$oldTRANGTHAIMON','$oldCHUTHICH')";echo $sql."<br>";
 		$result1 = $conn->query($sql);
	}
  }

Delete_chitietdonhang($MAHDxoa);
Delete_HOADON($MAHDxoa);
Update_TRANGTHAI($TENBAN,0);
TongTien($MAHD);
?>