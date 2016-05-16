/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * ["2016-3-18","2016-3-19","2016-3-20","2016-3-21", "2016-3-22"]
 */

var request = null;
var rooturl = "http://localhost:8080/PainDiaryDB/webresources/restclient.patient"; 
var rooturl2 = "http://localhost:8080/PainDiaryDB/webresources/restclient.dailyrecord/q4b";

var painLevelArray = new Array();
var weatherArray = new Array();
var dateArray = new Array();

function createRequest() {
    try {
        request = new XMLHttpRequest();
    } catch (trymicrosoft) {
        try {
            request = new ActiveXObject("MsXML2.XMLHTTP"); 
        } catch (othermicrosoft) {
            try {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (failed) {
                request = null;
            }
        } 
    }
    if (request == null)
        alert("Error creating request object!");
}

function getPatientName()
{ 
    createRequest();
    request.onreadystatechange = handlePatientNameJSONResponse; 
    request.open("GET", rooturl, true); 
    request.send(null);
}

function handlePatientNameJSONResponse() 
{
    if (request.readyState == 4 && request.status == 200)
    {
        var det = JSON.parse(request.responseText);
        var output = "<table>";
        output += "<tr> Select a Patient: </tr>";
        for (i = 0; i < det.length; i++) {
            output += "<tr>";
            output += "<td><input type=\"radio\" name=\"pat\" value=\"" + (i+1) + "\"></td>";
            output += "<td>" + det[i].userFirstname;
            output += "<td>" + det[i].userSurname;
            output += "</tr>";
        }
        output += "</table>"; 
        document.getElementById("output").innerHTML = output;
    }
}

function drawLineGraph()
{
    var userId = document.querySelector('input[name = "pat"]:checked').value;
    var weather = document.getElementById("weather").value;
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;
    
    createRequest();
    request.onreadystatechange = handleRecordJSONResponse; 
    request.open("GET", rooturl2 + "/" + userId + "/" + startDate + "/" + endDate + "/" + weather, true); 
    request.send(null);
    
    var trace1 = {
        x: dateArray, 
        y: painLevelArray, 
        name: 'painLevel', 
        type: 'scatter'
    };

    var trace2 = {
        x: dateArray, 
        y: weatherArray, 
        name: weather, 
        yaxis: 'y2', 
        type: 'scatter'
    };

    var data = [trace1, trace2];

    var layout = {
      title: 'Line Graph', 
      yaxis: {title: 'pain level'}, 
      yaxis2: {
        title: weather, 
        titlefont: {color: 'rgb(148, 103, 189)'}, 
        tickfont: {color: 'rgb(148, 103, 189)'}, 
        overlaying: 'y', 
        side: 'right'
      }
    };    
    Plotly.newPlot('plot', data, layout);
}

function handleRecordJSONResponse() 
{
    if (request.readyState == 4 && request.status == 200)
    {
        dateArray = new Array();
        painLevelArray = new Array();
        weatherArray = new Array();
        var det = JSON.parse(request.responseText);       
        for (i = 0; i < det.length; i++) {
            dateArray.push(det[i].recordDate);
            painLevelArray.push(det[i].painLevel);
            weatherArray.push(det[i].attribute);
        }
    }
}
