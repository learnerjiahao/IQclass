<?php

$class_id = $arr['class_id'];

$select_sql = "select *from ustb_class_information where class_id = '{$class_id}'";

$result_sql = mysql_query($select_sql,$link);

$result = mysql_fetch_assoc($result_sql);

//echo $result;
//var_dump($result);

$longitude = $result['class_longitude'];
$latitude = $result['class_latitude'];

if ($longitude == 1.0 || $latitude == 1.0) {

	$array_result = array('status' => 3);  //老师未更新位置
	echo json_encode($array_result,JSON_UNESCAPED_UNICODE);
	
}else{
	$array_result = array('status' => 1,'class_longitude'=>$longitude,'class_latitude'=>$latitude);
	echo json_encode($array_result,JSON_UNESCAPED_UNICODE);
}

?>