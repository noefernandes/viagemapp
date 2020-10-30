package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.repository.AgenciaRepository;
import com.spring.viagemapp.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.spring.viagemapp.security.MD5.getMd5;

@Service
@Transactional(readOnly = true)
public class AgenciaServiceImpl implements AgenciaService {

    @Autowired
    AgenciaRepository agenciaRepository;

    @Override
    public List<Agencia> findAll() {
        List<Agencia> agencias = agenciaRepository.findAll();
        if(agencias.isEmpty()){
            throw new NotFoundAgenciaException("Agências não encontradas");
        }
        return agencias;
    }

    @Override
    public Optional<Agencia> findById(long id) {
        Optional<Agencia> agencia = agenciaRepository.findById(id);
        if(!agencia.isPresent()){
            throw new NotFoundAgenciaException("Agência não encontrada");
        }

        return agencia;
    }


    @Override
    @Transactional(readOnly = false)
    public Agencia save(Agencia agencia) {
        if(agenciaRepository.existsByNome(agencia.getNome())){
            throw new RepeatedNameException("O nome já existe");
        }else if(agenciaRepository.existsByCnpj(agencia.getCnpj())) {
            throw new RepeatedCpfException("O CPF já existe");
        }else if(agenciaRepository.existsByEmail(agencia.getEmail())){
            throw new RepeatedEmailException("O E-mail já existe");
        }else if(agenciaRepository.existsByNomeUsuario(agencia.getNomeUsuario())){
            throw new RepeteadUsernameException("O nome de usuário já existe");
        }

        agencia.setSenha(getMd5(agencia.getSenha()));
        return agenciaRepository.save(agencia);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return agenciaRepository.existsByCnpj(cnpj);
    }

    @Override
    public boolean existsByEmail(String email) {
        return agenciaRepository.existsByEmail(email);
    }
    @Override
    public void updateNota(Agencia agencia, List<AvaliacaoPerUser> avaliacao){
        Double media1 = 0.0;
        Double media2 = 0.0;
        Double media3 = 0.0;
        Double media4 = 0.0;
        Double media5 = 0.0;
        

        for(AvaliacaoPerUser nota : avaliacao)
        {
            media1 += nota.getAvaliacaoAtendimento();
            media2 += nota.getAvaliacaoLimpeza();
            media3 += nota.getAvaliacaoRapidez();
            media4 += nota.getAvaliacaoConforto();
            media5 += nota.getAvaliacaoPreco();
        }

        media1 /= avaliacao.size();
        media2 /= avaliacao.size();
        media3 /= avaliacao.size();
        media4 /= avaliacao.size();
        media5 /= avaliacao.size();

        Double mediaGeral = (media1+media2+media3+media4+media5) / 5;
        agencia.setNota(mediaGeral);
        agenciaRepository.save(agencia);
    }

    @Override
    public List<Double> showNotas(Agencia agencia){
        List<AvaliacaoPerUser> avaliacao = findByAgencia(agencia);

        if(avaliacao.isEmpty()){
            throw new NotFoundAgenciaException("Agencia não encontrada");
        }
        Double media1 = 0.0;
        Double media2 = 0.0;
        Double media3 = 0.0;
        Double media4 = 0.0;
        Double media5 = 0.0;
        List<Double> notas = new ArrayList<>();

        for(AvaliacaoPerUser nota : avaliacao)
        {
            media1 += nota.getAvaliacaoAtendimento();
            media2 += nota.getAvaliacaoLimpeza();
            media3 += nota.getAvaliacaoRapidez();
            media4 += nota.getAvaliacaoConforto();
            media5 += nota.getAvaliacaoPreco();
        }

        if(avaliacao.isEmpty()){
            throw new NotFoundException("Não há avaliações ainda!");
        }

        media1 /= avaliacao.size();
        media2 /= avaliacao.size();
        media3 /= avaliacao.size();
        media4 /= avaliacao.size();
        media5 /= avaliacao.size();

        notas.add(media1);
        notas.add(media2);
        notas.add(media3);
        notas.add(media4);
        notas.add(media5);
        return notas;
    }

    @Override
    public boolean existsByNomeUsuario(String nomeUsuario) {
        return agenciaRepository.existsByNomeUsuario(nomeUsuario);
    }

    @Override
    public boolean existsByNome(String nome) {
        return agenciaRepository.existsByNome(nome);
    }

    @Override
    public Optional<Agencia> findByNomeUsuario(String nomeUsuario) {
        return agenciaRepository.findByNomeUsuario(nomeUsuario);
    }

    @Override
    public Agencia checkLogin(Usuario usuario) {
        Optional<Agencia> agenciaOp = agenciaRepository.findByNomeUsuario(usuario.getNomeUsuario());

        if (!agenciaOp.isPresent()) {
            throw  new NotFoundLoginException("O usuário não existe");
        } else if (!agenciaOp.get().getSenha().equals(getMd5(usuario.getSenha()))) {
            throw new WrongPasswordException("Senha incorreta");
        }
        return agenciaOp.get();
    }

    @Override
    @Transactional(readOnly = false)
	public void deleteById(long id) {
		 agenciaRepository.deleteById(id);
	}
}
