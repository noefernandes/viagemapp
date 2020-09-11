import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

export default function PerfilCliente(){
 //Pega os dados anteriormente armazenados no localStorage.
    const idUsuario = localStorage.getItem('idUsuario');
    const nomeUsuario = localStorage.getItem('nomeUsuario');

    const history = useHistory();


    function handleLogout(){
       //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginCliente');
    }

    return(
      <div className="container-perfil-cliente">
         <header>
            <img src={logoImg} alt="Logo ViagemApp"/>
            <span>Bem-vindo(a), {nomeUsuario}!</span>
            <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                <FiPower size={50} />
            </button>
         </header>


      </div>

    );
}