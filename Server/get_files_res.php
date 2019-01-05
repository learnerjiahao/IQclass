<?php
$class_id = $arr["class_id"];

//建表

$sql_create = "CREATE TABLE IF NOT EXISTS `ustb_class_".$class_id."_files` (
  `id` int(20) NOT NULL auto_increment,
  `publish_date` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '222222',
  `publish_person` varchar(30) COLLATE utf8_unicode_ci DEFAULT '0000',
  `file_name` varchar(80) COLLATE utf8_unicode_ci NOT NULL DEFAULT '00000',
  `file_size` int(11) NOT NULL DEFAULT '1024',
  `file_type` varchar(80) COLLATE utf8_unicode_ci NOT NULL DEFAULT '000000',
  `file_path` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT './',
  `publish_describe` varchar(1000) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'no',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ;";

mysql_query($sql_create,$link);

$array_files = array();
$array_file = array();
$count = 0;

//$isExitsTable = 'show tables like ustb_class_'.$class_id.'_files';
$result =mysql_num_rows(mysql_query("SHOW TABLES LIKE 'ustb_class_{$class_id}_files'",$link));
//echo $result;
//echo $isExitsTable ;
if($result == 1){
	$sql_select = 'select *from ustb_class_'.$class_id.'_files where 1 order by id desc';
	$result = mysql_query($sql_select,$link);
	while($row = mysql_fetch_assoc($result)){
		$array_file['file_name'] = $row['file_name'];
		$array_file['file_type'] = $row['file_type'];
		$array_file['file_size'] = $row['file_size'];
		$array_file['publish_date'] = $row['publish_date'];
		$array_file['publish_person'] = $row['publish_person'];
		$array_file['file_path'] = $row['file_path'];
		$array_files[$count] = $array_file;
		$count ++;

	}
}

$array = array (
			'status' => 1,
			'files_res' => $array_files);
echo json_encode($array,JSON_UNESCAPED_UNICODE);

?>