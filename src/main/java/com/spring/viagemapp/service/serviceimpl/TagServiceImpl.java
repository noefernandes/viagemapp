package com.spring.viagemapp.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.viagemapp.model.Tag;
import com.spring.viagemapp.repository.TagRepository;
import com.spring.viagemapp.service.TagService;
import com.spring.viagemapp.error.RepeatedTagException;

@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService
{
	 	@Autowired
	    TagRepository tagRepository;

	    @Override
	    public List<Tag> findAll() {
	        return tagRepository.findAll();
	    }

	    @Override
	    public Optional<Tag> findById(long id) {
	        return tagRepository.findById(id);
	    }
	    
	    @Override
	    @Transactional(readOnly = false)
	    public Tag save(Tag tag) {
	        if(tagRepository.existsByOpcao(tag.getOpcao()))
	        {
	            throw new RepeatedTagException("A tag já existe");
	        }

	        return tagRepository.save(tag);
	    }

	    @Override
	    @Transactional(readOnly = false)
		public void deleteById(long id) {
			 tagRepository.deleteById(id);
		}

		@Override
		public boolean existsByOpcao(String opcao)
		{
			 return tagRepository.existsByOpcao(opcao);
		}
}
