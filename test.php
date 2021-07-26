<?php
$conn = mysqli_connect('localhost', 'root', '', 'thachcoffee') or die ('Lỗi kết nối');
$query = "SELECT huymon.ID,huymon.MANV,huymon.LYDO,huymon.TIME,chitiethuymon.MASP,chitiethuymon.SOLUONG FROM chitiethuymon LEFT JOIN huymon ON chitiethuymon.ID = huymon.ID ORDER BY huymon.ID DESC"; 
$result = mysqli_query($conn, $query);
class HuyMon{
	function HuyMon($ID,$MANV,$LYDO,$TIME,$MASP,$SOLUONG){
		$this -> ID=$ID;
		$this -> MANV=$MANV;
		$this -> LYDO=$LYDO;
		$this -> TIME=$TIME;
		$this -> MASP=$MASP;
		$this -> SOLUONG=$SOLUONG;
	}
}
$huymonArray = array();
while($row = mysqli_fetch_assoc($result)){
	array_push($huymonArray, new HuyMon($row['ID'],$row['MANV'],$row['LYDO'],$row['TIME'],$row['MASP'],$row['SOLUONG']));
	}
echo json_encode($huymonArray);
?>