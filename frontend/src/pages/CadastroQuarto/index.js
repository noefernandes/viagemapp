import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2, FiArrowLeft } from '../CadastroHotel/node_modules/react-icons/fi'      //npm install react-icons
import './styles.css';

import logoImg from '../../assets/logo.png';
import api from '../../services/api';
import PerfilHotel from '../PerfilHotel';

//install react-currency-format --save
const CurrencyFormat = require('react-currency-format');

export default function CadastroQuarto(){

    const [inicioReserva, setLocalPartida] = useState('');
    const [fimReserva, setLocalChegada] = useState('');
    const [numero, setData] = useState('');
    const [preco, setPreco] = useState();
    const [andar, setCapacidade] = useState();
    const [tagString, setTags] = useState('');
    const [idHotel, setIdHotel] = useState();
    
    const history = useHistory();

    const idUsuario = parseInt(localStorage.getItem('idUsuario'));

    async function handleCadastroQuarto(e){
        e.preventDefault();

         const quarto = {
         		quarto:{
                	inicioReserva,
                	fimReserva,
                	numero,
                	andar,
                	preco,
                    idHotel 
                },
            		tagString
            }

        try{
            console.log(quarto);
            const response = await api.post(`/${idUsuario}/cadastrarQuarto`, quarto);
            console.log(response.numero);
            history.push('PerfilHotel'); 

        }catch(err){
            alert(err);
        }
    }

    //Função responsáel por limpar o localStorage ao deslogar.
    function handleLogout(){
        //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginHotel');
    }
    
    return (

        <div className='container-cadastro-quarto'>
            <header>
                <div>
                    <img src={logoImg} alt='Logo QuartoApp'/>
                    <Link className='back-link' to='perfilHotel' style={{marginLeft: 40}}>
                        <FiArrowLeft/> Voltar ao perfil
                    </Link>
                </div>
                <button onClick={handleLogout} type='button' className='power' style={{ borderStyle:'none' }}>
                    <FiPower size={50} />
                </button>
            </header>
            
            <form className='form-cadastro-quarto' onSubmit={handleCadastroQuarto} >
                <h1 style={{marginBottom: 30}}>Cadastre um quarto</h1>
                <div className='local'>
                    <input 
                        type='text' 
                        placeholder='Número do quarto' 
                        value={numero}
                        onChange={e => {setLocalPartida(e.target.value);
                                       setIdHotel(idUsuario)
                        }}
                    />
                    <input
                        type='text' 
                        placeholder='Andar do Quarto' 
                        value={andar}
                        onChange={e => setLocalChegada(e.target.value)}
                    />
                </div>
                <div className='inicioReserva'>
                    <CurrencyFormat 
                        format="##/##/####" 
                        placeholder="Início (DD/MM/AAAA)" 
                        mask={['D', 'D', 'M', 'M', 'A', 'A', 'A', 'A']}
                        value={inicioReserva}
                        onChange={e => setData(e.target.value)}
                    />
                </div>
                <div className='fimReserva'>
                    <CurrencyFormat
                        format="##/##/####"
                        placeholder="Fim (DD/MM/AAAA)"
                        mask={['D', 'D', 'M', 'M', 'A', 'A', 'A', 'A']}
                        value={fimReserva}
                        onChange={e => setData(e.target.value)}
                    />
                </div>
                <div>
                    <CurrencyFormat 
                        placeholder='Preço' 
                        value={preco}
                        onChange={e => setPreco(e.target.value)}
                    />
                </div>

                <h2 style={{marginBottom: 30}}>Insira as ofertas de seu quarto (separe com ";")</h2>
                <div className="tags">
                    <input 
                        type='text'
                        placeholder='Tags' 
                        value={tagString}
                        onChange={e => setTags(e.target.value)}
                    />
                </div>

                <button type='submit'>Criar quarto</button>
            </form>
        </div>
    );
}