<?php

$conn = mysqli_connect('localhost', 'root', '', 'thachcoffee') or die ('Lỗi kết nối');
$query = "SELECT * FROM sanpham"; 
$result = mysqli_query($conn, $query);
class SanPham{
	function SanPham($TENSP,$HINHSP,$GIASP,$MASP,$SOLUONG,$MonPhu){
		$this -> TENSP=$TENSP;
		$this -> HINHSP=$HINHSP;
		$this -> GIASP=$GIASP;
		$this -> MASP=$MASP;
		$this -> SOLUONG=$SOLUONG;
		$this -> MonPhu=$MonPhu;
	}
}
class QLSanPham{
	function QLSanPham($TENSP,$HINHSP,$GIASP,$MASP,$SOLUONG,$MonPhu,$MALOAISP){
		$this -> TENSP=$TENSP;
		$this -> HINHSP=$HINHSP;
		$this -> GIASP=$GIASP;
		$this -> MASP=$MASP;
		$this -> SOLUONG=$SOLUONG;
		$this -> MonPhu=$MonPhu;
		$this -> MALOAISP=$MALOAISP;
	}
}
$SPArray = array();
while($row = mysqli_fetch_assoc($result)){
	if (isset($_GET['QL'])) {
		if ($row['MonPhu']==0) {
		array_push($SPArray, new QLSanPham($row['TENSP'],$row['HINHSP'],$row['GIASP'],$row['MASP'],$row['SOLUONG'],(int)$row['MonPhu'],$row['MALOAISP']));	}
	}else{
	array_push($SPArray, new SanPham($row['TENSP'],$row['HINHSP'],$row['GIASP'],$row['MASP'],$row['SOLUONG'],(int)$row['MonPhu']));
		}
	}
	echo json_encode($SPArray);
?>