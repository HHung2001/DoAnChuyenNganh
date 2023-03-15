<?php
include "connect.php";
$id = $_POST['id'];
$trangthai = $_POST['trangthai'];


// check data
$query = 'UPDATE `donhang` SET `trangthai`= '.$trangthai.' WHERE `id`='.$id;
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