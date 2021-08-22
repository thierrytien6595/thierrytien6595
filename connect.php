<?php
date_default_timezone_set('Asia/Ho_Chi_Minh');
$conn = new mysqli('localhost', 'root', '', 'thachcoffee');
if ($conn->connect_error) {
  die("Kết nối bị lỗi: " . $conn->connect_error);
};
?>