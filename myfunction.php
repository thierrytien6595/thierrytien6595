<?php
// Hàm Lấy MABAN thông qua tenban function Lay_ma_ban($tenban)
function Get_MABAN($tenban)
	{
		include 'connect.php';
		$sql = "SELECT MABAN FROM `ban` WHERE TENBAN='$tenban'";
		$result = $conn->query($sql); //echo $result;
		$row = $result->fetch_assoc();
		$conn->close();
		return $row['MABAN'];
		
	}

function Get_MAHD($maban)
	{
		include 'connect.php';
		$sql = "SELECT MAHD FROM `hoadon` WHERE MABAN='$maban'";
		$result = $conn->query($sql); //echo $result;
		$row = $result->fetch_assoc();
		$conn->close();
		return $row['MAHD'];
		
	}

function Add_chitietbanhang($MAHD,$MASP,$SOLUONG=1){
	include 'connect.php';
	$sql = "INSERT INTO `chitietbanhang` (`MAHD`,`MASP`,`SOLUONG`) VALUES ('$MAHD','$MASP','$SOLUONG')";
	if ($conn->query($sql) === TRUE) 
		{
  			$conn->close();
		} else 
		{	
  			echo "Lỗi truy vấn: " . $sql . "<br>" . $conn->error;
		}
		
}

function Add_hoadon($tenban)
	{
		$maban = Get_MABAN($tenban);
		include 'connect.php';
		$sql = "INSERT INTO `hoadon` (`MABAN`) VALUES ('$maban')";
		if ($conn->query($sql) === TRUE) 
		{
			
  			$kq= $conn->insert_id;
  			$conn->close();
  			return $kq;
		} else 
		{	
  			echo "Lỗi truy vấn: " . $sql . "<br>" . $conn->error;
		}
		
	}
?>