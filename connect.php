<?php
$conn = new mysqli('localhost', 'root', '', 'thachcoffee');
if ($conn->connect_error) {
  die("Kết nối bị lỗi: " . $conn->connect_error);
};
?>