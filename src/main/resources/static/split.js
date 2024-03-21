function onMapClick(e) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/geo", false);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify({'lat': e.latlng.lat, 'lon': e.latlng.lng}));
    xhr = new XMLHttpRequest();
    xhr.overrideMimeType('application/json');
    xhr.open("GET", "http://localhost:8080/geo", false);
    xhr.send(null);
    console.log(JSON.parse(xhr.responseText));
    L.polygon(JSON.parse(xhr.responseText), {
        fillOpacity: 0,
        weight: 1,
        opacity: 1,
        color: 'red'
    }).addTo(map);
}

