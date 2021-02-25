package com.api.XmemeBackend.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import com.api.XmemeBackend.entity.Meme;
import com.api.XmemeBackend.exception.InvalidUrlException;
import com.api.XmemeBackend.exception.MemeCollectionException;
import com.api.XmemeBackend.exception.MemeWrongUpdateException;
import com.api.XmemeBackend.services.MemeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Xmeme Endpoints", description = "Information Regarding All Available Endpoints for Xmeme")
@RestController
public class RequestController {

    @Autowired
    private MemeService memeService;

    @ApiOperation(value = "Return Latest 100 Memes")
    @ApiResponses(
        value = {
            @ApiResponse(code = 204, message = "There is no Meme to send for this request."),
            @ApiResponse(code = 401, message = "client need to authenticate itself to get the requested Memes."),
            @ApiResponse(code = 403, message = "The client does not have access rights to the Memes."),
            @ApiResponse(code = 404, message = "The server can not find the requested Memes."),
            @ApiResponse(code = 500, message = "The server has encountered a situation it doesn't know how to handle.")
        }
    )
    @GetMapping("/memes")
    public ResponseEntity<?>getLatestMemes() {
        try {
            List<Meme> memeList = memeService.getMemes();
            return new ResponseEntity<>(memeList, memeList.size() > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT);
            
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  
        }
       
    }
    
    @ApiOperation(value = "Return a particular Meme corresponds to provided Id")
    @ApiResponses(
        value = {
            @ApiResponse(code = 401, message = "client need to authenticate itself to get the requested Meme."),
            @ApiResponse(code = 403, message = "The client does not have access rights to the Meme."),
            @ApiResponse(code = 404, message = "The server can not find the requested Meme."),
            @ApiResponse(code = 500, message = "The server has encountered a situation it doesn't know how to handle.")
        }
    )
    @GetMapping("/memes/{memeId}")
    public ResponseEntity<?>getSingleMeme(@PathVariable String memeId) {
        try {
            Meme requiredMeme = memeService.getMeme(memeId);
            return new ResponseEntity<Meme>(requiredMeme, HttpStatus.OK);

        } catch (MemeCollectionException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

        }
        catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  
        }
        
    }

    @ApiOperation(value = "Let you create new Meme")
    @ApiResponses(
        value = {
            @ApiResponse(code = 201, message = "The request has succeeded and a new Meme has been created as a result."),
            @ApiResponse(code = 401, message = "client need to authenticate itself to create a New Meme."),
            @ApiResponse(code = 403, message = "The client does not have access rights to Create new Meme."),
            @ApiResponse(code = 404, message = "The server can not find the requested Meme."),
            @ApiResponse(code = 500, message = "The server has encountered a situation it doesn't know how to handle.")
        }
    )
    @PostMapping("/memes")
    public ResponseEntity<?> createMeme(@RequestBody Meme meme) {
        try {
            ResponseEntity<?>response = this.memeService.createMeme(meme);
            return response;
            
        } catch(ConstraintViolationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);

        } catch(MemeCollectionException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);

        } catch (InvalidUrlException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @ApiOperation(value = "Let You Update the Existing Meme corresponds to provided Id")
    @ApiResponses(
        value = {
            @ApiResponse(code = 401, message = "client are not allowed to update meme creator name."),
            @ApiResponse(code = 403, message = "The client does not have access rights to Create new Meme."),
            @ApiResponse(code = 404, message = "The server can not find the requested Meme to update."),
            @ApiResponse(code = 500, message = "The server has encountered a situation it doesn't know how to handle.")
        }
    )
    @PatchMapping("/memes/{memeId}")
    public ResponseEntity<?> patchMeme(@RequestBody HashMap<String, String>newValues, @PathVariable String memeId) {
        try {
            memeService.updateMeme(newValues, memeId);
            return new ResponseEntity<String>(String.valueOf(HttpStatus.OK.value()), HttpStatus.OK);

        } catch(MemeWrongUpdateException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);

        } catch(MemeCollectionException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        
        } catch (InvalidUrlException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        
        }

    }

    @ApiOperation(value = " mapping to redirect request for swagger ui ")
    @ApiResponses(
        value = {
            @ApiResponse(code = 401, message = "client are not allowed to update meme creator name."),
            @ApiResponse(code = 403, message = "The client does not have access rights to Create new Meme."),
            @ApiResponse(code = 404, message = "The server can not find the requested Meme to update."),
        }
    )
    @GetMapping("/swagger-ui")
    public RedirectView swaggerMapping() {
       
        return new RedirectView("/swagger-ui.html");
    }

    
}
