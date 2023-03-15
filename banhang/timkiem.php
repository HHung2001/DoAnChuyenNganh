<?php
include "connect.php";
$timkiem = $_POST['timkiem'];
if(empty($timkiem)){
	$arr = [
		'success' => false,
		'message' => " khong thanh cong",
		
	];
}else{
	$query = "SELECT * FROM `sanphammoi` WHERE `tensp` LIKE '%".$timkiem."%'";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
}
if (!empty($result)) {
	$arr = [
		'success' => true,
		'message' => "thanh cong",
		'result' => $result
	];
}else{
	$arr = [
		'success' => false,
		'message' => " khong thanh cong",
		'result' => $result
	];
}
}

print_r(json_encode($arr));

?>



