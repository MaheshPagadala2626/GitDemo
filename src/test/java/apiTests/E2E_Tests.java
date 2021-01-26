package apiTests;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class E2E_Tests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String userID = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
        String userName = "TOOLSQA-Test";
        String password = "Test@@123";
        String baseUrl = "https://bookstore.toolsqa.com";
        
        RestAssured.baseURI = baseUrl;
        RequestSpecification request = RestAssured.given();
        System.out.println(request);
      //Step - 1
        //Test will start from generating Token for Authorization
        request.header("Content-Type", "application/json");
 
        Response response = request.body("{ \"userName\":\"" + userName + "\", \"password\":\"" + password + "\"}")
                .post("/Account/v1/GenerateToken");
        
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(response.asString());
        String jsonString = response.asString();
        Assert.assertTrue(jsonString.contains("token"));
        
      //This token will be used in later requests
        String token = JsonPath.from(jsonString).get("token");
        
      //Step - 2
        // Get Books - No Auth is required for this.
        response = request.get("/BookStore/v1/Books");
        
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(response.asString());
        jsonString = response.asString();
        List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
        Assert.assertTrue(books.size() > 0);
 
         //This bookId will be used in later requests, to add the book with respective isbn
        String bookId = books.get(0).get("description");
        System.out.println("Book Id Description  : \n"+ bookId);
        System.out.println("Book Id Description  : \n"+ bookId);
     
        
        System.out.println("Book Id Description  : \n"+ bookId);
        System.out.println("Book Id Description  : \n"+ bookId);
	}

}
