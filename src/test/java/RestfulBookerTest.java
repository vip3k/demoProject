import config.Configuration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RestfulBookerTest {

    private final Configuration configuration = new Configuration();
    private final String user1 = configuration.getProperties().getProperty("user1");
    private final String passwordUser1 = configuration.getProperties().getProperty("passwordUser1");
    private int bookingId;

    @Test(priority = 1)
    public void testCreateBooking() {
        baseURI = "https://restful-booker.herokuapp.com/";

        JSONObject requestBody = new JSONObject()
                .put("firstname", "John")
                .put("lastname", "Doe")
                .put("totalprice", 100)
                .put("depositpaid", true)
                .put("bookingdates", new JSONObject()
                        .put("checkin", "2022-09-01")
                        .put("checkout", "2022-09-10")
                )
                .put("additionalneeds", "Breakfast");

        Response response = given()
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post("/booking");

        response.then()
                .statusCode(200)
                .body("bookingid", notNullValue());

        bookingId = response.jsonPath().getInt("bookingid");
    }

    @Test(priority = 2)
    public void testGetBooking() {
        baseURI = "https://restful-booker.herokuapp.com/";

        given()
                .pathParam("bookingId", bookingId)
                .when()
                .get("/booking/{bookingId}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("John"))
                .body("lastname", equalTo("Doe"))
                .body("totalprice", equalTo(100))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2022-09-01"))
                .body("bookingdates.checkout", equalTo("2022-09-10"))
                .body("additionalneeds", equalTo("Breakfast"));
    }

    @Test(priority = 3)
    public void testUpdateBooking() {
        baseURI = "https://restful-booker.herokuapp.com";


        JSONObject requestBody = new JSONObject()
                .put("firstname", "John")
                .put("lastname", "Doe")
                .put("totalprice", 100)
                .put("depositpaid", true)
                .put("bookingdates", new JSONObject()
                        .put("checkin", "2023-03-10")
                        .put("checkout", "2023-03-15")
                )
                .put("additionalneeds", "Breakfast");

        Response createResponse = given()
                .contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/booking");

        int bookingId = createResponse.jsonPath().getInt("bookingid");
        System.out.println("Created booking with ID: " + bookingId);

        JSONObject updateBody = new JSONObject()
                .put("firstname", "Jane")
                .put("lastname", "Doe")
                .put("totalprice", 150)
                .put("depositpaid", false)
                .put("bookingdates", new JSONObject()
                        .put("checkin", "2023-03-12")
                        .put("checkout", "2023-03-18")
                )
                .put("additionalneeds", "Gym");

        Response updateResponse = given()
                .auth()
                .preemptive()
                .basic((user1), (passwordUser1))
                .contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .body(updateBody.toString())
                .when()
                .put("/booking/" + bookingId);

        updateResponse.then().statusCode(200);

        System.out.println("Updated booking with ID: " + bookingId + ", new data: " + updateBody.toString());
    }

    @Test(priority = 4)
    public void testDeleteBooking() {
        baseURI = "https://restful-booker.herokuapp.com/";

        Response response = given()
                .auth()
                .preemptive()
                .basic((user1), (passwordUser1))
                .pathParam("bookingId", bookingId)
                .when()
                .delete("/booking/{bookingId}");

        response.then()
                .statusCode(201);

        given().auth()
                .preemptive()
                .basic((user1), (passwordUser1))
                .pathParam("bookingId", bookingId)
                .when()
                .get("/booking/{bookingId}")
                .then()
                .statusCode(404);

        System.out.println(response.getBody().asString());
    }

}
