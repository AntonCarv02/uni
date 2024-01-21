
let page = 0;
const last = 9;

function nextpage() {

    page = (page + 1) % (last + 1);
    getFarmacias(page);
};

function prevpage() {

    page = (page - 1) % (last + 1);
    getFarmacias(page);

};
function hideButtons() {
    document.getElementById("ant").style.visibility = "hidden";
    document.getElementById("prox").style.visibility = "hidden";

}

let farmaciaID = null;

function Showmore(farmacia) {
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {
                let data = JSON.parse(this.responseText);
                let vacinaDrop = document.getElementById("tipovac");
                vacinaDrop.innerHTML = ""

                // exibir os detalhes na página
                let detalhesFarmacia = document.getElementById("detalhesFarmacia");
                detalhesFarmacia.style.display = "block"; // Torna visível

                let detalhesNome = document.getElementById("detalhesNome");
                let detalhesEmail = document.getElementById("detalhesEmail");
                let detalhesLocalidade = document.getElementById("detalhesLocalidade");
                let detalhesRua = document.getElementById("detalhesRua");
                let detalhesCodigoPostal = document.getElementById("detalhesCodigoPostal");
                let detalhesTelefone = document.getElementById("detalhesTelefone");
                let detalhesServicos = document.getElementById("detalhesServicos");

                detalhesNome.textContent = `Nome: ${data.farmacia.name}`;
                detalhesEmail.textContent = `Email: ${data.farmacia.email}`;
                detalhesLocalidade.textContent = `Localidade: ${data.farmacia.postal_code_locality}`;
                detalhesRua.textContent = `Rua: ${data.farmacia.street_name}`;
                detalhesCodigoPostal.textContent = `Código Postal: ${data.farmacia.postal_code_zone}-${data.farmacia.postal_code_sufix}`;
                detalhesTelefone.textContent = `Telefone: ${data.farmacia.telephone}`;
                console.log(data.farmacia.services)



                // Limpa e adiciona os serviços
                detalhesServicos.innerHTML = "";
                if (data.farmacia.services && data.farmacia.services.length > 0) {
                    let servicosParagrafo = document.createElement("p");
                    servicosParagrafo.textContent = `Serviços: ${data.farmacia.services.join(", ")}`;
                    detalhesServicos.appendChild(servicosParagrafo);


                }

                if (data.farmacia.services.includes("covid-19")) {
                    let covid = document.createElement("option");
                    covid.setAttribute('value', "covid-19");
                    let optionText = document.createTextNode("Covid-19");
                    covid.appendChild(optionText);
                    vacinaDrop.appendChild(covid)

                }


                if (data.farmacia.services.includes("gripe")) {
                    let gripe = document.createElement("option");
                    gripe.setAttribute('value', "gripe");
                    let optionText = document.createTextNode("Gripe");
                    gripe.appendChild(optionText);
                    vacinaDrop.appendChild(gripe)
                }




            } else {
                console.error("Erro na solicitação. Status: " + this.status);
            }
        }
    };
    farmaciaID = farmacia.entity_id;
    let url = "https://magno.di.uevora.pt/tweb/t1/farmacia/get/" + farmaciaID;


    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send();
}



function getFarmacias(ipage) {
    page = ipage;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let data = JSON.parse(this.responseText);
            let farmacias = data.farmacias;
            let datadiv = document.getElementById("lista");
            datadiv.innerHTML = "";

            if (farmacias.length > 0) {


                farmacias.forEach(function (farmacia) {
                    let farmaciaItem = document.createElement("li");
                    farmaciaItem.textContent = `Nome: ${farmacia.name}, Localidade: ${farmacia.postal_code_locality}`;
                    
                    datadiv.appendChild(farmaciaItem);
                    
                    farmaciaItem.onclick = function () {
                        Showmore(farmacia);
                    };
                });


            } else {
                datadiv.textContent = "Nenhuma farmácia encontrada.";
            }
        }
    };
    ant = document.getElementById("ant");
    prox = document.getElementById("prox");
    if (page > 0) {

        ant.style.visibility = "visible";
    } else {
        ant.style.visibility = "hidden";
    }

    if ((page < 9)) {

        prox.style.visibility = "visible";
    } else {
        prox.style.visibility = "hidden";
    }


    console.log(ipage);
    let url = "https://magno.di.uevora.pt/tweb/t1/farmacia/list";
    xhttp.open("post", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("page=" + ipage);
}

let i = 0;

