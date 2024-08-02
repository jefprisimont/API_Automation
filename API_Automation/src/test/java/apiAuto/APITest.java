package apiAuto;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import org.json.JSONObject;

import java.net.http.HttpResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class APITest {

    @Test
    public void getUserTest() {
        //Test GET API /public/v1/users with total data 10
        RestAssured.given().when().get("https://gorest.co.in/public/v1/users")
                .then()
                .log().all() //is used to print the entire request to console (optional)
                .assertThat().statusCode(200) //assert the status code to be 200 (Ok)
                .assertThat().body("meta.pagination.pages", Matchers.equalTo(286))
                .assertThat().body("meta.pagination.page", Matchers.equalTo(1))//assert that we access the correct page
                .assertThat().body("data.id", Matchers.hasSize(10)); //assert that the data has 10 entries
    }


    @Test
    public void createNewUserTest() {
        //create POST body with parameter below in JSON format
        String name= "Andi";
        String email = "andi123@gmail.com";
        String gender = "male";
        String status = "active";
        //HashMap Alternative
        JSONObject jsonObject = new JSONObject();
        jsonObject.put ("name", name);
        jsonObject.put ("email", email);
        jsonObject.put ("gender", gender);
        jsonObject.put ("status", status);
        //Test POST with header that accept json format
        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(jsonObject.toString())//convert json to string format -> (name, email, gender, status)
                .when()
                .post("https://gorest.co.in/public/v1/users")
                .then()
                .log().all() //is used to print hte entire request to console (optional)
                .assertThat().statusCode(201) //assert the status code to be 201 (created)
                .assertThat().body("name", Matchers.equalTo(name)) //assert response body "name"
                .assertThat().body("email", Matchers.equalTo(email)) //assert response body "email"
                .assertThat().body("gender", Matchers.equalTo(gender)) //assert response body "gender"
                .assertThat().body("status", Matchers.equalTo(status)) //assert response body "status"
                .assertThat().body("$", Matchers.hasKey("id")) //assert response has key "id"
                .assertThat().body("$", Matchers.hasKey("createdAt")); //assert response body has key"createdAt"
    }


}

