package com.api.XmemeBackend.repository;

import java.util.List;

import com.api.XmemeBackend.entity.Meme;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemeRepository extends MongoRepository<Meme, String> {

   List<Meme> findByUrl(String url); 
   
}
