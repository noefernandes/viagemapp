import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

export default function PerfilAgencia(){
    //Inicia-se com um vetor vazio como total de viagens do usuario.
    const [mesas, setMesas] = useState([])
    
    //Pega os dados anteriormente armazenados no localStorage.
    const idUsuario = localStorage.getItem('idUsuario');
    const nomeUsuario = localStorage.getItem('nomeUsuario');

    const history = useHistory();

    useEffect(() => {
        api.get(`/agencias/${idUsuario}`).then(response => {
            console.log(response.data)
            setMesas(response.data.mesas);
            console.log(response.data.mesas);
        })
    }, [idUsuario]);

    async function handleDeleteMesa(id){
        try{
            console.log("Valor do id:" + id);
            await api.delete(`/viagens/${id}`);
            /*Filtra a lista de incidents mantendo apenas aqueles
            com id diferente do com id deletado*/
            setMesas(mesas.filter(mesa => mesa.id !== id));
        }catch(Err){
            alert('Erro ao deletar Mesa.');
        }
    }

    //Função responsáel por limpar o localStorage ao deslogar.
    function handleLogout(){
        //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginAgencia');
    }

    function handleGuardarRestaurante(id, nomeRestaurante){
        localStorage.setItem('idRestaurante', id);
        localStorage.setItem('nomeRestaurante', nomeRestaurante);
        console.log('Primeiro:' + nomeRestaurante);
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
                <Link className='button-cadastro-viagem' to='cadastroViagem'>Cadastrar mesa</Link>
                <Link className='button-avaliar-agencia' to='showAvaliacoesAgencia'
                                      onClick={() => handleGuardarRestaurante(idUsuario, 
                                                                    nomeUsuario)}>Minhas avaliações</Link>
                        
                <h1>Minhas mesas</h1>
                <div className="lista-viagens">
                    <ul>
                        {mesas.map(mesa => (
                        <li>
                            <strong>Número da mesa</strong>
                            <p>{mesa.numero}</p>
                            <strong>Situação</strong>
                            <p>{mesa.estado}</p>
                            <strong>Nota</strong>

                            <strong>Tags: </strong>
                            <ul className="listaTags">
                                {mesa.tags.map(tag => (
                                    <li>
                                        <p>{tag}</p>
                                    </li>
                                ))}
                            </ul>

                            <button 
                                onClick={() => handleDeleteMesa(mesa.id)}
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