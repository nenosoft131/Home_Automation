<?php


include"config.php";

////////////////////////////////////////////////////////
//////////////////////////////////////////////////////



function addbookmark(){
		
	global $mysqli;
	global $user_name;
	global $book_name;
	global $author_name;
	
	$ResponseStatus = array();	
	$examArray = array();

$query = "SELECT * FROM bookmarks WHERE user_id = '".$user_name."' && book_name = '".$book_name."'";
		$result = mysql_query($query);
		
		while($row = mysql_fetch_assoc($result))
		{		
			array_push($ResponseStatus,$row);
	}
	
	$resulst = count($ResponseStatus);
	if($resulst==0)
	{
	$qry_classes = "INSERT INTO `mdl`.`bookmarks` (`user_id`, `book_name`,`auther_name`) VALUES ('".mysql_real_escape_string(htmlentities($user_name))."','".mysql_real_escape_string(htmlentities($book_name))."','".mysql_real_escape_string(htmlentities($author_name))."')";
	$res_classes = mysql_query($qry_classes);
	while($row_classes = mysql_fetch_assoc($res_classes)){
		
		$examArray = $row_classes;
		array_push($ResponseStatus,$examArray);
	}//while($row_notes = mysql_fetch_assoc($res_notes))		
	
	$status = true;
	header('Content-type: application/json');
	echo json_encode(array('State'=>$status,'Response'=>'Successfully Bookmark'));
	}
	else
	{$status = false;
		echo json_encode(array('State'=>$status,'Response'=>'Already Bookmark'));
	}
	
}//function getClass()
function Bookmark(){
		
	global $mysqli;
	global $user_name;
	global $book_name;
	
	$ResponseStatus = array();	
	$examArray = array();


	$qry_classes = "SELECT * FROM bookmarks WHERE user_id = '" .$user_name."'";
	$res_classes = mysql_query($qry_classes);
	
	while($row_classes = mysql_fetch_assoc($res_classes)){
		
		$examArray = $row_classes;
		array_push($ResponseStatus,$examArray);
	}//while($row_notes = mysql_fetch_assoc($res_notes))		
	
	$status = true;
	header('Content-type: application/json');
	echo json_encode(array('State'=>$status,'Response'=>$ResponseStatus));
	
}//function getClass()






function Searchbook(){
		
	global $mysqli;
	global $name;
	
	$ResponseStatus = array();	
	$examArray = array();


	$qry_classes = "SELECT * FROM book WHERE b_id = " .$name;
	$res_classes = mysql_query($qry_classes);
	
	while($row_classes = mysql_fetch_assoc($res_classes)){
		
		$examArray = $row_classes;
		array_push($ResponseStatus,$examArray);
	}//while($row_notes = mysql_fetch_assoc($res_notes))		
	
	$status = true;
	header('Content-type: application/json');
	echo json_encode(array('State'=>$status,'Response'=>$ResponseStatus));
	
}//function getClass()

function Searchbookbycatid(){
		
	global $mysqli;
	global $name;
	$ResponseStatus = array();	
	$examArray = array();

	$qry_classes = "SELECT * FROM book where category_id =".$name;
	$res_classes = mysql_query($qry_classes);
	
	while($row_classes = mysql_fetch_assoc($res_classes)){
		
		$examArray = $row_classes;
		array_push($ResponseStatus,$examArray);
	}//while($row_notes = mysql_fetch_assoc($res_notes))		
	
	$status = true;
	header('Content-type: application/json');
	echo json_encode(array('State'=>$status,'Response'=>$ResponseStatus));
	
}//function getClass()



