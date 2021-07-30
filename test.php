<?php
$conn = mysqli_connect('localhost', 'root', '', 'thachcoffee') or die ('Lỗi kết nối');

// Tổng tiền đang phục vụ chưa thanh toán
$query = "SELECT SUM(TONGTIEN) AS TONGTIEN FROM hoadon WHERE TIMEOUT='0000-00-00 00:00:00'"; 
$result = mysqli_query($conn, $query);
$row = $result->fetch_assoc();
$TONGTIEN = $row['TONGTIEN'];
if ($TONGTIEN==null) {
	$TONGTIEN="0";
  } 
// Số lượng bàn đang phục vụ
$query = "SELECT COUNT(MABAN) AS SOBAN_PV FROM ban WHERE TRANGTHAI=1"; 
$result = mysqli_query($conn, $query);
$row = $result->fetch_assoc();
$SOBAN_PV = $row['SOBAN_PV'];
// Số lượng món đã phục vụ
$query = "SELECT SUM(SOLUONG) AS SOMON_PV FROM chitietbanhang WHERE TRANGTHAIMON=1"; 
$result = mysqli_query($conn, $query);
$row = $result->fetch_assoc();
$SOMON_PV = $row['SOMON_PV'];
if ($SOMON_PV==null) {
	$SOMON_PV = "0";
}
// Số lượng món chưa phục vụ
$query = "SELECT SUM(SOLUONG) AS SOMON_CPV FROM chitietbanhang WHERE TRANGTHAIMON=0"; 
$result = mysqli_query($conn, $query);
$row = $result->fetch_assoc();
$SOMON_CPV = $row['SOMON_CPV'];
if ($SOMON_CPV==null) {
	$SOMON_CPV = "0";
}
// Số lượng bàn còn trống 
$query = "SELECT COUNT(MABAN) AS SOBAN_CPV FROM ban WHERE TRANGTHAI=0"; 
$result = mysqli_query($conn, $query);
$row = $result->fetch_assoc();
$SOBAN_CPV = $row['SOBAN_CPV'];
if ($SOBAN_CPV==null) {
	$SOBAN_CPV = "0";
}

class QUANLY{
	// PV là đang phục vụ
	// CPV là chưa phục vụ
	// TONGTIEN là số tiền chưa thanh toán

	function QUANLY($TONGTIEN,$SOBAN_PV,$SOMON_PV,$SOMON_CPV,$SOBAN_CPV,$DOANHTHU){
		$this -> TONGTIEN=$TONGTIEN;
		$this -> SOBAN_PV=$SOBAN_PV;
		$this -> SOMON_PV=$SOMON_PV;
		$this -> SOMON_CPV=$SOMON_CPV;
		$this -> SOBAN_CPV=$SOBAN_CPV;
		$this -> DOANHTHU=$DOANHTHU;
	}
}

class DOANHTHU{
	function DOANHTHU($DOANHTHU,$ID){
		$this -> DOANHTHU = $DOANHTHU;
		$this -> ID = $ID;
	}
}

$QLArray = array();
$DOANHTHUArray = array();
$ID=$_GET['ID'];
if($ID==0){
	// Lấy doanh thu trong từng giờ hôm nay
	for ($i=0; $i < 24 ; $i++) { 
		$j=$i+1;
		$query = "SELECT SUM(DATHANHTOAN) AS DOANHTHU FROM hoadon WHERE TIMEOUT>ADDDATE(CURRENT_DATE,INTERVAL $i HOUR) AND TIMEOUT<ADDDATE(CURRENT_DATE,INTERVAL $j HOUR)"; 
		$result = mysqli_query($conn, $query);
		$row = $result->fetch_assoc();
		$DOANHTHU = $row['DOANHTHU'];
		if ($DOANHTHU==null) {
			$DOANHTHU="0";
	  	}
	  	array_push($DOANHTHUArray, new DOANHTHU($DOANHTHU,$i));
	}
}
if ($ID==1) {
	// Lấy doanh thu trong từng ngày của tháng
	$numdayofmonth = new DateTime('last day of this month');
	$numdayofmonth = $numdayofmonth->format('d');
	for ($i=0; $i < $numdayofmonth ; $i++) { 
		$j=$i+1;
		$date=date("Y-m-01");
		$query = "SELECT SUM(DATHANHTOAN) AS DOANHTHU FROM hoadon WHERE TIMEOUT>ADDDATE('$date', INTERVAL $i DAY) AND TIMEOUT<ADDDATE('$date', INTERVAL $j DAY)";
		$result = mysqli_query($conn, $query);
		$row = $result->fetch_assoc();
		$DOANHTHU = $row['DOANHTHU'];
		if ($DOANHTHU==null) {
			$DOANHTHU="0";
	  	}
	  	array_push($DOANHTHUArray, new DOANHTHU($DOANHTHU,$i+1));
	}
}

if ($ID==2) {
	$datenow = date('Y-m-d');// date now
	$mon = date_create()->modify('Next Monday')->format('Y-m-d');
	$sotuanhienthi = 8;
	$week = date_create()->format('W')-$sotuanhienthi;
	// Lấy doanh thu trong từng Tuần của tháng
	for ($i=$sotuanhienthi; $i > 0 ; $i--) { 
		$j=$i-1;
		$query = "SELECT SUM(DATHANHTOAN) AS DOANHTHU FROM hoadon WHERE TIMEOUT>ADDDATE('$mon', INTERVAL -$i WEEK) AND TIMEOUT<ADDDATE('$mon', INTERVAL -$j WEEK)";
		$result = mysqli_query($conn, $query);
		$row = $result->fetch_assoc();
		$DOANHTHU = $row['DOANHTHU'];
		if ($DOANHTHU==null) {
			$DOANHTHU="0";
	  	}
	  	$week++;
	  	array_push($DOANHTHUArray, new DOANHTHU($DOANHTHU,$week));
	  	
	}
}

if ($ID==3) {
	$date= date('Y-01-01');// date now
	$sothanghienthi = 12;
	$month=0;
	// Lấy doanh thu trong từng Tháng trong năm
	for ($i=0; $i < $sothanghienthi ; $i++) { 
		$j=$i+1;
		$query = "SELECT SUM(DATHANHTOAN) AS DOANHTHU FROM hoadon WHERE TIMEOUT>ADDDATE('$date', INTERVAL $i MONTH) AND TIMEOUT<ADDDATE('$date', INTERVAL $j MONTH)";
		$result = mysqli_query($conn, $query);
		$row = $result->fetch_assoc();
		$DOANHTHU = $row['DOANHTHU'];
		if ($DOANHTHU==null) {
			$DOANHTHU="0";
	  	}
	  	$month++;
	  	array_push($DOANHTHUArray, new DOANHTHU($DOANHTHU,$month));
	  	
	}
}
array_push($QLArray, new QUANLY($TONGTIEN,$SOBAN_PV,$SOMON_PV,$SOMON_CPV,$SOBAN_CPV,$DOANHTHUArray));
echo json_encode($QLArray);
?>