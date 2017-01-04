package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.ws._
import play.api.Play.current

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import com.typesafe.config.ConfigFactory

object ServiceConfig {
  private val config = ConfigFactory.load()
  protected val appConfig = config.getConfig("app")
  val echoHost = appConfig.getString("echo.host")
}

@Singleton
class HomeController @Inject() (ws: WSClient) extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index(""))
  }

  def echo() = Action.async { implicit request =>
    // val url = "http://127.0.0.1:8080/v1/echo"

    println(s"Echo Service Endpoint is at: ${ServiceConfig.echoHost}")
    val url = "http://" + ServiceConfig.echoHost + "/v1/echo"

    val holder: WSRequest = ws.url(url)

    val response =
      for {
        r <- holder.withRequestTimeout(1000.seconds).get
      } yield r

    response.map { resp =>
      resp.status match {
        case 200 => Ok(views.html.index(resp.json.toString))
        case _ => Ok(views.html.index(s"$url not reachable"))
      }
    }.recover {
      case err => Ok(views.html.index(s"Exception: ${err.getMessage}"))
    }
  }
}
