<?php

		// //设置post的数据
	 //    $studentLoginPost = array(
	 //        'j_username'=>'41355073,undergraduate',
	 //        'j_password'=>'wu1050069091'
	 //    );
        
        $j_username = $arr['userId'].",".$arr['userType'];
        $j_password = $arr['password'];
        
        $postLogin = array('j_username' => $j_username,'j_password'=> $j_password);


		//登录地址
		$urlLogin = "http://elearning.ustb.edu.cn/choose_courses/j_spring_security_check";
        //设置cookie保存路径
		$cookieLogin = dirname(__FILE__) . '/cookie_logincheck.txt';
		 //模拟登录
		login_post($urlLogin, $cookieLogin, $postLogin);

	    //登录后要获取信息的地址
	    $urlLoginCheck = "http://elearning.ustb.edu.cn/choose_courses/loginsucc.action";
	    //获取登录页的信息
	    $content = get_content($urlLoginCheck, $cookieLogin);
	     //删除cookie文件
	    @ unlink($cookieLogin);

	    if (strpos($content, "success:true") !== false) {  //content中包含success：true，则登录成功
	    	if ($arr['userType']!='teacher') {
	    		$create_table = "CREATE TABLE IF NOT EXISTS `ustb_user_".$arr['userId']."` (
  			`class_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'wwwww',
  			`record_date` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '2016-11-11',
  			`record_describe` varchar(30) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'xxxxx',
 			`record_tag` int(1) NOT NULL DEFAULT '0'
			) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci";

			mysql_query($create_table,$link);
	    	}
	    	
	    	$array = array('status'=>1,'content'=>$content);
	    	echo json_encode($array,JSON_UNESCAPED_UNICODE);
	    }else {     //否则，密码或者帐号有误
				$array = array('status'=>2);
	    		echo json_encode($array,JSON_UNESCAPED_UNICODE);
			}
			           
?>