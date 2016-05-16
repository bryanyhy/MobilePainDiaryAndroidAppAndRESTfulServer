/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var request = null;
var rooturl = "http://localhost:8080/PainDiaryDB/webresources/restclient.doctor"; 
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

function getAllDoctors()
{
    document.getElementById('docId').value = "";
    document.getElementById('docFirstname').value = ""; 
    document.getElementById('docSurname').value = "";
    document.getElementById('docPhone').value = "";
    document.getElementById('clinicAddress').value = ""; 
    document.getElementById('clinicPhone').value = "";
    
    createRequest();
    request.onreadystatechange = handleListJSONResponse; 
    request.open("GET", rooturl, true); 
    request.send(null);
}

function getDoctor()
{
    document.getElementById("output").innerHTML = ""; 
    document.getElementById('docFirstname').value = ""; 
    document.getElementById('docSurname').value = "";
    document.getElementById('docPhone').value = "";
    document.getElementById('clinicAddress').value = ""; 
    document.getElementById('clinicPhone').value = "";
    
    createRequest();
    var doctorId = document.getElementById("docId").value; 
    docId = eval(doctorId);
    var url = rooturl + "/" + docId; 
    request.onreadystatechange = handleJSONResponse; 
    request.open("GET", url, true);
    request.send(null);
}

function addDoctor()
{
    document.getElementById("output").innerHTML = ""; 
    var docId = document.getElementById('docId').value;
    var docFirstname = document.getElementById('docFirstname').value;
    var docSurname = document.getElementById('docSurname').value;
    var docPhone = document.getElementById('docPhone').value;
    var clinicAddress = document.getElementById('clinicAddress').value;
    var clinicPhone = document.getElementById('clinicPhone').value;
    
    createRequest();
    var data = JSON.stringify({"docId": docId.toString(), "docFirstname": docFirstname.toString(),"docSurname": docSurname.toString(), "docPhone": docPhone.toString(), "clinicAddress": clinicAddress.toString(),"clinicPhone": clinicPhone.toString()});
    request.open("POST", rooturl); 
    request.setRequestHeader('Content-type', 'application/json'); 
    request.setRequestHeader("Content-Length", data.length); 
    request.setRequestHeader('Accept', 'application/json'); 
    request.send(data);
    document.getElementById('docId').value = "";
    document.getElementById('docFirstname').value = ""; 
    document.getElementById('docSurname').value = "";
    document.getElementById('docPhone').value = "";
    document.getElementById('clinicAddress').value = ""; 
    document.getElementById('clinicPhone').value = "";
}

function updateDoctor()
{
    document.getElementById("output").innerHTML = ""; 
    var docId = document.getElementById('docId').value;
    var docFirstname = document.getElementById('docFirstname').value;
    var docSurname = document.getElementById('docSurname').value;
    var docPhone = document.getElementById('docPhone').value;
    var clinicAddress = document.getElementById('clinicAddress').value;
    var clinicPhone = document.getElementById('clinicPhone').value;
    
    createRequest();
    var data = JSON.stringify({"docId": docId.toString(), "docFirstname": docFirstname.toString(),"docSurname": docSurname.toString(), "docPhone": docPhone.toString(), "clinicAddress": clinicAddress.toString(),"clinicPhone": clinicPhone.toString()});
    var id = document.getElementById("docId").value;
    id = eval(id);
    var url = rooturl + "/" + id;
    request.open("PUT", url); 
    request.setRequestHeader('Content-type', 'application/json'); 
    request.setRequestHeader("Content-Length", data.length); 
    request.setRequestHeader('Accept', 'application/json'); 
    request.send(data);
    document.getElementById('docId').value = "";  
    document.getElementById('docFirstname').value = ""; 
    document.getElementById('docSurname').value = "";
    document.getElementById('docPhone').value = "";
    document.getElementById('clinicAddress').value = ""; 
    document.getElementById('clinicPhone').value = "";
}

function deleteDoctor()
{
    document.getElementById("output").innerHTML = ""; 
    createRequest();
    var id = document.getElementById("docId").value;
    id = eval(id);
    var url = rooturl + "/" + id; 
    request.open("DELETE", url, true); 
    request.send(null); 
    document.getElementById('docId').value = "";  
    document.getElementById('docFirstname').value = ""; 
    document.getElementById('docSurname').value = "";
    document.getElementById('docPhone').value = "";
    document.getElementById('clinicAddress').value = ""; 
    document.getElementById('clinicPhone').value = "";
}

function handleJSONResponse() {
    if (request.readyState == 4 && request.status == 200)
    {
        var det = JSON.parse(request.responseText); 
        document.getElementById('docFirstname').value = det.docFirstname; 
        document.getElementById('docSurname').value = det.docSurname;
        document.getElementById('docPhone').value = det.docPhone;
        document.getElementById('clinicAddress').value = det.clinicAddress; 
        document.getElementById('clinicPhone').value = det.clinicPhone;
    } 
}

function handleListJSONResponse() {
    if (request.readyState == 4 && request.status == 200)
    {
        var det = JSON.parse(request.responseText);
        var output = "<table border = '1'>";
        output += "<tr>" + "<th>DoctorID</th>" + "<th>Doctor Firstname</th>" + "<th>Doctor Surname</th>" + "<th>Doctor Phone</th>" + "<th>Clinic Address</th>" + "<th>Clinic Phone</th>" + "</tr>";
        for (i = 0; i < det.length; i++) {
            output += "<tr>";
            output += "<td>" + det[i].docId + "</td>";
            output += "<td>" + det[i].docFirstname + "</td>";
            output += "<td>" + det[i].docSurname + "</td>";
            output += "<td>" + det[i].docPhone + "</td>";
            output += "<td>" + det[i].clinicAddress + "</td>";
            output += "<td>" + det[i].clinicPhone + "</td>";
            output += "</tr>";
        }
        output += "</table>"; 
        document.getElementById("output").innerHTML = output;
    }
}
