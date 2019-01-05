<?php
$class_id = $arr["class_id"];
$front_id = $arr['front_id'];
$user_name = $arr['user_name'];
$user_id = $arr['userId'];

$array_questions = array();
$array_question = array();
$countout = 0;


// $sql_create = "CREATE TABLE `ustb_class_".$class_id."_questions` (
//   `id` int(255) NOT NULL auto_increment,
//   `publish_date` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '2016-11-11',
//   `question_content` varchar(500) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'xxx',
//   `publish_person_name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
//   `publish_person_id` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
//   `pictures_path` varchar(400) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'no',
//   `front_id` int(255) NOT NULL DEFAULT '-1',
//    `question_too_persons_name` varchar(1000) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'no',
//   `question_too_persons_id` varchar(1000) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'no',
//   `ask_persons_count` int(3) NOT NULL DEFAULT '0',
//   PRIMARY KEY (`id`)
// ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci";

// mysql_query($sql_create,$link);

//$isExitsTable = 'show tables like ustb_class_'.$class_id.'_files';
//$result =mysql_num_rows(mysql_query("SHOW TABLES LIKE 'ustb_class_{$class_id}_questions'",$link));
//echo $result;
//echo $isExitsTable ;
//if($result == 1){
	$sql_select = 'select *from ustb_class_'.$class_id.'_questions where front_id = '.$front_id.' order by id desc';
	//front_id = -1 order by id desc';
	//echo $sql_select;
	$result = mysql_query($sql_select,$link);
	while($row = mysql_fetch_assoc($result)){
		$array_question['publish_date'] = $row['publish_date'];
		$array_question['publish_person_name'] = $row['publish_person_name'];
		$array_question['question_content'] = $row['question_content'];
		$array_question['id'] = $row['id'];
		$array_question['pictures_path'] = $row['pictures_path'];
		$array_question['front_id'] = $row['front_id'];
		$array_question['publish_person_id'] = $row['publish_person_id'];
		//strpos($a, $b) !== false
		if(strpos($row['question_too_persons_id'],$user_id) !== false){
			$array_question['type'] = 1;
		}else{
			$array_question['type'] = 0;
		}
		$array_question['question_too_persons_name'] = $row['question_too_persons_name'];
		$array_question['ask_persons_count'] = $row['ask_persons_count'];

		$array_questions[$countout] = $array_question;
		$countout ++;

	}
//}

$array = array (
			'status' => 1,
			'questions' => $array_questions);
echo json_encode($array,JSON_UNESCAPED_UNICODE);

?>