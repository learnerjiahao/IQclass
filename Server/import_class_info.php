<?php

// $str = '[{"SKRS":"141","XS":null,"BZ":null,"JSH":"B0805472","KHFS":null,"QTSKRS":"1","PTK":[{"SKRS":"0","DYXF":"0","XS":null,"BZ":"配套课","DYXS":null,"SFYXTX":"1","PTK":[],"XF":"0","BXJZRQ":"2016-03-20","ID":13401592,"DYKCH":"305040Q","JTLB":"普通","SKSJDDSTR":"(周1,第2节,3-8周 地点未知) (周5,第2节,5-8周 地点未知) (周3,第2节,3-8周 地点未知) ","SFYXCX":"1","SKSJDD":{"86":["地点未知","3-8周"],"98":["地点未知","3-8周"],"128":["地点未知","3-8周"],"140":["地点未知","3-8周"],"170":["地点未知","3-8周"],"182":["地点未知","3-8周"],"194":["地点未知","5-8周"],"212":["地点未知","3-8周"],"224":["地点未知","3-8周"],"236":["地点未知","5-8周"],"254":["地点未知","3-8周"],"266":["地点未知","3-8周"],"278":["地点未知","5-8周"],"296":["地点未知","3-8周"],"308":["地点未知","3-8周"],"320":["地点未知","5-8周"]},"XXK":[],"DYKCM":"软件工程课程设计(实验)","KXH":"2001","SFKX":"1","KRL":132,"KCLB":"10","SFXZRS":null,"PTKBJ":null,"XNXQ":"2015-2016-2","JSM":[],"FREERS":"0","KCM":"软件工程课程设计(实验)","JTZT":"0","ZJT_ID":13401591,"TKJZRQ":"2016-03-24","KCH":"305040Q"}],"PSBL":null,"XF":"2","BXJZRQ":"2016-03-06","ID":13401591,"KHLX":{"CHECKCLASS":"hundredMark","COMBONAME":"bfz","KHLX":"百分制","SXYS":"1","BZ":null},"JTLB":"普通","SKSJDDSTR":"(周3,第2节,1-2周 逸夫楼805) (周1,第2节,1-3周 逸夫楼805) ","SKSJDD":{"2":["逸夫楼805","1-3周"],"14":["逸夫楼805","1-2周"],"44":["逸夫楼805","1-3周"],"56":["逸夫楼805","1-2周"],"86":["逸夫楼805","1-3周"]},"SFBJMD":"2","KXH":"1001","KCLB":"10","XNXQ":"2015-2016-2","JSM":[{"JSM":"殷绪成"}],"KSBL":null,"KCM":"软件工程课程设计","JTZT":"1","ZJT_ID":null,"TKJZRQ":"2016-03-10","KCH":"305040Q"}]';
// $str = '{"jtInfo":[{"SKRS":"141","XS":null,"BZ":null,"JSH":"B0805472","KHFS":null,"QTSKRS":"1","PTK":[{"SKRS":"0","DYXF":"0","XS":null,"BZ":"配套课","DYXS":null,"SFYXTX":"1","PTK":[],"XF":"0","BXJZRQ":"2016-03-20","ID":13401592,"DYKCH":"305040Q","JTLB":"普通","SKSJDDSTR":"(周1,第2节,3-8周 地点未知) (周5,第2节,5-8周 地点未知) (周3,第2节,3-8周 地点未知) ","SFYXCX":"1","SKSJDD":{"98":["地点未知","3-8周"],"212":["地点未知","3-8周"],"128":["地点未知","3-8周"],"224":["地点未知","3-8周"],"194":["地点未知","5-8周"],"308":["地点未知","3-8周"],"296":["地点未知","3-8周"],"320":["地点未知","5-8周"],"170":["地点未知","3-8周"],"182":["地点未知","3-8周"],"278":["地点未知","5-8周"],"86":["地点未知","3-8周"],"254":["地点未知","3-8周"],"236":["地点未知","5-8周"],"140":["地点未知","3-8周"],"266":["地点未知","3-8周"]},"XXK":[],"DYKCM":"软件工程课程设计(实验)","KXH":"2001","SFKX":"1","KRL":132,"KCLB":"10","SFXZRS":null,"PTKBJ":null,"XNXQ":"2015-2016-2","JSM":[],"FREERS":"0","KCM":"软件工程课程设计(实验)","JTZT":"0","ZJT_ID":13401591,"TKJZRQ":"2016-03-24","KCH":"305040Q"}],"PSBL":null,"XF":"2","BXJZRQ":"2016-03-06","ID":13401591,"KHLX":{"CHECKCLASS":"hundredMark","COMBONAME":"bfz","KHLX":"百分制","SXYS":"1","BZ":null},"JTLB":"普通","SKSJDDSTR":"(周3,第2节,1-2周 逸夫楼805) (周1,第2节,1-3周 逸夫楼805) ","SKSJDD":{"2":["逸夫楼805","1-3周"],"44":["逸夫楼805","1-3周"],"56":["逸夫楼805","1-2周"],"86":["逸夫楼805","1-3周"],"14":["逸夫楼805","1-2周"]},"SFBJMD":"2","KXH":"1001","KCLB":"10","XNXQ":"2015-2016-2","JSM":[{"JSM":"殷绪成"}],"KSBL":null,"KCM":"软件工程课程设计","JTZT":"1","ZJT_ID":null,"TKJZRQ":"2016-03-10","KCH":"305040Q"}]}';
// var_dump ( json_decode ( $str ) );
$j_username = $arr ['userId'] . "," . $arr ['userType'];
$j_password = $arr ['password'];