function foundFarmacyByVaccine(farmacia, vacina) {
    return new Promise((resolve, reject) => {
        let res = 0;

        if (vacina === "all") {
            res = 1;
            resolve(res);
        }

        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4) {
                if (this.status == 200) {
                    let data = JSON.parse(this.responseText);

                    let services = `${data.farmacia.services}`.split(",");
                    //console.log(services);

                    services.forEach(function (s) {
                        if (s === vacina) {
                            res = 1;
                            //console.log(s + res + vacina);
                        }
                    });

                    resolve(res);
                } else {
                    reject(new Error(`Failed to fetch data. Status: ${this.status}`));
                }
            }
        };

        let url = "https://magno.di.uevora.pt/tweb/t1/farmacia/get/" + farmacia.entity_id;

        //console.log((i++) + res + vacina);
        xhttp.open("GET", url, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send();
    });
}


function inserirFarmacia(datadiv, farmacia) {
    let farmaciaItem = document.createElement("li");
    farmaciaItem.id = farmacia.entity_id;
    farmaciaItem.textContent = `Nome: ${farmacia.name}, Localidade: ${farmacia.postal_code_locality}`;

    if ((document.getElementById(farmacia.entity_id) === null)) {
        datadiv.appendChild(farmaciaItem);


        farmaciaItem.onclick = function () {
            Showmore(farmacia);
        };
    }
}



function searchLocality(xhttp, input, url, datadiv, vacina) {
    let i = 0;
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {

                let data = JSON.parse(this.responseText);
                if (data.farmacias && data.farmacias.length > 0) {

                    let farmacias = data.farmacias;


                    farmacias.forEach(function (farmacia) {
                        foundFarmacyByVaccine(farmacia, vacina)
                            .then(result => {

                                if (result) {
                                    inserirFarmacia(datadiv, farmacia);
                                }


                            })
                            .catch(error => {
                                console.error(error); // Handle errors here
                            })
                            ;

                    });


                } else if (datadiv.length === 0) {

                    datadiv.textContent = "Nenhuma farmácia encontrada.";

                }
            } else {
                console.error("Erro na solicitação. Status: " + this.status);
            }
        }
    };
    let r2 = "locality=" + input;


    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send((r2));

}







function searchFarmacias() {
    let xhttp = new XMLHttpRequest();
    let input = document.getElementById("nFarmacia").value;
    let vacina = document.getElementById("tipoVacina").value;

    let url = "https://magno.di.uevora.pt/tweb/t1/farmacia/search";

    // Remova caracteres acentuados temporariament
    //input = removeAccents(input);

    hideButtons()
    
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {

                let data = JSON.parse(this.responseText);
                let datadiv = document.getElementById("lista");
                datadiv.innerHTML = "";
                if (data.farmacias && data.farmacias.length > 0) {
                    let farmacias = data.farmacias;


                    farmacias.forEach(function (farmacia) {

                        foundFarmacyByVaccine(farmacia, vacina)

                            .then(result => {
                                if (result) {
                                    inserirFarmacia(datadiv, farmacia);
                                }
                                


                            })
                            .catch(error => {
                                console.error(error); // Handle errors here
                            });

                    });
                    searchLocality(xhttp, input, url, datadiv, vacina);

                } else {

                    //datadiv.textContent = "Nenhuma farmácia encontrada.";
                    searchLocality(xhttp, input, url, datadiv, vacina);
                }
            } else {
                console.error("Erro na solicitação. Status: " + this.status);
            }
        }
    };

    let requestBody = "name=" + input;



    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send((requestBody));

}

function removeAccents(text) {
    return text.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}



function searchFarmaciasByVaccine() {
    let xhttp = new XMLHttpRequest();
    let vacina = document.getElementById("tipoVacina").value;

    if(vacina === "all"){
        getFarmacias(0);
    }else{

        hideButtons()
    }

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {
                let data = JSON.parse(this.responseText);

                if (data.status === "ok" && data.farmacias && data.farmacias.length > 0) {
                    let farmacias = data.farmacias;
                    let datadiv = document.getElementById("lista");
                    datadiv.innerHTML = "";

                    farmacias.forEach(function (farmacia) {
                        let farmaciaItem = document.createElement("li");
                        farmaciaItem.textContent = `Nome: ${farmacia.name}, Localidade: ${farmacia.postal_code_locality}`;
                        farmaciaItem.onclick = function () {
                            Showmore(farmacia);
                        };
                        datadiv.appendChild(farmaciaItem);
                    });
                } else {
                    let datadiv = document.getElementById("lista");
                    datadiv.textContent = "Nenhuma farmácia encontrada.";
                }
            } else {
                console.error("Erro na solicitação. Status: " + this.status);
            }
        }
    };

    let url = "https://magno.di.uevora.pt/tweb/t1/farmacia/searchvaccine";
    let requestBody = "vaccine=" + vacina;

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(requestBody);
}



