/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var request = null;
var rooturl = "http://localhost:8080/PainDiaryDB/webresources/restclient.patient"; 
var rooturl2 = "http://localhost:8080/PainDiaryDB/webresources/restclient.dailyrecord/q4c";

var dataArray = null;

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

function drawPieChart()
{
    var userId = document.querySelector('input[name = "pat"]:checked').value;
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;
    createRequest();
    request.onreadystatechange = handleRecordJSONResponse; 
    request.open("GET", rooturl2 + "/" + userId + "/" + startDate + "/" + endDate, true); 
    request.send(null);
       
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
      var data = google.visualization.arrayToDataTable(dataArray);
      var options = {
        title: 'Pain Location and its frequency'
      };    
      var chart = new google.visualization.PieChart(document.getElementById('piechart'));
      chart.draw(data, options);
    }
}

function handleRecordJSONResponse() 
{
    if (request.readyState == 4 && request.status == 200)
    {
        var det = JSON.parse(request.responseText);  
        dataArray = new Array();
        dataArray[0] = new Array();
        dataArray[0] = ['Pain Location', 'Frequency'];
        for (i = 0; i < det.length; i++) {
            dataArray[i+1] = new Array();           
            dataArray[i+1].push(det[i].painLocation);
            dataArray[i+1].push(det[i].frequency);
        }
    }
}

