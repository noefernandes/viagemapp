import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom 
import { FiArrowRight, FiArrowLeft } from 'react-icons/fi'           //npm install react-icons

import './styles.css';

import logoImg from '../../assets/logo.png';
import api from '../../services/api';

export default function LoginHotel(){
    //Cria os estados a serem armazenados
    const [nomeUsuario, setNomeUsuario] = useState('');
    const [senha, setSenha] = useState(''); 

    const history = useHistory();

    const usuario = {
        nomeUsuario,
        senha
    } 

    async function handleLogin(e){
        e.preventDefault();

        try{
            //Manda a requisição
            const response = await api.post('loginHotel', usuario);
            //Armazena o id e o nome no localStorage do navegador
            localStorage.setItem('idUsuario', response.data.id);
            localStorage.setItem('nomeUsuario', response.data.nome);
            //Redireciona para a página
            history.push('perfilHotel');

        }catch(err){
            //BADREQUEST
            if(err.response.status === 400){
                alert('Preencha com todos os dados');
            //NOTFOUND
            }else if(err.response.status === 404){
                alert(err.response.data);
            //FORBIDDEN
            }else if(err.response.status === 401){
                alert(err.response.data);
            }else{
                alert('Falha ao logar, tente novamente.');
            }
        }
    }

    return (
        <div className='login-hotel-container'>
            <img src={logoImg} alt='Logo ViagemApp' />

            {/*Executa a função toda vez que submeter o formulário */}
            <form onSubmit={handleLogin}>
                <h1>Efetue o login</h1>
                <input 
                    placeholder='Nome de usuário' 
                    value={nomeUsuario}
                    onChange={ e => setNomeUsuario(e.target.value)}
                >
                </input>
                
                <input 
                    placeholder='Senha'
                    type='password'
                    value={senha}    
                    onChange={e => setSenha(e.target.value)}
                >
                </input>
                
                <button className='button' type='submit'>
                    Entrar como hotel
                </button>
            </form>
            <Link className = "back-link" to="/cadastroHotel">
                <FiArrowRight/> Não tenho cadastro
            </Link>
            <Link className = "back-link" to="/">
                <FiArrowLeft/> Voltar ao início
            </Link>   
        </div>
    );
}