function addAgendamento() {
    let xhttp = new XMLHttpRequest();
    let id = document.getElementById("utenteNumber").value;
    let vacina = document.getElementById("tipovac").value;
    let datauser = document.getElementById("datauser").value;

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            datadiv = document.getElementById("response");
            // Agendamento adicionado com sucesso, você pode exibir uma mensagem ou atualizar a lista de agendamentos.
            var response = JSON.parse(this.responseText);
            var mensagem = response.schedule_msg;

            if (response.schedule_code) {
                mensagem += " Código do Agendamento: " + response.schedule_code;
            }
            datadiv.appendChild(document.createTextNode(mensagem));

        } else {
            console.error("Erro na solicitação. Status: " + this.status);
        }
    };



    let url = "https://magno.di.uevora.pt/tweb/t1/schedule/add";
    let requestBody ="user_id=" + id + "&vaccine=" + vacina + "&entity_id=" + farmaciaID + "&schedule_date=" + datauser;

    xhttp.open("post", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send((requestBody));
}



let agendaCode = null;

function listAgendamentos() {
    let xhttp = new XMLHttpRequest();
    let idagenda = document.getElementById("utenteNumber").value;


    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let data = JSON.parse(this.responseText);

            let agendamentos = data.schedule_list;
            let datadiv = document.getElementById("tableBody");
            datadiv.innerHTML = "";

            for (let i = 0; i < agendamentos.length; i++) {
                let agendamentoItem = document.createElement("tr");
                agendamentoItem.code = agendamentos[i][4];
                
                agendamentoItem.innerHTML = `<td>${agendamentos[i][0]}</td><td>${agendamentos[i][1]}</td><td>${agendamentos[i][2]}</td><td>${agendamentos[i][3]}</td><td>${agendamentos[i][4]}</td>`;


                if ((document.getElementById(agendamentoItem.code) === null)) {

                    datadiv.appendChild(agendamentoItem);
                    agendamentoItem.onclick = function () {

                        agendaCode = agendamentos[i][4];
                        let codelabel = document.getElementById("codigoagenda");
                        console.log(agendaCode)
                        codelabel.innerHTML = "";
                        codelabel.value = agendaCode;
                    };
                }
            }

        }
    };
    let user = "user_id=" + idagenda;

    let url = "https://magno.di.uevora.pt/tweb/t1/schedule/list";
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhttp.send((user));
}



function removeAgendamento() {
    
    let xhttp = new XMLHttpRequest();
    idagenda = document.getElementById("utenteNumber").value;

    if(agendaCode === null){
        agendaCode=document.getElementById("codigoagenda").value;
    }

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {
                // Agendamento removido com sucesso
                var response = JSON.parse(this.responseText);
                var mensagem = response.status;


                console.log(mensagem);
                // Atualizar a lista de agendamentos após a remoção
                listAgendamentos();
            } else {
                console.error("Erro na solicitação. Status: " + this.status);
            }
        }
    };

    let url = "https://magno.di.uevora.pt/tweb/t1/schedule/remove";
    let requestBody = "user_id=" + idagenda + "&schedule_code=" + agendaCode;
    console.log(requestBody)
    agendaCode = null;
    xhttp.open("post", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(requestBody);
}



function listProgramGripe() {
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let data = JSON.parse(this.responseText);
            let farmacias = data.farmacias;
            let datadiv = document.getElementById("listagestao");
            datadiv.innerHTML = "";
            document.getElementById('removeid').value = "";
            document.getElementById("removeresponse").innerHTML = "";

            if (farmacias.length > 0) {



                farmacias.forEach(function (farmacia) {
                    let farmaciaItem = document.createElement("li");
                    farmaciaItem.textContent = `Nome: ${farmacia.name}, Localidade: ${farmacia.postal_code_locality}, Id: ${farmacia.entity_id}`;
                    datadiv.appendChild(farmaciaItem);
                    farmaciaItem.onclick = function () {
                        
                        let id =farmacia.entity_id;
                        let codelabel = document.getElementById("removeid");
                        console.log(id)
                        codelabel.innerHTML = "";
                        codelabel.value = id;
                    };
                });

                countFarmaciasPorLocalidade(farmacias);
            } else {
                datadiv.textContent = "Nenhuma farmácia de gripe encontrada.";
            }
        }
    };

    let url = "https://magno.di.uevora.pt/tweb/t1/program/gripe/list";
    xhttp.open("GET", url, true);
    xhttp.send();
}




