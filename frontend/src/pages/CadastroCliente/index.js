import React from 'react';
import { Link } from 'react-router-dom';
import { FiArrowLeft } from 'react-icons/fi'
import './styles.css';

import logoImg from '../../assets/logo.png';

export default function CadastroCliente(){
    return (
        <div className="cadastro-cliente-container">
           <section>
                <img src={logoImg} width={200} alt="ViagemApp logo"/>
                <Link className="back-link" style={{ marginLeft: 120 }} to="/loginCliente">
                    <FiArrowLeft/>
                    Já tenho login
                </Link>
            </section>
            
            <section className="form">
                <form>
                    <h1>Faça seu cadastro</h1>

                    <input placeholder="Nome completo"/>
                    <input placeholder="cpf" />
                    <input placeholder="E-mail"/>
                    <div className="container-autenticacao">
                        <input placeholder="Nome de usuário" />
                        <input type="password" placeholder="Senha" />
                    </div>
                    <button type="submit">Cadastrar</button>

                </form>
            </section>
        </div>
    );
}