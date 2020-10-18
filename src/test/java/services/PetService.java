package services;

import com.jayway.restassured.response.Response;
import helpers.ApiHelper;
import model.api.NewPetRequestModel;

public class PetService extends ApiHelper {

    public static Response getStatus(String status) {
        return petConfig().when().log().all().get("/findByStatus?status=" + status);
    }

    public static Response postNewPet(NewPetRequestModel newPetRequestModel) {
        return petConfig().body(gson().toJson(newPetRequestModel)).log().all().post("");
    }

    public static Response getNewPet(String petId) {
        return petConfig().when().log().all().get("/" + petId);
    }

    public static Response updatePetStatus(NewPetRequestModel newPetRequestModel) {
        return petConfig().body(gson().toJson(newPetRequestModel)).log().all().put("");
    }

    public static Response deleteSoldPet(String petId) {
        return petConfig().when().log().all().delete("/" + petId);
    }
}
