import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

export default function PerfilAgencia(){
    //Inicia-se com um vetor vazio como total de viagens do usuario.
    const [viagens, setViagens] = useState([])
    
    //Pega os dados anteriormente armazenados no localStorage.
    const idUsuario = localStorage.getItem('idUsuario');
    const nomeUsuario = localStorage.getItem('nomeUsuario');

    const history = useHistory();

    useEffect(() => {
        api.get(`/agencias/${idUsuario}`).then(response => {
            setViagens(response.data.viagens);
            console.log(response.data.viagens);
        })
    }, [idUsuario]);

    async function handleDeleteViagem(idv){
        try{
            console.log("Valor do id:" + idv);
            await api.delete(`/viagens/${idv}`);
            /*Filtra a lista de incidents mantendo apenas aqueles
            com id diferente do com id deletado*/
            setViagens(viagens.filter(viagem => viagem.idv !== idv));
        }catch(Err){
            alert('Erro ao deletar viagem.');
        }
    }

    //Função responsáel por limpar o localStorage ao deslogar.
    function handleLogout(){
        //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginAgencia');
    }

    return (
        <div className="container-perfil-agencia">
            <header>
                <img src={logoImg} alt="Logo ViagemApp"/>
                <span>Bem-vindo(a), {nomeUsuario}!</span>
                <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                    <FiPower size={50} />
                </button>
            </header>
            <div className='container-viagens-agencia'>
                <Link className='button-cadastro-viagem' to='cadastroViagem'>Cadastrar viagem</Link>
                <h1>Minhas viagens</h1>
                <div className="lista-viagens">
                    <ul>
                        {viagens.map(viagem => (
                        <li>
                            <strong>Local de partida</strong>
                            <p>{viagem.localPartida}</p>
                            <strong>Local de chegada</strong>
                            <p>{viagem.localChegada}</p>
                            <strong>Data</strong>
                            <p>{viagem.data}</p>
                            <strong>Horário de partida</strong>
                            <p>{viagem.horarioPartida}</p>
                            <strong>Horário de chegada</strong>
                            <p>{viagem.horarioChegada}</p>
                            <strong>Preço</strong>
                            <p>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(viagem.preco)}</p>
                            <strong>Capacidade</strong>
                            <p>{viagem.qtdPassageiros}/{viagem.capacidade}</p>
                            <strong>Tags: </strong>
                            <ul className="listaTags">
                                {viagem.tags.map(tag => (
                                    <li>
                                        <p>{tag}</p>
                                    </li>
                                ))}
                            </ul>

                            <button 
                                onClick={() => handleDeleteViagem(viagem.idv)}
                                type='button' 
                                className='trash' 
                            >
                                <FiTrash2 />
                            </button>
                        </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
} 