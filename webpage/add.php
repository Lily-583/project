
<?php
$host = "303.itpwebdev.com";
$user = "lqiang_db_user";
$pass = "Qjy4102001!!!";
$db = "lqiang_newdessert_db";




// DB Connection.
$mysqli = new mysqli($host, $user, $pass, $db);
if ( $mysqli->connect_errno ) {
	echo $mysqli->connect_error;
	exit();
}

$mysqli->set_charset('utf8');
	
$sql_types = "SELECT * FROM type;";
$results_types = $mysqli->query($sql_types);
// var_dump($results_labels->fetch_assoc());
if ( $results_types == false ) {
	echo $mysqli->error;
	exit();
}



$sql_prices = "SELECT * FROM price;";
$results_prices = $mysqli->query($sql_prices);
// var_dump($results_prices->fetch_assoc());
// var_dump($results_labels->fetch_assoc());
if ( $results_prices == false ) {
	echo $mysqli->error;
	exit();
}

$sql_ratings = "SELECT * FROM rating;";
$results_ratings = $mysqli->query($sql_ratings);
// var_dump($results_prices->fetch_assoc());
// var_dump($results_labels->fetch_assoc());
if ( $results_ratings == false ) {
	echo $mysqli->error;
	exit();
}
// var_dump($results_ratings);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Add</title>
    <link rel="stylesheet" href="nav.css">
     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">

   <!--  <link rel="stylesheet" type="text/css" href="style.css"> -->
</head>
<style>
	html,body{
		background-color: #6D6466;
	}
	.row{
		margin-bottom:3%;
	}

	.row1{
		margin-top: 3%;
	}

	.container{
		margin-top: 3%;
	}
	.section1{
		margin-top: 2%;
		background-color: pink;
		padding:2%;
		margin-bottom: 4%;
		background-color: #9F9F92;
	}
	.section2{
		background-color: blue;
		padding:2%;
		margin-bottom: 4%;
		background-color: #9F9F92;
	}
	.section3{
		background-color: green;
		padding:2%;
		margin-bottom: 4%;
		background-color: #9F9F92;
	}

	.thetitle{
		color: white;
		text-align: center;
	}
	.thebutton{
		margin-left: 40%;
		margin-right: auto;
		margin-bottom: 2%;
	}

	.reset{
		background-color: grey;
		border: grey;
	}

</style>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		  <div class="container-fluid">
		    <a class="navbar-brand" href="#">Dessert</a>
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon"></span>
		    </button>
		    <div class="collapse navbar-collapse" id="navbarSupportedContent">
		      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
		        <li class="nav-item">
		          <a class="nav-link" aria-current="page" href="homepage.html">Home</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link active" href="restaurant.html">Restaurant</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="recipe.html">Recipe</a>
		        </li>

		        <!-- <li class="nav-item">
		          <a class="nav-link" href="#">World</a>
		        </li>
		         -->
		      </ul>
		      <!-- <form class="d-flex">
		        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
		        <button class="btn btn-outline-success" type="submit">Search</button>
		      </form> -->
		    </div>
		  </div>
		</nav>

	
	<div class="container">
		<h2 class="thetitle">Add Restaurant</h2>
		<form action="add_confirmation.php" method="POST" id="theform">
			<div class="section1">
				<h5>Basic Info</h5>
				<div class="form-group row row1">
					<label for="name-id" class="col-sm-3 col-form-label text-sm-right">Restaurant Name: <span class="text-danger">*</span></label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="name-id" name="name" value="">
					</div>
				</div> <!-- .form-group -->

				<!--  -->
				<!-- type -->
				<div class="form-group row">
					<label for="type-id" class="col-sm-3 col-form-label text-sm-right">Type:</label>
					<div class="col-sm-9">
						<select name="type_id" id="type-id" class="form-control">
							<option value="" selected disabled>--Choose One--</option>
								<?php while( $row = $results_types->fetch_assoc() ): ?>
								<option value="<?php echo $row['id']; ?>">
											<?php echo $row['type']; ?>
								</option>
							<?php endwhile; ?>

							
							
						</select>
					</div>
				</div> <!-- .form-group -->

				<!--  -->
				<div class="form-group row">
					<label for="price-id" class="col-sm-3 col-form-label text-sm-right">Price:<span class="text-danger">*</span></label>
					<div class="col-sm-9">
						<select name="price_id" id="price-id" class="form-control">
							<option value="" selected disabled>-- Select One--</option>

								<?php while( $row = $results_prices->fetch_assoc() ): ?>
								
								<option value="<?php echo $row['id']; ?>">
											<?php echo $row['description']; ?>
								</option>
							<?php endwhile; ?>


							
							
						</select>
					</div>
				</div> <!-- .form-group -->


				<div class="form-group row">
					<label for="rating-id" class="col-sm-3 col-form-label text-sm-right">Rating:<span class="text-danger">*</span></label>
					<div class="col-sm-9">
						<select name="rating_id" id="rating-id" class="form-control" >
							<option value="" selected disabled>-- Select One--</option>

								<?php while( $row = $results_ratings->fetch_assoc() ): ?>
								
								<option value="<?php echo $row['id']; ?>">
											<?php echo $row['rating']; ?>
								</option>
							<?php endwhile; ?>

							
							
						</select>
					</div>
				</div> <!-- .form-group -->

				<div class="form-group row row">
					<label for="img-id" class="col-sm-3 col-form-label text-sm-right">Image Link:</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="image-id" name="theimage" value="">
					</div>
				</div> 

			</div>

			<div class="section2">
				<h5>Location</h5>

				<div class="form-group row row1">
					<label for="city-id" class="col-sm-3 col-form-label text-sm-right">City:</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="city-id" name="city" value="">
					</div>
				</div> <!-- .form-group -->

				<div class="form-group row row1">
					<label for="state-id" class="col-sm-3 col-form-label text-sm-right">State: </label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="state-id" name="state" value="">
					</div>
				</div> <!-- .form-group -->

				<div class="form-group row row1">
					<label for="street-id" class="col-sm-3 col-form-label text-sm-right">Street: <span class="text-danger">*</span></label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="street-id" name="street" value="">
					</div>
				</div> <!-- .form-group -->
			</div>
			<div class="section3">
				<h5>Your Thoughts</h5>

				<div class="form-group row">
					<label for="comment" class="col-sm-3 col-form-label text-sm-right">Comment:</label>
					<div class="col-sm-9">
						<textarea name="comment" id="award-id" class="form-control" ></textarea>
					</div>
				</div> <!-- .form-group -->
			</div>
			<div class="thebutton">
				<button type="submit" class="btn btn-primary">Submit</button>
				<button type="reset" class="btn btn-light">Reset</button>
			</div>
		</form>

	</div> <!-- .container -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
<script>
	document.querySelector('#theform').onsubmit=function(event)
	{
		// event.preventDefault();
		let thename=document.querySelector("#name-id").value.trim();
		let theprice=document.querySelector("#price-id").value.trim();
		let therating=document.querySelector("#rating-id").value.trim();
		let thestreet=document.querySelector("#street-id").value.trim();
		
		if(thename.length<=0)
		{
			alert("Name cannot be empty!");
			return false;
		}
		else if(thename.length>=100)
		{
			alert("Name is too long!");
			return false;
		}

		if(theprice.length<=0)
		{
			alert("Price cannot be empty!");
			return false;
		}

		if(therating.length<=0)
		{
			alert("Rating cannot be empty!");
			return false;
		}

		if(thestreet.length<=0)
		{
			alert("Street cannot be empty!");
			return false;
		}
		
		return true;
	}

</script>
</body>
</html>
