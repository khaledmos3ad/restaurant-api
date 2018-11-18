package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import service._

@Singleton
class RestaurantAPIController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val restaurantService = new RestaurantService()

  def getAll(closed: Int) = Action { implicit request: Request[AnyContent] =>
    var restaurants=restaurantService.getAllRestaurantObjects 
    if( closed == 0 ){
        restaurants = restaurants.filter(_.data.closed == false)
    }
    Ok(restaurantService.getPrettyRestaurant(restaurants))
  } 
    	
  def post() = Action { implicit request: Request[AnyContent] =>
    val inputJson=request.body.asJson.get
    restaurantService.saveRestaurant(inputJson)
    val status = restaurantService.generateStatusCode("OK","Restaurant Added Successfully", "200")
    Ok(status)
    
  }

  def put(uuid: String) = Action { implicit request: Request[AnyContent] =>
    val inputJson=request.body.asJson.get  
    var restaurants=restaurantService.getAllRestaurantObjects
    val restaurantExist=restaurants.exists(_.uuid == uuid) 
    if(restaurantExist){
      restaurantService.updateRestaurant(uuid,inputJson,restaurants)
      val status = restaurantService.generateStatusCode("OK","Restaurant Updated Successfully", "200")
	 Ok(status)
    }else{
       val status = restaurantService.generateStatusCode("Error","UUID "+uuid+" not Found", "404")
       Ok(status)   
    }
  }

}
