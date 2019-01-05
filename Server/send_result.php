<?php
$class_id = $arr['class_id'];
$user_id = $arr['userId'];
$tag = $arr['tag'];
$lateTime = $arr['lateTime'];
$class_name = $arr['class_name'];

$record_date = date('y-m-d',time());


//缺勤记为0，早退记为1，迟到记为2
$selectsql = "select *from ustb_class_".$class_id." where member_id = '{$user_id}'";
$record = mysql_fetch_assoc(mysql_query($selectsql,$link));
switch($tag){
	case 1:            //上课签到，按时，需要将缺勤次数减1，早退次数加1,个人记录加上早退记录
	$absent_times = $record['absent_times'] - 1;
	$leave_early_times = $record['leave_early_times'] + 1;
	$updatesql = "update ustb_class_".$class_id." set absent_times={$absent_times},leave_early_times={$leave_early_times} where member_id = '{$user_id}'";
	mysql_query($updatesql,$link);
	$insertsql = "insert into ustb_user_".$user_id." (class_name,record_date,record_describe,record_tag) values ('{$class_name}','{$record_date}','早退',1)";
	mysql_query($insertsql,$link);
	break;              

	case 2:                //迟到， 将缺勤次数减1 ，迟到次数加1，早退次数加1，个人记录加上早退记录和迟到记录
	$absent_times = $record['absent_times'] - 1;
	$leave_early_times = $record['leave_early_times'] + 1;
	$late_times = $record['late_times'] + 1;
	
	$updatesql = "update ustb_class_".$class_id." set absent_times={$absent_times},leave_early_times={$leave_early_times},late_times={$late_times} where member_id = '{$user_id}'";
	mysql_query($updatesql,$link);
	
	$insertsql = "insert into ustb_user_".$user_id." (class_name,record_date,record_describe,record_tag) values ('{$class_name}','{$record_date}','早退',1)";
	mysql_query($insertsql,$link);

	$late_des = '迟到'.$lateTime.'分钟';
	$insertsql = "insert into ustb_user_".$user_id." (class_name,record_date,record_describe,record_tag) values ('{$class_name}','{$record_date}','{$late_des}',2)";
	mysql_query($insertsql,$link);
	break;

	case 3:                 //早退次数减1，个人记录删去该条早退记录
	$leave_early_times = $record['leave_early_times'] - 1;
	$updatesql = "update ustb_class_".$class_id." set leave_early_times={$leave_early_times} where member_id = '{$user_id}'";
	mysql_query($updatesql,$link);
	
	$deletesql = "delete from ustb_user_".$user_id." where class_name = '{$class_name}' and record_date = '{$record_date}' and record_tag = 1";
	mysql_query($deletesql,$link);
	break; 

	case 4:                  // 早退次数减1，删去个人记录中的早退记录
	$leave_early_times = $record['leave_early_times'] - 1;
	$updatesql = "update ustb_class_".$class_id." set leave_early_times={$leave_early_times} where member_id = '{$user_id}'";
	mysql_query($updatesql,$link);
	
	$deletesql = "delete from ustb_user_".$user_id." where class_name = '{$class_name}' and record_date = '{$record_date}' and record_tag = 1";
	mysql_query($deletesql,$link);
	break;

	case 5:                 //缺勤，个人记录更新为迟到时间过长，视为缺勤，将缺勤消息修改为：迟到时间过长，视为缺勤
	$updatesql = "update ustb_user_".$user_id." set record_describe = '迟到时间过长，视为缺勤' where class_name = '{$class_name}' and record_date = '{$record_date}' and record_tag = 0";
	//echo $updatesql;
	mysql_query($updatesql,$link);
	break;

	case 6:                 //该早退记录更新为：未中场签到，视为早退
	$updatesql = "update ustb_user_".$user_id." set record_describe = '未中场签到，视为早退' where class_name = '{$class_name}' and record_date = '{$record_date}' and record_tag = 1";
	mysql_query($updatesql,$link);
	break;


}
$array_result = array('status' => 1);
echo json_encode($array_result,JSON_UNESCAPED_UNICODE);


?>