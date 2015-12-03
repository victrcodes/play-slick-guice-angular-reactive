package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import fixtures.ControllerSpec
import maps.Converters._
import maps.Maps._
import maps.OrderMap
import org.joda.time.DateTime
import org.mockito.Mockito._
import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest}
import services.IOrderService

class OrderControllerSpec extends ControllerSpec {

	val service = mock[IOrderService]
	val controller = new OrderController(service)

	"getOrders() method" should {
		"return order list JSON" in {
			when(service.getOrders) thenReturn Future(Seq(OrderMap(Some(1), Some(DateTime.parse("2015-11-20 00:00:00.000000", formatter)), "name", Some("description"))))
			val result: Future[Result] = controller.getOrders().apply(FakeRequest())
			contentAsString(result) mustEqual """[{"id":1,"date":"2015-11-20 00:00:00.000000","name":"name","description":"description"}]"""
		}
	}

	"upsertOrder() method" should {
		"insert or update order and return a success JSON" in {
			val order = OrderMap(Some(1), None, "name", Some("description"))
			when(service.upsertOrder(order)) thenReturn Future(1)
			val result: Future[Result] = controller.upsertOrder().apply(FakeRequest("PUT", "/data/order", FakeHeaders().add(("Content-Type", "application/json")), Json.toJson(order)))
			contentAsString(result) mustEqual """{"status":"success","message":"order saved"}"""
		}
		"fail to insert or update order and return an error JSON" in {
			val json = Json.parse("""{"description": "description"}""")
			val result: Future[Result] = controller.upsertOrder().apply(FakeRequest("PUT", "/data/order", FakeHeaders().add(("Content-Type", "application/json")), json))
			contentAsString(result) mustEqual """{"status":"error","message":{"obj.name":[{"msg":["error.path.missing"],"args":[]}]}}"""
		}
		"fail to insert or update order and return an internal error JSON" in {
			val order = OrderMap(Some(1), None, "name", Some("description"))
			when(service.upsertOrder(order)) thenReturn Future(0)
			val result: Future[Result] = controller.upsertOrder().apply(FakeRequest("PUT", "/data/order", FakeHeaders().add(("Content-Type", "application/json")), Json.toJson(order)))
			contentAsString(result) mustEqual """{"status":"error","message":"unknown error occurred"}"""
		}
	}

}