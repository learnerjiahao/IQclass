<?php

$semester = $arr['semester'];

$selectSql = "select *from ustb_class_information where class_semester = '{$semester}'";
$result = mysql_query($selectSql,$link);

$arrayOutter = array();
$count = 0;

while($row = mysql_fetch_assoc($result)){

	$class_name = $row['class_name'];
	$class_id = $row['class_id'];
	$class_time_location = explode('+', $row['class_time_location'])[0];
	$class_teacher_name = $row['class_teacher_name'];

	$arrayInner = array('class_name'=>$class_name,'class_id'=>$class_id,'class_time_location'=>$class_time_location,
		'class_teacher_name'=>$class_teacher_name);
	$arrayOutter[$count] = $arrayInner;
	$count++;

}

$array_result = array('status' => 1,'other_lessons'=>$arrayOutter);
echo json_encode($array_result,JSON_UNESCAPED_UNICODE);

?>