
function displayresult(results)
{
    // let convertedResults=JSON.parse(results);
    

    // console.log("Bye");
    // console.log(convertedResults.hits[0].recipe.image);


    let paragaphs=document.querySelector(".search-container");
    while( paragaphs.hasChildNodes() ) {
        // As long as #paragaphs-container has children run the following code:
        paragaphs.removeChild(paragaphs.lastChild);
    }

    //convert JSon string 
    let convertedResults=JSON.parse(results);
    
    document.querySelector("#num-results").innerHTML=convertedResults.hits.length;

    if(convertedResults.hits.length!=0)
    {
        // console.log("Calories2: ");
        // console.log("Recipe length: ");
        // console.log(convertedResults.hits.length);
        for(let i=0;i<convertedResults.hits.length;i++)
        {
            // let synopsis=convertedResults.results[i].overview.substring(0, 200);
            // if(synopsis.length>0)
            // {
            //     synopsis+="...";
            // }
            // else
            // {
            //     synopsis="No overview found."
            // }
            // let ingredient[]=convertedResults.hits[i].ingredients;
            let ingre="<ul>";
            let imgsrc="";
            let thecookingtime="";

            if(convertedResults.hits[i].recipe.totalTime!=0&&convertedResults.hits[i].recipe.totalTime!=null)
            {
                thecookingtime=convertedResults.hits[i].recipe.totalTime+" mins";
            }
            else{
                thecookingtime="Unknown";
            }

            //check image
            if(convertedResults.hits[i].recipe.image!=null)
            {
                imgsrc=convertedResults.hits[i].recipe.image;
            }
            else{
                imgsrc="no-image.jpeg";
            }


            // console.log("ingre");
            let len=5;
            if(convertedResults.hits[i].recipe.ingredients.length<=5)
            {
                len=convertedResults.hits[i].recipe.ingredients.length;
            }
            for(let j=0;j<len;j++)
            {
                // console.log(convertedResults.hits[i].recipe.ingredients[j].text);
                ingre+="<li>";
                ingre+=convertedResults.hits[i].recipe.ingredients[j].text;
                ingre+="<br>";
                ingre+="</li>";
                // console.log(convertedResults.hits[i].recipe.ingredients[j].text);
                // console.log(convertedResults.hits[i].recipe.ingredients[j+1].text);
            }
            // console.log("ingre is:");
            // console.log(ingre);

         

            if(true)
            {
                let htmlString=`
                 <div class="item">
                        
                            
                          
                            
                                 <img src=${imgsrc} class="img-fluid">
                            <div class="text">  
                                 <p>Label: ${convertedResults.hits[i].recipe.label}   
                                 <p>Cuisine Type: ${convertedResults.hits[i].recipe.cuisineType}</p>
                                
                                 
                                 
                                 

                            </div>
                                <div class="accordion collapse show" id="accordionExample">
                                        <div class="accordion-item">
                                    <h2 class="accordion-header" id="headingOne">
                                      <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                        More
                                      </button>
                                    </h2>
                                    <div id="collapseOne" class="accordion-collapse collapse " aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                      <div class="accordion-body">
                                        <p>Health Labels: ${convertedResults.hits[i].recipe.healthLabels[0]}</p>
                                         <p>Cautions: ${convertedResults.hits[i].recipe.cautions}
                                         <p>CookingTime: ${thecookingtime}
                                         <p>Ingredient: ${ingre}</p>
                                        
                                      </div>
                                    </div>
                                  </div>
                                
                      
                        
                 </div>
                 `;
                 document.querySelector(".search-container").innerHTML += htmlString;
            }
            
        }
        
      }

}
    //         if(convertedResults.hits[i].recipe.image!=null)
    //         {
    //             console.log("Calories3: ");
    //             console.log(convertedResults.hits[0].recipe.calories);
    //             let htmlString=`
    //             <div class="item">
    //                    
                            
                          
    //                         <div class="text">
    //                             <p>Calories: ${convertedResults.hits[i].recipe.calories}</p>
    //                             <p>Cuisine Type: ${convertedResults.hits[i].recipe.cuisineType}</p>
    //                             <p>Ingredient: ${ingre}</p>
                            
    //                         </div>
                      
                        
    //             </div>
    //             `;
    //             document.querySelector(".searchresult").innerHTML += htmlString;
    //         }
    //         else{
    //             // let htmlString=`
    //             // <div class="item">
    //             //        <div class="hoverimg">
                            
    //             //             <img src="download.png">
    //             //             <div class="overview">
    //             //                 <p>Rating: ${convertedResults.results[i].vote_average}</p>
    //             //                 <p>Number of Voters: ${convertedResults.results[i].vote_count}</p>
    //             //                 <span class="text">${synopsis}</span>
                                
    //             //             </div>
    //             //         </div>
    //             //         <div class="titleandrelease">
    //             //             <p>${convertedResults.results[i].title}</p>
    //             //             <p>${convertedResults.results[i].release_date}</p>
    //             //         </div>
    //             // </div>
    //             // `;
    //             // document.querySelector(".search-result").innerHTML += htmlString;
    //         }
    //     }
    // }
    // else
    // {
    //     document.querySelector(".search-result").innerHTML+="No Results Found.";
    // }




