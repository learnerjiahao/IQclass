<?php


    header("Content-Type: text/html;charset=utf-8");
    date_default_timezone_set('prc');  //设置时间为北京时间
    // define("HOST", "mysql.hostinger.com.hk");
    // define("USER", "u559788047_hungr");
    // define("PASSWORD", "wu1050069091");
    // define("DBNAME", "u559788047_date");

    define("HOST", "localhost");
    define("USER", "root");
    define("PASSWORD", "");
    define("DBNAME", "mydata");

    function login_post($url, $cookie, $post) {
        $curl = curl_init();//初始化curl模块
        curl_setopt($curl, CURLOPT_URL, $url);//登录提交的地址
        curl_setopt($curl, CURLOPT_HEADER, 0);//是否显示头信息
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, 0);//是否自动显示返回的信息
        curl_setopt($curl, CURLOPT_COOKIEJAR, $cookie); //设置Cookie信息保存在指定的文件中
        curl_setopt($curl, CURLOPT_POST, 1);//post方式提交
        curl_setopt($curl, CURLOPT_POSTFIELDS, http_build_query($post));//要提交的信息
        curl_exec($curl);//执行cURL
        curl_close($curl);//关闭cURL资源，并且释放系统资源
    }

    //登录成功后获取数据
    function get_content($url, $cookie) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_HEADER, 0);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_COOKIEFILE, $cookie); //读取cookie
        $rs = curl_exec($ch); //执行cURL抓取页面内容
        curl_close($ch);
        return $rs;
    }


    //连接数据库
    //$link = mysqli_connect(HOST,USER,PASSWORD,DBNAME);
    $link = mysql_connect(HOST,USER,PASSWORD);
    //mysql_query("SET NAMES UTF8");
    mysql_query("set names 'utf8'");
    mysql_select_db("mydata",$link);
    //mysql_query("set names 'utf8'");
    if (!$link) {
        # code...
        $array = array('status' => 0);;
        echo json_encode($array,JSON_UNESCAPED_UNICODE);
        return;
    }

?>