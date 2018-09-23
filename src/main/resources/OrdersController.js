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