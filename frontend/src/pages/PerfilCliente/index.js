import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

export default function PerfilCliente(){
//Inicia-se com um vetor vazio como total de viagens do usuario.
    const [viagensComNome, setViagensComNome] = useState([])
 //Pega os dados anteriormente armazenados no localStorage.
    const idUsuario = localStorage.getItem('idUsuario');
    const nomeUsuario = localStorage.getItem('nomeUsuario');


    const history = useHistory();

    function emptyFunc() {
        
    }

    useEffect(() => {
        //Pegando viagens do cliente
        api.get(`/viagensCliente/${idUsuario}`).then(response => {
            setViagensComNome(response.data);
            console.log(response.data)
        })
    }, [idUsuario]);

    async function handleDeleteViagemDoCliente(id){
        try{
            await api.delete(`/${idUsuario}/deletarViagemDoCliente/${id}`);
            /*Filtra a lista de incidents mantendo apenas aqueles
            com id diferente do com id deletado*/
            setViagensComNome(viagensComNome.filter(viagemComNome => viagemComNome.mesa.id !== id));
        }catch(Err){
            alert('Erro ao deletar mesa.');
        }
    }
    function handleLogout(){
       //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginCliente');
    }
    
    function handleGuardarAgencia(id){
        localStorage.setItem('idAgencia', id);
    }
    
    return(
       <div className="container-perfil-cliente">
            <header>
            <img src={logoImg} alt="Logo ViagemApp"/>

            <div style={{display:"flex", alignItems: "center", justifyContent: "center", flexDirection: "row"}}>
            <Link className='button-minhas-viagens' to='feedCliente'>Feed</Link>
                <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                    <FiPower size={50} />
                </button>
            </div>
            </header>
            <div className='container-viagens-cliente'>
                <h1>Minhas viagens</h1>
                <div className="lista-viagens">
                    <ul>
                        {viagensComNome.map(viagemComNome => (
                        <li>
                            <strong>Restaurante</strong>
                            <p>{viagemComNome.nomeRestaurante}</p>
                            <strong>Situação</strong>
                            <p>{viagemComNome.mesa.estado}</p>
                            <strong>Nota</strong>
                            <p>{viagemComNome.nota}</p>
                            <strong>Número da mesa</strong>
                            <p>{viagemComNome.mesa.numero}</p>
                            <strong>Horário de início da última reserva</strong>
                            <p>{viagemComNome.mesa.inicioReserva}</p>

                            <div>
                            <button
                                onClick={() => handleDeleteViagemDoCliente(viagemComNome.mesa.id)}
                                type='button'
                                className='trash'
                            >
                                <FiTrash2 />
                            </button>

                                <Link className='button-avaliar-agencia' to='avaliarAgencia'
                                onClick={() => handleGuardarAgencia(viagemComNome.mesa.idRestaurante)}>
                                    Avaliar Agência
                                </Link>

                            </div>
                        </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>

    );
}