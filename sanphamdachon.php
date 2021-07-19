<?php
include 'myfunction.php';
$TENBAN  = $_GET['TENBAN'];
$maban = Get_MABAN($TENBAN);
$mahd = Get_MAHD($maban);
class SanPhamDaChon{
	function SanPhamDaChon($TENSP,int $SOLUONG,$CHUTHICH,$TRANGTHAIMON,$MAHD){
		$this -> TENSP=$TENSP;
		$this -> SOLUONG=$SOLUONG;
		$this -> CHUTHICH=$CHUTHICH;
		$this -> TRANGTHAIMON=$TRANGTHAIMON;
		$this -> MAHD=$MAHD;
	}
}
$SPArray = array();
include 'connect.php';
$sql = "SELECT chitietbanhang.SOLUONG,sanpham.TENSP,chitietbanhang.TRANGTHAIMON,chitietbanhang.CHUTHICH,chitietbanhang.MAHD FROM chitietbanhang INNER JOIN sanpham ON chitietbanhang.MASP = sanpham.MASP AND chitietbanhang.MAHD = $mahd AND chitietbanhang.TRANGTHAIMON!=2";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
    array_push($SPArray, new SanPhamDaChon($row['TENSP'],$row['SOLUONG'],$row['CHUTHICH'],$row['TRANGTHAIMON'],$row['MAHD']));
  }
} else {
  echo "0 results";
}
$conn->close();
echo json_encode($SPArray);
?>