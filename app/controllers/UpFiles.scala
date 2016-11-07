package controllers
import javax.inject.Inject
import java.io.File
import play.api._
import play.api.mvc._
class UpFiles @Inject() extends Controller {
 
def index = Action {
 	Ok(views.html.index("File Upload In Play"))
 }
}