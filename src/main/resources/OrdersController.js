var order={
	"order_id": 1,
	"table_id": 1,
	"products": [{
			"product": "PIZZA",
			"quantity": 3,
			"price": "$15.000"
		},
		{
			"product": "HAMBURGER",
			"quantity": 1,
			"price": "$12.300"
		}
	]
}
function addOrder(){
	axios.post('/orders',order)
	.then(function (response) {
		console.log(response);
	})
	.catch(function (error) {
		console.log(error);
	});
}
function removeOrderById(id){
	var element = document.getElementById(id).remove();
	axios.delete('/orders',{params:{idmesa:id}})
	   .then(function(response){
		   
			console.log(response);
		})
		.catch(function(error){
			console.log(error);
			alert("There is a problem we can delete the given Order");
		});
}

function loadOrders(){
    orders = [];
    axios.get('/orders')
    .then(function(result){
    orders = result.data;
             
    $("#change").empty();
    for(i in orders){
            products = Object.keys(orders[i].orderAmountsMap);
            console.log(products);
            $("#change").append("<p id='tag"+i+"'>tabla "+i+ "</p>");                                
            $("#change").append("<table id="tabla" class="table table-striped"> <thead class="thead-light"> <tr> <th scope="col">Product</th> <th scope="col">Quantity</th> <th scope="col">Prices</th> </tr> </thead>");
            for(j in products){
                    console.log(orders[i].orderAmountsMap[products[j]]);
                    str="#Order"+i.toString();
                    $(str).append("<tbody> <tr> <td>"+products[j]+"</td> <td>"+orders[i].orderAmountsMap[products[j]]+"</td> </tr> </tbody>");
            }
        }						
    })
    .catch(function(error){
        console.log(orders);
            console.log(error);
            errorMessage();
    });
}