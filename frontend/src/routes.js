import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import BemVindo from './pages/BemVindo/';
import CadastroCliente from './pages/CadastroCliente';
import CadastroAgencia from './pages/CadastroAgencia';
import LoginCliente from './pages/LoginCliente';
import LoginAgencia from './pages/LoginAgencia';
import PerfilAgencia from './pages/PerfilAgencia';
import CadastroViagem from './pages/CadastroViagem';

export default function Routes(){
    return (
        <BrowserRouter>
            <Switch>
                <Route path='/' exact component={BemVindo} />
                <Route path='/cadastroCliente' exact component={CadastroCliente} />
                <Route path='/cadastroAgencia' exact component={CadastroAgencia} />
                <Route path='/loginCliente' exact component={LoginCliente} />
                <Route path='/loginAgencia' exact component={LoginAgencia} />
                <Route path='/perfilAgencia' exact component={PerfilAgencia} />
                <Route path='/cadastroViagem' exact component={CadastroViagem} />
            </Switch>
        </BrowserRouter>
    );
}