import React from 'react';
import { Link } from 'react-router-dom';
import './styles.css';

import logoImg from '../../assets/logo.png';

export default function BemVindo(){
    return (
        <div className="container">
            <img src={logoImg} alt="Logo QuartoApp" />

            <h1>Faça sua escolha</h1>
            
            <Link 
            className='button-bem-vindo' 
            style={{ marginTop: '40px' }} 
            to='/loginAgencia'>
                Entrar como agência
            </Link>
            
            <Link 
            className='button-bem-vindo' 
            style={{ padding: '20px 120px', marginTop: '10px'}} 
            to='/loginCliente'>
                Entrar como cliente
            </Link>   
        </div>
    );
}

