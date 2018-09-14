/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.ProductType;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/orders")
@Service
public class OrdersAPIController {

    
   
        @Autowired
        RestaurantOrderServices restaurantService;
        
       
 	@RequestMapping(method = RequestMethod.GET)
 	public ResponseEntity<?> getOrders(){
            ArrayList<Order> arreglo = new ArrayList<>();
 		try {
                    for(Integer j:restaurantService.getTablesWithOrders()){
                        arreglo.add(restaurantService.getTableOrder(j));
                    }
 			//obtener datos que se enviarán a través del API
 			return new ResponseEntity<>(arreglo,HttpStatus.ACCEPTED);
 		} catch (Exception ex) {
 			Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
 			return new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
 		}  
 	}
        
        
        @RequestMapping(method = RequestMethod.GET,value="/{idmesa}")
 	public ResponseEntity<?> getMesaByiD(@PathVariable("idmesa") int idMesa){
            try{
                return new ResponseEntity<>(restaurantService.getTableOrder(idMesa),HttpStatus.ACCEPTED);
            } catch (Exception ex) {
 			Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
 			return new ResponseEntity<>("error 404 Order not found",HttpStatus.NOT_FOUND);
            }
        }
        
        @RequestMapping(method = RequestMethod.POST)	
	public ResponseEntity<?> registOrder(@RequestBody Order o){
		try {
			//registrar dato
                        //curl -i -X POST -HContent-Type:application/json -HAccept:application/json http://localhost:8080/orders -d '{"orderAmountsMap":{"HAMBURGER":2,"PIZAA":3,"BEER":2},"tableNumber":2}'
                        restaurantService.addNewOrderToTable(o);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception ex) {
			Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>("Error Orden no puede ser Registrada ",HttpStatus.FORBIDDEN);            
		}        
	
	}
        
        @RequestMapping(method = RequestMethod.GET,value="/{idmesa}/total")
 	public ResponseEntity<?> getValorCuenta(@PathVariable("idmesa") int idMesa){
            try{
                return new ResponseEntity<>(restaurantService.calculateTableBill(idMesa),HttpStatus.ACCEPTED);
            } catch (Exception ex) {
 			Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
 			return new ResponseEntity<>("error 404 Order not found",HttpStatus.NOT_FOUND);
            }
        }
        
        @RequestMapping(method = RequestMethod.PUT,value="/{idmesa}")
        public ResponseEntity<?> putProductOrder(@PathVariable("idmesa") int idMesa,@RequestBody String product ){
            try{
                //curl -i -X PUT -HContent-Type:application/json -HAccept:application/json http://localhost:8080/orders/1 -d 'BEER'
                restaurantService.getTableOrder(idMesa).addDish(product, idMesa);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (Exception ex) {
 			Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
 			return new ResponseEntity<>("error 404 Order not found",HttpStatus.NOT_FOUND);
            }
        }
        
        
 
}
