
let page =0;
const last = 9;

function nextpage(page){

    page = (page + 1) % (last+1);
    return page;

};
function getFarmacias() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var data = JSON.parse(this.responseText);
            var farmacias = data.farmacias;
            var datadiv = document.getElementById("farmacias");
            datadiv.innerHTML = "";

            if (farmacias.length > 0) {
                var farmaciasList = document.getElementById("lista");

                farmacias.forEach(function (farmacia) {
                    var farmaciaItem = document.createElement("li");
                    farmaciaItem.textContent = `Nome: ${farmacia.name}, Localidade: ${farmacia.postal_code_locality}`;
                    farmaciasList.appendChild(farmaciaItem);
                });

                datadiv.appendChild(farmaciasList);
            } else {
                datadiv.textContent = "Nenhuma farmácia encontrada.";
            }
        }
    };
    page = nextpage(page);

    console.log(page);
    var url = "https://magno.di.uevora.pt/tweb/t1/farmacia/list";
    xhttp.open("post", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("page="+page);
}

function searchFarmacias() {
    var xhttp = new XMLHttpRequest();
    var nomeFarmacia = document.getElementById("nFarmacia").value;

    // Remova caracteres acentuados temporariamente
    nomeFarmacia = removeAccents(nomeFarmacia);

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {
                var data = JSON.parse(this.responseText);

                if (data.farmacias && data.farmacias.length > 0) {
                    var farmacias = data.farmacias;
                    var datadiv = document.getElementById("lista");
                    datadiv.innerHTML = "";

                    farmacias.forEach(function (farmacia) {
                        var farmaciaI = document.createElement("li");
                        farmaciaI.textContent = `Nome: ${farmacia.name}, Localidade: ${farmacia.postal_code_locality}`;
                        datadiv.appendChild(farmaciaI);
                    });
                } else {
                    var datadiv = document.getElementById("procura");
                    datadiv.textContent = "Nenhuma farmácia encontrada.";
                }
            } else {
                console.error("Erro na solicitação. Status: " + this.status);
            }
        }
    };

    var url = "https://magno.di.uevora.pt/tweb/t1/farmacia/search";
    var requestBody = "name=" + nomeFarmacia;

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(requestBody);

}

// Função para remover caracteres acentuados
function removeAccents(text) {
    return text.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

function searchFarmaciasByGripe() {
    searchFarmaciasByVaccine("gripe");
}

function searchFarmaciasByCOVID19() {
    searchFarmaciasByVaccine("covid19");
}

function searchFarmaciasByGripe() {
    searchFarmaciasByVaccine("gripe");
}

function searchFarmaciasByCovid19() {
    searchFarmaciasByVaccine("covid19");
}

function searchFarmaciasByVaccine() {
    var xhttp = new XMLHttpRequest();
    var vacina = document.getElementById("tipoVacina").value;

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            if (this.status == 200) {
                var data = JSON.parse(this.responseText);

                if (data.status === "ok" && data.farmacias && data.farmacias.length > 0) {
                    var farmacias = data.farmacias;
                    var datadiv = document.getElementById("vacina");
                    datadiv.innerHTML = "";

                    farmacias.forEach(function (farmacia) {
                        var farmaciaItem = document.createElement("p");
                        farmaciaItem.textContent = `Nome: ${farmacia.name}, Localidade: ${farmacia.postal_code_locality}`;
                        datadiv.appendChild(farmaciaItem);
                    });
                } else {
                    var datadiv = document.getElementById("vacina");
                    datadiv.textContent = "Nenhuma farmácia encontrada.";
                }
            } else {
                console.error("Erro na solicitação. Status: " + this.status);
            }
        }
    };

    var url = "https://magno.di.uevora.pt/tweb/t1/farmacia/searchvaccine";
    var requestBody = "vaccine=" + vacina;

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(requestBody);
}