function ajax(endpoint, returnFunction) {
    // Use AJAX to make a request to the iTunes API endpoint
    // We will be using XMLHttpRequest objects to make ajax requests
    // Note: there are other ways to make AJAX requests. fetch api is one of them.
    
    let httpRequest = new XMLHttpRequest();

    // .open() starts a request.
    // 1st param: the method, GET or POST (depends on the API)
    // 2nd param: the endpoint to make the request to
    httpRequest.open("GET", endpoint);

    // .send() sends the request!
    httpRequest.send();

    // don't know when iTunes is going to send a response. No need to wait around.
    // Set up a "notification." This function will be called when iTunes eventually gives a response back
    httpRequest.onreadystatechange = function() {
        // This function runs when iTunes gives us some kind of response back
        console.log("got a response!!!");
        console.log(httpRequest.readyState);

        // When the response reqaches the 4th state, it means it's ready for us to use!
        if(httpRequest.readyState == 4) {
            // Some kind of response has been received
            // Can check here if there is an error or not. Status code 200 means everything is succesful
            if(httpRequest.status == 200) {
                //.responseText returns the string of the response that was received
                //console.log(httpRequest.responseText);

                // Results were received!! Let's display the results now. In a separate function.
                returnFunction(httpRequest.responseText);
               

            }
            else {
                console.log("AJAX error!");
                console.log(httpRequest.status);
            }
        }
    }

    console.log("moving along...");
    
    
}
let endpoint="https://api.edamam.com/search?r=http%3A%2F%2Fwww.edamam.com%2Fontologies%2Fedamam.owl%23recipe_0123456789abcdef0123456789abcdef&q=dessert&app_id=e2938058&app_key=0049fd54e6ef6400307913e8ea7bd540";

ajax(endpoint,displayresult);

// <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
document.querySelector("#search-form").onsubmit=function(event)
{
    //  let paragaphs=docuemnt.querySelector(".search-result");
    //   while( paragaphs.hasChildNodes() ) {
    //         // As long as #paragaphs-container has children run the following code:
    //     paragraphs.removeChild(paragraphs.lastChild);
    // }


    event.preventDefault();
    let searchInput=document.querySelector("#search-id").value.trim();
    // console.log(searchInput);
    if(searchInput.length<=0)
    {
        alert('Must enter a keyword!');
        
        return false;
    }

    let endpoint1="https://api.edamam.com/search?r=http%3A%2F%2Fwww.edamam.com%2Fontologies%2Fedamam.owl%23recipe_0123456789abcdef0123456789abcdef&q="+encodeURIComponent(searchInput)+"&app_id=e2938058&app_key=0049fd54e6ef6400307913e8ea7bd540";
    ajax(endpoint1, displayresult);
    
    // console.log("the id is :"+theid);
    // let endpoint2 = "https://api.themoviedb.org/3/configuration?api_key=https%3A%2F%2Fapi.themoviedb.org%2F3%2Fmovie%2F550%3Fapi_key%3D9f4163e669585aadfad767ab71828ad6";
    // ajaxRequest(endpoint2, displayResults);
    



}