function listProgramCOVID19() {
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let data = JSON.parse(this.responseText);
            let farmacias = data.farmacias;
            let datadiv = document.getElementById("listagestao");
            datadiv.innerHTML = "";
            document.getElementById('removeid').value = "";
            document.getElementById("removeresponse").innerHTML = "";

            if (farmacias.length > 0) {


                farmacias.forEach(function (farmacia) {
                    let farmaciaItem = document.createElement("li");
                    farmaciaItem.textContent = `Nome: ${farmacia.name}, Localidade: ${farmacia.postal_code_locality}, Id: ${farmacia.entity_id}`;
                    datadiv.appendChild(farmaciaItem);
                    farmaciaItem.onclick = function () {

                        let id =farmacia.entity_id;
                        let codelabel = document.getElementById("removeid");
                        console.log(id)
                        codelabel.innerHTML = "";
                        codelabel.value = id;
                    };
                });

                countFarmaciasPorLocalidade(farmacias);


            } else {
                datadiv.textContent = "Nenhuma farmácia de COVID-19 encontrada.";
            }
        }
    };

    let url = "https://magno.di.uevora.pt/tweb/t1/program/covid19/list";
    xhttp.open("GET", url, true);
    xhttp.send();
}


function addFarmacia() {
    let xhttp = new XMLHttpRequest();
    let servicos = null;
    let id = document.getElementById("farmid").value;
    let nome = document.getElementById("farmname").value;

    if (document.getElementById("covid").checked && document.getElementById("gripe").checked) {
        servicos = "covid-19,gripe";
    } else if (document.getElementById("gripe").checked) {
        servicos = "gripe";
    } else if (document.getElementById("covid").checked) {
        servicos = "covid-19";
    }


    console.log(servicos)
    console.log(id)
    console.log(nome)

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Programa de vacinação adicionado com sucesso, você pode exibir uma mensagem ou atualizar a lista de programas.
            if (JSON.parse(this.responseText).status === "ok") {

                document.getElementById("covid").checked = false;
                document.getElementById("gripe").checked = false;
                document.getElementById("farmid").value = ""
                document.getElementById("farmname").value = ""

                document.getElementById("removeresponse").innerHTML = "";
                window.alert("Sucesso")
            }
            else{
                document.getElementById("removeresponse").innerHTML = JSON.parse(this.responseText).status;
            }

        }
    };

    let url = "https://magno.di.uevora.pt/tweb/t1/program/add";
    let requestBody = "name=" + nome + "&services=" + servicos + "&entity_id=" + id;

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send((requestBody));
}



function removeFarmacia() {
    let xhttp = new XMLHttpRequest();
    let id = document.getElementById("removeid").value;

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Programa de vacinação removido com sucesso

            if(JSON.parse(this.responseText ).status=== "ok"){
                document.getElementById("removeresponse").innerHTML = ("Removido com sucesso!");
                console.log("ok")
            }
        }
    };

    let url = "https://magno.di.uevora.pt/tweb/t1/program/remove";
    let requestBody = "entity_id=" + id;

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send((requestBody));
}


function countFarmaciasPorLocalidade(farmacias) {

    let localidadeCount = {};


    farmacias.forEach(function (farmacia) {
        let localidade = farmacia.postal_code_locality;


        if (localidadeCount[localidade]) {
            localidadeCount[localidade]++;
        } else {
            localidadeCount[localidade] = 1;
        }
    });


    console.log(localidadeCount);

    // Criar o gráfico
    createBarChart(localidadeCount);

}

let myChart = null;

function createBarChart(localidadeCount) {
    let localidades = Object.keys(localidadeCount);
    let counts = Object.values(localidadeCount);
    console.log(localidades);
    console.log(counts)

    let backgroundColors = counts.map(() => "rgba(144, 238, 144, 0.5)");
    let borderColors = counts.map(() => "rgba(60, 179, 113, 1)");

    let ctx = document.getElementById("barChart").getContext("2d");

    if (myChart) {
        myChart.destroy();
    }

    myChart = new Chart(ctx, {
        type: "bar",
        data: {
            labels: localidades,
            datasets: [{
                data: counts,
                label: "Número de Farmácias por Localidade",
                backgroundColor: backgroundColors,
                borderColor: borderColors,
                borderWidth: 0.5
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    display: false
                }
            }
        }
    });

}
