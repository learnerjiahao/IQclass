<?php

$user_id = $arr['user_id'];
$user_type = $arr['user_type'];

$messages = array();
$count = 0;

if($user_type == 'teacher'){
	$selectsql = 'select *from ustb_class_information where class_teacher_id = '.$user_id;
	$result = mysql_query($selectsql,$link);
	while($row=mysql_fetch_assoc($result)){
		$select = "SELECT * FROM ustb_class_".$row['class_id'].'_questions WHERE `ask_persons_count` >= 3';
		//echo $select;
		$res = mysql_query($select,$link);
		while($ro = mysql_fetch_assoc($res)){
			$message = array();
			$message['message_type'] = 1;
			$message['class_id'] = $row['class_id'];
			$message['class_name'] = $row['class_name'];
			$message['publish_date'] = $ro['publish_date'];
			$message['publish_person_name'] = $ro['publish_person_name'];
			$message['question_content'] = $ro['question_content'];
			$message['id'] = $ro['id'];
			$message['pictures_path'] = $ro['pictures_path'];
			$messages[$count] = $message;
			$count ++;
		}
		$select = 'select *from ustb_vocation_notes where class_id = '.$row['class_id'].' and type = 0';
		$res = mysql_query($select,$link);
		while($ro = mysql_fetch_assoc($res)){
			$message = array();
			$message['message_type'] = 0;
			$message['class_id'] = $ro['class_id'];
			$message['class_name'] = $ro['class_name'];
			$message['student_name'] = $ro['student_name'];
			$message['student_id'] = $ro['student_id'];
			$message['notes_path'] = $ro['notes_path'];
			$message['peroid'] = $ro['vocation_start_time'].'~'.$ro['vocation_end_time'];
			$message['absent_times'] = $ro['absent_times'];
			$message['type'] = $ro['type'];
			$message['date'] = $ro['date'];
			$messages[$count] = $message;
			$count ++;
		}
	}

}else{
	    $select = 'select *from ustb_vocation_notes where student_id = '.$user_id;
	    //echo $select;
		$res = mysql_query($select,$link);
		while($ro = mysql_fetch_assoc($res)){
			$message = array();
			$message['message_type'] = 0;
			$message['class_id'] = $ro['class_id'];
			$message['class_name'] = $ro['class_name'];
			$message['student_name'] = $ro['student_name'];
			$message['student_id'] = $ro['student_id'];
			$message['notes_path'] = $ro['notes_path'];
			$message['peroid'] = $ro['vocation_start_time'].'到'.$ro['vocation_end_time'];
			$message['absent_times'] = $ro['absent_times'];
			$message['type'] = $ro['type'];
			$message['date'] = $ro['date'];
			$messages[$count] = $message;
			$count ++;
		}
}

$array = array (
			'status' => 1,
			'messages' => $messages);
echo json_encode($array,JSON_UNESCAPED_UNICODE);


?>