<?php

$class_id = $arr['class_id'];
$notation_type = $arr['notation_type'];
$notation_title = $arr['notation_title'];
$notation_person = $arr['notation_person'];
$notation_date = $arr['notation_date'];
$notation_title = $arr['notation_title'];
$notation_content = $arr['notation_content'];
$date = date('y-m-d',time());
$notation_file_path = 'no';


// $sql_create = "CREATE TABLE IF NOT EXISTS `ustb_class_".$class_id."_notations` (
//   `id` int(10) NOT NULL auto_increment,
//   `notation_type` int(1) NOT NULL DEFAULT '0',
//   `publish_person` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
//   `notation_date` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
//   `notation_content` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
//   `notation_title` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
//   `addition_file_path` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
//   PRIMARY KEY (`id`)
// ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci";
// mysql_query($sql_create,$link);

// $sql_create = "CREATE TABLE IF NOT EXISTS `ustb_class_".$class_id."_files` (
//   `id` int(20) NOT NULL auto_increment,
//   `publish_date` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '222222',
//   `publish_person` varchar(30) COLLATE utf8_unicode_ci DEFAULT '0000',
//   `file_name` varchar(80) COLLATE utf8_unicode_ci NOT NULL DEFAULT '00000',
//   `file_size` int(11) NOT NULL DEFAULT '1024',
//   `file_type` varchar(80) COLLATE utf8_unicode_ci NOT NULL DEFAULT '000000',
//   `file_path` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT './',
//   `publish_describe` varchar(1000) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'no',
//   PRIMARY KEY (`id`)
// ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ;";

// mysql_query($sql_create,$link);

$file = $_FILES;
//var_dump($file);


if (count($file)>0) {
	// 首先需要检测b目录是否存在
	if (!is_dir('ustb_notation_files/')) mkdir('ustb_notation_files/'); // 如果不存在则创建
	
    $target_path  = "./ustb_notation_files/";//接收文件目录 
    $target_path = $target_path . basename( $_FILES['file0']['name']);
    if(move_uploaded_file($_FILES['file0']['tmp_name'], $target_path)) { 
    	$notation_file_path = $target_path;
    	$sql_insert = "insert into ustb_class_".$class_id. "_files (`publish_date`, `publish_person`, 
          `file_name`, `file_size`, `file_type`, `file_path`,`publish_describe`) VALUES ('{$date}','{$notation_person}',
          '{$_FILES["file0"]["name"]}',{$_FILES['file0']['size']},'{$_FILES['file0']['type']}
            ','{$target_path}','unknown')";
        //echo $sql_insert;
        mysql_query($sql_insert,$link);
    }else{
    	$array_result = array('status' => 0);
       	//var_dump($_FILES);
		echo json_encode($array_result,JSON_UNESCAPED_UNICODE);
		return;
    }
}

$sql_insert = "insert into ustb_class_".$class_id."_notations (`notation_type`,`publish_person`,`notation_date`,`notation_content`,
	`notation_title`,`addition_file_path`) values ('{$notation_type}','{$notation_person}','{$date}','{$notation_content}',
	'{$notation_title}','{$notation_file_path}')";
mysql_query($sql_insert,$link);

$array_result = array('status' => 1);
echo json_encode($array_result,JSON_UNESCAPED_UNICODE); 



?>