package model.api;

import model.api.Category;
import model.api.Tags;

public class NewPetRequestModel {

    private String[] photoUrls;

    private String name;

    private String id;

    private Category category;

    private Tags[] tags;

    private String status;

    public String[] getPhotoUrls ()
    {
        return photoUrls;
    }

    public void setPhotoUrls (String[] photoUrls)
    {
        this.photoUrls = photoUrls;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Category getCategory ()
    {
        return category;
    }

    public void setCategory (Category category)
    {
        this.category = category;
    }

    public Tags[] getTags ()
    {
        return tags;
    }

    public void setTags (Tags[] tags)
    {
        this.tags = tags;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

}
