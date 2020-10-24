package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Avaliacoes;
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
    public Agencia addAvaliacao(Agencia agencia, Cliente cliente, Avaliacoes avaliacoes){
        agencia.addAvaliacaoAtendimento(avaliacoes.getAvaliacaoAtendimento());
        agencia.addAvaliacaoConforto(avaliacoes.getAvaliacaoConforto());
        agencia.addAvaliacaoLimpeza(avaliacoes.getAvaliacaoLimpeza());
        agencia.addAvaliacaoPreco(avaliacoes.getAvaliacaoPreco());
        agencia.addAvaliacaoRapidez(avaliacoes.getAvaliacaoRapidez());
        agencia.addComentarios(avaliacoes.getComentarios());
        agencia.addAvaliador(cliente.getNome());

        return agenciaRepository.save(agencia);
    }
    @Override
    public List<Double> showNotas(Agencia agencia){
        List<Double> avaliacoes = new ArrayList<Double>();
        avaliacoes.add(agencia.getAvaliacaoAtendimento());
        avaliacoes.add(agencia.getAvaliacaoLimpeza());
        avaliacoes.add(agencia.getAvaliacaoRapidez());
        avaliacoes.add(agencia.getAvaliacaoConforto());
        avaliacoes.add(agencia.getAvaliacaoPreco());
        avaliacoes.add(agencia.getAvaliacaoGeral());
        return avaliacoes;
    }

    @Override
    public HashMap<String,String> showCometarios(Agencia agencia){
        HashMap<String,String> comentariosAvaliador = new HashMap<String,String>();
        List<String> comentarios = new ArrayList<String>();
        List<String> avaliadores = new ArrayList<String>();

        comentarios = agencia.getComentarios();
        avaliadores = agencia.getAvaliadores();
        for(int i = 0; i < comentarios.size();i++){
            comentariosAvaliador.put(avaliadores.get(i),comentarios.get(i));
        }
        
        return comentariosAvaliador;
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
