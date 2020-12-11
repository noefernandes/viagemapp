import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';
export default function ShowAvaliacoes(){
    const [avaliacaoLimpeza, setLimpeza] = useState();
    const [avaliacaoLocalidade, setLocalidade] = useState();
    const [avaliacaoAtendimento, setAtendimento] = useState();
    const [avaliacaoPreco, setPreco] = useState();
    const [avaliacaoConforto, setConforto] = useState();
    const[comentarios, setComentarios] = useState([]);

    const[avaliacoes,setAvaliacoes] = useState([]);

    const idHotel = localStorage.getItem('idUsuario');
    const nomeHotel = localStorage.getItem('nomeUsuario');

    const history = useHistory();

    useEffect(() => {
        console.log('Nome da agência:' + nomeHotel);
        //Pegando quartos do cliente
        api.get(`/showNotas/${idHotel}`).then(response => {
            setAvaliacoes(response.data);
            console.log(response.data)
        })
        api.get(`/showComentarios/${idHotel}`).then(response => {
            setComentarios(response.data);
            console.log(response.data)
        })
    }, [idHotel]);


    function handleLogout(){
        //Limpa o localStorage
         localStorage.clear();
         //Redireciona a home
         history.push('/loginCliente');
     }
     return(
        <div className="container-show-avaliacoes">
            <header>
                <img src={logoImg} alt="Logo QuartoApp"/>

                <div style={{display:"flex", alignItems: "center", justifyContent: "center", flexDirection: "row"}}>
                <Link className='button-minhas-quartos' to='perfilHotel'>Minhas quartos</Link>
                    <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                        <FiPower size={50} />
                    </button>
                </div>
            </header>
            <div className='container-cagetorias'>
                <h1>Avaliação de {nomeHotel}</h1>
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
                        <strong>Avaliação de Localidade</strong>
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