function addAgendamento(farmaciaId, data, hora) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Agendamento adicionado com sucesso, você pode exibir uma mensagem ou atualizar a lista de agendamentos.
        }
    };

    var url = "https://magno.di.uevora.pt/tweb/t1/schedule/add";
    var requestBody = {
        farmacia_id: farmaciaId,
        data: data,
        hora: hora
    };

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(JSON.stringify(requestBody));
}

function listAgendamentos() {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var data = JSON.parse(this.responseText);
            var agendamentos = data.agendamentos;
            var datadiv = document.getElementById("agendamentos");
            datadiv.innerHTML = "";

            agendamentos.forEach(function (agendamento) {
                var agendamentoItem = document.createElement("p");
                agendamentoItem.textContent = `Farmácia ID: ${agendamento.farmacia_id}, Data: ${agendamento.data}, Hora: ${agendamento.hora}`;
                datadiv.appendChild(agendamentoItem);
            });
        }
    };

    var url = "https://magno.di.uevora.pt/tweb/t1/schedule/list";
    xhttp.open("GET", url, true);
    xhttp.send();
}

function removeAgendamento(agendamentoId) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Agendamento removido com sucesso, você pode exibir uma mensagem ou atualizar a lista de agendamentos.
        }
    };

    var url = "https://magno.di.uevora.pt/tweb/t1/schedule/remove";
    var requestBody = {
        agendamento_id: agendamentoId
    };

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(JSON.stringify(requestBody));
}


function listProgramGripe() {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var data = JSON.parse(this.responseText);
            var farmacias = data.farmacias;
            var datadiv = document.getElementById("programasGripe");
            datadiv.innerHTML = "";

            if (farmacias.length > 0) {
                var farmaciasList = document.createElement("ul");

                farmacias.forEach(function (farmacia) {
                    var farmaciaItem = document.createElement("li");
                    farmaciaItem.textContent = `Nome: ${farmacia.name}, Localidade: ${farmacia.postal_code_locality}`;
                    farmaciasList.appendChild(farmaciaItem);
                });

                datadiv.appendChild(farmaciasList);
            } else {
                datadiv.textContent = "Nenhuma farmácia de gripe encontrada.";
            }
        }
    };

    var url = "https://magno.di.uevora.pt/tweb/t1/program/gripe/list";
    xhttp.open("GET", url, true);
    xhttp.send();
}




function listProgramCOVID19() {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var data = JSON.parse(this.responseText);
            var farmacias = data.farmacias;
            var datadiv = document.getElementById("programasCOVID19");
            datadiv.innerHTML = "";

            if (farmacias.length > 0) {
                var farmaciasList = document.createElement("ul");

                farmacias.forEach(function (farmacia) {
                    var farmaciaItem = document.createElement("li");
                    farmaciaItem.textContent = `Nome: ${farmacia.name}, Localidade: ${farmacia.postal_code_locality}`;
                    farmaciasList.appendChild(farmaciaItem);
                });

                datadiv.appendChild(farmaciasList);
            } else {
                datadiv.textContent = "Nenhuma farmácia de COVID-19 encontrada.";
            }
        }
    };

    var url = "https://magno.di.uevora.pt/tweb/t1/program/covid19/list";
    xhttp.open("GET", url, true);
    xhttp.send();
}


function addProgramaVacinacao(nome, tipo) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Programa de vacinação adicionado com sucesso, você pode exibir uma mensagem ou atualizar a lista de programas.
        }
    };

    var url = "https://magno.di.uevora.pt/tweb/t1/program/add";
    var requestBody = {
        nome: nome,
        tipo: tipo
    };

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(JSON.stringify(requestBody));
}

function removeProgramaVacinacao(programaId) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Programa de vacinação removido com sucesso, você pode exibir uma mensagem ou atualizar a lista de programas.
        }
    };

    var url = "https://magno.di.uevora.pt/tweb/t1/program/remove";
    var requestBody = {
        programa_id: programaId
    };

    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(JSON.stringify(requestBody));
}