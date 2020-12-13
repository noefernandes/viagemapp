import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import BemVindo from './pages/BemVindo/';
import CadastroCliente from './pages/CadastroCliente';
import CadastroHotel from './pages/CadastroHotel';
import LoginCliente from './pages/LoginCliente';
import LoginHotel from './pages/LoginHotel';
import PerfilHotel from './pages/PerfilHotel';
import CadastroQuarto from './pages/CadastroQuarto';
import PerfilCliente from './pages/PerfilCliente';
import FeedCliente from './pages/FeedCliente';
import AvaliarHotel from './pages/AvaliarHotel';
import ShowAvaliacoes from './pages/ShowAvaliacoes';
import ShowAvaliacoesHotel from './pages/ShowAvaliacoesHotel';

export default function Routes(){
    return (
        <BrowserRouter>
            <Switch>
                <Route path='/' exact component={BemVindo} />
                <Route path='/cadastroCliente' exact component={CadastroCliente} />
                <Route path='/cadastroHotel' exact component={CadastroHotel} />
                <Route path='/loginCliente' exact component={LoginCliente} />
                <Route path='/loginHotel' exact component={LoginHotel} />
                <Route path='/perfilHotel' exact component={PerfilHotel} />
                <Route path='/cadastroQuarto' exact component={CadastroQuarto} />
                <Route path='/perfilCliente' exact component={PerfilCliente}/>
                <Route path='/feedCliente' exact component={FeedCliente}/>
                <Route path='/avaliarHotel' exact component={AvaliarHotel}/>
                <Route path='/showAvaliacoes' exact component={ShowAvaliacoes}/>
                <Route path='/showAvaliacoesHotel' exact component={ShowAvaliacoesHotel}/>
            </Switch>
        </BrowserRouter>
    );
}