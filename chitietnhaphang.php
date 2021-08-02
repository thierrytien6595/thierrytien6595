<?php
$conn = mysqli_connect('localhost', 'root', '', 'thachcoffee') or die ('Lỗi kết nối');
$query = "SELECT * FROM nhaphang ORDER BY ID DESC LIMIT 6";
$result = mysqli_query($conn, $query);
$chitietArray = array();
$nhaphangArray = array();
while($row = mysqli_fetch_assoc($result)){
	array_push($nhaphangArray, new NhapHang($row['ID'],$row['MANV'],$row['TIME']));
	}
foreach($nhaphangArray as $key => $value) {
		$ID = $nhaphangArray[$key]->ID;
		$MANV = $nhaphangArray[$key]->MANV;
		$query = "SELECT * FROM nhanvien WHERE MANV=$MANV";
		$result = mysqli_query($conn, $query);
		$row = $result->fetch_assoc();
		$TENNV = $row['TENNV'];
		$TIME = $nhaphangArray[$key]->TIME;
		$query = "SELECT * FROM chitietnhaphang WHERE ID=$ID";
		$result = mysqli_query($conn, $query);
		$SPArray = array();
		$maSPArray = array();
		while($row = mysqli_fetch_assoc($result))
		{
			array_push($maSPArray,new maSP($row['MASP'],$row['SOLUONG']));
		}
		$TONGTIEN=0;
		foreach ($maSPArray as $key => $value) {
		$MASP = $maSPArray[$key]->MASP;
		$SOLUONG = $maSPArray[$key]->SOLUONG;
		$query = "SELECT * FROM sanpham WHERE MASP=$MASP";
		$result = mysqli_query($conn, $query);
		$row = $result->fetch_assoc();
		$TENSP = $row['TENSP'];
		$DONGIA = $row['GIASP'];
		$TONGTIEN +=$SOLUONG*$DONGIA; 
		array_push($SPArray,new SPNHAP($TENSP,$SOLUONG,$DONGIA));
	}
	array_push($chitietArray, new chitiet($ID,$TENNV,$TIME,$TONGTIEN,$SPArray));
}
echo json_encode($chitietArray);
class NhapHang{
	function NhapHang($ID,$MANV,$TIME){
		$this -> ID=$ID;
		$this -> MANV=$MANV;
		$this -> TIME=$TIME;
	}
}
class SPNHAP{
	function SPNHAP($TENSP,$SOLUONG,$DONGIA){
		$this -> TENSP=$TENSP;
		$this -> SOLUONG=$SOLUONG;
		$this -> DONGIA=$DONGIA;
	}
}
class maSP{
	function maSP($MASP,$SOLUONG){
		$this -> MASP=$MASP;
		$this -> SOLUONG=$SOLUONG;
	}
}
class chitiet{
	function chitiet($ID,$TENNV,$TIME,$TONGTIEN,$SPNHAP){
		$this -> ID=$ID;
		$this -> TENNV=$TENNV;
		$this -> TIME=$TIME;
		$this -> TONGTIEN=$TONGTIEN;
		$this -> SPNHAP=$SPNHAP;
	}
}
?>