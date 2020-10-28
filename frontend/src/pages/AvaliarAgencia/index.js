import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './style.css';
import { FiArrowLeft } from 'react-icons/fi';           //npm install react-icons

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

export default function AvaliarAgencia(){
    const [avaliacaoLimpeza, setLimpeza] = useState(0);
    const [avaliacaoRapidez, setRapidez] = useState(0);
    const [avaliacaoAtendimento, setAtendimento] = useState(0);
    const [avaliacaoPreco, setPreco] = useState(0);
    const [avaliacaoConforto, setConforto] = useState(0);
    const[comentario,setComentario] = useState('');

    const idUsuario = localStorage.getItem('idUsuario');
    const nomeUsuario = localStorage.getItem('nomeUsuario');
    const idAgencia = localStorage.getItem('idAgencia');

    const history = useHistory();


    function handleLogout(){
        //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginCliente');
    }



    async function handleAvaliarAgencia(e){
        e.preventDefault();

        const data = {
            avaliacao:{
                avaliacaoConforto,
                avaliacaoPreco,
                avaliacaoAtendimento,
                avaliacaoRapidez,
                avaliacaoLimpeza,
                comentario
            }
        }
        try{
            const response = await api.post(`/${idUsuario}/avaliarAgencia/${idAgencia}`
                , idUsuario,idAgencia,data);
            console.log(response.data.nome);
            if(response.status === 200){
                await api.put(`/updateToSortNotas/${idAgencia}`,idAgencia);
            }
        }catch(Err){
            alert('Erro ao avaliar a agência.');
        }
    }

    return(
        <div className="container-avaliar-agencia">
            <section>
                <img src={logoImg} width={200} alt="ViagemApp logo"/>
                <Link className="back-link" style={{ marginLeft: 120 }} to="/perfilCliente">
                    <FiArrowLeft/>
                    Não quero avaliar ainda
                </Link>
            </section>

            <section className='form'>
                <form onSubmit={handleAvaliarAgencia}>
                    <h1>Avalie a agência da viagem!</h1>

                    <label> Conforto</label>
                    <input
                        placeholder = '1-5'
                        type="number" min="1" max="5"
                        value={avaliacaoConforto}
                        onChange = {e => setConforto((e.target.value))}
                    />

                    <label> Preço</label>
                    <input
                        placeholder = '1-5'
                        type="number" min="1" max="5"
                        value={avaliacaoPreco}
                        onChange = {e => setPreco((e.target.value))}
                    />

                    <label> Atendimento</label>
                    <input
                        placeholder = '1-5'
                        type="number" min="1" max="5"
                        value={avaliacaoAtendimento}
                        onChange = {e => setAtendimento((e.target.value))}
                    />

                    <label> Rapidez</label>
                    <input
                        placeholder = '1-5'
                        type="number" min="1" max="5"
                        value={avaliacaoRapidez}
                        onChange = {e => setRapidez((e.target.value))}
                    />

                    <label> Limpeza</label>
                    <input
                        placeholder = '1-5'
                        type="number" min="1" max="5"
                        value={avaliacaoLimpeza}
                        onChange = {e => setLimpeza((e.target.value))}
                    />
                    <label> Comentário</label>
                    <input

                        value={comentario}
                        onChange = {e => setComentario((e.target.value))}
                    />
                </form>
            </section>
        </div>
    )



}