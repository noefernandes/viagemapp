import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './style.css';
import { FiArrowLeft } from 'react-icons/fi';           //npm install react-icons

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

export default function AvaliarHotel(){
    const [avaliacaoLimpeza, setLimpeza] = useState();
    const [avaliacaoLocalidade, setLocalidade] = useState();
    const [avaliacaoAtendimento, setAtendimento] = useState();
    const [avaliacaoPreco, setPreco] = useState();
    const [avaliacaoConforto, setConforto] = useState();
    const [comentarios,setComentarios] = useState('');

    const idUsuario = localStorage.getItem('idUsuario');
    const nomeUsuario = localStorage.getItem('nomeUsuario');
    const idHotel = localStorage.getItem('idHotel');

    const history = useHistory();


    function handleLogout(){
        //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginCliente');
    }



    async function handleAvaliarHotel(e){
        e.preventDefault();

        const data = {
            avaliacaoConforto,
            avaliacaoPreco,
            avaliacaoAtendimento,
            avaliacaoLocalidade,
            avaliacaoLimpeza,
            comentarios
        }
        try{
            console.log("id do hotel: " + idHotel);
            console.log("id do cliente: " + idUsuario);
            const response = await api.post(`/${idUsuario}/avaliarHotel/${idHotel}`, data);
            console.log(response.data.nome);
            if(response.status === 200){
                console.log("Entra");
                try{
                    await api.put(`/updateToSortNotas/${idHotel}`);
                }catch(err){
                    alert('Erro ao gravar notas.')
                }
            }
            history.push('PerfilCliente');
        }catch(Err){
            alert('Erro ao avaliar o hotel.');
        }
    }

    return(
        <div className="container-avaliar-hotel">
            <section>
                <img src={logoImg} width={200} alt="QuartoApp logo"/>
                <Link className="back-link" style={{ marginLeft: 98 }} to="/perfilCliente">
                    <FiArrowLeft/>
                    Não quero avaliar ainda
                </Link>
            </section>

            <section className='form'>
            <h1>Avalie o hotel do quarto!</h1>
                <form onSubmit={handleAvaliarHotel}>
                    <div className="avaliacao-parte">
                        <div>    
                            <strong> Conforto</strong>
                            <input
                                placeholder = '1-5'
                                type="number" min="1" max="5"
                                value={avaliacaoConforto}
                                onChange = {e => setConforto(e.target.value)}
                            />
                        </div>
                        <div>
                            <strong> Preço</strong>
                            <input
                                placeholder = '1-5'
                                type="number" min="1" max="5"
                                value={avaliacaoPreco}
                                onChange = {e => setPreco(e.target.value)}
                            />
                        </div>

                        <div>

                            <strong> Atendimento</strong>
                            <input
                                placeholder = '1-5'
                                type="number" min="1" max="5"
                                value={avaliacaoAtendimento}
                                onChange = {e => setAtendimento(e.target.value)}
                            />
                        </div>

                        <div>
                            <strong> Localidade</strong>
                            <input
                                placeholder = '1-5'
                                type="number" min="1" max="5"
                                value={avaliacaoLocalidade}
                                onChange = {e => setLocalidade(e.target.value)}
                            />
                        </div>

                        <div>
                            <strong> Limpeza</strong>
                            <input
                                placeholder = '1-5'
                                type="number" min="1" max="5"
                                value={avaliacaoLimpeza}
                                onChange = {e => setLimpeza(e.target.value)}
                            />
                        </div>
                    </div>

                    <div>
                        <strong> Comentário</strong>
                        <textarea

                            value={comentarios}
                            onChange = {e => setComentarios((e.target.value))}
                        />
                    </div>
                    <button type='submit'>Cadastrar avaliação</button>
                </form>
            </section>
        </div>
    )



}