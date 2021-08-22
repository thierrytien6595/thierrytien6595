<?php
include 'connect.php';
include 'myfunction.php';

$TENNV = $_GET['tennv'];
$MANV=Get_MANV($TENNV);
if (isset($_GET['feature'])&&isset($_GET['tennv'])) {
	if ($_GET['feature']=="vao") {
		$sql = "SELECT NGAY,TIMEOUT FROM `chamcong` WHERE MANV=$MANV ORDER BY ID DESC LIMIT 1";
		$result = $conn->query($sql);
		if ($result->num_rows>0) {
			$row = $result->fetch_assoc();
			$PREDATE=$row['NGAY'];
			$DATE = date('d-m-Y');
			$TIMEOUT = $row['TIMEOUT'];
			if ($TIMEOUT=="00:00:00") {
			 	echo "NULL<BR>";
			 	return;
			 } 
		}
		$THU = date('D');
		$sql = "INSERT INTO `chamcong` (`MANV`,`THU`,`NGAY`,`TIMEIN`) VALUES ($MANV,'$THU',CURRENT_DATE,CURRENT_TIME);";
		$result = $conn->query($sql);
	}
	elseif ($_GET['feature']=="ra") {
		$giolam = $_GET['giolam'];
		$sql="SET @id:=(SELECT ID FROM `chamcong` WHERE MANV=$MANV ORDER BY ID DESC LIMIT 1);UPDATE `chamcong` SET TIMEOUT = CURRENT_TIME,WORKTIME=$giolam WHERE ID = @id;";
		$result = $conn->multi_query($sql);
		$conn->close();
	}
}
$chamcongArray = array();
$date=date("Y-m-01");
$sql = "SELECT * FROM `chamcong` WHERE TIMEIN>$date AND MANV=$MANV ORDER BY ID DESC";
$result = $conn->query($sql);
while($row = $result->fetch_assoc()){
	$TIMEIN = $row['TIMEIN'];
	$TIMEOUT = $row['TIMEOUT'];
	$DATE = $row['NGAY'];
	$THU = $row['THU'];
	$WORKTIME =$row['WORKTIME'];
	$DIEMTRU = $row['DIEMTRU'];
			array_push($chamcongArray, new chamcong($THU,$DATE,$TIMEIN,$TIMEOUT,$WORKTIME,$DIEMTRU));
	}
echo json_encode($chamcongArray);

class chamcong{
	function chamcong($THU,$NGAY,$TIMEIN,$TIMEOUT,$WORKTIME,$DIEMTRU){
		$this -> THU=$THU;
		$this -> NGAY=$NGAY;
		$this -> TIMEIN=$TIMEIN;
		$this -> TIMEOUT=$TIMEOUT;
		$this -> WORKTIME=$WORKTIME;
		$this -> DIEMTRU=$DIEMTRU;
	}
}
?>