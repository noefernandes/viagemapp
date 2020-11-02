import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';
export default function ShowAvaliacoes(){
    const [avaliacaoLimpeza, setLimpeza] = useState();
    const [avaliacaoRapidez, setRapidez] = useState();
    const [avaliacaoAtendimento, setAtendimento] = useState();
    const [avaliacaoPreco, setPreco] = useState();
    const [avaliacaoConforto, setConforto] = useState();
    const[comentarios, setComentarios] = useState([]);

    const[avaliacoes,setAvaliacoes] = useState([]);

    const idAgencia = localStorage.getItem('idUsuario');
    const nomeAgencia = localStorage.getItem('nomeUsuario');

    const history = useHistory();

    useEffect(() => {
        console.log('Nome da agência:' + nomeAgencia);
        //Pegando viagens do cliente
        api.get(`/showNotas/${idAgencia}`).then(response => {
            setAvaliacoes(response.data);
            console.log(response.data)
        })
        api.get(`/showComentarios/${idAgencia}`).then(response => {
            setComentarios(response.data);
            console.log(response.data)
        })
    }, [idAgencia]);


    function handleLogout(){
        //Limpa o localStorage
         localStorage.clear();
         //Redireciona a home
         history.push('/loginCliente');
     }
     return(
        <div className="container-show-avaliacoes">
            <header>
                <img src={logoImg} alt="Logo ViagemApp"/>

                <div style={{display:"flex", alignItems: "center", justifyContent: "center", flexDirection: "row"}}>
                <Link className='button-minhas-viagens' to='perfilAgencia'>Minhas viagens</Link>
                    <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                        <FiPower size={50} />
                    </button>
                </div>
            </header>
            <div className='container-cagetorias'>
                <h1>Avaliação de {nomeAgencia}</h1>
                <div className="lista-categorias">
                    <div>
                        <strong>Avaliação de Conforto</strong>
                        <p>{avaliacoes[3]}</p>
                    </div>
                    <div>
                        <strong>Avaliação de Preço</strong>
                        <p>{avaliacoes[4]}</p>
                    </div>
                    <div>
                        <strong>Avaliação de Atendimento</strong>
                        <p>{avaliacoes[0]}</p>
                    </div>
                    <div>
                        <strong>Avaliação de Rapidez</strong>
                        <p>{avaliacoes[2]}</p>
                    </div>
                    <div>
                        <strong>Avaliação de Limpeza</strong>
                        <p>{avaliacoes[1]}</p>
                    </div>
                    <div>
                        <strong>Avaliação Geral</strong>
                        <p>{(avaliacoes[0] + avaliacoes[1] + avaliacoes[2] + avaliacoes[3] + avaliacoes[4])/5 }</p>
                    </div>
                </div>
            </div>

            <h2>Comentários relevantes</h2>

            <div className="lista-comentarios">
                <ul>
                    {comentarios.map(comentario => (
                    <li>
                        <strong>{comentario.nomeCliente}</strong>
                        <p>{comentario.comentario}</p>
                    </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}