function find_books()
{
	global $search_string;
	global $search_string_pub;
	global $user_id;
	global $auther_name;
	global $publisher_name;
	global $categories;
	$ResponseStatus = array();	

if($search_string!= "" && $auther_name!= "" && $publisher_name!= "" && $categories!="")
{
		  $query = "SELECT * FROM book WHERE b_name LIKE '%".$search_string."%' AND b_auther LIKE '%".$auther_name."%' AND b_publisher LIKE '%".$publisher_name."%' AND category_id LIKE '%".$categories."%'";
}
else if($search_string!= "" && $auther_name!= "" && $publisher_name!= "" )
{
		  $query = "SELECT * FROM book WHERE b_name LIKE '%".$search_string."%' AND b_auther LIKE '%".$auther_name."%' AND b_publisher LIKE '%".$publisher_name."%'";
}
else if($categories!= "" && $auther_name!= "" && $publisher_name!= "" )
{
		  $query = "SELECT * FROM book WHERE category_id LIKE '%".$categories."%' AND b_auther LIKE '%".$auther_name."%' AND b_publisher LIKE '%".$publisher_name."%'";
}
else if($categories!= "" && $auther_name!= "" && $search_string!= "" )
{
		  $query = "SELECT * FROM book WHERE category_id LIKE '%".$categories."%' AND b_auther LIKE '%".$auther_name."%' AND b_name LIKE '%".$search_string."%'";
}
  else if($search_string!= "" && $auther_name!= "")
{
	
		$query = "SELECT * FROM book WHERE b_name LIKE '%".$search_string."%' AND b_auther LIKE '%".$auther_name."%' ";
}
 else if($search_string!= "" && $publisher_name!= "")
{
	
		$query = "SELECT * FROM book WHERE b_name LIKE '%".$search_string."%' AND b_publisher LIKE '%".$publisher_name."%' ";
}
 else if($search_string!= "" && $categories!= "")
{
	
		$query = "SELECT * FROM book WHERE b_name LIKE '%".$search_string."%' AND category_id LIKE '%".$categories."%' ";
}
 else if($auther_name!= "" && $publisher_name!= "")
{
	
		$query = "SELECT * FROM book WHERE b_auther LIKE '%".$auther_name."%' AND b_publisher LIKE '%".$publisher_name."%' ";
}
 else if($categories!= "" && $publisher_name!= "")
{
	
		$query = "SELECT * FROM book WHERE category_id LIKE '%".$categories."%' AND b_publisher LIKE '%".$publisher_name."%' ";
}

  else if($search_string!="")
{
	
		$query = "SELECT * FROM book WHERE b_name LIKE '%".$search_string."%' ";
}
 else if($auther_name!="")
{
	
		$query = "SELECT * FROM book WHERE b_auther LIKE '%".$auther_name."%' ";
}
else if($publisher_name!="")
{
	
		$query = "SELECT * FROM book WHERE b_publisher LIKE '%".$publisher_name."%' ";
}
	
	else if($categories!="")
{
	
		$query = "SELECT * FROM book WHERE category_id LIKE '%".$categories."%' ";
}
		
		
		
		$result = mysql_query($query);
		
		while($row = mysql_fetch_assoc($result))
		{		
			array_push($ResponseStatus,$row);

		
	}
	
	$status = true;
	header('Content-type: application/json');
	echo json_encode(array('State'=>$status,'Response'=>$ResponseStatus));
}

function signup(){	
	
	global $mysqli;
	$pics=$_POST['profile_image'];
    $name = $_POST['name'];
	$email = $_POST['email'];
	$emp_id = $_POST['emp_id'];
	$password = $_POST['password'];
	$DOB = $_POST['dob'];
	$Role = $_POST['role'];
	$City = $_POST['city'];
	$Country = $_POST['country'];
	$Gender = $_POST['gender'];
	
	
	
	
	$query = "INSERT INTO authentication ".
       "(User_Name,Email,User_ID,Password,DOB,Role,City,Country,Gender)".
       "VALUES('$name','$email','$emp_id','$password' ,'$DOB','$Role','$City','$Country','$Gender')";
	//mysql_select_db('test_db');
	$retval = mysql_query( $query );

	if(! $retval )
	{
	die('Could not enter data: ' . mysql_error());
	}
	else
	{
	echo "Entered data successfully\n";
		file_put_contents('images/'.$emp_id.'.jpg',base64_decode($_POST['profile_image']));
		}


	

}//function signup()


function login(){
	
	
	global $mysqli;
	global $email;
	global $password;
	$Pass = "java@123";

	$online_status = 1;
	$ResponseStatus = array();	

	$domain = strstr($email, '@');
	if($domain)
		$where_cls	= "User_ID='".$email."'";
	else
		$where_cls	= "User_ID='".$email."'";
	
	$query = "SELECT * FROM authentication WHERE ".$where_cls." AND 	Password='".$password."'";
	$res = $mysqli->query($query) or die(queryFailed());
	
	if($res->num_rows > 0){
		$result	=	mysql_query($query);
		
		while($row = mysql_fetch_assoc($result)){
		
			array_push($ResponseStatus,$row);	
		}
		
		$status=true;
		
	}else{
	
		$status = false;
		$ResponseStatus = array(array('error_code'=>109, 'error_message'=>'Invalid email or password'));
	}

	header('Content-type: application/json');
	echo json_encode(array('State'=>$status,'Response'=>$ResponseStatus));
}


function getCategories(){
		
	global $mysqli;
	$ResponseStatus = array();	

	$qry_user = "SELECT * FROM category";
	$res_user = mysql_query($qry_user);
	
	while($row_classes = mysql_fetch_assoc($res_user)){
		
		$classesArray = $row_classes;
		array_push($ResponseStatus,$classesArray);
	}
	$status = true;
	header('Content-type: application/json');
	echo json_encode(array('State'=>$status,'Response'=>$ResponseStatus));
	
}


?>