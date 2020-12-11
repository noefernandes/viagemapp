import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

export default function PerfilHotel(){
    //Inicia-se com um vetor vazio como total de quartos do usuario.
    const [quartos, setViagens] = useState([])
    
    //Pega os dados anteriormente armazenados no localStorage.
    const idUsuario = localStorage.getItem('idUsuario');
    const nomeUsuario = localStorage.getItem('nomeUsuario');

    const history = useHistory();

    useEffect(() => {
        api.get(`/hotel/${idUsuario}`).then(response => {
            setViagens(response.data.quartos);
            console.log(response.data.quartos);
        })
    }, [idUsuario]);

    async function handleDeleteQuarto(idv){
        try{
            console.log("Valor do id:" + idv);
            await api.delete(`/quartos/${idv}`);
            /*Filtra a lista de incidents mantendo apenas aqueles
            com id diferente do com id deletado*/
            setViagens(quartos.filter(quarto => quarto.idv !== idv));
        }catch(Err){
            alert('Erro ao deletar quarto.');
        }
    }

    //Função responsáel por limpar o localStorage ao deslogar.
    function handleLogout(){
        //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginHotel');
    }

    function handleGuardarHotel(id, nomeHotel){
        localStorage.setItem('idHotel', id);
        localStorage.setItem('nomeHotel', nomeHotel);
        console.log('Primeiro:' + nomeHotel);
    }

    return (
        <div className="container-perfil-hotel">
            <header>
                <img src={logoImg} alt="Logo QuartoApp"/>
                <span>Bem-vindo(a), {nomeUsuario}!</span>
                <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                    <FiPower size={50} />
                </button>
            </header>
            <div className='container-quartos-hotel'>
                <Link className='button-cadastro-quarto' to='cadastroQuarto'>Cadastrar quarto</Link>
                <Link className='button-avaliar-hotel' to='showAvaliacoesHotel'
                                      onClick={() => handleGuardarHotel(idUsuario, 
                                                                    nomeUsuario)}>Minhas avaliações</Link>
                        
                <h1>Minhas quartos</h1>
                <div className="lista-quartos">
                    <ul>
                        {quartos.map(quarto => (
                        <li>
                            <strong>Numero</strong>
                            <p>{quarto.numero}</p>
                            <strong>Andar</strong>
                            <p>{quarto.andar}</p>
                            <strong>Início da Reserva</strong>
                            <p>{quarto.inicioReserva}</p>
                            <strong>Fim da Reserva</strong>
                            <p>{quarto.fimReserva}</p>
                            <strong>Preço</strong>
                            <p>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(quarto.preco)}</p>
                            <strong>Tags: </strong>
                            <ul className="listaTags">
                                {quarto.tags.map(tag => (
                                    <li>
                                        <p>{tag}</p>
                                    </li>
                                ))}
                            </ul>

                            <button 
                                onClick={() => handleDeleteQuarto(quarto.idv)}
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