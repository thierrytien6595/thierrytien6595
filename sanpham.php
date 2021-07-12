<?php
$conn = mysqli_connect('localhost', 'root', '', 'thachcoffee') or die ('Lỗi kết nối');
$query = "SELECT * FROM sanpham"; 
$result = mysqli_query($conn, $query);
class SanPham{
	function SanPham($TENSP,$HINHSP,$GIASP,$MASP){
		$this -> TENSP=$TENSP;
		$this -> HINHSP=$HINHSP;
		$this -> GIASP=$GIASP;
		$this -> MASP=$MASP;
	}
}
$SPArray = array();
while($row = mysqli_fetch_assoc($result)){
	array_push($SPArray, new SanPham($row['TENSP'],$row['HINHSP'],$row['GIASP'],$row['MASP']));	}
echo json_encode($SPArray);
?>