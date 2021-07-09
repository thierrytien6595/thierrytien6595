<?php
$conn = mysqli_connect('localhost', 'root', '', 'thachcoffee') or die ('Lỗi kết nối');
$query = "SELECT * FROM ban"; 
$result = mysqli_query($conn, $query);
class SanPham{
	function SanPham($MABAN,$TENBAN,$TRANGTHAI){
		$this -> MABAN=$MABAN;
		$this -> TENBAN=$TENBAN;
		$this -> TRANGTHAI=$TRANGTHAI;
	}
}
$SPArray = array();
while($row = mysqli_fetch_assoc($result)){
		array_push($SPArray, new SanPham($row['MABAN'],$row['TENBAN'],$row['TRANGTHAI']));
	}
echo json_encode($SPArray);
?>