<?php


$createsql = "CREATE TABLE `ustb_vocation_notes` (
  `id` int(255) NOT NULL auto_increment,
  `teacher_id` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `teacher_name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `student_name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `student_id` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `notes_path` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `date` int(20) NOT NULL,
  `class_id` int(15) NOT NULL,
  `class_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `vocation_start_time` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `vocation_end_time` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `absent_times` int(3) NOT NULL DEFAULT '0',
  `type` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci";

mysql_query($createsql,$link);


$class_id = $arr['class_id'];
$student_name = $arr['student_name'];
$student_id = $arr['student_id'];
$vocation_start_time = $arr['vocation_start_time'];
$vocation_end_time = $arr['vocation_end_time'];
$absent_times = $arr['absent_times'];
$date = time();

$selectsql = 'select *from ustb_class_information where class_id = '.$class_id;
$result = mysql_fetch_assoc(mysql_query($selectsql,$link));

$class_name = $result['class_name'];
$teacher_name = $result['class_teacher_name'];
$teacher_id = $result['class_teacher_id'];

// 首先需要检测b目录是否存在
if (!is_dir('ustb_note_pictures/')) mkdir('ustb_note_pictures/'); // 如果不存在则创建
$target_path  = "./ustb_note_pictures/";//接收文件目录
$target_path = $target_path . basename( $_FILES['file0']['name']);

$notes_path = $target_path;

if(move_uploaded_file($_FILES['file0']['tmp_name'], $target_path)) {  
      
	$sqlinsert = "INSERT INTO `ustb_vocation_notes`(`teacher_id`, 
		`teacher_name`, `student_name`, `student_id`, `notes_path`, 
		`date`, `class_id`, `class_name`, `type`, `vocation_start_time`, `vocation_end_time`
		, `absent_times`) VALUES ('{$teacher_id}','{$teacher_name}','{$student_name}',
		{$student_id},'{$notes_path}',{$date},{$class_id},'{$class_name}',0,'{$vocation_start_time}'
		,'{$vocation_end_time}',{$absent_times})";

	mysql_query($sqlinsert,$link);
    $array_result = array('status' => 1,'file'=>$_FILES);
	echo json_encode($array_result,JSON_UNESCAPED_UNICODE); 
    }else{  
       	$array_result = array('status' => 0);
       	echo json_encode($array_result,JSON_UNESCAPED_UNICODE);  
    }


?>