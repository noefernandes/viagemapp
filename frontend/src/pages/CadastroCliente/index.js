import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiArrowLeft } from 'react-icons/fi';           //npm install react-icons
import './styles.css';

import logoImg from '../../assets/logo.png';
import api from '../../services/api';


export default function CadastroCliente(){
    //Cria os estados a serem armazenados
    const [nome, setNome] = useState('');
    const [cpf, setCpf] = useState('');
    const [email, setEmail] = useState('');
    const [nomeUsuario, setNomeUsuario] = useState('');
    const [senha, setSenha] = useState('');
    const [tagString, setTags] = useState('');

    const history = useHistory();

    async function handleCadastro(e){
        e.preventDefault();

        const data = {
        	cliente:{
            	nome,
            	cpf,
            	email,
            	nomeUsuario,
            	senha,
            },
            	tagString
        }

	
        try{
            //Manda a requisição
            const response = await api.post('cadastroCliente', data);

            if(response.status === 200){
                alert('Usuario cadastrado com sucesso!');
            }
            //Redireciona para a página
            history.push('loginCliente');
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
        <div className="cadastro-cliente-container">
            <section>
                <img src={logoImg} width={200} alt="ViagemApp logo"/>
                <Link className="back-link" style={{ marginLeft: 120 }} to="/loginCliente">
                    <FiArrowLeft/>
                    Já tenho login
                </Link>
            </section>

             {/*Executa a função toda vez que submeter o formulário */}
            <section className='form'>
                <form onSubmit={handleCadastro}>
                    <h1>Faça seu cadastro</h1>

                    <input
                        placeholder='Seu nome'
                        value={nome}
                        onChange = {e => setNome(e.target.value)}
                    />
                    <input
                        placeholder='CPF'
                        value={cpf}
                        onChange = {e => setCpf(e.target.value)}
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

                    <h2 style={{marginBottom: 30}}>Insira suas preferências para viagem (separe com ";")</h2>
                    <div className="tags">
                        <input 
                            type='text'
                            placeholder='Tags' 
                            value={tagString}
                            onChange={e => setTags(e.target.value)}
                        />
                    </div>

                    <button type='submit'>Cadastrar</button>

                </form>
            </section>
        </div>
    );
}