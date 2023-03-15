<?php
include "connect.php";
$tensp = $_POST['tensp'];
$gia = $_POST['gia'];
$hinhanh = $_POST['hinhanh'];
$mota = $_POST['mota'];
$loai = $_POST['loai'];
$id = $_POST['id'];

// check data
$query = 'UPDATE `sanphammoi` SET `tensp`="'.$tensp.'" ,`giasp`="'.$gia.'",`hinhanh`="'.$hinhanh.'",`mota`="'.$mota.'",`loai`='.$loai.' WHERE `id` ='.$id;
$data = mysqli_query($conn, $query);
if ($data == true) {
		$arr = [
		'success' => true,
		'message' => "Sửa sản phẩm thành công"	
			   ];
}else{
		$arr = [
		'success' => false,
		'message' => " Sửa không  được"
		       ];
	}
print_r(json_encode($arr));

?>