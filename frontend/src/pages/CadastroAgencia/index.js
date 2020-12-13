import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiArrowLeft } from 'react-icons/fi';           //npm install react-icons
import './styles.css';

import logoImg from '../../assets/logo.png';
import api from '../../services/api';

export default function CadastroAgencia(){
    //Cria os estados a serem armazenados
    const [nome, setNome] = useState('');
    const [cnpj, setCnpj] = useState('');
    const [email, setEmail] = useState('');
    const [nomeUsuario, setNomeUsuario] = useState('');
    const [senha, setSenha] = useState('');
    
    const history = useHistory();

    async function handleCadastro(e){
        e.preventDefault();
        
        const data = {
            nome,
            cnpj,
            email,
            nomeUsuario,
            senha
        }

        try{
            //Manda a requisição
            const response = await api.post('cadastroAgencia', data);

            if(response.status === 200){
                alert('Usuario cadastrado com sucesso!');
            }
            //Redireciona para a página
            history.push('loginAgencia');
        }catch (err){
            //Equivale ao BADREQUEST
            if(err.response.status === 400){
                alert('Preencha com todos os dados');
            //Equivale ao FORBIDDEN
            }else if(err.response.status === 403){
                alert(err.response.data);
            }else{
                alert('Falha ao cadastrar, tente novamente.');
            }
        }
    }

    return (
        <div className="cadastro-agencia-container">
            <section>
                <img src={logoImg} width={200} alt="ViagemApp logo"/>
                <Link className="back-link" style={{ marginLeft: 120 }} to="/loginAgencia">
                    <FiArrowLeft/>
                    Já tenho login
                </Link>
            </section>

             {/*Executa a função toda vez que submeter o formulário */}
            <section className='form'>
                <form onSubmit={handleCadastro}>
                    <h1>Faça seu cadastro</h1>

                    <input 
                        placeholder='Nome do restaurante'
                        value={nome}
                        onChange = {e => setNome(e.target.value)}
                    />
                    <input 
                        placeholder='CNPJ'
                        value={cnpj}
                        onChange = {e => setCnpj(e.target.value)}
                    />
                    <input 
                        placeholder='E-mail'
                        type='email'
                        value={email}
                        onChange = {e => setEmail(e.target.value)}
                    />
                    <div className='container-autenticacao'>
                        <input 
                            placeholder='Nome de usuário' 
                            value={nomeUsuario}
                            onChange = {e => setNomeUsuario(e.target.value)}
                        />
                        
                        <input 
                            type='password' 
                            placeholder='Senha' 
                            value={senha}
                            onChange = {e => setSenha(e.target.value)}
                        />
                    </div>
                    <button type='submit'>Cadastrar</button>

                </form>
            </section>
        </div>
    );
}