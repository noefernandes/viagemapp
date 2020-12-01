import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2, FiShoppingCart } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

const CurrencyFormat = require('react-currency-format');

export default function PerfilAgencia(){
    //Inicia-se com um vetor vazio como total de viagens do usuario.
    const [viagensComNome, setViagensComNome] = useState([])
    const [nomeAgencia, setNomeAgencia] = useState('');
    const [localPartida, setLocalPartida] = useState('');
    const [localChegada, setLocalChegada] = useState('');
    const [data, setData] = useState('');
    const [tags, setTags] = useState([]);
    const [loading, setLoading] = useState(false);
    const [filteredViagensComNome, setFilteredViagensComNome] = useState([]);
    const [aux, setAux] = useState([]);

    //Para pegar valor do nome da agencia
    const [temp, setTemp] = useState('');
    
    //Pega os dados anteriormente armazenados no localStorage.
    const idUsuario = localStorage.getItem('idUsuario');

    const history = useHistory();

    async function handleComprarViagem(id){
        try{
            const response = await api.post(`/${idUsuario}/comprarViagem/${id}`, idUsuario);
            /*Filtra a lista de incidents mantendo apenas aqueles
            com id diferente do com id deletado*/
            setViagensComNome(viagensComNome.filter(viagemComNome => viagemComNome.viagem.id !== id));
        }catch(err){
            if(err.response.status === 403){
                alert(err.response.data);
            }else{
                alert('Erro ao comprar viagem.');
            }
        }
    }

    useEffect(() => {
        setLoading(true);
        //
        api.get(`${idUsuario}/viagensComNome/`).then(response => {
            setViagensComNome(response.data);
            console.log(response.data);
            setAux(response.data);
            setLoading(false);
        })
    }, [idUsuario]);


    useEffect(() => {

        setFilteredViagensComNome(
          viagensComNome.filter((viagemComNome) =>{
            if(tags[0] === "")
            {
                console.log(aux);
                return aux;
            }
            return (
               viagemComNome.nomeAgencia.toLowerCase().includes(nomeAgencia.toLowerCase())
            && viagemComNome.viagem.localPartida.toLowerCase().includes(localPartida.toLowerCase())
            && viagemComNome.viagem.localChegada.toLowerCase().includes(localChegada.toLowerCase())
            && viagemComNome.viagem.data.toLowerCase().includes(data)
            && tags.every(e => viagemComNome.viagem.tags.includes(e)));    
          })
        );
      }, [nomeAgencia, localPartida, localChegada, data, tags, viagensComNome]);


    //Função responsáel por limpar o localStorage ao deslogar.
    function handleLogout(){
        //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginCliente');
    }

    function handleGuardarAgencia(id, nomeAgencia){
        localStorage.setItem('idAgencia', id);
        localStorage.setItem('nomeAgencia', nomeAgencia);
        console.log('Primeiro:' + nomeAgencia);
    }

    if (loading) {
        return <p>Carregando Viagens...</p>;
    }

    return (
        <div className="container-feed-cliente">
            <header>
                <img src={logoImg} alt="Logo ViagemApp"/>
                <div style={{display:"flex", alignItems: "center", justifyContent: "center", flexDirection: "row"}}>
                <Link className='button-minhas-viagens' to='perfilCliente'>Minhas viagens</Link>
                    <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                        <FiPower size={50} />
                    </button>
                </div>
            </header>
           
            <div className="filtro">
                <h1 style={{marginBottom: 30}}>Busque uma viagem</h1>
                <div className='part1'>
                    <input 
                        type='text' 
                        placeholder='Nome da agência' 
                        value={nomeAgencia}
                        onChange={e => setNomeAgencia(e.target.value)}
                    />
                    
                    <input 
                        type='text' 
                        placeholder='Local de partida' 
                        value={localPartida}
                        onChange={e => setLocalPartida(e.target.value)}
                    />
                    <input
                        type='text' 
                        placeholder='Local de chegada' 
                        value={localChegada}
                        onChange={e => setLocalChegada(e.target.value)}
                    />
                    <CurrencyFormat 
                        format="##/##/####" 
                        placeholder="Data (DD/MM/AAAA)" 
                        mask={['D', 'D', 'M', 'M', 'A', 'A', 'A', 'A']}
                        value={data}
                        onChange={e => setData(e.target.value)}
                    />
                </div>
                    
                <div className="part2">
                    <input 
                        type='text'
                        placeholder='Tags' 
                        value={tags}
                        onChange={e => setTags(e.target.value.split(';'))}
                    />
                </div>
            </div>   
                



            <div className='container-feed-cliente'>
                <div className="lista-viagens">
                    <ul>
                        {filteredViagensComNome.map(viagemComNome => (
                        <li>
                            <strong>Agência</strong>
                            <Link className='button-avaliar-agencia' to='showAvaliacoes'
                                      onClick={() => handleGuardarAgencia(viagemComNome.viagem.idAgencia, 
                                                                    viagemComNome.nomeAgencia)}>
                                {viagemComNome.nomeAgencia}
                            </Link>
                            <strong>Nota</strong>
                            <p>{viagemComNome.nota}</p>
                            <strong>Local de partida</strong>
                            <p>{viagemComNome.viagem.localPartida}</p>
                            <strong>Local de chegada</strong>
                            <p>{viagemComNome.viagem.localChegada}</p>
                            <strong>Data</strong>
                            <p>{viagemComNome.viagem.data}</p>
                            <strong>Horário de partida</strong>
                            <p>{viagemComNome.viagem.horarioPartida}</p>
                            <strong>Horário de chegada</strong>
                            <p>{viagemComNome.viagem.horarioChegada}</p>
                            <strong>Preço</strong>
                            <p>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(viagemComNome.viagem.preco)}</p>
                            <strong>Capacidade</strong>
                            <p>{viagemComNome.viagem.qtdPassageiros}/{viagemComNome.viagem.capacidade}</p>
                            <strong>Tags: </strong>
                            <ul className="listaTags">
                                {viagemComNome.viagem.tags.map(tag => (
                                    <li>
                                        <p>{tag}</p>
                                    </li>
                                ))}
                            </ul>

                            <button 
                                onClick={() => handleComprarViagem(viagemComNome.viagem.id)}
                                type='button' 
                                className='buy'
                            >
                                <FiShoppingCart/>
                            </button>
                        </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}