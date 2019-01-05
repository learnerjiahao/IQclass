<?php

	//require("config.php");
	include("simple_html_dom.php") ;

	$j_username = $arr['userId'].",undergraduate";
    //'41355073,undergraduate';//
    //echo $j_username;
    $j_password = $arr['password'];
    //'wu1050069091';//
    //echo $j_password;
    //$semester_peroid = $arr['peroidTag'];

    $postLogin = array('j_username' => $j_username,'j_password'=> $j_password);

	//登录地址
	$urlLogin = "http://elearning.ustb.edu.cn/choose_courses/j_spring_security_check";
	//设置cookie保存路径
	$cookie = dirname(__FILE__) . '/cookie_logincheck.txt';

	//http://elearning.ustb.edu.cn/choose_courses/information/singleStuInfo_singleStuInfo_loadSingleStuScorePage.action?uid=41355073
	//登录后要获取信息的地址
    $url = "http://elearning.ustb.edu.cn/choose_courses/information/singleStuInfo_singleStuInfo_loadSingleStuScorePage.action?uid=".$arr['userId'];

    //uid=41355073";
    //;
    //模拟登录
    login_post($urlLogin, $cookie, $postLogin);
    //获取登录页的信息
    $content = get_content($url, $cookie);
    //echo $content;
    //删除cookie文件
    @ unlink($cookie);


    //echo $content;

    $html = new simple_html_dom();
    // 从字符串中加载
    $html->load($content);
    //$ret = $html->find('title');
    //$html->find('table.gridtable tr');
    $arrayOuter = array();
    $countOuter = 0;

    $other_grade = $html->find('table.gridtable h5',0)->innertext;
    $other_grade = explode("):", $other_grade)[1];
    //var_dump($other_grade);
    $whole_grade = $html->find('table.gridtable h5',1)->innertext;
    $whole_grade = explode("):", $whole_grade)[1];
    //var_dump($whole_grade);


    foreach ($html->find('table.gridtable tr') as $res) {
    	# code...
    	//echo $res->innertext;
    	//$class_name = $res->find('td')->innertext;
    	//echo $class_name;
    	if($countOuter != 0) {

    		$countInner = 0;
    		$arrayInner = array();
    		foreach ($res->find('td') as $tdres) {
    			switch ($countInner) {
    				case 0:
    					$arrayInner['class_semester'] = $tdres->innertext;
    					break;
    				case 2:
    					$arrayInner['class_name'] = $tdres->innertext;
    					break;
    				case 3:
    					$arrayInner['class_type'] = $tdres->innertext;
    					break;
    				case 5:
    					$arrayInner['class_grade'] = $tdres->innertext;
    					break;
    				case 7:
    					$arrayInner['my_grade'] = $tdres->innertext;
    					break;
    				
    			}
    			$countInner++;
    		}
    		//if($arrayInner['class_semester'] != $semester_peroid&&$semester_peroid!='all') continue;
            $arrayOuter[$countOuter-1] = $arrayInner;
    	}
        
    	$countOuter ++;
    	


    }
    //var_dump($arrayOuter);


    //var_dump($es->innertext);
    

    //var_dump($html);
    $html->clear();
    unset($html);
    //var_dump($content);
    //$ret = $html->find('table.gridtable td');
    //var_dump($ret);
    // foreach($ret as $element) 
    // 	var_dump($ret->);
    
    $array_result = array('status' => 1,'my_grade'=>$arrayOuter,'other_grade'=>$other_grade,'whole_grade'=>$whole_grade);
	echo json_encode($array_result,JSON_UNESCAPED_UNICODE);

?>