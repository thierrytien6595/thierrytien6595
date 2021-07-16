<?php
// CAC HAM 	function Get_MABAN($tenban)
// 		   	function Get_MASP($tensp)
// 		   	function Update_TRANGTHAI($tenban,$trangthai)
//			function Get_MAHD($maban)
//			function Add_chitietbanhang($MAHD,$MASP,$SOLUONG=1,$TRANGTHAIMON,$CHUTHICH)
// 			function Add_hoadon($tenban)
//			function Get_TRANGTHAIBAN($tenban)
//			function Delete_chitietdonhang($MAHD)
function Delete_HOADON($MAHD)
	{
		include 'connect.php';
		$sql = "DELETE FROM `hoadon` WHERE `hoadon`.`MAHD`=$MAHD";
		$result = $conn->query($sql); //echo $result;
		$conn->close();
	}
function Delete_chitietdonhang($MAHD)
	{
		include 'connect.php';
		$sql = "DELETE FROM `chitietbanhang` WHERE MAHD=$MAHD";
		$result = $conn->query($sql); //echo $result;
		$conn->close();
	}
function Get_TRANGTHAIBAN($tenban)
	{
		include 'connect.php';
		$sql = "SELECT TRANGTHAI FROM `ban` WHERE TENBAN='$tenban'";
		$result = $conn->query($sql); //echo $result;
		$row = $result->fetch_assoc();
		$conn->close();
		return $row['TRANGTHAI'];
		
	}
function Get_MABAN($tenban)
	{
		include 'connect.php';
		$sql = "SELECT MABAN FROM `ban` WHERE TENBAN='$tenban'";
		$result = $conn->query($sql); //echo $result;
		$row = $result->fetch_assoc();
		$conn->close();
		return $row['MABAN'];
		
	}

function Get_MASP($tensp)
	{
		include 'connect.php';
		$sql = "SELECT MASP FROM `sanpham` WHERE TENSP='$tensp'";
		$result = $conn->query($sql); //echo $result;
		$row = $result->fetch_assoc();
		$conn->close();
		return $row['MASP'];
		
	}
function Update_TRANGTHAI($tenban,$trangthai)
	{
		include 'connect.php';
		$sql = "UPDATE `ban`
				SET TRANGTHAI = $trangthai
				WHERE TENBAN='$tenban'";
		if ($conn->query($sql) == TRUE) 
		{
		} else 
		{	
  			echo "Lỗi truy vấn: " . $sql . "<br>" . $conn->error;
		}
	}

function Get_MAHD($maban)
	{
		include 'connect.php';
		$sql = "SELECT MAHD FROM `hoadon` WHERE MABAN='$maban' ORDER BY MAHD DESC LIMIT 1;";
		$result = $conn->query($sql); //echo $result;
		$row = $result->fetch_assoc();
		$conn->close();
		return $row['MAHD'];
		
	}

function Add_chitietbanhang($MAHD,$MASP,$SOLUONG=1,$TRANGTHAIMON=0,$CHUTHICH,$COROI=0){
	include 'connect.php';
	if ($COROI==0) {
		$sql = "INSERT INTO `chitietbanhang` VALUES ('$MAHD','$MASP','$SOLUONG','$TRANGTHAIMON','$CHUTHICH')";
		echo $sql. "<br>";
	}else{
		$sql = "UPDATE `chitietbanhang` SET SOLUONG=$SOLUONG,CHUTHICH='$CHUTHICH' WHERE MAHD=$MAHD AND MASP=$MASP AND TRANGTHAIMON=0";
		echo $sql. "<br>";
	}
	
	if ($conn->query($sql) == TRUE)
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
		$sql = "INSERT INTO `hoadon` (`MABAN`,`TIMEIN`) VALUES ('$maban',CURRENT_TIMESTAMP)";
		if ($conn->query($sql) == TRUE) 
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