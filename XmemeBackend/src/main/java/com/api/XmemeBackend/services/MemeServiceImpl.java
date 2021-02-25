package com.api.XmemeBackend.services;

import java.util.List;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.api.XmemeBackend.entity.Meme;
import com.api.XmemeBackend.exception.InvalidUrlException;
import com.api.XmemeBackend.exception.MemeCollectionException;
import com.api.XmemeBackend.exception.MemeWrongUpdateException;
import com.api.XmemeBackend.repository.MemeRepository;
import com.google.common.base.Strings;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MemeServiceImpl implements MemeService {

    @Autowired
    private MemeRepository memeRepository;

    public static boolean isValid(String url) 
    { 
        try { 
            new URL(url).toURI(); 
            return true; 
        } 
          
        catch (Exception e) { 
            return false; 
        }
    } 

    @Override
    public List<Meme> getMemes() {

        List<Meme> memeList = memeRepository.findAll(Sort.by("id").descending());

        if (memeList.size() > 0) {
            return memeList.subList(0, Math.min(100, memeList.size()));
        } else {
            return new ArrayList<Meme>();
        }
    }

    @Override
    public ResponseEntity<?> createMeme(Meme meme) throws ConstraintViolationException, MemeCollectionException, InvalidUrlException {

        if(isValid(meme.getUrl()) != true) {
            throw new InvalidUrlException(InvalidUrlException.wrongUrlException());
        }

        List<Meme> sameUrlMeme = memeRepository.findByUrl(meme.getUrl());

        if (sameUrlMeme.size() == 0) {
            String id = ObjectId.get().toHexString();
            meme.setId(id);
            memeRepository.save(meme);

            return new ResponseEntity<String>(id, HttpStatus.CREATED);
        } else {
            throw new MemeCollectionException(MemeCollectionException.MemeAlreadyExists());
        }
    }

    @Override
    public Meme getMeme(String memeId) throws MemeCollectionException {
        Optional<Meme> requiredMeme = memeRepository.findById(memeId);

        if (requiredMeme.isPresent() != true) {
            throw new MemeCollectionException(MemeCollectionException.NotFoundException(memeId));
        } else {
            return requiredMeme.get();
        }
    }

    @Override
    public void updateMeme(HashMap<String, String> newValues, String memeId) throws MemeCollectionException, MemeWrongUpdateException, InvalidUrlException {
        Optional<Meme> requiredMemeWithId = memeRepository.findById(memeId);

        if (requiredMemeWithId.isPresent() == true) {

            if(Strings.isNullOrEmpty(newValues.get("name")) != true) {
                throw new MemeWrongUpdateException(MemeWrongUpdateException.updationNotAllowedException(memeId));
            }

            String newCaption = newValues.get("caption");
            String newUrl = newValues.get("url");
           
            Meme oldMemeToUpdate = requiredMemeWithId.get();

            if(!(Strings.isNullOrEmpty(newCaption)) && (isValid(newUrl) != true)) {
                throw new InvalidUrlException(InvalidUrlException.wrongUrlException());
            }

            newCaption = Strings.isNullOrEmpty(newCaption) ? oldMemeToUpdate.getCaption() : newCaption;
            newUrl = Strings.isNullOrEmpty(newUrl) ? oldMemeToUpdate.getUrl() : newUrl;

            oldMemeToUpdate.setCaption(newCaption);
            oldMemeToUpdate.setUrl(newUrl);

            this.memeRepository.save(oldMemeToUpdate);
        } else {
            throw new MemeCollectionException(MemeCollectionException.NotFoundException(memeId));
        }
        
    }
    
}
