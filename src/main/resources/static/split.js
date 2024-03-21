function onMapClick(e) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/geo");
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify({'lat': e.latlng.lat, 'lon': e.latlng.lng}));
    draw();
}

