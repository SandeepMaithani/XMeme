package com.api.XmemeBackend.services;

import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import com.api.XmemeBackend.entity.Meme;
import com.api.XmemeBackend.exception.InvalidUrlException;
import com.api.XmemeBackend.exception.MemeCollectionException;
import com.api.XmemeBackend.exception.MemeWrongUpdateException;

import org.springframework.http.ResponseEntity;

public interface MemeService {
    public List<Meme> getMemes();
    public ResponseEntity<?> createMeme(Meme meme) throws ConstraintViolationException, MemeCollectionException, InvalidUrlException;
    public void updateMeme(HashMap<String, String>newValues, String memeId) throws MemeCollectionException, MemeWrongUpdateException, InvalidUrlException;
    public Meme getMeme(String memeId) throws MemeCollectionException;
    
}
