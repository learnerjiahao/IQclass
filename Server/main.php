<?php

require("config.php");

// //获得post请求数据
// $str = file_get_contents("php://input");
// //parse_str:将字符串解析成多个变量  
// parse_str($str,$arr);

//var_dump($_POST);
//var_dump($arr);
//var_dump($_REQUEST);

$arr = $_REQUEST;

$action = $arr["action"];


switch ($action) {
	case 'login':
		include("login.php");
		break;
	case 'import_class_info':
		include("import_class_info.php");
		break;
	case 'update_location':
		include("update_location.php");
		break;
	case 'get_class_location':
		include("get_class_location.php");
		break;
	case 'send_result':
		include("send_result.php");
		break;	
	case 'get_personal_record':
		include("get_personal_record.php");
		break;
	case 'get_other_classes':
		include("get_other_classes.php");
		break;	
	case 'get_class_records':
		include("get_class_records.php");
		break;
	case 'get_my_grade':
		include("get_my_grade.php");
		break;
	case 'ask_vocation':
		include("ask_vocation.php");
		break;
	case 'update_file_res':
		include("ReceiveFiles.php");
		break;
	case 'get_files_res':
		include("get_files_res.php");
		break;
	case 'add_notation':
		include("add_notation.php");
		break;
	case 'show_notation':
		include("show_notation.php");
		break;
	case 'ask_question':
		include("ask_question.php");
		break;
	case 'show_questions':
		include("show_questions.php");
		break;
	case 'question_too':
		include("question_too.php");
		break;
	case 'get_messages':
		include("get_messages.php");
		break;				
	default:
		$array = array('status' => 0);
        echo json_encode($array,JSON_UNESCAPED_UNICODE);
		break;
}

?>
