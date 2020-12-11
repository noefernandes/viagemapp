import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

export default function PerfilCliente(){
//Inicia-se com um vetor vazio como total de quartos do usuario.
    const [quartosComNome, setViagensComNome] = useState([])
 //Pega os dados anteriormente armazenados no localStorage.
    const idUsuario = localStorage.getItem('idUsuario');
    const nomeUsuario = localStorage.getItem('nomeUsuario');

    const history = useHistory();

useEffect(() => {
        //Pegando quartos do cliente
        api.get(`/quartosCliente/${idUsuario}`).then(response => {
            setViagensComNome(response.data);
            console.log(response.data)
        })
    }, [idUsuario]);

    async function handleDeleteQuartoDoCliente(id){
        try{
            await api.delete(`/${idUsuario}/deletarQuartoDoCliente/${id}`);
            /*Filtra a lista de incidents mantendo apenas aqueles
            com id diferente do com id deletado*/
            setViagensComNome(quartosComNome.filter(quartoComNome => quartoComNome.quarto.id !== id));
        }catch(Err){
            alert('Erro ao deletar quarto.');
        }
    }
    function handleLogout(){
       //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginCliente');
    }
    
    function handleGuardarHotel(id){
        localStorage.setItem('idHotel', id);
    }
    
    return(
       <div className="container-perfil-cliente">
                  <header>
                    <img src={logoImg} alt="Logo QuartoApp"/>

                    <div style={{display:"flex", alignItems: "center", justifyContent: "center", flexDirection: "row"}}>
                    <Link className='button-minhas-quartos' to='feedCliente'>Feed</Link>
                        <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                            <FiPower size={50} />
                        </button>
                    </div>
                  </header>
                  <div className='container-quartos-cliente'>
                      <h1>Minhas quartos</h1>
                      <div className="lista-quartos">
                          <ul>
                              {quartosComNome.map(quartoComNome => (
                              <li>
                                  <strong>Hotel</strong>
                                  <p>{quartoComNome.nomeHotel}</p>
                                  <strong>Nota</strong>
                                  <p>{quartoComNome.nota}</p>
                                  <strong>Ínicio da Reserva</strong>
                                  <p>{quartoComNome.quarto.inicioReserva}</p>
                                  <strong>Fim da Reserva</strong>
                                  <p>{quartoComNome.quarto.fimReserva}</p>
                                  <strong>Preço</strong>
                                  <p>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(quartoComNome.quarto.preco)}</p>
                                  <strong>Numero</strong>
                                  <p>{quartoComNome.quarto.numero}

                                  <div>
                                    <button
                                        onClick={() => handleDeleteQuartoDoCliente(quartoComNome.quarto.id)}
                                        type='button'
                                        className='trash'
                                    >
                                        <FiTrash2 />
                                    </button>

                                      <Link className='button-avaliar-hotel' to='avaliarHotel'
                                      onClick={() => handleGuardarHotel(quartoComNome.quarto.idHotel)}>
                                          Avaliar Hotel
                                      </Link>

                                  </div>
                                    </p>
                              </li>
                              ))}
                          </ul>
                      </div>
                  </div>
              </div>

    );
}