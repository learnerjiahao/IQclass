<?php
$class_id = $arr['class_id'];

$selectSql = "select *from ustb_class_".$class_id." where 1";
$result = mysql_query($selectSql,$link);

$arrayOutter = array();
$count = 0;

while($row = mysql_fetch_assoc($result)){
	$arrayInner = array('member_id'=>$row['member_id'],'member_name'=>$row['member_name'],'absent_times'=>$row['absent_times'],
		'late_times'=>$row['late_times'],'note_times'=>$row['note_times'],'leave_early_times'=>$row['leave_early_times']);
	$arrayOutter[$count] = $arrayInner;
	$count++;
}

$array_result = array('status' => 1,'class_records'=>$arrayOutter);
echo json_encode($array_result,JSON_UNESCAPED_UNICODE);

?>