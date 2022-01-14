<?php
// echo $_GET["term"];
// echo "hello";
header("Access-Control-Allow-Origin: *");
$host = "303.itpwebdev.com";
$user = "lqiang_db_user";
$password = "Qjy4102001!!!";
$db = "lqiang_newdessert_db";


$mysqli = new mysqli($host, $user, $password, $db);
// // echo "Hello";
if($mysqli->connect_errno) {
	echo $mysqli->connect_error;
	exit();
}


$mysqli->set_charset("utf-8");


$sql="SELECT restaurant.name, type.type, rating.rating, price.description, restaurant.id
FROM restaurant
LEFT JOIN type on restaurant.type_id=type.id
LEFT JOIN rating on restaurant.rating_id=rating.id
LEFT JOIN price on restaurant.price_id=price.id
";


// // echo json_encode($_GET["term"]);

// // echo "jello";
if( $_GET["term"]==1)
{
	$sql=$sql."Order BY rating.rating desc;";
    // echo json_encode($sql);
}
else if($_GET["term"]==2)
{
	$sql=$sql."Order BY rating.rating;";
}

else if($_GET["term"]==3)
{
	$sql=$sql."Order BY price.id desc;";
}

else
{
	$sql=$sql."Order BY price.id;";
}
// else if( $_GET["term"]==3)
// {
// 	$sql=$sql."Order BY rating.rating DESC;"
// }


// // else if( $_GET["term"]==3)
// // {
// // 	$sql=$sql."Order BY price.price DESC;"
// // }

// // else{
// // 	$sql=$sql."Order BY price.price;"
// // }
$results=$mysqli->query($sql);

if($results==false)
{
	echo $mysqli->error;
	exit();
}


$mysqli->close();

// if( $_GET["term"]==2)
// {
// // 	// $sql=$sql."Order BY rating.rating;"
//     // echo json_encode("h");
//     echo json_encode($results->fetch_assoc());
// }


$results_array = [];

	// Run through all the results and push each row into the $results_array
	while($row = $results->fetch_assoc()) {
		array_push($results_array, $row);
	}
echo json_encode($results_array);
	


// var_dump($_GET);
// if(isset($_GET["country"])){
//        $country=$_POST["country"];
//        echo "select country is => ".$country;
//    }
// $sql=$sql."ORDER BY rating.rating DESC";
?>
