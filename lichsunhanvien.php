<?php
include 'myfunction.php';
include 'connect.php';
if (($_GET['diemtru']!=0)&&(isset($_GET['data']))) {
	$data = $_GET['data'];
	$myjson = json_decode($data);
	$DIEMTRU=$_GET['diemtru'];
	foreach ($myjson as $key => $value) {
		$TENNV = $myjson[$key]->TENNV;
		$MANV = Get_MANV($TENNV);
		$THU = $myjson[$key]->THU;
		$NGAY = $myjson[$key]->NGAY;
		$TIMEIN = $myjson[$key]->TIMEIN;
		$TIMEOUT = $myjson[$key]->TIMEOUT;
		$WORKTIME = $myjson[$key]->WORKTIME;
		$sql = "UPDATE `chamcong` SET DIEMTRU=DIEMTRU+$DIEMTRU WHERE MANV=$MANV AND NGAY='$NGAY' AND TIMEIN='$TIMEIN'";
		$result = $conn->query($sql);
	}
}
if (isset($_GET['TENNV'])) {
	$TENNV=$_GET['TENNV'];
	$MANV = Get_MANV($TENNV);
	$startDate = $_GET['startDate'];
	$endDate = $_GET['endDate'];
	$startTime = $_GET['startTime'];
	$endTime = $_GET['endTime'];
	$chamcongArray = array();
	if ($startDate==$endDate) {
			if ($TENNV=="CHỌN") {
				$sql = "SELECT * FROM `chamcong` WHERE NGAY='$startDate' AND TIMEIN>'$startTime' AND TIMEIN<'$endTime' ORDER BY TIMEIN DESC";
			}else{
				$sql = "SELECT * FROM `chamcong` WHERE MANV=$MANV AND NGAY='$startDate' AND TIMEIN>'$startTime' AND TIMEIN<'$endTime' ORDER BY TIMEIN DESC";
			}
			$result = $conn->query($sql);
			while($row = $result->fetch_assoc()){
				$MANV = $row['MANV'];
				$TENNV = Get_TENNV($MANV);
				$TIMEIN = $row['TIMEIN'];
				$TIMEOUT = $row['TIMEOUT'];
				$DATE = $row['NGAY'];
				$THU = $row['THU'];
				$WORKTIME =$row['WORKTIME'];
				$DIEMTRU = $row['DIEMTRU'];
				array_push($chamcongArray, new chamcong($TENNV,$THU,$DATE,$TIMEIN,$TIMEOUT,$WORKTIME,$DIEMTRU));
				}
		}else{
			if ($TENNV=="CHỌN") {
				$sql = "SELECT * FROM `chamcong` WHERE NGAY>='$startDate' AND NGAY<='$endDate' AND TIMEIN>'$startTime' AND TIMEIN<'$endTime' ORDER BY TIMEIN DESC";
			}else{
				$sql = "SELECT * FROM `chamcong` WHERE MANV=$MANV AND NGAY>='$startDate' AND NGAY<='$endDate' AND TIMEIN>'$startTime' AND TIMEIN<'$endTime' ORDER BY TIMEIN DESC";
			}
			$result = $conn->query($sql);
			while($row = $result->fetch_assoc()){
				$MANV=$row['MANV'];
				$TENNV=Get_TENNV($MANV);
				$TIMEIN = $row['TIMEIN'];
				$TIMEOUT = $row['TIMEOUT'];
				$DATE = $row['NGAY'];
				$THU = $row['THU'];
				$WORKTIME =$row['WORKTIME'];
				$DIEMTRU = $row['DIEMTRU'];
				array_push($chamcongArray, new chamcong($TENNV,$THU,$DATE,$TIMEIN,$TIMEOUT,$WORKTIME,$DIEMTRU));
				}
		}
	}

	echo json_encode($chamcongArray);
class chamcong{
	function chamcong($TENNV,$THU,$NGAY,$TIMEIN,$TIMEOUT,$WORKTIME,$DIEMTRU){
		$this -> TENNV=$TENNV;
		$this -> THU=$THU;
		$this -> NGAY=$NGAY;
		$this -> TIMEIN=$TIMEIN;
		$this -> TIMEOUT=$TIMEOUT;
		$this -> WORKTIME=$WORKTIME;
		$this -> DIEMTRU=$DIEMTRU;
	}
}
?>