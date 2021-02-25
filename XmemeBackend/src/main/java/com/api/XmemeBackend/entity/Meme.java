package com.api.XmemeBackend.entity;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

@Document
public class Meme {
    
    @Id
    @ApiModelProperty(notes = "Unique Id of the Meme")
    private String id;

    @ApiModelProperty(notes = "Name of the Meme creator")
    @NotNull(message = "Name cannot be Empty")
    private String name;

    @ApiModelProperty(notes = "URL of the Posted Meme")
    @NotNull(message = "url cannot be Empty")
    private String url;

    @ApiModelProperty(notes = "Caption for the Posted Meme")
    @NotNull(message = "caption cannot be Empty")
    private String caption;

    public Meme() {
    }

    public Meme(String id, String name, String url, String caption) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.caption = caption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return "Meme [id=" + id + ", name=" + name + ", url=" + url + ", caption=" + caption + "]";
    }
    
    
    
}
