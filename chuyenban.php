<?php
include 'myfunction.php';
$TENBAN  = $_GET['TENBAN'];
$banchuyentoi = $_GET['banchuyentoi'];
$MABAN = Get_MABAN($TENBAN);
$MAbanchuyentoi = Get_MABAN($banchuyentoi);
$MAHD = Get_MAHD($MABAN);
Update_HOADON($MAHD,$MAbanchuyentoi);
Update_TRANGTHAI($TENBAN,0);
Update_TRANGTHAI($banchuyentoi,1);
TongTien($MAHD);
?>