package com.repl.store.api.rest;

import com.repl.store.api.sdk.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StoreInventoryControllerTest extends BaseTest {

    @Test
    public void createNewStoreWithoutInventory() {
        given()
                .contentType(CONTENT_TYPE)
                .body("{\n" +
                        "  \"storeId\" : 5" +
                        "}")
                .when()
                .post(getContextPath()+ "/store")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void failsToCreateNewStoreIfEmptyRequest() {
        given()
                .contentType(CONTENT_TYPE)
                .body("{\n" +
                        "}")
                .when()
                .post(getContextPath()+ "/store")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errorMessage", equalTo("storeId must not be null"));
    }

    @Test
    public void createNewStoreWithInventory() {
        given()
                .contentType(CONTENT_TYPE)
                .body("{\n" +
                        "    \"StoreId\": 99304,\n" +
                        "    \"Refund\": [\n" +
                        "        {\n" +
                        "            \"ItemName\": \"T-shirt\",\n" +
                        "            \"ItemId\": 728392017342,\n" +
                        "            \"Quantity\": 2\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"Delivery\": [\n" +
                        "        {\n" +
                        "            \"ItemName\": \"T-shirt\",\n" +
                        "            \"ItemId\": 728392017342,\n" +
                        "            \"Quantity\": 3\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"Sale\": [\n" +
                        "        {\n" +
                        "            \"ItemName\": \"T-shirt\",\n" +
                        "            \"ItemId\": 728392017342,\n" +
                        "            \"Quantity\": 4\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .when()
                .post(getContextPath()+ "/store")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void failsToCreateNewStoreWithInventoryIfItemNameMissing() {
        given()
                .contentType(CONTENT_TYPE)
                .body("{\n" +
                        "    \"StoreId\": 99304,\n" +
                        "    \"Refund\": [\n" +
                        "        {\n" +
                        "            \"ItemId\": 728392017342,\n" +
                        "            \"Quantity\": 2\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .when()
                .post(getContextPath()+ "/store")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errorMessage", equalTo("refund[0].itemName must not be empty"));
    }

    @Test
    public void failsToCreateNewStoreWithInventoryIfItemNameNotSet() {
        given()
                .contentType(CONTENT_TYPE)
                .body("{\n" +
                        "    \"StoreId\": 99304,\n" +
                        "    \"Refund\": [\n" +
                        "        {\n" +
                        "            \"ItemName\": \"\",\n" +
                        "            \"ItemId\": 728392017342,\n" +
                        "            \"Quantity\": 2\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .when()
                .post(getContextPath()+ "/store")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errorMessage", equalTo("refund[0].itemName must not be empty"));
    }

    @Test
    public void createNewStoreWithPartialInventory() {
        given()
                .contentType(CONTENT_TYPE)
                .body("{\n" +
                        "    \"StoreId\": 99304,\n" +
                        "    \"Refund\": [\n" +
                        "        {\n" +
                        "            \"ItemName\": \"T-shirt\",\n" +
                        "            \"Quantity\": 2\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"Sale\": [\n" +
                        "        {\n" +
                        "            \"ItemName\": \"T-shirt\",\n" +
                        "            \"ItemId\": 728392017342\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .when()
                .post(getContextPath()+ "/store")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void createMultipleNewStoresWithoutInventory() {
        given()
                .contentType(CONTENT_TYPE)
                .body("[\n " +
                        "{\n" +
                        "  \"storeId\" : 5" +
                        "  },\n" +
                        "{\n" +
                        "  \"storeId\" : 10" +
                        "  }\n" +
                        "]")
                .when()
                .post(getContextPath()+ "/stores")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void createMultipleNewStoresWithInventory() {
        given()
                .contentType(CONTENT_TYPE)
                .body("[\n" +
                        "    {\n" +
                        "        \"StoreId\": 99304,\n" +
                        "        \"Sale\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"T-shirt\",\n" +
                        "                \"ItemId\": 728392017342,\n" +
                        "                \"Quantity\": 2\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"Refund\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"Shorts\",\n" +
                        "                \"ItemId\": 364422297782,\n" +
                        "                \"Quantity\": 1\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"Delivery\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"Polo-shirt\",\n" +
                        "                \"ItemId\": 464368907499,\n" +
                        "                \"Quantity\": 100\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"ItemName\": \"T-shirt\",\n" +
                        "                \"ItemId\": 728392017342,\n" +
                        "                \"Quantity\": 75\n" +
                        "\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"StoreId\": 99283,\n" +
                        "        \"Sale\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"T-shirt\",\n" +
                        "                \"ItemId\": 728392017342,\n" +
                        "                \"Quantity\": 4\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"Refund\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"Shorts\",\n" +
                        "                \"ItemId\": 364422297782,\n" +
                        "                \"Quantity\": 3\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"Delivery\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"Polo-shirt\",\n" +
                        "                \"ItemId\": 464368907499,\n" +
                        "                \"Quantity\": 76\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"ItemName\": \"T-shirt\",\n" +
                        "                \"ItemId\": 728392017342,\n" +
                        "                \"Quantity\": 88\n" +
                        "\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "]\n")
                .when()
                .post(getContextPath()+ "/stores")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void updateStoreInventory() {
        createNewStoreWithInventory();

        given()
                .contentType(CONTENT_TYPE)
                .body("{\n" +
                        "    \"StoreId\": 99304,\n" +
                        "    \"Refund\": [\n" +
                        "        {\n" +
                        "            \"ItemName\": \"T-shirt\",\n" +
                        "            \"ItemId\": 464368907499,\n" +
                        "            \"Quantity\": 5\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put(getContextPath()+ "/store/inventory")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void updateMultipleStoreInventories() {
        createMultipleNewStoresWithInventory();

        given()
                .contentType(CONTENT_TYPE)
                .body("[\n" +
                        "    {\n" +
                        "        \"StoreId\": 99304,\n" +
                        "        \"Sale\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"T-shirt\",\n" +
                        "                \"ItemId\": 728395557349,\n" +
                        "                \"Quantity\": 6\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"Delivery\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"Polo-shirt\",\n" +
                        "                \"ItemId\": 464368999488,\n" +
                        "                \"Quantity\": 74\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"ItemName\": \"T-shirt\",\n" +
                        "                \"ItemId\": 728392017342,\n" +
                        "                \"Quantity\": 37\n" +
                        "\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"StoreId\": 99283,\n" +
                        "        \"Refund\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"Shorts\",\n" +
                        "                \"ItemId\": 364433397716,\n" +
                        "                \"Quantity\": 47\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "]\n")
                .when()
                .put(getContextPath()+ "/stores/inventories")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteStore() {
        createNewStoreWithoutInventory();

        given()
                .contentType(CONTENT_TYPE)
                .when()
                .delete(getContextPath() + "/store/5")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteManyStores() {
        createMultipleNewStoresWithoutInventory();

        given()
                .contentType(CONTENT_TYPE)
                .when()
                .body("{\n" +
                        "    \"storeIds\" : [5, 10]\n" +
                        "}")
                .delete(getContextPath() + "/stores")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteStoreInventoryItems() {
        createNewStoreWithInventory();

        given()
                .contentType(CONTENT_TYPE)
                .when()
                .body("{\n" +
                "    \"StoreId\": 99304,\n" +
                "    \"Refund\": [\n" +
                "        {\n" +
                "            \"ItemName\": \"T-shirt\"\n" +
                "        }\n" +
                "    ]\n" +
                "}")
                .when()
                .delete(getContextPath()+ "/store/inventory")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteMultipleStoreInventoryItems() {
        createMultipleNewStoresWithInventory();

        given()
                .contentType(CONTENT_TYPE)
                .when()
                .body("[\n" +
                        "    {\n" +
                        "        \"StoreId\": 99304,\n" +
                        "        \"Sale\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"T-shirt\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"Delivery\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"T-shirt\"\n" +
                        "\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"StoreId\": 99283,\n" +
                        "        \"Refund\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"Shorts\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"Delivery\": [\n" +
                        "            {\n" +
                        "                \"ItemName\": \"Polo-shirt\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "]\n")
                .when()
                .delete(getContextPath()+ "/stores/inventories")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void retrieveStore() {
        createNewStoreWithInventory();

        given()
                .contentType(CONTENT_TYPE)
                .when()
                .get(getContextPath() + "/store/99304")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("refund[0].itemId", equalTo(728392017342L))
                .body("sale[0].quantity", equalTo(4));
    }

    @Test
    public void retrieveAllStores() {
        createMultipleNewStoresWithoutInventory();

        given()
                .contentType(CONTENT_TYPE)
                .when()
                .get(getContextPath() + "/stores")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("storeId", hasItems(5, 10));
    }

    @Test
    public void retrieveNoStoresWhenNoneExists() {
        given()
                .contentType(CONTENT_TYPE)
                .when()
                .get(getContextPath() + "/stores")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("$", not(hasKey(5)));
    }

}
