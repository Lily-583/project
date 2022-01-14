<?php
 	// var_dump($_GET['restaurant_name']);
	if(!isset($_GET['restaurant_id'])||empty($_GET['restaurant_id']))
	{
		$error="Invalid Restaurant.";
	}

	
							
	else{
		
		$host = "303.itpwebdev.com";
		$user = "lqiang_db_user";
		$pass = "Qjy4102001!!!";
		$db = "lqiang_newdessert_db";

		$mysqli=new mysqli($host,$user,$pass,$db);

		//check if connection was successful
		if($mysqli->connect_errno)
		{
			echo $mysqli->connect_error;
			exit();
		}

		$mysqli->set_charset('utf8');

		$sql="Select restaurant.name, restaurant.state, restaurant.city, restaurant.street, restaurant.img,
		   restaurant.comment, type.type, price.symbol,price.description, rating.rating, restaurant.id
		   FROM restaurant
		   LEFT JOIN type ON restaurant.type_id=type.id
		   LEFT JOIN price ON restaurant.price_id=price.id
		   LEFT JOIN rating ON restaurant.rating_id=rating.id
		   WHERE restaurant.id=".$_GET['restaurant_id'].";";

		  $results=$mysqli->query($sql);
		   if(!$results){
		    echo $mysqli->error;
		    exit();
		   }

		   $row=$results->fetch_assoc();
		   // var_dump($row);
		   $mysqli->close();

		   // echo ($row[img]);

	}
?>
	
	

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Detail</title>
    <link rel="stylesheet" href="nav.css">
     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">

   <!--  <link rel="stylesheet" type="text/css" href="style.css"> -->
</head>
<style>
	.image{
		float:left;
		width:40%;
		margin-left: 10%;
		margin-top: 3%;
	}
	.res-name h2{
		margin-left: 10%;
		margin-top: 4%;
	}
	.image img{
		width:100%;
	}
	.info{
		float:left;
		width:50%;
		padding-left: 3%;
		/*text-align: center;*/
		box-sizing: border-box;
		/*line-height: 200%;*/
		margin-top:1%;
		/*padding-top: 0px;*/
		
		background-color: white;
	}

	.clearfloat{
		clear: both;
	}

	.button-group{
		margin-left: 10%;
		margin-top: 2%;

	}

	.button-group .btn{
		margin-right: 1%;
	}
	.button-group{
		margin-bottom: 2%;
	}
	@media(max-width:2100px) 
	{
		
	}

	@media(max-width:991px) 
	{
		

	}

	@media(max-width:767px)
	{
		.image{
			width:96%;
			margin-left:2%;
			margin-right: 2%;
		}
		.info{
			width:96%;
			margin-left: 2%;
			margin-right: 2%;
			margin-bottom: 2%;
		}
	}
	.button-group a{
		text-decoration: none;
		color: none;
	}

	.btn-outline-secondary{
		text-color:grey;
	}

	.btn-outline-secondary a{
		color:grey;
	}

	.btn-outline-danger a{
		color:red;
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
		        </li> -->
		        
		      </ul>
		      <!-- <form class="d-flex">
		        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
		        <button class="btn btn-outline-success" type="submit">Search</button>
		      </form> -->
		    </div>
		  </div>
	</nav>


	<?php if ( isset($error) && !empty($error) ) : ?>

					<div class="text-danger">
						<?php echo $error; ?>
					</div>

	<?php else : ?>



	<div class="res-name">
		<h2><?php echo $row['name']; ?></h2>
	</div>

	<div class="image">
		<img src="<?php echo $row['img']; ?>" alt="restaurant-img">
	</div>

	<div class="info">
		<div class="container">

			<div class="row mt-4">
				<div class="col-12">
						
					<tr>
				        <th class="text-right"><strong>Rating:</strong></th>
				        <td><?php echo $row['rating']; ?></td>
			       </tr>

			       <br>
			       <br>
			       <tr>
				        <th class="text-right"><strong>Type:</strong></th>
				        <td><?php echo $row['type']; ?></td>
			       </tr>
			       <br>
			 	   <br>

			 	   <tr>
				        <th class="text-right"><strong>Price:</strong></th>
				        <td><?php echo $row['description']; ?></td>
			       </tr>
			       <br>
			       <br>
			       <tr>
				        <th class="text-right"><strong>Location:</strong></th>
				       
				         <td>
				         	<?php 
				         		if(isset($row['state'])&&!empty($row['state']))
				         		{
				         			echo $row['state'];
				         		}
				         		else{
				         			echo 'Unknown State';
				         		}

				         		echo ", ";
				         	?>
				         	
				         </td>

				         <td>
				         	<?php 
				         		if(isset($row['city'])&&!empty($row['state']))
				         		{
				         			echo $row['city'];

				         		}
				         		else{
				         			echo 'Unknown City';
				         		}

				         		echo ", ";
				         	?>
				         	
				         </td>

				         <td>
				         	<?php 
				         		if(isset($row['street'])&&!empty($row['street']))
				         		{
				         			echo $row['street'];
				         		}
				         		else{
				         			echo 'Unknown Street';
				         		}
				         	?>
				         	
				         </td>
				        


			       </tr>
						
					
							
				</div>
			</div>
		</div>
	</div>

	<?php endif; ?>
	<div class="clearfloat"></div>
	<div class="button-group">
		<button type="button" class="btn btn-outline-secondary" onclick="window.location.href = 'restaurant.html';">Back</button>

		<button type="button" class="btn btn-outline-secondary" onclick="window.location.href = 'edit.php?restaurant_id=<?php echo $_GET['restaurant_id']; ?>';">Edit</button>
		
		

		<a onclick="return confirm('Do you want to delete this restaurant?')" href="Delete_confirmation.php?restaurant_id=<?php echo $_GET['restaurant_id'];?>&restaurant_name=<?php echo $_GET['restaurant_name'];?>" class="btn btn-outline-danger delete-btn">
									Delete
		</a>

	</div>




<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
</body>
</html>