package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Hotel;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.model.Quarto;
import com.spring.viagemapp.repository.HotelRepository;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.repository.QuartoRepository;
import com.spring.viagemapp.service.HotelService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.QuartoService;
import com.spring.viagemapp.utils.ClienteTags;

import com.spring.viagemapp.utils.QuartoComNome;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.spring.viagemapp.security.MD5.getMd5;

@Service
@Transactional(readOnly = true)
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    QuartoService quartoService;

    @Autowired
    HotelService hotelService;
    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        if(clientes.isEmpty()) {
            throw new NotFoundClienteException("Clientes não encontrados");
        }
        return clientes;
    }

    @Override
    public Optional<Cliente> findById(long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(!cliente.isPresent()){
            throw  new NotFoundClienteException("Cliente não encontrado");
        }  
        
        return cliente;
    }

    @Override
    @Transactional(readOnly = false)
    public Cliente save(ClienteTags clienteTags) {
        if(clienteRepository.existsByNome(clienteTags.cliente.getNome())){
            throw new RepeatedNameException("O nome já existe");
        }else if(clienteRepository.existsByCpf(clienteTags.cliente.getCpf())) {
            throw new RepeatedCpfException("O CNPJ já existe");
        }else if(clienteRepository.existsByEmail(clienteTags.cliente.getEmail())){
            throw new RepeatedEmailException("O E-mail já existe");
        }else if(clienteRepository.existsByNomeUsuario(clienteTags.cliente.getNomeUsuario())){
            throw new RepeteadUsernameException("O nome de usuário já existe");
        }

        if(clienteTags.tagString != "") {
            List<String> tags = Arrays.asList(clienteTags.tagString.split(";"));
            for(int i = 0; i < tags.size(); i++) {
                //System.out.println("Tag Cliente (Antes):" + tags.get(i));
                tags.set(i, StringUtils.stripAccents(tags.get(i)).trim().toLowerCase());
                //System.out.println("Tag Cliente (Depois):" + tags.get(i));
            }
            clienteTags.cliente.setTags(tags);
        }
        
        clienteTags.cliente.setSenha(getMd5(clienteTags.cliente.getSenha()));
        return clienteRepository.save(clienteTags.cliente);
    }

    @Override
    @Transactional(readOnly = false)
    public Cliente resave(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    //@Override
    //public List<Quarto> getViagensDoCliente(long idCliente){
       // return clienteRepository.getViagensDoCliente(idCliente);
    //}

    public List<QuartoComNome> convert(List<Object[]> quartosObj){
        List<Quarto> quartos = new ArrayList<Quarto>();

        for(Object[] obj : quartosObj) {
            Quarto quarto = new Quarto();
            
            //System.out.print("OBJETOOOOOOOOO:: " + obj[1].toString());
            
            String bigString = (obj[1].toString());
            BigInteger bi = new BigInteger(bigString);
            Long idQuarto = bi.longValue();
            quarto.setId(idQuarto);

            List<String> tags = quartoService.getTagsQuarto(quarto.getId());
            quarto.setTags(tags);

            quarto.setNumero((Integer) obj[2]);
            quarto.setInicioReserva((String) obj[4]);
            quarto.setFimReserva((String) obj[5]);
            quarto.setAndar((Integer) obj[6]);
            quarto.setIdHotel((Long.parseLong(obj[7].toString())));
            quarto.setPreco((double) obj[3]);

            quartos.add(quarto);
        }

        ArrayList<QuartoComNome> quartosComNome = new ArrayList<QuartoComNome>();
        for(Quarto quarto: quartos){
            QuartoComNome quartoComNome = new QuartoComNome();
            quartoComNome.quarto = quarto;
            Hotel hotel = (Hotel) hotelService.findById(quarto.getIdHotel()).get();
            quartoComNome.nomeHotel = hotel.getNome();
            quartoComNome.nota = hotel.getNota();
            quartosComNome.add(quartoComNome);
        }

        return quartosComNome;
    }

    @Override
    public int quantidadeDeClientes(long idQuarto) {
        return clienteRepository.quantidadeDeClientes(idQuarto);
    }

    @Override
    public List<Object[]> getQuartosDoCliente(long idCliente){
    	return clienteRepository.getQuartosDoCliente(idCliente);
    }

    @Override
    public List<QuartoComNome> getQuartosDoClienteComNome(long idCliente){
        List<Object[]> viagensObj = clienteRepository.getQuartosDoCliente(idCliente);
        List<QuartoComNome> quartosComNome = convert(viagensObj);
        return quartosComNome;
    }

    @Override
    public List<String> getTagsCliente(long idCliente){
        return clienteRepository.getTagsCliente(idCliente);
    }

    @Override
    public Cliente checkLogin(Usuario usuario) {
        Optional<Cliente> clienteOp = clienteRepository.findByNomeUsuario(usuario.getNomeUsuario());

        if (!clienteOp.isPresent()) {
            throw new NotFoundLoginException("O usuário não existe");
        } else if (!clienteOp.get().getSenha().equals(getMd5(usuario.getSenha()))) {
            throw new WrongPasswordException("Senha incorreta");
        }
        return clienteOp.get();
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return clienteRepository.existsByCpf(cpf);
    }

	@Override
    //@Transactional(readOnly = false)
    public void deleteById(long id) { clienteRepository.deleteById(id); }

    @Override
    public boolean existsByEmail(String email){return clienteRepository.existsByEmail(email);}

    @Override
    public boolean existsByNome(String nome){ return  clienteRepository.existsByNome(nome);}

    @Override
    public boolean existsByNomeUsuario(String nomeUsuario){return clienteRepository.existsByNomeUsuario(nomeUsuario);}
    
    @Override
	public Optional<Cliente> findByNomeUsuario(String nomeUsuario) { return clienteRepository.findByNomeUsuario(nomeUsuario); }

    public boolean addNewTags(long id, ClienteTags clienteTags){
        if(findById(id).isPresent()) {
            List<String> tags = Arrays.asList(clienteTags.tagString.split(";"));
            for(String str: tags){
                str = StringUtils.stripAccents(str);
                str = str.trim();
            }
            clienteTags.cliente.addTags(tags);
            return true;
        }
        return false;

    }
    
}
