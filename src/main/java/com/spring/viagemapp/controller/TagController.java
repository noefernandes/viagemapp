package com.spring.viagemapp.controller;

import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Tag;
import com.spring.viagemapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/")
public class TagController 
{
	@Autowired
    TagService tagService;
	
	 @PostMapping(value = "/cadastrarTag")
	 public ResponseEntity<?> cadastrarTag(@RequestBody @Valid Tag tag)
	 {
		 if(tagService.existsByOpcao(tag.getOpcao()))
	     {
			 return new ResponseEntity<>("A tag já existe", HttpStatus.FORBIDDEN);
	     }

	     return new ResponseEntity<Tag>(tagService.save(tag), HttpStatus.CREATED);
	  }
	 
	 @RequestMapping(value = "/tags", method = RequestMethod.GET)
	 public ResponseEntity<?> getTags()
	 {
		 List<Tag> tags =  tagService.findAll();
	     if(tags.isEmpty())
	     {
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     }

	     return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
	 }
	 
	 @RequestMapping(value="/tag/{id}", method=RequestMethod.GET)
	 public ResponseEntity<?> getPostTagDetails(@PathVariable("id") long id)
	 {
		 Optional<Tag> tag = tagService.findById(id);
	     if(!tag.isPresent())
	     {
	    	 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     }

	     return new ResponseEntity<Tag>(tag.get(),HttpStatus.OK);
	 }
	 
	 @RequestMapping("clientes/delete/{id}")
	 public String deleteTagById(@PathVariable long id, RedirectAttributes redirectAttrs)
	 {
		 tagService.deleteById(id);
		 redirectAttrs.addFlashAttribute("message","Tag excluída!");
	     return "redirect:/tags";
	 }
	 
	 
	 
}
