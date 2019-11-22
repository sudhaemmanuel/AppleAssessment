package AppleStoreTestCases;

	import java.io.IOException;
	import java.util.Map;
	import java.util.Set;
	import org.testng.annotations.Test;
	import org.testng.asserts.SoftAssert;
	import com.fasterxml.jackson.databind.JsonNode;
	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.fasterxml.jackson.databind.node.ObjectNode;

    import com.jayway.restassured.path.json.JsonPath;
	import static com.jayway.restassured.RestAssured.*;
	
	public class AppleStoreApiTest {
	@Test
	public void checkResponse() throws IOException {


	SoftAssert softAssert=new SoftAssert();
	String expectedStatus="n";
	String actualStatus="y";

	String res= given()
	.when()

	.get("https://istheapplestoredown.com/api/v1/status/worldwide")
	.then()
	.extract().response().asString();

	ObjectMapper mapper = new ObjectMapper();
	ObjectNode node = mapper.createObjectNode();

	JsonNode tree = mapper.readTree(res);

	JsonPath path=new JsonPath(res);
	       Map<String,String> map= path.getMap("$");
	       Set<String>mapSet=map.keySet();

	for(String s:mapSet){
	node.set(s, tree.get(s).get("status"));
	if(tree.get(s).get("status").asText().contains("y")){
	softAssert.assertEquals(actualStatus, expectedStatus);
	System.out.println("Country name with status:'y' is:");
	System.out.println(tree.get(s).get("name"));
	           }
	softAssert.assertAll();      
	       }


	}

	
}
