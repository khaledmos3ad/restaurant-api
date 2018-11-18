package service

import java.io.File
import java.io.PrintWriter
import play.api._
import play.api.libs.json
import play.api.libs.json._
import scala.io.Source

import model._
import utils._


class RestaurantService{

         val JSON_FILE_PATH="app/resources/sample-restaurant-data.json"
         //val JSON_FILE_PATH="app/resources/test.json"

	  def getAllRestaurantObjects() : Seq[Restaurant] =  {
	     val source: String = Source.fromFile(JSON_FILE_PATH).getLines.mkString
	     val json: JsValue = Json.parse(source)
	     val jsonString= json.toString
	     val restaurants = JSONUtil.fromJson[Seq[Restaurant]](jsonString)   
	     return restaurants;
	  }

	  
	  def saveRestaurant(inputJson: JsValue){
	    val inputJsonString= inputJson.toString
	    val restaurant = JSONUtil.fromJson[Restaurant](inputJsonString)  
	    var restaurants=getAllRestaurantObjects
	    restaurants = restaurants :+ restaurant
	    saveRestaurantListToFile(restaurants)     
	  }

	  def updateRestaurant(uuid: String,inputJson: JsValue,restaurants: Seq[Restaurant]){
	    val inputJsonString= inputJson.toString 
	    val dataSection = JSONUtil.fromJson[Data](inputJsonString)  
	    var restaurant = new Restaurant(uuid,dataSection)
	    var newRestaurants=restaurants.filter(_.uuid != uuid)
	    newRestaurants = newRestaurants :+ restaurant
	    saveRestaurantListToFile(newRestaurants)       
	  } 
	  

	  def getPrettyRestaurant(restaurants: Seq[Restaurant]) : JsValue = {
	    val restaurantsJson=JSONUtil.toJson(restaurants)
	    val restaurantObjects: JsValue = Json.parse(restaurantsJson)
	    Json.prettyPrint(restaurantObjects)  
	    return restaurantObjects 	  
	  }

	  /////////// Helper Function  
	  def saveRestaurantListToFile(restaurants: Seq[Restaurant]) {
	    val prettyRestaurant=getPrettyRestaurant(restaurants)
	    val writer = new PrintWriter(new File(JSON_FILE_PATH))
            writer.write(prettyRestaurant.toString)
            writer.close() 
          }
	  
	  def generateStatusCode(status: String,message: String,statusCode: String)  : JsValue =  {
	    val statusObject = HttpStatus(status,message,statusCode)
	    val statusJson=JSONUtil.toJson(statusObject)
	    val statusJs: JsValue = Json.parse(statusJson)
	    Json.prettyPrint(statusJs)
	    return statusJs 
	  }

}
