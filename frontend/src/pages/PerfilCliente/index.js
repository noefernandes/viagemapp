import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

export default function PerfilCliente(){
//Inicia-se com um vetor vazio como total de viagens do usuario.
    const [viagens, setViagens] = useState([])
 //Pega os dados anteriormente armazenados no localStorage.
    const idUsuario = localStorage.getItem('idUsuario');
    const nomeUsuario = localStorage.getItem('nomeUsuario');

    const history = useHistory();

useEffect(() => {
        //Pegando viagens do cliente
        api.get(`/viagensCliente/${idUsuario}`).then(response => {
            setViagens(response.data);
            console.log(response.data)
        })
    }, [idUsuario]);

    async function handleDeleteViagem(id){
        try{
            await api.delete(`/viagens/${id}`);
            /*Filtra a lista de incidents mantendo apenas aqueles
            com id diferente do com id deletado*/
            setViagens(viagens.filter(viagem => viagem.id !== id));
        }catch(Err){
            alert('Erro ao deletar viagem.');
        }
    }
    function handleLogout(){
       //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginCliente');
    }

    return(
       <div className="container-perfil-cliente">
                  <header>
                    <img src={logoImg} alt="Logo ViagemApp"/>

                    <div style={{display:"flex", alignItems: "center", justifyContent: "center", flexDirection: "row"}}>
                    <Link className='button-minhas-viagens' to='perfilCliente'>Feed</Link>
                        <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                            <FiPower size={50} />
                        </button>
                    </div>
                  </header>
                  <div className='container-viagens-cliente'>
                      <h1>Minhas viagens</h1>
                      <div className="lista-viagens">
                          <ul>
                              {viagens.map(viagem => (
                              <li>
                                  <strong>Local de partida</strong>
                                  <p>{viagem.localPartida}</p>
                                  <strong>Local de chegada</strong>
                                  <p>{viagem.localChegada}</p>
                                  <strong>Horário de partida</strong>
                                  <p>{viagem.horarioPartida}</p>
                                  <strong>Horário de chegada</strong>
                                  <p>{viagem.horarioChegada}</p>
                                  <strong>Preço</strong>
                                  <p>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(viagem.preco)}</p>
                                  <strong>Capacidade</strong>
                                  <p>{viagem.capacidade}</p>

                                  <div>
                                    <button
                                        onClick={() => handleDeleteViagem(viagem.id)}
                                        type='button'
                                        className='trash'
                                    >
                                        <FiTrash2 />
                                    </button>

                                    <button
                                        //onClick={() => handleDeleteViagem(viagem.id)}
                                        type='button'
                                        
                                    >
                                        
                                    </button>
                                    </div>
                              </li>
                              ))}
                          </ul>
                      </div>
                  </div>
              </div>

    );
}