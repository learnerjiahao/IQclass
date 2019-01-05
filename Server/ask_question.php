<?php

$class_id = $arr['class_id'];
$date = date('y-m-d',time());
$publish_person_name = $arr['publish_person_name'];
$publish_person_id = $arr['publish_person_id'];
$question_content = $arr['question_content'];
$front_id = $arr['front_id'];
$pictures_path = '';

// $sql_create = "CREATE TABLE `ustb_class_".$class_id."_questions` (
//   `id` int(255) NOT NULL auto_increment,
//   `publish_date` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '2016-11-11',
//   `question_content` varchar(500) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'xxx',
//   `publish_person_name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
//   `publish_person_id` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
//   `pictures_path` varchar(400) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'no',
//   `front_id` int(255) NOT NULL DEFAULT '-1',
//   PRIMARY KEY (`id`)
// ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci";

// mysql_query($sql_create,$link);
$count = 0;

if(count($_FILES)==0){
  $pictures_path = 'no';
}else{

  //echo count($_FILES);
  while($count < count($_FILES)){
  //echo 'file'.$count;
  $file = $_FILES['file'.$count];
  // 首先需要检测b目录是否存在
  if (!is_dir('ustb_question_pictures/')) mkdir('ustb_question_pictures/'); // 如果不存在则创建
  
    $target_path  = "./ustb_question_pictures/";//接收文件目录 
    $target_path = $target_path . basename( $file['name']);
    
    if(move_uploaded_file($file['tmp_name'], $target_path)) { 
      $pictures_path = $pictures_path."&".$target_path;
      $count++;
      //echo "path--".$pictures_path;
    }else{
      $array_result = array('status' => 0,'files'=>$_FILES);
        //var_dump($_FILES);
    echo json_encode($array_result,JSON_UNESCAPED_UNICODE);
    return;
    }
}
}
//echo "------".$pictures_path;


$sql_insert = "insert into ustb_class_{$class_id}_questions (publish_date,publish_person_name,publish_person_id,pictures_path,question_content,front_id)
		values ('{$date}','{$publish_person_name}','{$publish_person_id}','{$pictures_path}','{$question_content}',{$front_id})";
mysql_query($sql_insert,$link);

$array_result = array('status' => 1);
//var_dump($_FILES);
echo json_encode($array_result,JSON_UNESCAPED_UNICODE);

?>