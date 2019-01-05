<?php
$class_id = $arr["class_id"];


$sql_create = "CREATE TABLE IF NOT EXISTS `ustb_class_".$class_id."_notations` (
  `id` int(10) NOT NULL auto_increment,
  `notation_type` int(1) NOT NULL DEFAULT '0',
  `publish_person` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `notation_date` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `notation_content` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `notation_title` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `addition_file_path` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci";
mysql_query($sql_create,$link);

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

$array_notations = array();
$array_notation = array();
$count = 0;

//$isExitsTable = 'show tables like ustb_class_'.$class_id.'_files';
$result =mysql_num_rows(mysql_query("SHOW TABLES LIKE 'ustb_class_{$class_id}_notations'",$link));
//echo $result;
//echo $isExitsTable ;
if($result == 1){
	$sql_select = 'select *from ustb_class_'.$class_id.'_notations where 1 order by id desc';
	$result = mysql_query($sql_select,$link);
	while($row = mysql_fetch_assoc($result)){
		$array_notation['publish_person'] = $row['publish_person'];
		$array_notation['notation_date'] = $row['notation_date'];
		$array_notation['notation_title'] = $row['notation_title'];
		$array_notation['notation_content'] = $row['notation_content'];
		$array_notation['addition_file_path'] = $row['addition_file_path'];
		$array_notation['notation_type'] = $row['notation_type'];
		$array_notations[$count] = $array_notation;
		$count ++;

	}
}

$array = array (
			'status' => 1,
			'notation' => $array_notations);
echo json_encode($array,JSON_UNESCAPED_UNICODE);

?>