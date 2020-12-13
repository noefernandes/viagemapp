package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.CapacityException;
import com.spring.viagemapp.error.NotFoundClienteException;
import com.spring.viagemapp.error.NotFoundMesasException;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.ClienteMesa;
import com.spring.viagemapp.model.Mesa;
import com.spring.viagemapp.repository.ClienteMesaRepository;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.repository.MesaRepository;
import com.spring.viagemapp.service.ClienteMesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClienteMesaServiceImpl implements ClienteMesaService {
    @Autowired
    ClienteMesaRepository clienteMesaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    MesaRepository mesaRepository;
    
    String getHoraAtual() {
    	Calendar now = Calendar.getInstance();
        int horaIni = now.get(Calendar.HOUR_OF_DAY);
        int minIni = now.get(Calendar.MINUTE);
        
        String horaIniStr = "";
        String minIniStr = "";
        if(horaIni < 10) {
        	horaIniStr = "0" + Integer.toString(horaIni);
        }else {
        	horaIniStr = Integer.toString(horaIni);
        }
        
        if(minIni < 10) {
        	minIniStr = "0" + Integer.toString(minIni);
        }else {
        	minIniStr = Integer.toString(minIni);
        }
        
        return horaIniStr + ":" + minIniStr;
    }
    
    @Override
    @Transactional(readOnly = false)
    public ClienteMesa comprarMesa(@PathVariable long idCliente, @PathVariable long idMesa){
        Mesa mesa = mesaRepository.findById(idMesa).get();

        if(mesa.isOcupada()) {
            throw new CapacityException("A mesa já está ocupada!");
        }
        
        mesa.setInicioReserva(getHoraAtual());
        mesa.setOcupada(true);
        mesa.setEstado("Ocupada");
        
        mesa.setTotalCompras(mesa.getTotalCompras() + 1);
        
        ClienteMesa clienteMesa = new ClienteMesa();
        clienteMesa.setIdCliente(idCliente);
        clienteMesa.setIdMesa(idMesa);

        return clienteMesaRepository.save(clienteMesa);
    }
    

    @Override
    public List<ClienteMesa> findAll() {
        return clienteMesaRepository.findAll();
    }

    @Override
    public ClienteMesa findById(long id) {
        return clienteMesaRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = false)
    public ClienteMesa save(ClienteMesa clienteMesa) {
        return clienteMesaRepository.save(clienteMesa);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(long id) {
        clienteMesaRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = false)
    public void deleteClienteMesa(long idCliente, long idMesa){
        Optional<Cliente> clienteOp = clienteRepository.findById(idCliente);
        Optional<Mesa> mesaOp = mesaRepository.findById(idMesa);

        if(!clienteOp.isPresent()){
            throw new NotFoundClienteException("Cliente não encontrado. Nenhuma mesa foi deletada.");
        }

        if(!mesaOp.isPresent()){
            throw new NotFoundMesasException("Mesa não encontrada. Nenhuma mesa foi deletada");
        }

        
        mesaOp.get().setOcupada(false);
        mesaOp.get().setEstado("Disponível");
        
        clienteMesaRepository.deleteClienteMesa(idCliente, idMesa);
    }
}
