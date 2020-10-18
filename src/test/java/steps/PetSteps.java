package steps;

import com.jayway.restassured.response.Response;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.ApiHelper;
import model.api.Category;
import model.api.Tags;
import model.api.NewPetRequestModel;
import model.api.get.GetErrorResponseModel;
import model.api.get.GetNewAddedPetResponseModel;
import model.api.get.GetPetListResponseModel;
import org.apache.commons.lang3.RandomStringUtils;
import services.PetService;
import org.junit.Assert;

import java.util.List;


public class PetSteps extends ApiHelper {
    private static Response getPetListResponse, addNewPetResponse, getNewPetResponse, updatePetStatusResponse, deleteSoldPetResponse;
    private static GetPetListResponseModel[] getPetListResponseModel;
    private static GetNewAddedPetResponseModel getNewAddedPetResponseModel;
    private static NewPetRequestModel newPetRequestModel;
    private static Category category = new Category();
    private static String petId, name, categoryName, categoryId, photo, tagId, tagName;
    private static String photoUrl[];
    private static GetErrorResponseModel getErrorResponseModel;
    Tags[] tag;


    @When("^user request to search list of \"([^\"]*)\" pets$")
    public void userRequestToSearchListOfPets(String status) throws Throwable {

        //hitting the get api for available pets
        getPetListResponse = PetService.getStatus(status);
        Assert.assertTrue(getPetListResponse.getStatusCode() == 200);
    }

    @Then("^list of \"([^\"]*)\" pets returned in response$")
    public void listOfPetsReturnedInResponse(String status) throws Throwable {
        //fetching the response
        getPetListResponseModel = gson().fromJson(getPetListResponse.body().prettyPrint(), GetPetListResponseModel[].class);
        for (int i = 0; i < getPetListResponseModel.length; i++)

            //verifying the get API returned data with status available
            Assert.assertEquals(status, getPetListResponseModel[i].getStatus());
    }

    @When("^user provide the category details to add new pet in the store$")
    public void userProvideTheCategoryDetailsToAddNewPetInTheStore() {
        categoryId = RandomStringUtils.randomNumeric(5);
        categoryName = RandomStringUtils.randomAlphabetic(10);
        category.setId(categoryId);
        category.setName(categoryName);
    }


    @And("^provide the photourls of pet \"([^\"]*)\"$")
    public void provideThePhotourlsOfPet(String photoUrls) throws Throwable {
        String[] photos = photoUrls.split(",");
        photoUrl = new String[photos.length];
        for (int i = 0; i < photos.length; i++)
            photoUrl[i] = photos[i];

    }

    @And("^provide the tags details of new pet$")
    public void provideTheTagsDetailsWithStatusWithUniqueAndName(List<Tags> tags) throws Throwable {
        tag = new Tags[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            Tags Tags = new Tags();
            tagId = RandomStringUtils.randomNumeric(5);
            tagName = tags.get(i).getName();
            Tags.setName(tagName);
            Tags.setId(tagId);
            tag[i] = Tags;

        }


    }

    @Then("^add the new pet in the store with status \"([^\"]*)\" and having unique \"([^\"]*)\" and below name$")
    public void addTheNewPetInTheStoreWithStatusAndHavingUniqueAndBelowName(String status, String id, List<NewPetRequestModel> newPetRequestModel) throws Throwable {

        //setting the details in Request Model
        newPetRequestModel.get(0).setTags(tag);
        petId = RandomStringUtils.randomNumeric(5);
        name = newPetRequestModel.get(0).getName();
        newPetRequestModel.get(0).setId(petId);
        newPetRequestModel.get(0).setPhotoUrls(photoUrl);
        newPetRequestModel.get(0).setCategory(category);
        newPetRequestModel.get(0).setStatus(status);
        newPetRequestModel.get(0).setName(name);

        //adding a new pet in the store
        addNewPetResponse = PetService.postNewPet(newPetRequestModel.get(0));
        Assert.assertEquals(200, addNewPetResponse.getStatusCode());
    }


    @Then("^verify that the new pet is added in the store$")
    public void verifyThatTheNewPetIsAddedInTheStore() {
        getNewPetResponse = PetService.getNewPet(petId);
        Assert.assertEquals(200, getNewPetResponse.getStatusCode());
        getNewAddedPetResponseModel = gson().fromJson(getNewPetResponse.body().prettyPrint(), GetNewAddedPetResponseModel.class);

        //verifying the get API returned the new pet added
        Assert.assertEquals(petId, getNewAddedPetResponseModel.getId());

    }


    @Then("^user change the status of pet to \"([^\"]*)\" after it gets sold$")
    public void userChangeTheStatusOfPetToAfterItGetsSold(String status) throws Throwable {
        NewPetRequestModel newPetRequestModel = new NewPetRequestModel();
        newPetRequestModel.setId(petId);
        newPetRequestModel.setCategory(category);
        newPetRequestModel.setName(name);
        newPetRequestModel.setStatus(status);
        newPetRequestModel.setTags(tag);
        newPetRequestModel.setPhotoUrls(photoUrl);

        //updating the status of pet to sold
        updatePetStatusResponse = PetService.updatePetStatus(newPetRequestModel);
        Assert.assertEquals(200, updatePetStatusResponse.getStatusCode());
    }

    @Then("^verify that status is updated to \"([^\"]*)\"$")
    public void verifyThatStatusIsUpdatedTo(String status) throws Throwable {
        getNewPetResponse = PetService.getNewPet(petId);
        Assert.assertEquals(200, getNewPetResponse.getStatusCode());
        getNewAddedPetResponseModel = gson().fromJson(getNewPetResponse.body().prettyPrint(), GetNewAddedPetResponseModel.class);

        //verifying the pet status is updated to sold
        Assert.assertEquals(petId, getNewAddedPetResponseModel.getId());
        Assert.assertEquals(status, getNewAddedPetResponseModel.getStatus());
    }

    @Then("^user deletes the record of \"([^\"]*)\" pet$")
    public void userDeletesTheRecordOfPet(String arg0) throws Throwable {
        //deleting the sold Pet

        deleteSoldPetResponse= PetService.deleteSoldPet(petId);
        Assert.assertEquals(200, deleteSoldPetResponse.getStatusCode());
    }

    @Then("^verify that the \"([^\"]*)\" in the data$")
    public void verifyThatTheInTheData(String message) throws Throwable {

        //verifying that pet is deleted from the store
        getNewPetResponse = PetService.getNewPet(petId);
        getErrorResponseModel = gson().fromJson(getNewPetResponse.body().prettyPrint(), GetErrorResponseModel.class);
        Assert.assertEquals(404, getNewPetResponse.getStatusCode());
        Assert.assertEquals(message, getErrorResponseModel.getMessage());

    }
}
