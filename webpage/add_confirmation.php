<?php
	
	// var_dump($_POST);
	// echo (empty($_POST['name']));
	$isUpdated = false;
	if ( !isset($_POST['name']) ||empty($_POST['name'])
		||!isset($_POST['street']) ||empty($_POST['street'])
		||!isset($_POST['price_id']) ||empty($_POST['price_id'])
		||!isset($_POST['rating_id']) ||empty($_POST['rating_id']))
	{

		$error = "Please fill out all required fields.";
	}

else
{


	$host = "303.itpwebdev.com";
	$user = "lqiang_db_user";
	$pass = "Qjy4102001!!!";
	$db = "lqiang_newdessert_db";



// 	// DB Connection.
	$mysqli = new mysqli($host, $user, $pass, $db);
	if ( $mysqli->connect_errno ) {
		echo $mysqli->connect_error;
		exit();
	}

	$mysqli->set_charset('utf8');


	// var_dump($_POST);
	if ( isset($_POST['type_id']) && !empty($_POST['type_id']) ) {
		$type_id = ($_POST['type_id']);
	} else {
		$type_id = null;
	}

	// if ( isset($_POST['price_id']) && !empty($_POST['price_id']) ) {
	// 	$price_id = ($_POST['price_id']);
	// } else {
	// 	$price_id = null;
	// }

	// if ( isset($_POST['rating_id']) && !empty($_POST['rating_id']) ) {
	// 	$rating_id = ($_POST['rating_id']);
	// } else {
	// 	$rating_id = null;
	// }




	if ( isset($_POST['theimage']) && !empty($_POST['theimage']) ) {
		$theimage = $_POST['theimage'];
	} else {
		$theimage ='no-image.jpeg';
	}


	if ( isset($_POST['city']) && !empty($_POST['city']) ) {
		$city = $_POST['city'];
	} else {
		$city = null;
	}

	if ( isset($_POST['state']) && !empty($_POST['state']) ) {
		$state = $_POST['state'];
	} else {
		$state = null;
	}



	if ( isset($_POST['comment']) && !empty($_POST['comment']) ) {
		$comment = $_POST['comment'];
	} else {
		$comment = null;
	}

	$statement = $mysqli->prepare("INSERT INTO restaurant (name,state,city,street,img,comment,type_id, price_id, rating_id) VALUES(?,?,?,?,?,?,?,?,?)");

	// echo $_POST['name'];
	// echo $state;
	// echo $city;
	// echo $_POST['street'];
	// echo $theimage;
	// echo $comment;
	// echo $type_id;
	// echo $price_id;
	// echo $rating_id;

	$statement->bind_param("ssssssiii", $_POST['name'],$state,$city,$_POST['street'],$theimage, $comment, $type_id, $_POST['price_id'], $_POST['rating_id']);
	$executed = $statement->execute();

	if(!$executed) {
		echo $mysqli->error;
		exit();
	}

	// echo $statement->affected_rows;

	if($statement->affected_rows == 1) {
		$isUpdated = true;
	}

	
	// echo $statement->affected_rows;

	$statement->close();
	$mysqli->close();


// }

}
?>







<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Add Confirmation</title>
     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">

   <!--  <link rel="stylesheet" type="text/css" href="style.css"> -->
</head>
<style>
	.output{
		margin-left: 5%;
		margin-top: 2%;
		box-sizing: border-box;
		width:30%;
		/*background-color: pink;*/
	}
	a{
		text-decoration: none;
	}

	.thebutton{
		margin-left: 5%;
		margin-top: 5%;
	}
	/*body{
		background-color: green;
	}*/
</style>
</style>
<body>
	<div class="output">
				<?php if ( isset($error) && !empty($error) ) : ?>
					<div class="text-danger">
						<?php echo $error; ?>
					</div>
				<?php endif; ?>
				<!-- <div class="text-success"><span class="font-italic">Title</span> was successfully edited.</div> -->
				<?php if ($isUpdated) : ?>
					<div class="text-success">
						You added <?php echo $_POST['name']; ?> successfully.
					</div>
				<?php endif; ?>
	</div>
	<div class="thebutton">
		
		<button type="button" class="btn btn-outline-primary" onclick="window.location.href = 'restaurant.html';">Back to Ranking</button>
	</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
</body>
</html>