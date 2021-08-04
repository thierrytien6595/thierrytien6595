<?php
$conn = mysqli_connect('localhost', 'root', '', 'thachcoffee') or die ('Lỗi kết nối');
$query = "SELECT * FROM nhanvien ORDER BY MANV DESC"; 
$result = mysqli_query($conn, $query);
class NHANVIEN{
	function NHANVIEN($MANV,$TENNV,$LUONG,$SDT){
		$this -> MANV=$MANV;
		$this -> TENNV=$TENNV;
		$this -> LUONG=$LUONG;
		$this -> SDT=$SDT;
	}
}
$DSArray = array();
while($row = mysqli_fetch_assoc($result)){
			array_push($DSArray, new NHANVIEN($row['MANV'],$row['TENNV'],$row['LUONG'],$row['SDT']));	
	}
echo json_encode($DSArray);
?>