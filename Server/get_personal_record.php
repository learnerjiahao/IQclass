<?php
$userId = $arr['userId'];
$peroidTag = $arr['peroidTag'];

$sqlSelect = "select *from ustb_user_".$userId." where 1"; 
$result = mysql_query($sqlSelect,$link);

$arrayOutter = array();
$count = 0;


while($row = mysql_fetch_assoc($result)){

	$time = strtotime($row['record_date']);
	$nowTime = time();
	//echo "--".$time."---".$nowTime;
	//echo $nowTime-$time;
	//2368092
	switch ($peroidTag) {
		case 1:  //一个月内
			if($nowTime-$time<=30*24*60*60){
				$arrayInner = array('class_name'=>$row['class_name'],'record_date'=>$row['record_date'],'record_describe'=>$row['record_describe']);
	            $arrayOutter[$count] = $arrayInner;
	            $count++;
			}
			break;
		case 2:    //一个学期内
			if ($nowTime-$time<=4*30*24*60*60) {
				$arrayInner = array('class_name'=>$row['class_name'],'record_date'=>$row['record_date'],'record_describe'=>$row['record_describe']);
	            $arrayOutter[$count] = $arrayInner;
	            $count++;
			}
				
			break;
		default:   //默认一周内
				if ($nowTime-$time<=7*24*60*60) {
					$arrayInner = array('class_name'=>$row['class_name'],'record_date'=>$row['record_date'],'record_describe'=>$row['record_describe']);
	            	$arrayOutter[$count] = $arrayInner;
	            	$count++;
				}
				
			break;
	}

	
}

$array_result = array('status' => 1,'records'=>$arrayOutter);
echo json_encode($array_result,JSON_UNESCAPED_UNICODE);


?>