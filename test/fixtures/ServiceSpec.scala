package fixtures

import com.typesafe.config.ConfigFactory
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.test.FakeApplication
import slick.driver.JdbcProfile

trait ServiceSpec extends PlaySpec with OneAppPerSuite {

	val conf = ConfigFactory.load
	implicit override lazy val app = FakeApplication()
	lazy val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
	lazy val db = dbConfig.db

}