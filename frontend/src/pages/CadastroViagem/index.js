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

    const [inicioReserva, setInicioReserva] = useState('');
    const [ocupada, setOcupada]= useState(false);
    const [estado, setEstado] = useState('Disponível');
    const [numero, setNumero] = useState('');
    const [totalCompras, setTotalCompras] = useState(0);
    const [tagString, setTags] = useState('');
    const [idRestaurante, setIdRestaurante] = useState();
    
    const history = useHistory();

    const idUsuario = parseInt(localStorage.getItem('idUsuario'));

    async function handleCadastroViagem(e){
        e.preventDefault();

         const mesa = {
         	    mesa:{
                    numero,
                	inicioReserva,
                    estado,
                    ocupada,
                    totalCompras,
                    idRestaurante 
                },
            		tagString
            }

        try{
            console.log(mesa);
            const response = await api.post(`/${idUsuario}/cadastrarViagem`, mesa);
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
                <h1 style={{marginBottom: 30}}>Cadastre uma Mesa</h1>
                <div className='local'>
                    <input 
                        type='text' 
                        placeholder='Número da mesa' 
                        value={numero}
                        onChange={e => {setNumero(e.target.value);
                                       setIdRestaurante(idUsuario)
                        }}
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