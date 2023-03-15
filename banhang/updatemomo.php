<?php
include "connect.php";
$iddonhang = $_POST['id'];
$token = $_POST['token'];


// check data
$query = 'UPDATE `donhang` SET `momo`= "'.$token.'" WHERE `id`='.$id;
$data = mysqli_query($conn, $query);
if ($data == true) {
		$arr = [
		'success' => true,
		'message' => "thành công"	
			   ];
}else{
		$arr = [
		'success' => false,
		'message' => "không  được"
		       ];
	}
print_r(json_encode($arr));

?>