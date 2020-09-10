import React from 'react';
import { Link } from 'react-router-dom';
import './styles.css';
import { FiArrowRight } from 'react-icons/fi';

import logoImg from '../../assets/logo.png';

export default function LoginCliente(){
    return (
        <div className="login-cliente-container">
            <img src={logoImg} alt="Logo ViagemApp" />

            <h1>Efetue o login</h1>
            <input placeholder="Nome de usuário"></input>
            <input type="password" placeholder="Senha"></input>
            <button className="button">
                Entrar como cliente
            </button>
            <Link className = "back-link" to="/CadastroCliente">
                <FiArrowRight/> Não tenho cadastro
            </Link>   
        </div>
    );
}