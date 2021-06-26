<?php
$conn = mysqli_connect('localhost', 'root', '', 'thachcoffee') or die ('Lỗi kết nối');
$query = "SELECT * FROM ban"; 
$result = mysqli_query($conn, $query);
class Ban{
	function Ban($MABAN,$TENBAN,$TRANGTHAI){
		$this -> MABAN=$MABAN;
		$this -> TENBAN=$TENBAN;
		$this -> TRANGTHAI=$TRANGTHAI;		
	}
}
$BanArray = array();
while($row = mysqli_fetch_assoc($result)){
		array_push($BanArray, new Ban($row['MABAN'],$row['TENBAN'],$row['TRANGTHAI']));
	}
echo json_encode($BanArray);
?>