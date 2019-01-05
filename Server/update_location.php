<?php

//ignore_user_abort(true);
//set_time_limit(0);

$class_id = $arr['class_id'];
$longitude = $arr['longitude'];
$latitude = $arr['latitude'];
$class_name = $arr['class_name'];



//先判断课程信息表对应课堂的class_tag是否为0,

$sql_select = "select *from ustb_class_information where class_id = $class_id";
$result = mysql_fetch_assoc(mysql_query($sql_select,$link));
$class_tag = $result['class_tag'];

if ($class_tag == 0) {
	//1.将课程信息表中的class_tag设置为1，防止教师用户重复点击
	//2.将班级考勤表中所有同学的absent_times均加1
	//3.在班级中所有同学的个人考勤表中添加缺勤记录
	$sql = "update ustb_class_information set class_tag = 1 where class_id = {$class_id}";
	mysql_query($sql,$link);

	$sql = "select *from ustb_class_".$class_id." where 1";
	//echo $sql;
	$result = mysql_query($sql,$link);
	//var_dump($result);
	$record_date = date('y-m-d',time());
	while($row = mysql_fetch_assoc($result)){

		$absent_times = $row['absent_times']+1;
		$id = $row['member_id'];
		$updatesql = "update ustb_class_".$class_id." set absent_times = $absent_times where member_id = '{$id}'";
		//echo $updatesql;
		mysql_query($updatesql,$link);

		$insertSql = "insert into ustb_user_{$row['member_id']} (class_name,record_date,record_describe,record_tag) values ('{$class_name}','{$record_date}','缺勤',0)";
		mysql_query($insertSql,$link);

	}

}


$sql_update = "update ustb_class_information set class_longitude = '{$longitude}',class_latitude = '{$latitude}' where class_id = {$class_id}";
//echo $sql_update;

$result_update = mysql_query($sql_update,$link);

if($result_update){
	$array_result = array('status' => 1);
	echo json_encode($array_result,JSON_UNESCAPED_UNICODE);
}else{
	$array_result = array('status' => 2);  //服务器异常
	echo json_encode($array_result,JSON_UNESCAPED_UNICODE);
}


$endTime = $arr['endTime'];
$nowHour = date('H',time());
$nowMin = date('i',time());
$nowtime = $nowHour*60 + $nowMin;
//echo $nowtime.''.$endTime;
if ($nowtime >= $endTime) {
	$sql = "update ustb_class_information set class_tag = 0,class_longitude =1.0,class_latitude = 1.0 where class_id = {$class_id}";
	mysql_query($sql,$link);

}







// do{

// 	$nowHour = date('h',time());
// 	$nowMin = date('i',time());
// 	$nowtime = $nowHour*60 + $nowMin;
// 	sleep(10);
// 	echo 'hhh';
// }while($nowtime < $endTime)

//4.将课程信息表中的class_tag设置为0





?>