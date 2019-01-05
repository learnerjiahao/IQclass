<?php
$class_id = $arr['class_id'];
$id = $arr['id'];
$user_name = $arr['user_name'];
$user_id = $arr['userId'];
$type = $arr['type'];

$sql_select = 'select *from ustb_class_'.$class_id.'_questions where id = '.$id;
$row = mysql_fetch_assoc(mysql_query($sql_select,$link));

$person_name = $row['question_too_persons_name'];
$person_id = $row['question_too_persons_id'];
$counts = $row['ask_persons_count'];

if($type == 1){
	$person_name = str_replace($user_name.',','',$person_name);
	$person_id = str_replace($user_id.',','',$person_id);
	$counts = $counts-1;

}else{
	if($person_id == 'no'){
		$person_id = $user_id.',';
		$person_name = $user_name.',';
	}else{
		$person_id = $person_id.$user_id.',';
		$person_name = $person_name.$user_name.',';
	}
	$counts = $counts+1;
}

$updatesql = "update ustb_class_".$class_id."_questions set question_too_persons_name = '{$person_name}',question_too_persons_id = '{$person_id}',ask_persons_count = {$counts} where id = ".$id;
//echo $updatesql;
mysql_query($updatesql,$link);
$array = array ('status' => 1,'message' =>$person_name.'...'.$counts.'人同问');
echo json_encode($array,JSON_UNESCAPED_UNICODE);


?>