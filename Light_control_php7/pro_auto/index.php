<?php 

	define('DB_NAME', 'Home_Controler');	//MySQL database
	define('DB_USER', 'root');		//MySQL database username 
	define('DB_PASSWORD', 'toor');		//MySQL database password 
	define('DB_HOST', '127.0.0.1'); // MySQL hostname 
	$mysqli = new mysqli(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);
	
	if(!$mysqli){
	
			echo "MYSQLI connection failed";
			exit();
	}
	
	$mysqli -> select_db(DB_NAME);
	

	if(isset($_REQUEST['action']) && $_REQUEST['action']!=''){
	  
	  	include"commonFunctions.php";
		
		$status_code 	= "1";
		$action 	 	= $_REQUEST['action'];
		$todate		 	= date('Y:m:d H:m:s');
		$user_name   	= isset($_REQUEST['user_name'])?stripslashes($_REQUEST['user_name']):'';
		$password 	 	= isset($_REQUEST['password'])?$_REQUEST['password']:'';
		$light_num 	 	= isset($_REQUEST['light_num'])?$_REQUEST['light_num']:'';
		$light_ctr 	 	= isset($_REQUEST['light_ctr'])?$_REQUEST['light_ctr']:'';

		switch($action){
	
			case 'login':
					login();
					break;
			case 'light_1':
					light_1();
					break;
			case 'info':
					info();
					break;
							
			default:	
		}
	
  	$mysqli -> close();

	}else{
	
		header('Content-type: application/json');
		echo json_encode(array('State'=>'false','Response'=>'Action Missing'));
		
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function login(){
		
	global $mysqli;
	global $password;
	global $user_name;
	$ResponseStatus = array();
	$salt="jsd22!KJSD£%JWW$12BB";

	$password= sha1 ($salt.$password);

	$where_cls	= "user_name='".$user_name."'";
	$query = "SELECT user FROM login WHERE ".$where_cls." AND password='".$password."'";
	$res = $mysqli->query($query) or die(queryFailed());
	

	if($res->num_rows > 0){
		$result	=$mysqli -> query($query);
		
		while($row =  $result -> fetch_assoc()){
		
			array_push($ResponseStatus,$row);
		}
		
		$status=true;
		
	}else{
	
		$status = false;
		$ResponseStatus = array(array('error_code'=>109, 'error_message'=>'Invalid Cretentials'));
	}

	header('Content-type: application/json');
	echo json_encode(array('State'=>$status,'Response'=>$ResponseStatus));
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function light_1(){
		
	global $mysqli;
	global $user_name;
	global $light_num;
	global $light_ctr;

	$ResponseStatus = array();

	$query = "SELECT Ctr_1 FROM info WHERE  user ='".$user_name."'";
	$res = $mysqli->query($query) or die(queryFailed());
	
	if($res->num_rows > 0){
				$out = exec("sudo python /usr/local/bin/on.py ".$light_num.$light_ctr);
				print $out;
				$query = "UPDATE info  Set ".$light_ctr."=".$light_num;
				$retval = $mysqli -> query( $query );

				if(! $retval )
				{
					die('Could not enter data: ' . mysql_error());
				}
				else
				{
					$query = "SELECT * FROM info";
					$res = $mysqli->query($query) or die(queryFailed());		
				}
			}
	

	if($res->num_rows > 0){
		$result	=$mysqli -> query($query);
		
		while($row =  $result -> fetch_assoc()){
		
			array_push($ResponseStatus,$row);
		}
		
		$status=true;
		
	}else{
	
		$status = false;
		$ResponseStatus = array(array('error_code'=>109, 'error_message'=>'Invalid Cretentials'));
	}
echo $out;

	header('Content-type: application/json');
	echo json_encode(array('State'=>$status,'Response'=>$ResponseStatus));	
	
	
}//function getClass()
function info(){
	
	
	global $mysqli;
	global $user_name;

	$ResponseStatus = array();

	$query = "SELECT Ctr_1 FROM info WHERE  user ='".$user_name."'";
	$res = $mysqli->query($query) or die(queryFailed());
	
	if($res->num_rows > 0){
				
			$query = "SELECT * FROM info";
			$res = $mysqli->query($query) or die(queryFailed());
				
			}
	
	if($res->num_rows > 0){
		$result	=	$mysqli -> query($query);
		
		while($row =  $result -> fetch_assoc()){
		
			array_push($ResponseStatus,$row);	
		}
		
		$status=true;
		
	}else{
	
		$status = false;
		$ResponseStatus = array(array('error_code'=>109, 'error_message'=>'Invalid Cretentials'));
	}

	header('Content-type: application/json');
	echo json_encode(array('State'=>$status,'Response'=>$ResponseStatus));
}
		
?>
