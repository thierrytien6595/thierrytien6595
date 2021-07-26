<?php
$conn = mysqli_connect('localhost', 'root', '', 'thachcoffee') or die ('Lỗi kết nối');
$query = "SELECT * FROM huymon ORDER BY ID DESC LIMIT 4";
$result = mysqli_query($conn, $query);
$chitietArray = array();
$huymonArray = array();

while($row = mysqli_fetch_assoc($result)){
	array_push($huymonArray, new HuyMon($row['ID'],$row['MANV'],$row['LYDO'],$row['TIME']));
	}
foreach ($huymonArray as $key => $value) {
		$ID = $huymonArray[$key]->ID;
		$MANV = $huymonArray[$key]->MANV;
		$query = "SELECT * FROM nhanvien WHERE MANV=$MANV";
		$result = mysqli_query($conn, $query);
		$row = $result->fetch_assoc();
		$TENNV = $row['TENNV'];
		$LYDO = $huymonArray[$key]->LYDO;
		$TIME = $huymonArray[$key]->TIME;
		$query = "SELECT * FROM chitiethuymon WHERE ID=$ID";
		$result = mysqli_query($conn, $query);
		$SPArray = array();
		$maSPArray = array();
		while($row = mysqli_fetch_assoc($result))
		{
			array_push($maSPArray,new maSP($row['MASP'],$row['SOLUONG']));
		}
		foreach ($maSPArray as $key => $value) {
		$MASP = $maSPArray[$key]->MASP;
		$SOLUONG = $maSPArray[$key]->SOLUONG;
		$query = "SELECT * FROM sanpham WHERE MASP=$MASP";
		$result = mysqli_query($conn, $query);
		$row = $result->fetch_assoc();
		$TENSP = $row['TENSP'];
		array_push($SPArray,new SP($TENSP,$SOLUONG));
	}
	array_push($chitietArray, new chitiet($ID,$TENNV,$LYDO,$TIME,$SPArray));
}
echo json_encode($chitietArray);
class HuyMon{
	function HuyMon($ID,$MANV,$LYDO,$TIME){
		$this -> ID=$ID;
		$this -> MANV=$MANV;
		$this -> LYDO=$LYDO;
		$this -> TIME=$TIME;
	}
}
class SP{
	function SP($TENSP,$SOLUONG){
		$this -> TENSP=$TENSP;
		$this -> SOLUONG=$SOLUONG;
	}
}
class maSP{
	function maSP($MASP,$SOLUONG){
		$this -> MASP=$MASP;
		$this -> SOLUONG=$SOLUONG;
	}
}
class chitiet{
	function chitiet($ID,$TENNV,$LYDO,$TIME,$SP){
		$this -> ID=$ID;
		$this -> TENNV=$TENNV;
		$this -> LYDO=$LYDO;
		$this -> TIME=$TIME;
		$this -> SP=$SP;
	}
}
?>