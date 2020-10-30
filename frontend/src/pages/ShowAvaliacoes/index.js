import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2 } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';
export default function ShowAvaliacoes(){
    const [avaliacaoLimpeza, setLimpeza] = useState();
    const [avaliacaoRapidez, setRapidez] = useState();
    const [avaliacaoAtendimento, setAtendimento] = useState();
    const [avaliacaoPreco, setPreco] = useState();
    const [avaliacaoConforto, setConforto] = useState();
    const[comentarios,setComentarios] = useState('');

    const[avaliacoes,setAvaliacoes] = useState([]);

    const idAgencia = localStorage.getItem('idAgencia');

    const history = useHistory();

    useEffect(() => {
        //Pegando viagens do cliente
        api.get(`/showNotas/${idAgencia}`).then(response => {
            setAvaliacoes(response.data);
            console.log(response.data)
        })
        api.get(`/showComentarios/${idAgencia}`).then(response => {
            setAvaliacoes(response.data);
            console.log(response.data)
        })
    }, [idAgencia]);


    function handleLogout(){
        //Limpa o localStorage
         localStorage.clear();
         //Redireciona a home
         history.push('/loginCliente');
     }
     return(
        <div className="container-show-avaliacoes">
                   <header>
                     <img src={logoImg} alt="Logo ViagemApp"/>
 
                     <div style={{display:"flex", alignItems: "center", justifyContent: "center", flexDirection: "row"}}>
                     <Link className='button-minhas-viagens' to='perfilCliente'>Feed</Link>
                         <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                             <FiPower size={50} />
                         </button>
                     </div>
                   </header>
                   <div className='container-cagetorias'>
                       <h1>Categorias</h1>
                       <div className="lista-categorias">
                          <ul>
                               <li>
                                   <strong>Conforto</strong>
                                   <p>{avaliacoes[0]}</p>
                                   
                               </li>
                           </ul>
                       </div>
                   </div>
               </div>
 
     );

}