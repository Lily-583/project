<?php

// var_dump($_GET["restaurant_name"]);

$Deleted=false;

if(!isset($_GET["restaurant_id"])||empty($_GET["restaurant_id"]))
{
	$error="Invalid restaurant.";
}

else
{
	$host = "303.itpwebdev.com";
	$user = "lqiang_db_user";
	$pass = "Qjy4102001!!!";
	$db = "lqiang_newdessert_db";



	$mysqli = new mysqli($host, $user, $pass, $db);
	if ( $mysqli->connect_errno ) {
		echo $mysqli->connect_error;
		exit();
	}

	$mysqli->set_charset('utf8');

	$statement = $mysqli->prepare("DELETE FROM restaurant WHERE id = ?");

	$statement->bind_param("i", $_GET["restaurant_id"]);
	$executed = $statement->execute();

	// var_dump($_GET["restaurant_id"]);
	if(!$executed) {
			echo $mysqli->error;
			exit();
		}
		// echo $statement->affected_rows;
		if($statement->affected_rows == 1) {
			$Deleted = true;
		}
		$statement->close();
		$mysqli->close();
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Recipe</title>
     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">

   <!--  <link rel="stylesheet" type="text/css" href="style.css"> -->
</head>
<style>
	
	/*body{
		background-color: green;
	}*/
	a{
		text-decoration: none;
	}

	.output{
		margin-left: 5%;
		margin-top: 2%;
		box-sizing: border-box;
		width:30%;
		/*background-color: pink;*/
	}

	.thebutton{
		margin-left: 5%;
		margin-top: 5%;
	}
	/
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
				<?php if ($Deleted) : ?>
					<div class="text-success">
						You deleted <?php echo $_GET["restaurant_name"]; ?> successfully.
					</div>
				<?php endif; ?>
	</div>
	<div class="thebutton">
		
		<button type="button" class="btn btn-outline-primary"><a href="restaurant.html">Back to Ranking</a></button>
	</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
</body>
</html>