$postLogin = array (
		'j_username' => $j_username,
		'j_password' => $j_password 
);

$semester = $arr ['semester'];
$uid = $arr ['userId'];

// 登录地址
$urlLogin = "http://elearning.ustb.edu.cn/choose_courses/j_spring_security_check";
// 设置cookie保存路径
$cookie = dirname ( __FILE__ ) . '/' . $uid . '.txt';

// 登录后要获取信息的地址
if ($arr ['userType'] != 'teacher') {
	$url = "http://elearning.ustb.edu.cn/choose_courses/choosecourse/commonChooseCourse_courseList_loadTermCourses.action?listXnxq=" . $semester . "&uid=" . $uid;
	// 模拟登录
	login_post ( $urlLogin, $cookie, $postLogin );
	// 获取登录页的信息
	$content = get_content ( $url, $cookie );
	// 删除cookie文件
	@ unlink ( $cookie );
	// echo "heheh";
	$td_class = json_decode ( $content, true );
	$array_classes = $td_class ["selectedCourses"];
	// var_dump($array_classes[0]);
	$size_of_classes = sizeof ( $array_classes );
	$tag_import_status = 0;
	$array_info_of_classes = array ();
	$array_info_of_class = array ();
	$array_info_of_user = array ();
	
	$uname = 'jjj';
	
	if ($size_of_classes > 0) {
		
		// 获取个人信息
		$array_user_info = $array_classes [0];
		$uname = $array_info_of_user ['user_name'] = $user_name = $array_user_info ['XM'];
		$user_id = $arr ['userId'];
		$user_gender = 0; // 默认为男
		if ($array_user_info ['XB'] == "女") {
			$user_gender = 1;
		}
		$array_info_of_user ['user_gender'] = $user_gender;
		
		$array_info_of_user ['user_grade'] = $user_grade = $array_user_info ['RXNJ'] . "级";
		$array_info_of_user ['user_profession'] = $user_profession = $array_user_info ['ZYFX'];
		$array_info_of_user ['user_class'] = $user_class = $array_user_info ['JXBJ'];
		$user_type = 0; // 默认为学生
		if ($arr ['userType'] == "teacher") {
			$user_type = 1;
		}
		
		// 插入数据库
		$sqlSelect = "select *from ustb_user_information where user_id = '{$user_id}'";
		// echo $sqlSelect ;
		$resultSelect = mysql_query ( $sqlSelect, $link );
		// echo $resultSelect;
		if (mysql_num_rows ( $resultSelect ) < 1) {
			$insertSql = "insert into ustb_user_information (user_id,user_name,user_class,user_grade," . "user_profession,user_gender,user_type) values ('{$user_id}','{$user_name}'," . "'{$user_class}','{$user_grade}','{$user_profession}',{$user_gender},{$user_type})";
			mysql_query ( $insertSql, $link );
		}
		
		// 课程入库
		for($i = 0; $i < $size_of_classes; $i ++) {
			$array = $array_classes [$i];
			$array_info_of_class ['class_id'] = $class_id = $array ["ID"];
			
			$sql_create_table = "CREATE TABLE IF NOT EXISTS `ustb_class_" . $class_id . "` (
						`member_id` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'oooo',
                                `member_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'ooooo',
                                `absent_times` int(3) NOT NULL DEFAULT '0',
                                `late_times` int(3) NOT NULL DEFAULT '0',
                                `leave_early_times` int(3) NOT NULL DEFAULT '0',
                                `note_times` int(3) NOT NULL DEFAULT '0'
                                ) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci";
			
			mysql_query ( $sql_create_table, $link );

			$sql_create = "CREATE TABLE `ustb_class_".$class_id."_questions` (
  				`id` int(255) NOT NULL auto_increment,
 				`publish_date` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '2016-11-11',
  				`question_content` varchar(500) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'xxx',
  				`publish_person_name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  				`publish_person_id` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  				`pictures_path` varchar(400) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'no',
  				`front_id` int(255) NOT NULL DEFAULT '-1',
  				`question_too_persons_name` varchar(1000) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'no',
  				`question_too_persons_id` varchar(1000) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'no',
  				`ask_persons_count` int(3) NOT NULL DEFAULT '0',
  				PRIMARY KEY (`id`)
				) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci";

			mysql_query($sql_create,$link);
			
			// 将自己加入到班级表中
			$sql_select_member = "select *from ustb_class_" . $class_id . " where member_id = '{$uid}'";
			// echo $sql_select_member;
			if (mysql_num_rows ( mysql_query ( $sql_select_member, $link ) ) < 1) {
				$sql_insert_member = "insert into ustb_class_" . $class_id . " (member_id,member_name) values ('{$uid}','{$uname}')";
				mysql_query ( $sql_insert_member, $link );
			}
			
			$array_info_of_class ['class_name'] = $class_name = $array ["DYKCM"];
			$array_info_of_class ['class_teacher_name'] = $class_teacher_name = $array ["JSM"] [0] ["JSM"];
			$class_period = $array ["XS"];
			if (! $class_period) {
				$class_period = 0;
			}
			$array_info_of_class ['class_period'] = $class_period;
			$class_credit = $array ["DYXF"];
			if (! $class_credit) {
				$class_credit = 0;
			}
			$array_info_of_class ['class_credit'] = $class_credit;
			$class_time_location = $array ["SKSJDDSTR"] . "+";
			if (sizeof ( $array ["PTK"] ) >= 1) {
				$class_time_location = $class_time_location . $array ["PTK"] [0] ["SKSJDDSTR"];
			}
			
			$array_info_of_class ['class_time_location'] = $class_time_location;
			$class_type = $array ["KCLBM"];
			if (! $class_type) {
				$class_type = "未知";
			}
			$array_info_of_class ['class_type'] = $class_type;
			$array_info_of_class ['class_semester'] = $semester;
			
			$array_info_of_classes [$i] = $array_info_of_class;
			// echo $class_id."--".$class_name."--".$class_teacher_name."--".$class_period
			// ."--".$class_credit."--".$class_time_location."--".$class_type."<br/>";
			$sql_is_exit = "select *from ustb_class_information where class_id = " . $class_id;
			// echo $sql_is_exit;
			$resule_is_exit = mysql_query ( $sql_is_exit, $link );
			// var_dump($resule_is_exit);
			if (mysql_num_rows ( $resule_is_exit ) >= 1) {
				$sql_update = "UPDATE ustb_class_information SET class_name=" . "'{$class_name}'" . ",class_teacher_name=" . "'{$class_teacher_name}'" . ",class_credit=" . $class_credit . ",class_type=" . "'{$class_type}'" . ",class_period=" . $class_period . ",class_time_location=" . "'{$class_time_location}'" . ",class_semester='{$semester}' WHERE class_id = {$class_id}";
				// echo $sql_update;
				$result_update = mysql_query ( $sql_update, $link );
				if ($result_update) {
					$tag_import_status = 1;
				}
			} else {
				$sql_insert = "INSERT INTO ustb_class_information (class_id,class_name,class_teacher_name," . "class_credit,class_type,class_period,class_time_location,class_semester) VALUES " . "({$class_id},'{$class_name}','{$class_teacher_name}',{$class_credit},'{$class_type}',{$class_period},'{$class_time_location}','{$semester}')";
				// echo $sql_insert;
				$result_insert = mysql_query ( $sql_insert, $link );
				if ($result_insert) {
					$tag_import_status = 1;
				}
			}
		}
	} else {
		$tag_import_status = 3;
	}
	
	$array = array (
			'status' => $tag_import_status,
			'info_of_classes' => $array_info_of_classes,
			'info_of_user' => $array_info_of_user 
	);
	echo json_encode ( $array, JSON_UNESCAPED_UNICODE );
} else {
	$array_info_of_classes = array();
	$array_info_of_user = array ();
	$tag_import_status = 0;
	
	$url = "http://elearning.ustb.edu.cn/choose_courses/tchoperate/commonTchOperate_reviewSchedule_loadTchScheduleList.action?uid=" . $uid . "&xnxq=" . $semester;
	// 模拟登录
	login_post ( $urlLogin, $cookie, $postLogin );
	// 获取登录页的信息
	$content = get_content ( $url, $cookie );
	// 删除cookie文件
	@ unlink ( $cookie );
	// echo "heheh";
	$td_class = json_decode ( $content, true );
	$teacher_classes = $td_class ["jtInfo"];
	
	if (sizeof ( $teacher_classes ) >= 1) {
		
		$uname = $teacher_classes [0] ["JSM"] [0] ["JSM"];
		$array_info_of_user['user_name'] = $uname;
		// 插入数据库
		$sqlSelect = "select *from ustb_user_information where user_id = '{$uid}'";
		// echo $sqlSelect ;
		$resultSelect = mysql_query ( $sqlSelect, $link );
		// echo $resultSelect;
		if (mysql_num_rows ( $resultSelect ) < 1) {
			$insertSql = "insert into ustb_user_information (user_id,user_name" . ") values ('{$uid}','{$uname}')";
			mysql_query ( $insertSql, $link );
		}
		
		$count = 0;
		while ( $count < sizeof ( $teacher_classes ) ) {
			
			$array_info_of_class  = array();
			
			$array_info_of_class ['class_id'] = $class_id = $teacher_classes [$count] ["ID"];
			$array_info_of_class ['class_name'] = $class_name = $teacher_classes[$count] ["KCM"];
			$array_info_of_class ['class_teacher_name'] = $class_teacher_name = $teacher_classes[$count] ["JSM"] [0] ["JSM"];
			$class_credit = $teacher_classes[$count] ["XF"];
			if (! $class_credit) {
				$class_credit = 0;
			}
			$array_info_of_class ['class_credit'] = $class_credit;
			$array_info_of_class ['class_period'] = $class_period = $class_credit*16;
			$class_time_location = $teacher_classes[$count] ["SKSJDDSTR"] . "+";   //SKSJDDSTR
			if (sizeof ( $teacher_classes[$count] ["PTK"] ) >= 1) {
				$class_time_location = $class_time_location . $teacher_classes[$count] ["PTK"] [0] ["SKSJDDSTR"];
			}
				
			$array_info_of_class ['class_time_location'] = $class_time_location;
			$class_type = "未知";
			$array_info_of_class ['class_type'] = $class_type;
			$array_info_of_class ['class_semester'] = $semester;
				
			$array_info_of_classes [$count] = $array_info_of_class;
			$count ++;
			
			
			$sql_is_exit = "select *from ustb_class_information where class_id = " . $class_id;
			// echo $sql_is_exit;
			$resule_is_exit = mysql_query ( $sql_is_exit, $link );
			// var_dump($resule_is_exit);
			if (mysql_num_rows ( $resule_is_exit ) >= 1) {
				// 身份为老师，则更新教师id栏
				$sql_update = "UPDATE ustb_class_information SET class_teacher_id = '{$arr['userId']}' WHERE class_id = {$class_id}";
				// echo $sql_update;
				$result_update = mysql_query ( $sql_update, $link );
			} else {
				
				$sql_insert = "INSERT INTO ustb_class_information (class_id,class_name,class_teacher_name," 
						. "class_credit,class_type,class_period,class_time_location,class_semester,class_teacher_id) VALUES " 
								. "({$class_id},'{$class_name}','{$class_teacher_name}',{$class_credit},".
				"'{$class_type}',{$class_period},'{$class_time_location}','{$semester}','{$arr["userId"]}')";
				// echo $sql_insert;
				$result_insert = mysql_query ( $sql_insert, $link );
			}
		}
		$tag_import_status = 1;
	} else {
		$tag_import_status = 3;
	}
	$array = array (
			'status' => $tag_import_status,
			'info_of_classes' => $array_info_of_classes,
			'info_of_user' => $array_info_of_user);
	echo json_encode ($array, JSON_UNESCAPED_UNICODE);
	
}

?>