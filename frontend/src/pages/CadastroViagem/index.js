import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2, FiArrowLeft } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import logoImg from '../../assets/logo.png';
import api from '../../services/api';
import PerfilAgencia from '../PerfilAgencia';

//install react-currency-format --save
const CurrencyFormat = require('react-currency-format');

export default function CadastroViagem(){

    const [localPartida, setLocalPartida] = useState('');
    const [localChegada, setLocalChegada] = useState('');
    const [horarioPartida, sethorarioPartida] = useState('');
    const [horarioChegada, setHorarioChegada]= useState('');
    const [data, setData] = useState('');
    const [preco, setPreco] = useState();
    const [capacidade, setCapacidade] = useState();
    const [tagString, setTags] = useState('');
    const [idAgencia, setIdAgencia] = useState();
    
    const history = useHistory();

    const idUsuario = parseInt(localStorage.getItem('idUsuario'));

    async function handleCadastroViagem(e){
        e.preventDefault();

         const viagem = {
         		viagem:{
                	localPartida,
                	localChegada,
                	data,
                	horarioPartida,
                	horarioChegada,
                	preco,
                    capacidade,
                    idAgencia 
                },
            		tagString
            }

        try{
            console.log(viagem);
            const response = await api.post(`/${idUsuario}/cadastrarViagem`, viagem);
            console.log(response.data);
            history.push('PerfilAgencia'); 

        }catch(err){
            alert(err);
        }
    }

    //Função responsáel por limpar o localStorage ao deslogar.
    function handleLogout(){
        //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginAgencia');
    }
    
    return (

        <div className='container-cadastro-viagem'>
            <header>
                <div>
                    <img src={logoImg} alt='Logo ViagemApp'/>
                    <Link className='back-link' to='perfilAgencia' style={{marginLeft: 40}}>
                        <FiArrowLeft/> Voltar ao perfil
                    </Link>
                </div>
                <button onClick={handleLogout} type='button' className='power' style={{ borderStyle:'none' }}>
                    <FiPower size={50} />
                </button>
            </header>
            
            <form className='form-cadastro-viagem' onSubmit={handleCadastroViagem} >
                <h1 style={{marginBottom: 30}}>Cadastre uma viagem</h1>
                <div className='local'>
                    <input 
                        type='text' 
                        placeholder='Local de partida' 
                        value={localPartida}
                        onChange={e => {setLocalPartida(e.target.value);
                                       setIdAgencia(idUsuario)
                        }}
                    />
                    <input
                        type='text' 
                        placeholder='Local de chegada' 
                        value={localChegada}
                        onChange={e => setLocalChegada(e.target.value)}
                    />
                </div>
                <div className='data'>
                    <CurrencyFormat 
                        format="##/##/####" 
                        placeholder="Data (DD/MM/AAAA)" 
                        mask={['D', 'D', 'M', 'M', 'A', 'A', 'A', 'A']}
                        value={data}
                        onChange={e => setData(e.target.value)}
                    />
                </div>
                <div>
                    <CurrencyFormat
                        format="##:##" 
                        placeholder="Horário de partida (HH/MM)" 
                        mask={['H', 'H', 'M', 'M']}
                        value={horarioPartida}
                        onChange={e => sethorarioPartida(e.target.value)}
                    />
                    
                    <CurrencyFormat 
                        format="##:##" 
                        placeholder="Horário de chegada (HH/MM)" 
                        mask={['H', 'H', 'M', 'M']}
                        value={horarioChegada}
                        onChange={e => setHorarioChegada(e.target.value)}
                    />
                </div>
                <div>
                    <CurrencyFormat 
                        placeholder='Preço' 
                        value={preco}
                        onChange={e => setPreco(e.target.value)}
                    />
                    <input 
                        type='text'
                        placeholder='Capacidade' 
                        value={capacidade}
                        onChange={e => setCapacidade(e.target.value)}
                    />
                </div>

                <h2 style={{marginBottom: 30}}>Insira as ofertas de sua viagem (separe com ";")</h2>
                <div className="tags">
                    <input 
                        type='text'
                        placeholder='Tags' 
                        value={tagString}
                        onChange={e => setTags(e.target.value)}
                    />
                </div>

                <button type='submit'>Criar viagem</button>
            </form>
        </div>
    );
}