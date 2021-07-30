<?php
$conn = mysqli_connect('localhost', 'root', '', 'thachcoffee') or die ('Lỗi kết nối');
$query = "SELECT ban.MABAN,ban.TENBAN,ban.TRANGTHAI,hoadon.TONGTIEN FROM ban LEFT JOIN hoadon ON ban.MABAN=hoadon.MABAN AND hoadon.TONGTIEN!=0 AND hoadon.TIMEOUT='0000-00-00 00:00:00' ORDER BY ban.MABAN ASC"; 
$result = mysqli_query($conn, $query);
class SanPham{
	function SanPham($MABAN,$TENBAN,$TRANGTHAI,$TONGTIEN){
		$this -> MABAN=$MABAN;
		$this -> TENBAN=$TENBAN;
		$this -> TRANGTHAI=$TRANGTHAI;
		$this -> TONGTIEN=$TONGTIEN;
	}
}
$SPArray = array();
while($row = mysqli_fetch_assoc($result)){
		if ($row['TRANGTHAI']==0) {
			array_push($SPArray, new SanPham($row['MABAN'],$row['TENBAN'],$row['TRANGTHAI'],0));
		}else{
			array_push($SPArray, new SanPham($row['MABAN'],$row['TENBAN'],$row['TRANGTHAI'],$row['TONGTIEN']));
		}
		
	}
echo json_encode($SPArray);
?>