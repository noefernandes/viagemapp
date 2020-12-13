package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Hotel;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.utils.ComentarioComNome;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface HotelService extends PrestadorDeServicoService<Hotel, AvaliacaoPerUser>{
    //List<Hotel> findAll();
    //Optional<Hotel> findById(long id);
    //Hotel save(Hotel Hotel);
    //void deleteById(long id);
    //boolean existsByCnpj(String cnpj);
    //boolean existsByEmail(String email);
    //boolean existsByNomeUsuario(String nomeUsuario);
    //boolean existsByNome(String nome);
    //Optional<Hotel> findByNomeUsuario(String nomeUsuario);
    //Hotel checkLogin(Usuario usuario);
    void updateNota(Hotel hotel,  List<AvaliacaoPerUser> avaliacao);
    List<Double> showNotas(Hotel hotel);
    List<ComentarioComNome> showComentarios(Hotel hotel);
    public void salvarAvaliacao(AvaliacaoPerUser avaliacao);
   // public Hotel avaliarPrestador(@PathVariable long idCliente,
                                //  @PathVariable long idHotel, @RequestBody @Valid AvaliacaoPerUser avaliacao);
}
