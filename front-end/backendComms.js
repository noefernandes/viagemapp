function testVal()
{
    let val = "12345678900";
    //alert(form.cpf.value)
    axios.get("http://localhost:8080/clientes", {mode: 'no-cors'})
    .then(response => {
        console.log(response);
        sessionStorage.setItem("clienteNome", response.data.nome);
        for(let i = 0; i < response.data.length; i++)
        {
            console.log(response.data.name)
        }
        
    })
    .catch(error => {
        console.log(error);
    })
    //alert(form.cpf.value)
}

function showClienteData()
{
    document.getElementById("welcome").innerHTML += sessionStorage.getItem("clienteNome");
}