import React, { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';    //npm install react-router-dom
import { FiPower, FiTrash2, FiShoppingCart } from 'react-icons/fi'      //npm install react-icons
import './styles.css';

import api from '../../services/api';
import logoImg from '../../assets/logo.png';

const CurrencyFormat = require('react-currency-format');

export default function PerfilHotel(){
    //Inicia-se com um vetor vazio como total de quartos do usuario.
    const [quartosComNome, setQuartosComNome] = useState([])
    const [nomeHotel, setNomeHotel] = useState('');
    const [localPartida, setLocalPartida] = useState('');
    const [localChegada, setLocalChegada] = useState('');
    const [data, setData] = useState('');
    const [tags, setTags] = useState([]);
    const [loading, setLoading] = useState(false);
    const [filteredQuartosComNome, setFilteredQuartosComNome] = useState([]);
    const [aux, setAux] = useState([]);

    //Para pegar valor do nome da agencia
    const [temp, setTemp] = useState('');
    
    //Pega os dados anteriormente armazenados no localStorage.
    const idUsuario = localStorage.getItem('idUsuario');

    const history = useHistory();

    async function handleComprarQuarto(id){
        try{
            const response = await api.post(`/${idUsuario}/comprarQuarto/${id}`, idUsuario);
            /*Filtra a lista de incidents mantendo apenas aqueles
            com id diferente do com id deletado*/
            setQuartosComNome(quartosComNome.filter(quartoComNome => quartoComNome.quarto.id !== id));
        }catch(err){
            if(err.response.status === 403){
                alert(err.response.data);
            }else{
                alert('Erro ao comprar quarto.');
            }
        }
    }

    useEffect(() => {
        setLoading(true);
        //
        api.get(`${idUsuario}/quartosComNome/`).then(response => {
            setQuartosComNome(response.data);
            console.log(response.data);
            setAux(response.data);
            setLoading(false);
        })
    }, [idUsuario]);


    useEffect(() => {

        setFilteredQuartosComNome(
          quartosComNome.filter((quartoComNome) =>{
            if(tags[0] === "")
            {
                console.log(aux);
                return aux;
            }
            return (
               quartoComNome.nomeHotel.toLowerCase().includes(nomeHotel.toLowerCase())
            && quartoComNome.quarto.localPartida.toLowerCase().includes(localPartida.toLowerCase())
            && quartoComNome.quarto.localChegada.toLowerCase().includes(localChegada.toLowerCase())
            && quartoComNome.quarto.data.toLowerCase().includes(data)
            && tags.every(e => quartoComNome.quarto.tags.includes(e)));    
          })
        );
      }, [nomeHotel, localPartida, localChegada, data, tags, quartosComNome]);


    //Função responsáel por limpar o localStorage ao deslogar.
    function handleLogout(){
        //Limpa o localStorage
        localStorage.clear();
        //Redireciona a home
        history.push('/loginCliente');
    }

    function handleGuardarHotel(id, nomeHotel){
        localStorage.setItem('idHotel', id);
        localStorage.setItem('nomeHotel', nomeHotel);
        console.log('Primeiro:' + nomeHotel);
    }

    if (loading) {
        return <p>Carregando Quartos...</p>;
    }

    return (
        <div className="container-feed-cliente">
            <header>
                <img src={logoImg} alt="Logo QuartoApp"/>
                <div style={{display:"flex", alignItems: "center", justifyContent: "center", flexDirection: "row"}}>
                <Link className='button-minhas-quartos' to='perfilCliente'>Minhas quartos</Link>
                    <button onClick={handleLogout} type='button' className="power" style={{ borderStyle:'none' }}>
                        <FiPower size={50} />
                    </button>
                </div>
            </header>
           
            <div className="filtro">
                <h1 style={{marginBottom: 30}}>Busque uma quarto</h1>
                <div className='part1'>
                    <input 
                        type='text' 
                        placeholder='Nome da agência' 
                        value={nomeHotel}
                        onChange={e => setNomeHotel(e.target.value)}
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
                <div className="lista-quartos">
                    <ul>
                        {filteredQuartosComNome.map(quartoComNome => (
                        <li>
                            <strong>Hotel</strong>
                            <Link className='button-avaliar-hotel' to='showAvaliacoes'
                                      onClick={() => handleGuardarHotel(quartoComNome.quarto.idHotel, 
                                                                    quartoComNome.nomeHotel)}>
                                {quartoComNome.nomeHotel}
                            </Link>
                            <strong>Nota</strong>
                            <p>{quartoComNome.nota}</p>
                            <strong>Número</strong>
                            <p>{quartoComNome.quarto.numero}</p>
                            <strong>Andar</strong>
                            <p>{quartoComNome.quarto.andar}</p>
                            <strong>Ínicio da Reserva</strong>
                            <p>{quartoComNome.quarto.inicioReserva}</p>
                            <strong>Fim da Reserva</strong>
                            <p>{quartoComNome.quarto.fimReserva}</p>
                            <strong>Preço</strong>
                            <p>{Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(quartoComNome.quarto.preco)}</p>
                            <strong>Tags: </strong>
                            <ul className="listaTags">
                                {quartoComNome.quarto.tags.map(tag => (
                                    <li>
                                        <p>{tag}</p>
                                    </li>
                                ))}
                            </ul>

                            <button 
                                onClick={() => handleComprarQuarto(quartoComNome.quarto.id)}
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